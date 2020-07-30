package lilypuree.dragonvale.treats.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnchantableCrop {
    int getGrowthSpeed();

    boolean isMaxAge(BlockState state);

    BlockState growEnchanted(World worldIn, BlockPos pos, BlockState state);
}
