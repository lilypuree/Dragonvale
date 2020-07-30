package lilypuree.dragonvale.setup.data;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.maptags.NetworkMapTagManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = DragonvaleMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DataManagers {

    public static final NetworkMapTagManager mapTagManager = new NetworkMapTagManager();

    @SubscribeEvent
    public static void onServerStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(mapTagManager);
    }
}
