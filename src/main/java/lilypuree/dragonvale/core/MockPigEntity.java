package lilypuree.dragonvale.core;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MockPigEntity extends AnimalEntity {
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(MockPigEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(MockPigEntity.class, DataSerializers.VARINT);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;

    public MockPigEntity(EntityType<? extends MockPigEntity> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
    }

    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new SwimGoal(this));
//        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
//        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
//        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
//        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
//        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
//        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
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
////            } else if (distanceToHolder > 6.0F) {
//            } else if (false) {
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
//
//    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }


    public PigEntity createChild(AgeableEntity ageable) {
        return EntityType.PIG.create(this.world);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }
}
