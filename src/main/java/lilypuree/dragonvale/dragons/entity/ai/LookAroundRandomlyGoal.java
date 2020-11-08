package lilypuree.dragonvale.dragons.entity.ai;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.Random;

public class LookAroundRandomlyGoal extends Goal {
    private final MobEntity idleEntity;
    private double lookX;
    private double lookY;
    private double lookZ;
    private int idleTime;
    private float maxHeadYaw;
    private float maxHeadPitch;
    private float minHeadPitch;

    public LookAroundRandomlyGoal(MobEntity entitylivingIn, float maxHeadYaw, float maxHeadPitch, float minHeadPitch) {
        this.idleEntity = entitylivingIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.maxHeadYaw = maxHeadYaw;
        this.maxHeadPitch = maxHeadPitch;
        this.minHeadPitch = minHeadPitch;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        return this.idleEntity.getRNG().nextFloat() < 0.02F;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.idleTime >= 0) {
            return true;
        } else if (this.idleEntity.getRNG().nextFloat() < 0.8f) {
            startExecuting();
            return true;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        Random rand = this.idleEntity.getRNG();
        double bodyYaw = this.idleEntity.renderYawOffset;
        double randomYaw = (bodyYaw + maxHeadYaw * getRandomYawDistribution(rand)) * (Math.PI / 180.0f);
        this.lookZ = Math.cos(randomYaw);
        this.lookX = -Math.sin(randomYaw);

        if (rand.nextFloat() < 0.2f) {
            float randomPitch = (minHeadPitch + rand.nextFloat() * (maxHeadPitch - minHeadPitch)) * ((float) Math.PI / 180.0f);
            this.lookY = Math.sin(randomPitch);
            this.idleTime = 30 + rand.nextInt(20);
        } else {
            this.lookY = 0;
            this.idleTime = 20 + rand.nextInt(20);
        }

    }

    private float getRandomYawDistribution(Random rand) {
        float x = rand.nextFloat() - 0.5f;
        float cdf = 0.5f + 4 * x * x * x;
        return Math.signum(rand.nextFloat() - 0.5f) * cdf;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.idleTime;
        this.idleEntity.getLookController().setLookPosition(this.idleEntity.getPosX() + this.lookX, this.idleEntity.getPosYEye() + this.lookY, this.idleEntity.getPosZ() + this.lookZ);
    }
}
