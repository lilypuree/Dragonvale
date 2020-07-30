package lilypuree.dragonvale.treats.blocks.crops;

import lilypuree.dragonvale.treats.blocks.IEnchantableCrop;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

public class DVCropBlock extends CropsBlock implements IEnchantableCrop {
    private int growthSpeed;
    public DVCropBlock(Properties builder, int growthSpeed) {
        super(builder);
        this.growthSpeed = growthSpeed;
        this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), Integer.valueOf(0)));
    }

    @Override
    public BlockState growEnchanted(World worldIn, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + 1;
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        BlockState newState = this.withAge(i);
        worldIn.setBlockState(pos, newState , 2);
        return newState;
    }


    @Override
    public int getGrowthSpeed() {
        return growthSpeed;
    }

}
