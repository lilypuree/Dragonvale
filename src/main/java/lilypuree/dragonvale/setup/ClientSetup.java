package lilypuree.dragonvale.setup;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.dragons.entity.client.FireDragonRenderer;
import lilypuree.dragonvale.setup.registry.DragonvaleBlocks;
import lilypuree.dragonvale.setup.registry.DragonvaleEntities;
import lilypuree.dragonvale.setup.registry.Registration;
import lilypuree.dragonvale.treats.client.FarmEnchanterScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DragonvaleMain.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(DragonvaleEntities.FIRE_DRAGON.get(), FireDragonRenderer::new);
        RenderTypeLookup.setRenderLayer(DragonvaleBlocks.DRAGON_SNAPS.get(), RenderType.getCutoutMipped());
        ScreenManager.registerFactory(Registration.FARM_ENCHANTER_CONTAINER.get(), FarmEnchanterScreen::new);
    }

}
