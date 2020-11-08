package lilypuree.dragonvale.setup.registry;

import lilypuree.dragonvale.core.MockPigEntity;
import lilypuree.dragonvale.dragons.entity.FireDragonEntity;
import lilypuree.dragonvale.treats.blocks.EnchantedFarmlandBlock;
import lilypuree.dragonvale.treats.blocks.FarmEnchanterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lilypuree.dragonvale.DragonvaleMain.MODID;

public class DragonvaleEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);


    public static final RegistryObject<EntityType<FireDragonEntity>> FIRE_DRAGON = ENTITIES.register("fire_dragon", () -> EntityType.Builder.<FireDragonEntity>create(FireDragonEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(20)
            .setUpdateInterval(3)
            .immuneToFire()
            .setShouldReceiveVelocityUpdates(true)
            .size(1.0f, 1.0f)
            .build(MODID + ":fire_dragon"));

    public static final RegistryObject<EntityType<MockPigEntity>> MOCK_PIG = ENTITIES.register("pig", () -> EntityType.Builder.create(MockPigEntity::new, EntityClassification.CREATURE).size(0.9f, 0.9f).build(MODID + ":pig"));
}
