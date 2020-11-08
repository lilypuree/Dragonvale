package lilypuree.dragonvale.dragons.entity;

import lilypuree.dragonvale.setup.registry.DragonvaleEntities;
import lilypuree.dragonvale.setup.registry.Registration;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class FireDragonEntity extends DragonEntityBase {

    public FireDragonEntity(EntityType<FireDragonEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    @Nullable
    @Override
    public DragonEntityBase createChild(DragonEntityBase ageableEntity) {
        return null;
    }


//    @Override
//    public DragonEntityBase makeCopy(MovementMode mode) {
//        FireDragonEntity fireDragon = new FireDragonEntity(this.world, mode);
//
//        CompoundNBT compoundnbt = this.writeWithoutTypeId(new CompoundNBT());
//        compoundnbt.removeUniqueId("UUID");
//        fireDragon.read(compoundnbt);
//
//        return fireDragon;
//    }
}
