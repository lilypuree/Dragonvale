package lilypuree.dragonvale.setup.registry;

import lilypuree.dragonvale.treats.blocks.FarmEnchanterContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static lilypuree.dragonvale.DragonvaleMain.MODID;


public class Registration {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("dragonvale") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DragonvaleItems.DRAGON_STAFF.get());
        }
    };

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DragonvaleBlocks.BLOCKS.register(modEventBus);
        DragonvaleBlocks.TILE_ENTITIES.register(modEventBus);
        DragonvaleItems.ITEMS.register(modEventBus);
        DragonvaleEntities.ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
    }

    public static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        final RegistryObject<Block> b = DragonvaleBlocks.BLOCKS.register(name, blockSupplier);
        DragonvaleItems.ITEMS.register(name, () -> new BlockItem(b.get(), new Item.Properties().group(ITEM_GROUP)));
        return b;
    }

    public static final RegistryObject<ContainerType<FarmEnchanterContainer>> FARM_ENCHANTER_CONTAINER = CONTAINERS.register("farm_echanter", ()-> IForgeContainerType.create(FarmEnchanterContainer::getClientContainer));

}
