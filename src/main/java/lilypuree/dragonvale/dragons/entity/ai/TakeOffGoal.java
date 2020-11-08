package lilypuree.dragonvale.dragons.entity.ai;

import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import lilypuree.dragonvale.dragons.entity.MovementMode;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

import java.util.EnumSet;

public class TakeOffGoal extends Goal {

    private final DragonEntityBase dragonEntity;
    protected int executionChance;

    public TakeOffGoal(DragonEntityBase dragonEntity, int chance) {
        this.dragonEntity = dragonEntity;
        this.executionChance = chance;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.dragonEntity.getIdleTime() >= 100) {
            return false;
        }

        if (this.dragonEntity.getRNG().nextInt(this.executionChance) != 0) {
            return false;
        }
        return !dragonEntity.isFlying();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }


    @Override
    public void startExecuting() {
        dragonEntity.setFlying(true);

        dragonEntity.flightGoal.makeUpdate();
    }

}
