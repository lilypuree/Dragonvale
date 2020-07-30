package lilypuree.dragonvale.setup.registry;

import lilypuree.dragonvale.setup.registry.Registration;
import lilypuree.dragonvale.treats.blocks.EnchantedFarmlandBlock;
import lilypuree.dragonvale.treats.blocks.FarmEnchanterBlock;
import lilypuree.dragonvale.treats.blocks.FarmEnchanterTile;
import lilypuree.dragonvale.treats.blocks.crops.DVCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lilypuree.dragonvale.DragonvaleMain.MODID;

public class DragonvaleBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);


    public static RegistryObject<Block> ENCHANTED_FARMLAND = Registration.registerBlock("enchanted_farmland", () -> new EnchantedFarmlandBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static RegistryObject<Block> FARM_ENCHANTER = Registration.registerBlock("farm_enchanter", () -> new FarmEnchanterBlock(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(5.0F, 1200.0F)));
    public static RegistryObject<Block> DRAGON_SNAPS = BLOCKS.register("dragon_snaps", () -> new DVCropBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0,0).sound(SoundType.CROP), 10));

    public static RegistryObject<TileEntityType<FarmEnchanterTile>> FARM_ENCHANTER_TILE = TILE_ENTITIES.register("farm_enchanter", () -> TileEntityType.Builder.create(FarmEnchanterTile::new, FARM_ENCHANTER.get()).build(null));
}
