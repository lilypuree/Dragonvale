package lilypuree.dragonvale.dragons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FireDragonEntity extends DragonEntityBase{

    public FireDragonEntity(EntityType<FireDragonEntity> typeIn, World worldIn){
        super(typeIn, worldIn);
    }

    @Nullable
    @Override
    public DragonEntityBase createChild(DragonEntityBase ageableEntity) {
        return null;
    }
}
