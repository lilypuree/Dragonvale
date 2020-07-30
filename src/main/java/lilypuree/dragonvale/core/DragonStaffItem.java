package lilypuree.dragonvale.core;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.treats.blocks.FarmEnchanterTile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class DragonStaffItem extends Item {
    public DragonStaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if(world.isRemote) return super.onItemUse(context);
        ItemStack stack = context.getItem();
        if(stack.hasTag()){
           TileEntity tileEntity = world.getTileEntity(FarmEnchanterTile.getPosFromFarmEnchantTag(stack.getTag()));
           if(tileEntity instanceof FarmEnchanterTile){
               ((FarmEnchanterTile)tileEntity).tryAddFarmPlots(context.getPos());
           }
            DragonvaleMain.LOGGER.info("tried to add farmlands!");
           return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}
