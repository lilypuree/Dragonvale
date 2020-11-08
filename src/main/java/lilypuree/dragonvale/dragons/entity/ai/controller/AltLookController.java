package lilypuree.dragonvale.dragons.entity.ai.controller;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.util.math.MathHelper.clamp;
import static net.minecraft.util.math.MathHelper.wrapSubtractDegrees;

public class AltLookController extends LookController {

    public AltLookController(MobEntity mob) {
        super(mob);
    }

    public void setLookPosition(Vec3d p_220674_1_) {
        this.setLookPosition(p_220674_1_.x, p_220674_1_.y, p_220674_1_.z);
    }

    /**
     * Sets position to look at using entity
     */
    public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
        this.setLookPosition(entityIn.getPosX(), getEyePosition(entityIn), entityIn.getPosZ(), deltaYaw, deltaPitch);
    }

    public void setLookPosition(double p_220679_1_, double p_220679_3_, double p_220679_5_) {
        this.setLookPosition(p_220679_1_, p_220679_3_, p_220679_5_, (float) this.mob.getFaceRotSpeed(), (float) this.mob.getVerticalFaceSpeed());
    }

    /**
     * Sets position to look at
     */
    public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.deltaLookYaw = deltaYaw;
        this.deltaLookPitch = deltaPitch;
        this.isLooking = true;
    }

    /**
     * Updates look
     */
    public void tick() {

        if (this.isLooking) {
            this.isLooking = false;
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.getTargetYaw(), this.deltaLookYaw);
            this.mob.rotationPitch = this.clampedRotate(this.mob.rotationPitch, this.getTargetPitch(), this.deltaLookPitch);
        } else {
            this.mob.rotationPitch = 0.0F;
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.mob.renderYawOffset, 10.0F);
        }

        if (!this.mob.getNavigator().noPath()) {
//            this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, (float) this.mob.getHorizontalFaceSpeed());
            this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.renderYawOffset, this.mob.rotationYawHead, (float) this.mob.getHorizontalFaceSpeed());
        }

    }

    private static double getEyePosition(Entity entity) {
        return entity instanceof LivingEntity ? entity.getPosYEye() : (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D;
    }

}