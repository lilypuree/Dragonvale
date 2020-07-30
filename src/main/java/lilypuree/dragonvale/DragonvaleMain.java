package lilypuree.dragonvale;

import lilypuree.dragonvale.setup.ClientSetup;
import lilypuree.dragonvale.setup.registry.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DragonvaleMain.MODID)
public class DragonvaleMain {
    public static final String MODID = "dragonvale";
    public static final Logger LOGGER = LogManager.getLogger();

    public DragonvaleMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        Registration.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

    }


    /*
     * This is where you do all the manipulation and startup things you need to do for your mod. What is actually done here
     * will be different for every mod depending on what the mod is doing.
     *
     * Here, we will use this to add our structure to all biomes.
     */
    public void setup(final FMLCommonSetupEvent event)
    {

    }




}
