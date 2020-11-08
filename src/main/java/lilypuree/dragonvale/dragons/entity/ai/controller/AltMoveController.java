package lilypuree.dragonvale.dragons.entity.ai.controller;


import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;

public class AltMoveController extends MovementController {
    protected final DragonEntityBase mob;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double speed;
    protected float moveForward;
    protected float moveStrafe;
    protected Action action;
    protected int maxTurn;
    protected boolean noGravity;

    public AltMoveController(DragonEntityBase entity, int maxTurn, boolean noGravity) {
        super(entity);
        this.action = Action.WAIT;
        this.mob = entity;
        this.maxTurn = maxTurn;
        this.noGravity = noGravity;
    }

    public boolean isUpdating() {
        return this.action == Action.MOVE_TO;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setMoveTo(double x, double y, double z, double speed) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speed;
        if (this.action != Action.JUMPING) {
            this.action = Action.MOVE_TO;
        }
    }

    public void strafe(float moveForward, float moveStrafe) {
        this.action = Action.STRAFE;
        this.moveForward = moveForward;
        this.moveStrafe = moveStrafe;
        this.speed = 0.25D;
    }

    @Override
    public void tick() {
        if (mob.isFlying()) {
            flightTick();
        } else {
            groundTick();
        }
    }

    public void flightTick() {
        if (this.action == Action.MOVE_TO) {
            this.action = Action.WAIT;
            this.mob.setNoGravity(true);
            double dx = this.posX - this.mob.getPosX();
            double dy = this.posY - this.mob.getPosY();
            double dz = this.posZ - this.mob.getPosZ();
            double dsquare = dx * dx + dy * dy + dz * dz;
            if (dsquare < (double) 2.5000003E-7F) {
                this.mob.setMoveVertical(0.0F);
                this.mob.setMoveForward(0.0F);
                return;
            }

            float yaw = (float) (MathHelper.atan2(dz, dx) * (double) (180F / (float) Math.PI)) - 90.0F;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, yaw, 90.0F);
            float modSpeed;
            if (this.mob.onGround) {
                modSpeed = (float) (this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
            } else {
                modSpeed = (float) (this.speed * this.mob.getAttribute(SharedMonsterAttributes.FLYING_SPEED).getValue());
            }

            this.mob.setAIMoveSpeed(modSpeed);
            double horizontal = (double) MathHelper.sqrt(dx * dx + dz * dz);
            float pitch = (float) (-(MathHelper.atan2(dy, horizontal) * (double) (180F / (float) Math.PI)));
            this.mob.rotationPitch = this.limitAngle(this.mob.rotationPitch, pitch, (float) this.maxTurn);
            this.mob.setMoveVertical(dy > 0.0D ? modSpeed : -modSpeed);
        } else {
            if (!this.noGravity) {
                this.mob.setNoGravity(false);
            }

            this.mob.setMoveVertical(0.0F);
            this.mob.setMoveForward(0.0F);
        }
    }

    public void groundTick() {
        if (this.action == Action.START_MOVING) {

        }
        if (this.action == Action.STRAFE) {
            float speedAttribute = (float) this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
            float moveSpeed = (float) this.speed * speedAttribute;
            float forward = this.moveForward;
            float strafe = this.moveStrafe;
            float multiplier = MathHelper.sqrt(forward * forward + strafe * strafe);
            if (multiplier < 1.0F) {
                multiplier = 1.0F;
            }

            multiplier = moveSpeed / multiplier;
            forward *= multiplier;
            strafe *= multiplier;
            float sinYaw = MathHelper.sin(this.mob.rotationYaw * (float) (Math.PI / 180.0f));
            float cosYaw = MathHelper.cos(this.mob.rotationYaw * (float) (Math.PI / 180.0f));
            float xMove = forward * cosYaw - strafe * sinYaw;
            float zMove = strafe * cosYaw + forward * sinYaw;
            PathNavigator navigator = this.mob.getNavigator();
            if (navigator != null) {
                NodeProcessor nodeProcessor = navigator.getNodeProcessor();
                boolean unwalkable = nodeProcessor != null && nodeProcessor.getPathNodeType(this.mob.world, MathHelper.floor(this.mob.getPosX() + (double) xMove), MathHelper.floor(this.mob.getPosY()), MathHelper.floor(this.mob.getPosZ() + (double) zMove)) != PathNodeType.WALKABLE;
                if (unwalkable) {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                    moveSpeed = speedAttribute;
                }
            }

            this.mob.setAIMoveSpeed(moveSpeed);
            this.mob.setMoveForward(this.moveForward);
            this.mob.setMoveStrafing(this.moveStrafe);
            this.action = Action.WAIT;
        } else if (this.action == Action.MOVE_TO) {
            this.action = Action.WAIT;
            double xDiff = this.posX - this.mob.getPosX();
            double zDiff = this.posZ - this.mob.getPosZ();
            double yDiff = this.posY - this.mob.getPosY();
            double distSquared = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
            if (distSquared < 2.500000277905201E-7D) {
                this.mob.setMoveForward(0.0F);
                return;
            }

            float targetYaw = (float) (MathHelper.atan2(zDiff, xDiff) * (float) (180.0f / Math.PI)) - 90.0F;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, targetYaw, 10.0F);
            this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
            BlockPos currentPos = new BlockPos(this.mob);
            BlockState blockState = this.mob.world.getBlockState(currentPos);
            Block block = blockState.getBlock();
            VoxelShape collision = blockState.getCollisionShape(this.mob.world, currentPos);
            if (yDiff > (double) this.mob.stepHeight && xDiff * xDiff + zDiff * zDiff < (double) Math.max(1.0F, this.mob.getWidth()) || !collision.isEmpty() && this.mob.getPosY() < collision.getEnd(Axis.Y) + (double) currentPos.getY() && !block.isIn(BlockTags.DOORS) && !block.isIn(BlockTags.FENCES)) {
                this.mob.getJumpController().setJumping();
                this.action = Action.JUMPING;
            }
        } else if (this.action == Action.JUMPING) {
            this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
            if (this.mob.onGround) {
                this.action = Action.WAIT;
            }
        } else {
            this.mob.setMoveForward(0.0F);
        }

    }

    protected float limitAngle(float from, float to, float limit) {
        float f = MathHelper.wrapDegrees(to - from);
        if (f > limit) {
            f = limit;
        }

        if (f < -limit) {
            f = -limit;
        }

        float result = from + f;
        if (result < 0.0F) {
            result += 360.0F;
        } else if (result > 360.0F) {
            result -= 360.0F;
        }

        return result;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public static enum Action {
        WAIT,
        START_MOVING,
        MOVE_TO,
        STRAFE,
        JUMPING;

        private Action() {
        }
    }

    public Action getAction() {
        return action;
    }
}
