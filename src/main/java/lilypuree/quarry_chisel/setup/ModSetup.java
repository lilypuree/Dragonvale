package lilypuree.quarry_chisel.setup;

import lilypuree.quarry_chisel.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("quarry_chisel") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CHISEL);
        }
    };

    public void init(){
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }
}
