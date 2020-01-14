package lilypuree.quarry_chisel.items;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber()
public class ChiselEventHandler {

    @SubscribeEvent
    public static void onChiselOffhandUse(BlockEvent.BreakEvent event) {

        if (event.isCanceled()) return;

        ItemStack offHandItem = event.getPlayer().getHeldItemOffhand();

        if (Chisel.isItemChisel(offHandItem)) {
            if (event.getState().getBlock().equals(Blocks.STONE)) {
                Chisel.breakChisel(offHandItem, event.getWorld());
            }
        }
    }
}
