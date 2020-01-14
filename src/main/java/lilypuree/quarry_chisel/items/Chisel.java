package lilypuree.quarry_chisel.items;

import lilypuree.quarry_chisel.Config;
import lilypuree.quarry_chisel.QuarryChisel;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nullable;

public class Chisel extends Item {


    public Chisel() {
        super(new Item.Properties()
                .group(QuarryChisel.setup.itemGroup));
        this.addPropertyOverride(new ResourceLocation("quarry_chisel:chisel_using"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack stack, @Nullable World world, @Nullable LivingEntity entityIn) {
                boolean flag = entityIn != null;
                Entity entity = (Entity) (flag ? entityIn : stack.getItemFrame());
                if (world == null && entity != null) {
                    world = entity.world;
                }
                if (world == null || entity == null) {
                    return 0.0F;
                } else if (Chisel.isItemChisel(((LivingEntity) entity).getHeldItemOffhand())) {
                    if (((LivingEntity) entity).isSwingInProgress) {
                        return 1.0F;
                    }
                }
                return 0.0F;
            }
        });
        setRegistryName("chisel");

    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockState block = world.getBlockState(context.getPos());
        ItemStack stack = context.getPlayer().getHeldItemMainhand();

        if (block.getBlock() == Blocks.STONE && isItemChisel(stack)) {
            if (!world.isRemote()) {
                if (RandomUtils.nextDouble(0.0, 1.0) < Config.CHISEL_SUCCESS_PROBABILITY.get()) {
                    world.destroyBlock(context.getPos(), false);
                    world.setBlockState(context.getPos(), Blocks.SMOOTH_STONE.getDefaultState());
                }
                breakChisel(stack, world);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    public static boolean isItemChisel(ItemStack stack) {
        return stack.getItem() == ModItems.CHISEL;
    }

    public static void breakChisel(ItemStack stack, IWorld worldIn) {
        if (worldIn.isRemote()) return;
        if (!isItemChisel(stack)) return;

        if (RandomUtils.nextDouble(0.0, 1.0) < Config.CHISEL_BREAK_PROBABILITY.get()) {
            stack.setCount(stack.getCount() - 1);
        }
    }
}
