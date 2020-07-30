package lilypuree.dragonvale.treats.blocks;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.setup.registry.DragonvaleItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.INameable;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class FarmEnchanterBlock extends ContainerBlock {

    public FarmEnchanterBlock(Block.Properties properties){
        super(properties);
    }
    private static final TranslationTextComponent containerName = new TranslationTextComponent(DragonvaleMain.MODID + ".container.farm_enchanter");


    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new FarmEnchanterTile();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FarmEnchanterTile();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(worldIn.isRemote()){
            return ActionResultType.SUCCESS;
        }else {
            ItemStack hitStack = player.getHeldItem(handIn);
            if (hitStack.getItem() == DragonvaleItems.DRAGON_STAFF.get()){
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if(tileEntity instanceof FarmEnchanterTile){
                    hitStack.setTag(((FarmEnchanterTile)tileEntity).getFarmEnchantTag());
                    DragonvaleMain.LOGGER.info("copied pos!");
                    return ActionResultType.SUCCESS;
                }
            }
            player.openContainer(state.getContainer(worldIn,pos));
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
            return new SimpleNamedContainerProvider((id, playerInv, player) -> {
                return new FarmEnchanterContainer(id, playerInv, IWorldPosCallable.of(worldIn, pos));
            }, containerName);

    }
}
