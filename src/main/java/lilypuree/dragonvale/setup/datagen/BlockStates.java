package lilypuree.dragonvale.setup.datagen;

import lilypuree.dragonvale.DragonvaleMain;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DragonvaleMain.MODID, exFileHelper);
    }


    private String name(Block block) {
        return block.getRegistryName().getPath();
    }


    @Override
    protected void registerStatesAndModels() {

    }
}
