package lilypuree.dragonvale.dragons.entity.ai;

import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LandGoal extends Goal{

    private final DragonEntityBase dragonEntity;
    protected int executionChance;
    protected double x;
    protected double y;
    protected double z;

    public LandGoal(DragonEntityBase dragonEntity, int chance) {
        this.dragonEntity = dragonEntity;
        this.executionChance = chance;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.dragonEntity.getIdleTime() >= 100) {
            return false;
        }

        if (this.dragonEntity.getRNG().nextInt(this.executionChance) != 0) {
            return false;
        }
        return dragonEntity.onGround;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void resetTask() {
        super.resetTask();
    }
}
