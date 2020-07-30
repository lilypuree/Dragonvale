package lilypuree.dragonvale.treats.blocks;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.core.DVBlockProperties;
import lilypuree.dragonvale.setup.registry.DragonvaleBlocks;
import net.minecraft.block.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;

import java.util.*;

public class FarmEnchanterTile extends TileEntity implements ITickableTileEntity {

    private List<FarmlandData> farmlands;
    private int maxPlots = 25;
    private static final float GROWTH_VARIANCE = 5;
    private static final int GROWTH_INTERVAL = 20;

    private int counter;

    public FarmEnchanterTile() {
        super(DragonvaleBlocks.FARM_ENCHANTER_TILE.get());
        farmlands = new ArrayList<>();
    }

    @Override
    public void tick() {

        if (world == null || world.isRemote) {
            return;
        }

        if (counter > 0) {
            counter--;
        }
        if (counter <= 0) {
            updatePlots();
            counter = GROWTH_INTERVAL;
        }
    }

    private void updatePlots() {

        for (int i = farmlands.size() - 1; i >= 0; i--) {
            FarmlandData farmland = farmlands.get(i);
            if (world.isBlockLoaded(farmland.pos)) {
                BlockState farmlandBlock = world.getBlockState(farmland.pos);
                boolean noLongerEnchanted = farmlandBlock.getBlock() != DragonvaleBlocks.ENCHANTED_FARMLAND.get();
                if (noLongerEnchanted) {
                    farmlands.remove(farmland);
                } else if (!farmlandBlock.get(DVBlockProperties.MAGICAL) && world.isAirBlock(farmland.pos.up())) {
                    farmland.growthTicks = 0;
                    world.setBlockState(farmland.pos, farmlandBlock.with(DVBlockProperties.MAGICAL, true));
                } else {
                    growFarmPlot(farmland);
                }
            }
        }
        markDirty();
    }

    private void growFarmPlot(FarmlandData farmland) {
        BlockPos cropPos = farmland.pos.up();
        BlockState crop = world.getBlockState(cropPos);
        Block cropBlock = crop.getBlock();
        if (cropBlock instanceof IEnchantableCrop) {
            IEnchantableCrop enchantableCrop = (IEnchantableCrop) cropBlock;
            if (!enchantableCrop.isMaxAge(crop)) {
                float randomFactor = 1.0f + (float) world.rand.nextGaussian() * 0.01f * GROWTH_VARIANCE;
                farmland.growthTicks += enchantableCrop.getGrowthSpeed() * GROWTH_INTERVAL * randomFactor;
                if (farmland.growthTicks > 1000) {
                    BlockState newState = ((IEnchantableCrop) cropBlock).growEnchanted(world, cropPos, crop);
                    if(enchantableCrop.isMaxAge(newState)){
                        world.setBlockState(farmland.pos, DragonvaleBlocks.ENCHANTED_FARMLAND.get().getDefaultState().with(DVBlockProperties.MAGICAL, false));
                    }
                    farmland.growthTicks = 0;
                }
            }
        }
    }

    public CompoundNBT getFarmEnchantTag() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putLong("pos", pos.toLong());
        return nbt;
    }

    public static BlockPos getPosFromFarmEnchantTag(CompoundNBT compound) {
        return BlockPos.fromLong(compound.getLong("pos"));
    }

    public void tryAddFarmPlots(BlockPos startPos) {
        Set<Long> visited = new HashSet<>();
        LinkedList<BlockPos> queue = new LinkedList<>();

        visited.add(startPos.toLong());
        queue.add(startPos);
        while (queue.size() != 0 && farmlands.size() < maxPlots && visited.size() < 121) {
            BlockPos currentPos = queue.poll();
            visited.add(currentPos.toLong());
            if (getDistanceSq(currentPos.getX(), currentPos.getY(), currentPos.getZ()) > 121) continue;
            boolean farmEnchanted = addFarmPlot(currentPos);
            if (farmEnchanted) {
                Direction.Plane.HORIZONTAL.iterator().forEachRemaining(dir -> {
                    BlockPos newPos = currentPos.offset(dir);
                    if (!visited.contains(newPos.toLong())) {
                        queue.add(newPos);
                    }
                });
            } else {
                BlockPos downPos = currentPos.down();
                if (world.isAirBlock(currentPos) && !visited.contains(downPos.toLong())) {
                    queue.add(downPos);
                } else {
                    BlockPos upPos = currentPos.up();
                    if (!world.isAirBlock(upPos) && world.isAirBlock(upPos.up()) && !visited.contains(upPos.toLong())) {
                        queue.add(upPos);
                    }
                }
            }
        }
        markDirty();
    }

    private boolean addFarmPlot(BlockPos pos) {
        if (!world.isBlockLoaded(pos)) return false;

        BlockState farmBlock = world.getBlockState(pos);
        if (!isPlotEnchantable(pos, farmBlock)) {  //this will not invalidate existing farmlands, but we can do that on growth ticks. speed!
            return false;
        }

        FarmlandData[] existingPlots = farmlands.stream().filter(farmland -> farmland.pos.equals(pos)).toArray(FarmlandData[]::new);
        if (existingPlots.length > 1) {
            DragonvaleMain.LOGGER.info("duplicate farmBlocks in list! This shouldn't have happened!");
            farmlands.clear();
            return false;
        } else if (existingPlots.length == 1) {
            if (farmBlock.getBlock() == DragonvaleBlocks.ENCHANTED_FARMLAND.get()) {  //There's an existing entry already that hasn't been altered
                BlockState cropBlock = world.getBlockState(pos.up());
                if (!(cropBlock.getBlock() instanceof CropsBlock)) {                  //If the plot is empty, let's reenchant the farmland.
                    existingPlots[0].growthTicks = 0;
                    world.setBlockState(pos, farmBlock.with(DVBlockProperties.MAGICAL, true));
                }
            } else {                                                                 //Somehow the existing plot was changed.
//                if(isPlotEnchantable(pos, farmBlock)){
                world.setBlockState(pos, DragonvaleBlocks.ENCHANTED_FARMLAND.get().getDefaultState().with(DVBlockProperties.MAGICAL, true));
//                }else {
//                    farmlands.remove(existingPlots[0]);
//                }
            }
            return true;
        } else {
            if (farmBlock.getBlock() == Blocks.FARMLAND) {
                world.setBlockState(pos, DragonvaleBlocks.ENCHANTED_FARMLAND.get().getDefaultState().with(DVBlockProperties.MAGICAL, true));
                farmlands.add(FarmlandData.create(pos));
                return true;
            }
            return false;
        }
    }

    private boolean isPlotEnchantable(BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.FARMLAND || block == DragonvaleBlocks.ENCHANTED_FARMLAND.get()) return true;
        return true;
    }

    @Override
    public void remove() {
        for (int i = 0; i < farmlands.size(); i++) {
            world.setBlockState(farmlands.get(i).pos, Blocks.FARMLAND.getDefaultState());
        }
        super.remove();
    }

    @Override
    public void read(CompoundNBT compound) {
        counter = compound.getInt("counter");
        ListNBT farmList = compound.getList("farmlands", 10);
        farmlands = new ArrayList<>();
        farmList.forEach(inbt -> {
            CompoundNBT tag = (CompoundNBT) inbt;
            farmlands.add(FarmlandData.fromNBT(tag));
        });
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("counter", counter);
        ListNBT farmList = new ListNBT();
        farmlands.forEach(farmland -> farmList.add(farmland.toNBT()));
        compound.put("farmlands", farmList);
        return super.write(compound);
    }

    public static class FarmlandData {
        public BlockPos pos;
        public int growthTicks;

        public FarmlandData(BlockPos pos, int growthTicks) {
            this.pos = pos;
            this.growthTicks = growthTicks;
        }

        public static FarmlandData create(BlockPos pos) {
            return new FarmlandData(pos, 0);
        }

        public CompoundNBT toNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putLong("pos", pos.toLong());
            nbt.putInt("growth", growthTicks);
            return nbt;
        }

        public static FarmlandData fromNBT(CompoundNBT nbt) {
            BlockPos pos = BlockPos.fromLong(nbt.getLong("pos"));
            int growthTicks = nbt.getInt("growth");
            return new FarmlandData(pos, growthTicks);
        }
    }
}
