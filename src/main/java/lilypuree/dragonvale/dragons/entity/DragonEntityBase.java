package lilypuree.dragonvale.dragons.entity;

import lilypuree.dragonvale.dragons.DragonGrowthStages;
import lilypuree.dragonvale.treats.items.TreatItem;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import javax.annotation.Nullable;

//A different copy of AgeableEntity
public abstract class DragonEntityBase extends CreatureEntity implements IAnimatedEntity {
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(DragonEntityBase.class, DataSerializers.VARINT);
    protected int level = 1;
    protected int currentLevelGrowth;

    protected EntityAnimationManager manager = new EntityAnimationManager();

    protected EntityAnimationController stateController = new EntityAnimationController(this, "stateController", 10F, this::stateController);

    private <ENTITY extends Entity> boolean stateController(AnimationTestEvent<ENTITY> entityAnimationTestEvent) {
        stateController.transitionLengthTicks = 10;
//        stateController.setAnimation(new AnimationBuilder().addx  Animation("idle", true));
        return false;
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    public DragonEntityBase(EntityType<? extends DragonEntityBase> type, World worldIn) {
        super(type, worldIn);
        registerAnimationControllers();
    }


    protected void registerAnimationControllers() {
        if (world.isRemote) {
            manager.addAnimationController(stateController);
        }
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
        return false;
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

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingEntityData, @Nullable CompoundNBT tag) {
        if (livingEntityData == null) {
            livingEntityData = new AgeableEntity.AgeableData();
        }

        AgeableEntity.AgeableData ageableData = (AgeableEntity.AgeableData) livingEntityData;
        if (ageableData.canBabySpawn() && ageableData.getIndexInGroup() > 0 && this.rand.nextFloat() <= ageableData.getBabySpawnProbability()) {
            this.setLevel(1);
        } else {
            this.setLevel(1);
        }

        ageableData.incrementIndexInGroup();
        return super.onInitialSpawn(worldIn, difficulty, spawnReason, (ILivingEntityData) livingEntityData, tag);
    }


    @Override
    public void livingTick() {
        super.livingTick();
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
    public void notifyDataManagerChange(DataParameter<?> p_184206_1_) {
        if (LEVEL.equals(p_184206_1_)) {
            this.recalculateSize();
        }
        super.notifyDataManagerChange(p_184206_1_);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEVEL, 1);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
    }


}
