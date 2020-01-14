package lilypuree.quarry_chisel.setup;

import lilypuree.quarry_chisel.QuarryChisel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ForgeEventHandlers {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLootLoad(LootTableLoadEvent event) {
//        if (event.getName().equals(new ResourceLocation("minecraft", "blocks/stone"))) {
//            event.getTable().removePool("main");
//            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation("minecraft", "blocks/stone"))).build());
//        }
    }

}
