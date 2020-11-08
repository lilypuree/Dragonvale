//package lilypuree.dragonvale.core;
//
//import com.google.common.base.Objects;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.crash.CrashReport;
//import net.minecraft.crash.CrashReportCategory;
//import net.minecraft.crash.ReportedException;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.MoverType;
//import net.minecraft.entity.ai.attributes.IAttributeInstance;
//import net.minecraft.entity.passive.IFlyingAnimal;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.inventory.EquipmentSlotType;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.server.SEntityEquipmentPacket;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.potion.EffectUtils;
//import net.minecraft.potion.Effects;
//import net.minecraft.tags.FluidTags;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.server.ServerWorld;
//
//public class TestEntity extends LivingEntity {
//    public TestEntity() {
//        super();
//    }
//
//
//    @Override
//    public void baseTick() {
//        this.prevSwingProgress = this.swingProgress;
//        if (this.firstUpdate) {
//            this.getBedPosition().ifPresent(this::setSleepingPosition);
//        }
//
////        super.baseTick();
//        this.world.getProfiler().startSection("livingEntityBaseTick");
//        boolean isPlayer = this instanceof PlayerEntity;
//        if (this.isAlive()) {
//            if (this.isEntityInsideOpaqueBlock()) {
//                this.attackEntityFrom(DamageSource.IN_WALL, 1.0F);
//            } else if (isPlayer && !this.world.getWorldBorder().contains(this.getBoundingBox())) {
//                double d0 = this.world.getWorldBorder().getClosestDistance(this) + this.world.getWorldBorder().getDamageBuffer();
//                if (d0 < 0.0D) {
//                    double d1 = this.world.getWorldBorder().getDamagePerBlock();
//                    if (d1 > 0.0D) {
//                        this.attackEntityFrom(DamageSource.IN_WALL, (float) Math.max(1, MathHelper.floor(-d0 * d1)));
//                    }
//                }
//            }
//        }
//
//        if (this.isImmuneToFire() || this.world.isRemote) {
//            this.extinguish();
//        }
//
//        boolean flag1 = isPlayer && ((PlayerEntity) this).abilities.disableDamage;
//        if (this.isAlive()) {
//            if (this.areEyesInFluid(FluidTags.WATER) && this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosYEye(), this.getPosZ())).getBlock() != Blocks.BUBBLE_COLUMN) {
//                if (!this.canBreatheUnderwater() && !EffectUtils.canBreatheUnderwater(this) && !flag1) {
//                    this.setAir(this.decreaseAirSupply(this.getAir()));
//                    if (this.getAir() == -20) {
//                        this.setAir(0);
//                        Vec3d vec3d = this.getMotion();
//
//                        for (int i = 0; i < 8; ++i) {
//                            float f = this.rand.nextFloat() - this.rand.nextFloat();
//                            float f1 = this.rand.nextFloat() - this.rand.nextFloat();
//                            float f2 = this.rand.nextFloat() - this.rand.nextFloat();
//                            this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() + (double) f, this.getPosY() + (double) f1, this.getPosZ() + (double) f2, vec3d.x, vec3d.y, vec3d.z);
//                        }
//
//                        this.attackEntityFrom(DamageSource.DROWN, 2.0F);
//                    }
//                }
//
//                if (!this.world.isRemote && this.isPassenger() && this.getRidingEntity() != null && !this.getRidingEntity().canBeRiddenInWater(this)) {
//                    this.stopRiding();
//                }
//            } else if (this.getAir() < this.getMaxAir()) {
//                this.setAir(this.determineNextAir(this.getAir()));
//            }
//
//            if (!this.world.isRemote) {
//                BlockPos blockpos = new BlockPos(this);
//                if (!Objects.equal(this.prevBlockpos, blockpos)) {
//                    this.prevBlockpos = blockpos;
//                    this.frostWalk(blockpos);
//                }
//            }
//        }
//
//        if (this.isAlive() && this.isInWaterRainOrBubbleColumn()) {
//            this.extinguish();
//        }
//
//        if (this.hurtTime > 0) {
//            --this.hurtTime;
//        }
//
//        if (this.hurtResistantTime > 0 && !(this instanceof ServerPlayerEntity)) {
//            --this.hurtResistantTime;
//        }
//
//        if (this.getHealth() <= 0.0F) {
//            this.onDeathUpdate();
//        }
//
//        if (this.recentlyHit > 0) {
//            --this.recentlyHit;
//        } else {
//            this.attackingPlayer = null;
//        }
//
//        if (this.lastAttackedEntity != null && !this.lastAttackedEntity.isAlive()) {
//            this.lastAttackedEntity = null;
//        }
//
//        if (this.revengeTarget != null) {
//            if (!this.revengeTarget.isAlive()) {
//                this.setRevengeTarget((LivingEntity) null);
//            } else if (this.ticksExisted - this.revengeTimer > 100) {
//                this.setRevengeTarget((LivingEntity) null);
//            }
//        }
//
//        this.updatePotionEffects();
//        this.prevMovedDistance = this.movedDistance;
//        this.prevRenderYawOffset = this.renderYawOffset;
//        this.prevRotationYawHead = this.rotationYawHead;
//        this.prevRotationYaw = this.rotationYaw;
//        this.prevRotationPitch = this.rotationPitch;
//        this.world.getProfiler().endSection();
//    }
//
//    @Override
//    public void tick() {
//        if (net.minecraftforge.common.ForgeHooks.onLivingUpdate(this)) return;
//        super.tick();
//        //base tick happens
//        this.updateActiveHand();
//        this.updateSwimAnimation();
//        if (!this.world.isRemote) {
//            int i = this.getArrowCountInEntity();
//            if (i > 0) {
//                if (this.arrowHitTimer <= 0) {
//                    this.arrowHitTimer = 20 * (30 - i);
//                }
//
//                --this.arrowHitTimer;
//                if (this.arrowHitTimer <= 0) {
//                    this.setArrowCountInEntity(i - 1);
//                }
//            }
//
//            int j = this.getBeeStingCount();
//            if (j > 0) {
//                if (this.beeStingRemovalCooldown <= 0) {
//                    this.beeStingRemovalCooldown = 20 * (30 - j);
//                }
//
//                --this.beeStingRemovalCooldown;
//                if (this.beeStingRemovalCooldown <= 0) {
//                    this.setBeeStingCount(j - 1);
//                }
//            }
//
//            for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
//                ItemStack itemstack;
//                switch (equipmentslottype.getSlotType()) {
//                    case HAND:
//                        itemstack = this.handInventory.get(equipmentslottype.getIndex());
//                        break;
//                    case ARMOR:
//                        itemstack = this.armorArray.get(equipmentslottype.getIndex());
//                        break;
//                    default:
//                        continue;
//                }
//
//                ItemStack itemstack1 = this.getItemStackFromSlot(equipmentslottype);
//                if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
//                    if (!itemstack1.equals(itemstack, true))
//                        ((ServerWorld) this.world).getChunkProvider().sendToAllTracking(this, new SEntityEquipmentPacket(this.getEntityId(), equipmentslottype, itemstack1));
//                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent(this, equipmentslottype, itemstack, itemstack1));
//                    if (!itemstack.isEmpty()) {
//                        this.getAttributes().removeAttributeModifiers(itemstack.getAttributeModifiers(equipmentslottype));
//                    }
//
//                    if (!itemstack1.isEmpty()) {
//                        this.getAttributes().applyAttributeModifiers(itemstack1.getAttributeModifiers(equipmentslottype));
//                    }
//
//                    switch (equipmentslottype.getSlotType()) {
//                        case HAND:
//                            this.handInventory.set(equipmentslottype.getIndex(), itemstack1.copy());
//                            break;
//                        case ARMOR:
//                            this.armorArray.set(equipmentslottype.getIndex(), itemstack1.copy());
//                    }
//                }
//            }
//
//            if (this.ticksExisted % 20 == 0) {
//                this.getCombatTracker().reset();
//            }
//
//            if (!this.glowing) {
//                boolean flag = this.isPotionActive(Effects.GLOWING);
//                if (this.getFlag(6) != flag) {
//                    this.setFlag(6, flag);
//                }
//            }
//
//            if (this.isSleeping() && !this.isInValidBed()) {
//                this.wakeUp();
//            }
//        }
//
//        this.livingTick();
//        double xDistance = this.getPosX() - this.prevPosX;
//        double yDistance = this.getPosZ() - this.prevPosZ;
//        float distanceSquared = (float) (xDistance * xDistance + yDistance * yDistance);
//
//        float bodyRotation = this.renderYawOffset;
//        float headRotation = 0.0F;
//
//        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
//        float f5 = 0.0F;
//        if (distanceSquared > 0.0025000002F) {
//            f5 = 1.0F;
//            headRotation = (float) Math.sqrt((double) distanceSquared) * 3.0F;
//            float movementAngle = (float) MathHelper.atan2(yDistance, xDistance) * (180F / (float) Math.PI) - 90.0F;
//            float rotationDiff = MathHelper.abs(MathHelper.wrapDegrees(this.rotationYaw) - movementAngle);
//            if (95.0F < rotationDiff && rotationDiff < 265.0F) {
//                bodyRotation = movementAngle - 180.0F;
//            } else {
//                bodyRotation = movementAngle;
//            }
//        }
//
//        if (this.swingProgress > 0.0F) {
//            bodyRotation = this.rotationYaw;
//        }
//
//        if (!this.onGround) {
//            f5 = 0.0F;
//        }
//
//        this.onGroundSpeedFactor += (f5 - this.onGroundSpeedFactor) * 0.3F;
//        this.world.getProfiler().startSection("headTurn");
//        //also turns the body towards the rotation yaw and flips the head if the entity is moving backwards
//        headRotation = this.turnHead(bodyRotation, headRotation);
//        this.world.getProfiler().endSection();
//        this.world.getProfiler().startSection("rangeChecks");
//
//        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
//            this.prevRotationYaw -= 360.0F;
//        }
//
//        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
//            this.prevRotationYaw += 360.0F;
//        }
//
//        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
//            this.prevRenderYawOffset -= 360.0F;
//        }
//
//        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
//            this.prevRenderYawOffset += 360.0F;
//        }
//
//        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
//            this.prevRotationPitch -= 360.0F;
//        }
//
//        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
//            this.prevRotationPitch += 360.0F;
//        }
//
//        while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
//            this.prevRotationYawHead -= 360.0F;
//        }
//
//        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
//            this.prevRotationYawHead += 360.0F;
//        }
//
//        this.world.getProfiler().endSection();
//        this.movedDistance += headRotation;
//        if (this.isElytraFlying()) {
//            ++this.ticksElytraFlying;
//        } else {
//            this.ticksElytraFlying = 0;
//        }
//
//        if (this.isSleeping()) {
//            this.rotationPitch = 0.0F;
//        }
//
//    }
//
//    @Override
//    public void livingTick() {
//        if (this.jumpTicks > 0) {
//            --this.jumpTicks;
//        }
//
//        if (this.canPassengerSteer()) {
//            this.newPosRotationIncrements = 0;
//            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
//        }
//
//        if (this.newPosRotationIncrements > 0) {
//            double interpX = this.getPosX() + (this.interpTargetX - this.getPosX()) / (double) this.newPosRotationIncrements;
//            double interpY = this.getPosY() + (this.interpTargetY - this.getPosY()) / (double) this.newPosRotationIncrements;
//            double interpZ = this.getPosZ() + (this.interpTargetZ - this.getPosZ()) / (double) this.newPosRotationIncrements;
//            double yawInterpDiff = MathHelper.wrapDegrees(this.interpTargetYaw - (double) this.rotationYaw);
//            this.rotationYaw = (float) ((double) this.rotationYaw + yawInterpDiff / (double) this.newPosRotationIncrements);
//            this.rotationPitch = (float) ((double) this.rotationPitch + (this.interpTargetPitch - (double) this.rotationPitch) / (double) this.newPosRotationIncrements);
//            --this.newPosRotationIncrements;
//            this.setPosition(interpX, interpY, interpZ);
//            this.setRotation(this.rotationYaw, this.rotationPitch);
//        } else if (!this.isServerWorld()) {
//            this.setMotion(this.getMotion().scale(0.98D));
//        }
//
//        if (this.interpTicksHead > 0) {
//            this.rotationYawHead = (float) ((double) this.rotationYawHead + MathHelper.wrapDegrees(this.interpTargetHeadYaw - (double) this.rotationYawHead) / (double) this.interpTicksHead);
//            --this.interpTicksHead;
//        }
//
//        Vec3d v = this.getMotion();
//        double vX = v.x;
//        double vY = v.y;
//        double vZ = v.z;
//        if (Math.abs(v.x) < 0.003D) {
//            vX = 0.0D;
//        }
//
//        if (Math.abs(v.y) < 0.003D) {
//            vY = 0.0D;
//        }
//
//        if (Math.abs(v.z) < 0.003D) {
//            vZ = 0.0D;
//        }
//
//        this.setMotion(vX, vY, vZ);
//        this.world.getProfiler().startSection("ai");
//        if (this.isMovementBlocked()) {
//            this.isJumping = false;
//            this.moveStrafing = 0.0F;
//            this.moveForward = 0.0F;
//        } else if (this.isServerWorld()) {
//            this.world.getProfiler().startSection("newAi");
//            this.updateEntityActionState();
//            this.world.getProfiler().endSection();
//        }
//
//        this.world.getProfiler().endSection();
//        this.world.getProfiler().startSection("jump");
//        if (this.isJumping) {
//            if (!(this.submergedHeight > 0.0D) || this.onGround && !(this.submergedHeight > 0.4D)) {
//                if (this.isInLava()) {
//                    this.handleFluidJump(FluidTags.LAVA);
//                } else if ((this.onGround || this.submergedHeight > 0.0D && this.submergedHeight <= 0.4D) && this.jumpTicks == 0) {
//                    this.jump();
//                    this.jumpTicks = 10;
//                }
//            } else {
//                this.handleFluidJump(FluidTags.WATER);
//            }
//        } else {
//            this.jumpTicks = 0;
//        }
//
//        this.world.getProfiler().endSection();
//        this.world.getProfiler().startSection("travel");
//        this.moveStrafing *= 0.98F;
//        this.moveForward *= 0.98F;
//        this.updateElytra();
//        AxisAlignedBB axisalignedbb = this.getBoundingBox();
//        this.travel(new Vec3d((double) this.moveStrafing, (double) this.moveVertical, (double) this.moveForward));
//        this.world.getProfiler().endSection();
//        this.world.getProfiler().startSection("push");
//        if (this.spinAttackDuration > 0) {
//            --this.spinAttackDuration;
//            this.updateSpinAttack(axisalignedbb, this.getBoundingBox());
//        }
//
//        this.collideWithNearbyEntities();
//        this.world.getProfiler().endSection();
//    }
//
//    @Override
//    public void travel(Vec3d moveIn) {
//        if (this.isServerWorld() || this.canPassengerSteer()) {
//            double gravityValue = 0.08D;
//            IAttributeInstance gravity = this.getAttribute(ENTITY_GRAVITY);
//            boolean falling = this.getMotion().y <= 0.0D;
//            if (falling && this.isPotionActive(Effects.SLOW_FALLING)) {
//                if (!gravity.hasModifier(SLOW_FALLING)) gravity.applyModifier(SLOW_FALLING);
//                this.fallDistance = 0.0F;
//            } else if (gravity.hasModifier(SLOW_FALLING)) {
//                gravity.removeModifier(SLOW_FALLING);
//            }
//            gravityValue = gravity.getValue();
//
//            if (!this.isInWater() || this instanceof PlayerEntity && ((PlayerEntity) this).abilities.isFlying) {
//                if (!this.isInLava() || this instanceof PlayerEntity && ((PlayerEntity) this).abilities.isFlying) {
//                    if (this.isElytraFlying()) {
//                        Vec3d v = this.getMotion();
//                        if (v.y > -0.5D) {
//                            this.fallDistance = 1.0F;
//                        }
//
//                        Vec3d look = this.getLookVec();
//                        float pitchRadians = this.rotationPitch * ((float) Math.PI / 180F);
//                        double horizontalLook = Math.sqrt(look.x * look.x + look.z * look.z);
//                        double horizontalVel = Math.sqrt(horizontalMag(v));
//                        double lookLen = look.length();
//                        float pitchHorz = MathHelper.cos(pitchRadians);
//                        pitchHorz = (float) ((double) pitchHorz * (double) pitchHorz * Math.min(1.0D, lookLen / 0.4D));
//                        v = this.getMotion().add(0.0D, gravityValue * (-1.0D + (double) pitchHorz * 0.75D), 0.0D);
//                        if (v.y < 0.0D && horizontalLook > 0.0D) {
//                            double d3 = v.y * -0.1D * (double) pitchHorz;
//                            v = v.add(look.x * d3 / horizontalLook, d3, look.z * d3 / horizontalLook);
//                        }
//
//                        if (pitchRadians < 0.0F && horizontalLook > 0.0D) {
//                            double d13 = horizontalVel * (double) (-MathHelper.sin(pitchRadians)) * 0.04D;
//                            v = v.add(-look.x * d13 / horizontalLook, d13 * 3.2D, -look.z * d13 / horizontalLook);
//                        }
//
//                        if (horizontalLook > 0.0D) {
//                            v = v.add((look.x / horizontalLook * horizontalVel - v.x) * 0.1D, 0.0D, (look.z / horizontalLook * horizontalVel - v.z) * 0.1D);
//                        }
//
//                        this.setMotion(v.mul((double) 0.99F, (double) 0.98F, (double) 0.99F));
//                        this.move(MoverType.SELF, this.getMotion());
//                        if (this.collidedHorizontally && !this.world.isRemote) {
//                            double d14 = Math.sqrt(horizontalMag(this.getMotion()));
//                            double d4 = horizontalVel - d14;
//                            float f4 = (float) (d4 * 10.0D - 3.0D);
//                            if (f4 > 0.0F) {
//                                this.playSound(this.getFallSound((int) f4), 1.0F, 1.0F);
//                                this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f4);
//                            }
//                        }
//
//                        if (this.onGround && !this.world.isRemote) {
//                            this.setFlag(7, false);
//                        }
//                    } else {
//                        BlockPos blockpos = this.getPositionUnderneath();
//                        float slipperiness = this.world.getBlockState(blockpos).getSlipperiness(world, blockpos, this);
//                        float slowFactor = this.onGround ? slipperiness * 0.91F : 0.91F;
//                        this.moveRelative(this.getRelevantMoveFactor(slipperiness), moveIn);
//                        this.setMotion(this.handleOnClimbable(this.getMotion()));
//                        this.move(MoverType.SELF, this.getMotion());
//                        Vec3d newMotion = this.getMotion();
//                        if ((this.collidedHorizontally || this.isJumping) && this.isOnLadder()) {
//                            newMotion = new Vec3d(newMotion.x, 0.2D, newMotion.z);
//                        }
//
//                        double verticalMotion = newMotion.y;
//                        if (this.isPotionActive(Effects.LEVITATION)) {
//                            verticalMotion += (0.05D * (double) (this.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1) - newMotion.y) * 0.2D;
//                            this.fallDistance = 0.0F;
//                        } else if (this.world.isRemote && !this.world.isBlockLoaded(blockpos)) {
//                            if (this.getPosY() > 0.0D) {
//                                verticalMotion = -0.1D;
//                            } else {
//                                verticalMotion = 0.0D;
//                            }
//                        } else if (!this.hasNoGravity()) {
//                            verticalMotion -= gravityValue;
//                        }
//
//                        this.setMotion(newMotion.x * (double) slowFactor, verticalMotion * (double) 0.98F, newMotion.z * (double) slowFactor);
//                    }
//                } else {
//                    double y = this.getPosY();
//                    this.moveRelative(0.02F, moveIn);
//                    this.move(MoverType.SELF, this.getMotion());
//                    this.setMotion(this.getMotion().scale(0.5D));
//                    if (!this.hasNoGravity()) {
//                        this.setMotion(this.getMotion().add(0.0D, -gravityValue / 4.0D, 0.0D));
//                    }
//
//                    Vec3d vec3d4 = this.getMotion();
//                    if (this.collidedHorizontally && this.isOffsetPositionInLiquid(vec3d4.x, vec3d4.y + (double) 0.6F - this.getPosY() + y, vec3d4.z)) {
//                        this.setMotion(vec3d4.x, (double) 0.3F, vec3d4.z);
//                    }
//                }
//            }
//            else {
//                //entity in water
//                double y = this.getPosY();
//                float drag = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
//                float speedFactor = 0.02F;
//                float depthStriderModifier = (float) EnchantmentHelper.getDepthStriderModifier(this);
//                if (depthStriderModifier > 3.0F) {
//                    depthStriderModifier = 3.0F;
//                }
//
//                if (!this.onGround) {
//                    depthStriderModifier *= 0.5F;
//                }
//
//                if (depthStriderModifier > 0.0F) {
//                    drag += (0.54600006F - drag) * depthStriderModifier / 3.0F;
//                    speedFactor += (this.getAIMoveSpeed() - speedFactor) * depthStriderModifier / 3.0F;
//                }
//
//                if (this.isPotionActive(Effects.DOLPHINS_GRACE)) {
//                    drag = 0.96F;
//                }
//
//                speedFactor *= (float) this.getAttribute(SWIM_SPEED).getValue();
//                this.moveRelative(speedFactor, moveIn);
//                this.move(MoverType.SELF, this.getMotion());
//                Vec3d motion = this.getMotion();
//                if (this.collidedHorizontally && this.isOnLadder()) {
//                    motion = new Vec3d(motion.x, 0.2D, motion.z);
//                }
//
//                this.setMotion(motion.mul((double) drag, (double) 0.8F, (double) drag));
//                if (!this.hasNoGravity() && !this.isSprinting()) {
//                    Vec3d newMotion = this.getMotion();
//                    double d2;
//                    if (falling && Math.abs(newMotion.y - 0.005D) >= 0.003D && Math.abs(newMotion.y - gravityValue / 16.0D) < 0.003D) {
//                        d2 = -0.003D;
//                    } else {
//                        d2 = newMotion.y - gravityValue / 16.0D;
//                    }
//
//                    this.setMotion(newMotion.x, d2, newMotion.z);
//                }
//
//                Vec3d v = this.getMotion();
//                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(v.x, v.y + (double) 0.6F - this.getPosY() + y, v.z)) {
//                    this.setMotion(v.x, (double) 0.3F, v.z);
//                }
//            }
//        }
//
//        this.prevLimbSwingAmount = this.limbSwingAmount;
//        double xDiff = this.getPosX() - this.prevPosX;
//        double zDiff = this.getPosZ() - this.prevPosZ;
//        double yDiff = this instanceof IFlyingAnimal ? this.getPosY() - this.prevPosY : 0.0D;
//        float movementFactor = MathHelper.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff) * 4.0F;
//        if (movementFactor > 1.0F) {
//            movementFactor = 1.0F;
//        }
//
//        this.limbSwingAmount += (movementFactor - this.limbSwingAmount) * 0.4F;
//        this.limbSwing += this.limbSwingAmount;
//    }
//
//    @Override
//    public void move(MoverType typeIn, Vec3d motionIn) {
//        if (this.noClip) {
//            this.setBoundingBox(this.getBoundingBox().offset(motionIn));
//            this.resetPositionToBB();
//        } else {
//            if (typeIn == MoverType.PISTON) {
//                motionIn = this.handlePistonMovement(motionIn);
//                if (motionIn.equals(Vec3d.ZERO)) {
//                    return;
//                }
//            }
//
//            this.world.getProfiler().startSection("move");
//            if (this.motionMultiplier.lengthSquared() > 1.0E-7D) {
//                motionIn = motionIn.mul(this.motionMultiplier);
//                this.motionMultiplier = Vec3d.ZERO;
//                this.setMotion(Vec3d.ZERO);
//            }
//
//            motionIn = this.maybeBackOffFromEdge(motionIn, typeIn);
//            Vec3d actualMotion = this.getAllowedMovement(motionIn);
//            if (actualMotion.lengthSquared() > 1.0E-7D) {
//                this.setBoundingBox(this.getBoundingBox().offset(actualMotion));
//                this.resetPositionToBB();
//            }
//
//            this.world.getProfiler().endSection();
//            this.world.getProfiler().startSection("rest");
//            this.collidedHorizontally = !MathHelper.epsilonEquals(motionIn.x, actualMotion.x) || !MathHelper.epsilonEquals(motionIn.z, actualMotion.z);
//            this.collidedVertically = motionIn.y != actualMotion.y;
//            this.onGround = this.collidedVertically && motionIn.y < 0.0D;
//            this.collided = this.collidedHorizontally || this.collidedVertically;
//            BlockPos blockpos = this.getOnPosition();
//            BlockState blockstate = this.world.getBlockState(blockpos);
//            this.updateFallState(actualMotion.y, this.onGround, blockstate, blockpos);
//            Vec3d prevMotion = this.getMotion();
//            if (motionIn.x != actualMotion.x) {
//                this.setMotion(0.0D, prevMotion.y, prevMotion.z);
//            }
//
//            if (motionIn.z != actualMotion.z) {
//                this.setMotion(prevMotion.x, prevMotion.y, 0.0D);
//            }
//
//            Block block = blockstate.getBlock();
//            if (motionIn.y != actualMotion.y) {
//                block.onLanded(this.world, this);
//            }
//
//            if (this.onGround && !this.isSteppingCarefully()) {
//                block.onEntityWalk(this.world, blockpos, this);
//            }
//
//            if (this.canTriggerWalking() && !this.isPassenger()) {
//                double movedX = actualMotion.x;
//                double movedY = actualMotion.y;
//                double movedZ = actualMotion.z;
//                if (block != Blocks.LADDER && block != Blocks.SCAFFOLDING) {
//                    movedY = 0.0D;
//                }
//
//                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt(horizontalMag(actualMotion)) * 0.6D);
//                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt(movedX * movedX + movedY * movedY + movedZ * movedZ) * 0.6D);
//                if (this.distanceWalkedOnStepModified > this.nextStepDistance && !blockstate.isAir(this.world, blockpos)) {
//                    this.nextStepDistance = this.determineNextStepDistance();
//                    if (this.isInWater()) {
//                        Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
//                        float f = entity == this ? 0.35F : 0.4F;
//                        Vec3d entityV = entity.getMotion();
//                        float f1 = MathHelper.sqrt(entityV.x * entityV.x * (double)0.2F + entityV.y * entityV.y + entityV.z * entityV.z * (double)0.2F) * f;
//                        if (f1 > 1.0F) {
//                            f1 = 1.0F;
//                        }
//
//                        this.playSwimSound(f1);
//                    } else {
//                        this.playStepSound(blockpos, blockstate);
//                    }
//                } else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && blockstate.isAir(this.world, blockpos)) {
//                    this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
//                }
//            }
//
//            try {
//                this.inLava = false;
//                this.doBlockCollisions();
//            } catch (Throwable throwable) {
//                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
//                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
//                this.fillCrashReport(crashreportcategory);
//                throw new ReportedException(crashreport);
//            }
//
//            this.setMotion(this.getMotion().mul((double)this.getSpeedFactor(), 1.0D, (double)this.getSpeedFactor()));
//            boolean flag = this.isInWaterRainOrBubbleColumn();
//            if (this.world.isFlammableWithin(this.getBoundingBox().shrink(0.001D))) {
//                if (!flag) {
//                    ++this.fire;
//                    if (this.fire == 0) {
//                        this.setFire(8);
//                    }
//                }
//
//                this.dealFireDamage(1);
//            } else if (this.fire <= 0) {
//                this.fire = -this.getFireImmuneTicks();
//            }
//
//            if (flag && this.isBurning()) {
//                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
//                this.fire = -this.getFireImmuneTicks();
//            }
//
//            this.world.getProfiler().endSection();
//        }
//    }
//
//    @Override
//    public void moveRelative(float factor, Vec3d relative) {
//        Vec3d absoluteMotion = getAbsoluteMotion(relative, factor, this.rotationYaw);
//        this.setMotion(this.getMotion().add(absoluteMotion));
//    }
//
//    private float getRelevantMoveFactor(float slipperiness) {
//        return this.onGround ? this.getAIMoveSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness)) : this.jumpMovementFactor;
//    }
//
//    private static Vec3d getAbsoluteMotion(Vec3d relative, float factor, float yaw) {
//        double distance = relative.lengthSquared();
//        if (distance < 1.0E-7D) {
//            return Vec3d.ZERO;
//        } else {
//            Vec3d v = (distance > 1.0D ? relative.normalize() : relative).scale((double) factor);
//            float s = MathHelper.sin(yaw * ((float) Math.PI / 180F));
//            float c = MathHelper.cos(yaw * ((float) Math.PI / 180F));
//            return new Vec3d(v.x * (double) c - v.z * (double) s, v.y, v.z * (double) c + v.x * (double) s);
//        }
//    }
//
//    private Vec3d handleOnClimbable(Vec3d motion) {
//        if (this.isOnLadder()) {
//            this.fallDistance = 0.0F;
//            float f = 0.15F;
//            double d0 = MathHelper.clamp(motion.x, (double)-0.15F, (double)0.15F);
//            double d1 = MathHelper.clamp(motion.z, (double)-0.15F, (double)0.15F);
//            double d2 = Math.max(motion.y, (double)-0.15F);
//            if (d2 < 0.0D && this.getBlockState().getBlock() != Blocks.SCAFFOLDING && this.isSuppressingSlidingDownLadder() && this instanceof PlayerEntity) {
//                d2 = 0.0D;
//            }
//
//            motion = new Vec3d(d0, d2, d1);
//        }
//
//        return motion;
//    }
//
//    //updateDistance in MCP
//    protected float turnHead(float bodyRotation, float headRotation) {
//        float bodyYawDiff = MathHelper.wrapDegrees(bodyRotation - this.renderYawOffset);
//        this.renderYawOffset += bodyYawDiff * 0.3F;
//
//        //difference of the direction the body is heading and the movement
//        float actualYawDiff = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
//        boolean goingBackwards = actualYawDiff < -90.0F || actualYawDiff >= 90.0F;
//        if (actualYawDiff < -75.0F) {
//            actualYawDiff = -75.0F;
//        }
//
//        if (actualYawDiff >= 75.0F) {
//            actualYawDiff = 75.0F;
//        }
//
//        //limits body rotation to be within 75 degrees of yaw
//        this.renderYawOffset = this.rotationYaw - actualYawDiff;
//
//        //but adds some extra body rotation if it's over 50 degrees differnce
//        if (actualYawDiff * actualYawDiff > 2500.0F) {
//            this.renderYawOffset += actualYawDiff * 0.2F;
//        }
//
//        if (goingBackwards) {
//            headRotation *= -1.0F;
//        }
//
//        return headRotation;
//    }
//}