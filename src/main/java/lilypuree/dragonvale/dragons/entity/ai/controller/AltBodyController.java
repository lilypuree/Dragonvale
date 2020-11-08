package lilypuree.dragonvale.dragons.entity.ai.controller;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.util.math.MathHelper.clamp;
import static net.minecraft.util.math.MathHelper.wrapSubtractDegrees;

public class AltBodyController extends BodyController {
    private final MobEntity mob;
    private int rotationTickCounter;
    private float prevRenderYawHead;
    private int bodyHeadTicksOffset = 10;

    private boolean changedDirection;
    private float maxHeadYaw;
    private float maxHeadPitch;
    private float minHeadPitch;

    public AltBodyController(MobEntity mob, float maxHeadYaw, float maxHeadPitch, float minHeadPitch) {
        super(mob);
        this.mob = mob;
        this.maxHeadYaw = maxHeadYaw;
        this.maxHeadPitch = maxHeadPitch;
        this.minHeadPitch = minHeadPitch;
        this.changedDirection = false;
    }

    /**
     * Update the Head and Body rendering angles
     */
    @Override
    public void updateRenderAngles() {
        if (-this.mob.rotationPitch > maxHeadPitch) {
            this.mob.rotationPitch = MathHelper.func_219800_b((float) -maxHeadPitch, this.mob.rotationPitch, this.mob.getVerticalFaceSpeed());
        } else if (-this.mob.rotationPitch < minHeadPitch) {
            this.mob.rotationPitch = MathHelper.func_219800_b((float) -minHeadPitch, this.mob.rotationPitch, this.mob.getVerticalFaceSpeed());
        }
        if (this.moved()) {
            if (this.mob.getLeashed()) {
                updateLeashMovement();
            } else {
                this.mob.renderYawOffset = this.mob.rotationYaw;
            }
            this.rotateHeadToBody();
            this.rotationTickCounter = 0;
            this.changedDirection = false;
        } else {
            if (this.noMobPassengers()) {
                double headRotation = MathHelper.wrapSubtractDegrees(this.mob.renderYawOffset, this.mob.rotationYawHead);
                if (Math.abs(headRotation) > maxHeadYaw) {
                    this.changedDirection = true;
                    this.mob.renderYawOffset = this.mob.rotationYawHead - (float) MathHelper.clamp(headRotation, -maxHeadYaw, maxHeadYaw);
                }
                if (changedDirection || rotationTickCounter > 0) {
                    ++this.rotationTickCounter;
                    if (this.rotationTickCounter > 10) {
                        this.slowlyRotateBody();
                    }
                    if (Math.abs(this.mob.rotationYawHead - this.mob.renderYawOffset) < 5.0f) {
                        this.mob.renderYawOffset = this.mob.rotationYawHead;
                        rotationTickCounter = 0;
                    }
                    changedDirection = false;
                }
            }
        }
    }

    private void rotateBodyToHead() {
        this.mob.renderYawOffset = clampedRotate(this.mob.renderYawOffset, this.mob.rotationYawHead, (float) this.mob.getHorizontalFaceSpeed());
//        this.mob.renderYawOffset = clampedRotate(this.mob.rotationYawHead, this.mob.renderYawOffset, this.mob.getHorizontalFaceSpeed());
    }

    private void rotateHeadToBody() {
        this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, (float) this.mob.getHorizontalFaceSpeed());
//        this.mob.rotationYawHead = clampedRotate(this.mob.renderYawOffset, this.mob.rotationYawHead, (float) this.mob.getHorizontalFaceSpeed());
    }

    private void slowlyRotateBody() {
        int i = this.rotationTickCounter - bodyHeadTicksOffset;
        float f = clamp((float) i / bodyHeadTicksOffset, 0.0F, 1.0F);
        float f1 = (float) this.mob.getHorizontalFaceSpeed() * (1.0F - f);
//        this.mob.renderYawOffset = MathHelper.func_219800_b(this.mob.renderYawOffset, this.mob.rotationYawHead, f1);
        this.mob.renderYawOffset = MathHelper.func_219800_b(this.mob.renderYawOffset, this.mob.rotationYawHead, f1);
    }

    private boolean noMobPassengers() {
        return this.mob.getPassengers().isEmpty() || !(this.mob.getPassengers().get(0) instanceof MobEntity);
    }

    private boolean moved() {
        double d0 = this.mob.getPosX() - this.mob.prevPosX;
        double d1 = this.mob.getPosZ() - this.mob.prevPosZ;
        return d0 * d0 + d1 * d1 > (double) 2.5000003E-7F;
    }

    private void updateLeashMovement() {
        Entity leashHolder = this.mob.getLeashHolder();

        if (leashHolder instanceof PlayerEntity) {
            double xDiff = leashHolder.getPosX() - this.mob.getPosX();
            double zDiff = leashHolder.getPosZ() - this.mob.getPosZ();
            float yawToHolder = (float) (MathHelper.atan2(zDiff, xDiff) * (180.0f / Math.PI)) - 90.0F;
            float yawDiff = MathHelper.wrapDegrees(this.mob.rotationYaw - yawToHolder);
            if (yawDiff > 30) {
                this.mob.renderYawOffset = wrapDegreesAlt(yawToHolder + 30);
            } else if (yawDiff < -30) {
                this.mob.renderYawOffset = wrapDegreesAlt(yawToHolder - 30);
            } else {
                this.mob.renderYawOffset = this.mob.rotationYaw;
            }
        }
    }

    public static float clampedRotate(float to, float from, float limit) {
        float f = wrapSubtractDegrees(to, from);
        float f1 = clamp(f, -limit, limit);
        return wrapDegreesAlt(from - f1);
    }

    private static float wrapDegreesAlt(float value) {
        float v = value % 360.0F;
        if (v >= 360.0F) {
            v -= 360.0F;
        }

        if (v < 0.0F) {
            v += 360.0F;
        }

        return v;
    }

}
