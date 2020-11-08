package lilypuree.dragonvale.setup;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.core.MockPigRenderer;
import lilypuree.dragonvale.dragons.entity.client.FireDragonRenderer;
import lilypuree.dragonvale.setup.registry.DragonvaleBlocks;
import lilypuree.dragonvale.setup.registry.DragonvaleEntities;
import lilypuree.dragonvale.setup.registry.DragonvaleItems;
import lilypuree.dragonvale.setup.registry.Registration;
import lilypuree.dragonvale.treats.client.FarmEnchanterScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DragonvaleMain.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @OnlyIn(Dist.CLIENT)
    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(DragonvaleEntities.FIRE_DRAGON.get(), FireDragonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DragonvaleEntities.MOCK_PIG.get(), MockPigRenderer::new);
        RenderTypeLookup.setRenderLayer(DragonvaleBlocks.DRAGON_SNAPS.get(), RenderType.getCutoutMipped());
        ScreenManager.registerFactory(Registration.FARM_ENCHANTER_CONTAINER.get(), FarmEnchanterScreen::new);
    }

    @SubscribeEvent
    public static void onItemColor(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0x21bc31;
        }, DragonvaleItems.PLANT_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0xff210f;
        }, DragonvaleItems.FIRE_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0xcccac7;
        }, DragonvaleItems.EARTH_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0x20fffd;
        }, DragonvaleItems.LIGHTNING_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0x37a6ff;
        }, DragonvaleItems.WATER_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0x56e7d1;
        }, DragonvaleItems.AIR_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0xd1893d;
        }, DragonvaleItems.METAL_DRAGON_EGG.get());
        event.getItemColors().register((stack, i) -> {
            return i == 0 ? 0xfdedfd : 0x22fecd;
        }, DragonvaleItems.DARK_DRAGON_EGG.get());

    }
}
