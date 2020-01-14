package lilypuree.quarry_chisel;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.DoubleValue  CHISEL_BREAK_PROBABILITY;
    public static ForgeConfigSpec.DoubleValue  CHISEL_SUCCESS_PROBABILITY;

    static {
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        CHISEL_BREAK_PROBABILITY = COMMON_BUILDER.comment("Probability a chisel will break while using.")
                .defineInRange("break", 0.05, 0,1);

        CHISEL_SUCCESS_PROBABILITY = COMMON_BUILDER.comment("Probability to successfully chisel stone.")
                .defineInRange("success", 0.6, 0, 1);

        COMMON_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
    }


    public static void loadConfig(ForgeConfigSpec spec, Path path){

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent){

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent){

    }
}
