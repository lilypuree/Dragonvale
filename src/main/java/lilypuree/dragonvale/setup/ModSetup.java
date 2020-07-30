package lilypuree.dragonvale.setup;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.setup.ForgeEventHandlers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup(DragonvaleMain.MODID) {
        @Override
        public ItemStack createIcon() {
            return ItemStack.EMPTY;
        }
    };

    public void init(){
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }
}
