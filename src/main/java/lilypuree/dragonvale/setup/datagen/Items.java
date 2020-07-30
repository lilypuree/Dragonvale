package lilypuree.dragonvale.setup.datagen;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.setup.registry.DragonvaleItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.RegistryObject;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        for (RegistryObject<Item> registryObject : DragonvaleItems.TREATS) {
            singleTexture(registryObject.getId().getPath(), mcLoc("item/generated"), "layer0", new ResourceLocation(DragonvaleMain.MODID, "item/treat_1"));
        }
    }


    @Override
    public String getName() {
        return "Dragonvale Block Item Models";
    }
}
