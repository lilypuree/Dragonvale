package lilypuree.dragonvale.setup.datagen;

import lilypuree.dragonvale.DragonvaleMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Languages extends LanguageProvider {

    public Languages(DataGenerator gen, String locale) {
        super(gen, DragonvaleMain.MODID, locale);
    }

    @Override
    protected void addTranslations() {

    }
}
