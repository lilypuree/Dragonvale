package lilypuree.dragonvale.dragons.entity;

import lilypuree.dragonvale.dragons.DragonGrowthStages;
import lilypuree.dragonvale.dragons.entity.ai.LookAroundRandomlyGoal;
import lilypuree.dragonvale.dragons.entity.ai.TakeOffGoal;
import lilypuree.dragonvale.dragons.entity.ai.controller.*;
import lilypuree.dragonvale.treats.items.TreatItem;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.animation.snapshot.BoneSnapshotCollection;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import javax.annotation.Nullable;

public abstract class DragonEntityBase extends CreatureEntity implements IAnimatedEntity {

    public RandomWalkingGoal flightGoal;
    public RandomWalkingGoal walkingGoal;
    private MovementMode movementMode;

    public static final RedstoneParticleData DRAGON_GROWTH = new RedstoneParticleData(1.0F, 0.0F, 0.0F, 1.0F);
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(DragonEntityBase.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(DragonEntityBase.class, DataSerializers.BOOLEAN);
    //    private static final DataParameter<Integer> GROWTH_STAGE = EntityDataManager.createKey(DragonEntityBase.class, DragonGrowthStages.GROWTH_STAGE_SERIALIZER);
    protected int level = 1;
    protected int currentLevelGrowth;

    //TODO
    protected boolean immuneToDamage;

    protected static float maxHeadYaw = 80.0f;
    protected static float maxHeadPitch = 85.0f;
    protected static float minHeadPitch = -85.0f;

    protected EntityAnimationManager manager = new EntityAnimationManager();

    protected AnimationController idleController = new EntityAnimationController(this, "idleController", 10F, this::idleAnimationPredicate);

    private <E extends DragonEntityBase> boolean idleAnimationPredicate(AnimationTestEvent<E> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        if (event.getEntity().isChild()) {
            idleController.transitionLengthTicks = 3;
            if(dataManager.get(FLYING)){
                manager.setResetSpeedInTicks(6.0D);
                manager.setAnimationSpeed(1.5D);
                idleController.setAnimation(new AnimationBuilder().addAnimation("fly", true));
                return true;
            }else if (event.isWalking()) {
                manager.setResetSpeedInTicks(6.0D);
                manager.setAnimationSpeed(1.5D);
                idleController.setAnimation(new AnimationBuilder().addAnimation("hop", true));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    public DragonEntityBase(EntityType<? extends DragonEntityBase> type, World worldIn) {
        super(type, worldIn);
        this.movementMode = MovementMode.WALKING;
        this.moveController = new AltMoveController(this, 10, true);
        this.lookController = new AltLookController(this);
        registerAnimationControllers();
        registerMoveGoals();
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new FlyingWalkingNavigator(this, worldIn, new GroundPathNavigator(this, worldIn), new FlyingPathNavigator(this, worldIn));
    }

    protected void registerAnimationControllers() {
        if (world.isRemote) {
            manager.addAnimationController(idleController);
        }
    }

    @Override
    protected BodyController createBodyController() {
        return new AltBodyController(this, maxHeadYaw, maxHeadPitch, minHeadPitch);
    }

    protected void registerMoveGoals() {
        flightGoal = new WaterAvoidingRandomFlyingGoal(this, 1.0D) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && DragonEntityBase.this.isFlying();
            }
        };
        walkingGoal = new WaterAvoidingRandomWalkingGoal(this, 1.0D);
        this.goalSelector.addGoal(5, new TakeOffGoal(this, 500));
        this.goalSelector.addGoal(3, walkingGoal);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
//        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new LookAroundRandomlyGoal(this, maxHeadYaw, maxHeadPitch, minHeadPitch));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.4F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }


    @Nullable
    public abstract DragonEntityBase createChild(DragonEntityBase ageableEntity);

    protected void onChildSpawnFromEgg(PlayerEntity playerIn, DragonEntityBase childIn) {
    }


    @Override
    public boolean processInteract(PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Item item = stack.getItem();
        if (item instanceof TreatItem && !this.world.isRemote() && canConsumeTreat((TreatItem) item)) {
            int growthPower = ((TreatItem) item).getGrowthPower();
            if (playerIn.isSneaking()) {
                this.addGrowth(growthPower * stack.getCount());
                stack.shrink(stack.getCount());
            } else {
                this.addGrowth(growthPower);
                stack.shrink(1);
            }
            playerIn.swing(handIn, true);
            return true;
        }
        return super.processInteract(playerIn, handIn);
    }

    private boolean canConsumeTreat(TreatItem treatItem) {
        int tier = treatItem.getTier();
        if (level < 4) {
            return tier == 1;
        } else if (level < 7) {
            return tier <= 2;
        } else if (level < 12) {
            return tier > 1 && tier <= 3;
        } else if (level < 17) {
            return tier > 2 && tier <= 4;
        } else {
            return tier > 3 && tier <= 5;
        }
    }


    public int getLevel() {
        if (world.isRemote()) {
            return this.dataManager.get(LEVEL);
        }
        return level;
    }

    public void setLevel(int level) {
        updateLevel(level);
        this.currentLevelGrowth = 0;
    }

    public int getCurrentLevelGrowth() {
        return currentLevelGrowth;
    }

    public void setCurrentLevelGrowth(int currentLevelGrowth) {
        this.currentLevelGrowth = currentLevelGrowth;
    }

    public DragonGrowthStages getGrowthStage() {
        if (world.isRemote) {
            int level = this.dataManager.get(LEVEL);
            if (level < 4) return DragonGrowthStages.BABY;
            else if (level < 7) return DragonGrowthStages.JUVENILE;
            else {
                return DragonGrowthStages.ADULT;
            }
        }
        if (level < 4) return DragonGrowthStages.BABY;
        else if (level < 7) return DragonGrowthStages.JUVENILE;
        else {
            return DragonGrowthStages.ADULT;
        }
    }

    @Override
    public boolean isChild() {
        return getGrowthStage() == DragonGrowthStages.BABY;
    }

    public void addGrowth(int growth) {
        if (isMaxLevel()) return;
        currentLevelGrowth += growth;
        int lvl = level;
        while (currentLevelGrowth >= getGrowthToNextLevel(lvl)) {
            currentLevelGrowth -= getGrowthToNextLevel(lvl);
            lvl++;
        }
        updateLevel(lvl);
    }

    public int getGrowthToNextLevel(int level) {
        return level > 20 ? Integer.MAX_VALUE : (int) Math.pow(2, level - 1);
    }

    public boolean isMaxLevel() {
        return level == 21;
    }

    private void updateLevel(int newLevel) {
        int tempLevel = this.level;
        this.level = newLevel;
        this.dataManager.set(LEVEL, level);
        if (tempLevel < newLevel) {
            for (int i = 0; i < 10; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                float red = this.rand.nextFloat() * 0.3f;
                float green = this.rand.nextFloat() * 0.2f + 0.8f;
                float blue = this.rand.nextFloat() * 0.2f;
                this.world.addParticle(new RedstoneParticleData(red, green, blue, 1.0F), this.getPosXRandom(1.0D), this.getPosYRandom(), this.getPosZRandom(1.0D), d0, d1, d2);
            }
        }
        if (tempLevel < 4) {
            if (newLevel >= 4) {
//                this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.JUVENILE);
                this.onGrowingJuvenile();
            } else {
//                this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.BABY);
            }
        } else if (tempLevel < 7) {
            if (newLevel >= 7) {
//                this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.ADULT);
                this.onGrowingAdult();
            }
//            this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.JUVENILE);
        } else {
            if (newLevel < 7) {
//                this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.JUVENILE);
            }
//            this.dataManager.set(GROWTH_STAGE, DragonGrowthStages.ADULT);
        }
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn);
    }

    @Override
    public float getRenderScale() {
        return getScale(this.getLevel());
    }

    public static float getScale(int level) {
        if (level < 4) {
            return 0.8f + (level - 1) / 2.0f * 0.2f;
        } else if (level < 7) {
            return 0.6f + (level - 4) / 2.0f * 0.4f;
        } else {
            return 1.0f + (level - 7) / 16.0f * 0.5f;
        }
    }

    protected void onGrowingJuvenile() {

    }

    protected void onGrowingAdult() {
    }


    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Growth", this.getCurrentLevelGrowth());
        compound.putInt("Level", this.getLevel());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setCurrentLevelGrowth(compound.getInt("Growth"));
        this.setLevel(compound.getInt("Level"));
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> data) {
        if (LEVEL.equals(data)) {
            this.recalculateSize();
            this.getAnimationManager().setBoneSnapshotCollection(new BoneSnapshotCollection());
        }
        super.notifyDataManagerChange(data);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEVEL, 1);
        this.dataManager.register(FLYING, false);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        setFlying(false);
        return false;
    }


    @Override
    protected boolean makeFlySound() {
        return movementMode == MovementMode.FLYING;
    }

    @Override
    protected float playFlySound(float volume) {
        if (movementMode == MovementMode.FLYING) {

        }
        return super.playFlySound(volume);
    }

    //    @Override
//    protected void updateLeashedState() {
//        if (this.leashNBTTag != null) {
//            this.recreateLeash();
//        }
//
//        if (this.getLeashHolder() != null) {
//            if (!this.isAlive() || !this.getLeashHolder().isAlive()) {
//                this.clearLeashed(true, true);
//            }
//        }
//        Entity leashHolder = this.getLeashHolder();
//        if (leashHolder != null && leashHolder.world == this.world) {
//            this.setHomePosAndDistance(new BlockPos(leashHolder), 5);
//            float distanceToHolder = this.getDistance(leashHolder);
////            if (this instanceof TameableEntity && ((TameableEntity)this).isSitting()) {
////                if (distanceToHolder > 10.0F) {
////                    this.clearLeashed(true, true);
////                }
////                return;
////            }
//
//            this.onLeashDistance(distanceToHolder);
//            if (distanceToHolder > 10.0F) {
//                this.clearLeashed(true, true);
//                this.goalSelector.disableFlag(Goal.Flag.MOVE);
//            } else if (distanceToHolder > 6.0F) {
////            } else if (false) {
//                double d0 = (leashHolder.getPosX() - this.getPosX()) / (double) distanceToHolder;
//                double d1 = (leashHolder.getPosY() - this.getPosY()) / (double) distanceToHolder;
//                double d2 = (leashHolder.getPosZ() - this.getPosZ()) / (double) distanceToHolder;
//                this.setMotion(this.getMotion().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
//            } else {
//                this.goalSelector.enableFlag(Goal.Flag.MOVE);
//                float f1 = 2.0F;
//                Vec3d distance = new Vec3d(leashHolder.getPosX() - this.getPosX(), leashHolder.getPosY() - this.getPosY(), leashHolder.getPosZ() - this.getPosZ());
//                Vec3d vec3d = distance.normalize().scale((double) Math.max(distanceToHolder - 2.0F, 0.0F));
//                this.getNavigator().tryMoveToXYZ(this.getPosX() + vec3d.x, this.getPosY() + vec3d.y, this.getPosZ() + vec3d.z, this.followLeashSpeed());
//            }
//        }
//    }

    public boolean isFlying() {
        return movementMode == MovementMode.FLYING;
    }

    private boolean changeTasks = false;

    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote() && this.changeTasks) {
            if (this.isFlying()) {
                this.goalSelector.addGoal(3, flightGoal);
                this.goalSelector.removeGoal(walkingGoal);
            } else {
                this.goalSelector.addGoal(3, walkingGoal);
                this.goalSelector.removeGoal(flightGoal);
            }
            this.changeTasks = false;
        }
    }

    public void setFlying(boolean flying) {
        if (!world.isRemote()) {
            if (flying) {
                this.movementMode = MovementMode.FLYING;
                this.dataManager.set(FLYING, true);
            } else {
                this.movementMode = MovementMode.WALKING;
                this.dataManager.set(FLYING, false);
            }
        }
        this.changeTasks = true;
    }
}
