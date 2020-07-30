package lilypuree.dragonvale.setup.registry;

import lilypuree.dragonvale.core.DragonStaffItem;
import lilypuree.dragonvale.treats.items.TreatItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static lilypuree.dragonvale.DragonvaleMain.MODID;
import static lilypuree.dragonvale.setup.registry.Registration.ITEM_GROUP;

public class DragonvaleItems {


    private static final Item.Properties genericProperty = new Item.Properties().group(ITEM_GROUP);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static RegistryObject<Item> DRAGON_STAFF = ITEMS.register("dragon_staff", () -> new DragonStaffItem(new Item.Properties().maxStackSize(1).group(ITEM_GROUP)));

    public static RegistryObject<Item> DRAGON_SNAP_SEEDS = ITEMS.register("dragon_snap_seeds", () -> new BlockNamedItem(DragonvaleBlocks.DRAGON_SNAPS.get(), genericProperty));
    public static RegistryObject<Item> DRAGON_SNAP = ITEMS.register("dragon_snap", () -> new Item(genericProperty));

    public static List<RegistryObject<Item>> TREATS;

    static {
        TREATS = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            TREATS.add(ITEMS.register("treat_" + i, () -> new TreatItem(new Item.Properties().maxStackSize(16).group(ITEM_GROUP), finalI)));
        }
    }
}
