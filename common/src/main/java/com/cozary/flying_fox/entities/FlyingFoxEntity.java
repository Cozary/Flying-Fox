package com.cozary.flying_fox.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class FlyingFoxEntity extends FlyingMob {
    public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(FlyingFoxEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(FlyingFoxEntity.class, EntityDataSerializers.STRING);
    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    AttackPhase attackPhase = AttackPhase.CIRCLE;

    public FlyingFoxEntity(EntityType<? extends FlyingMob> p_33101_, Level p_33102_) {
        super(p_33101_, p_33102_);
        this.xpReward = 5;
        this.moveControl = new FlyingFoxEntityMoveControl(this);
        this.lookControl = new FlyingFoxEntityLookControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public static boolean canFlyingFoxSpawn(EntityType<? extends FlyingFoxEntity> creeper, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        BlockPos blockpos = pos.below();
        return reason == MobSpawnType.SPAWNER || world.getBlockState(blockpos).isValidSpawn(world, blockpos, creeper);
    }

    public FoxType getFoxType() {
        return FoxType.byType(this.entityData.get(DATA_TYPE));
    }

    private void setFoxType(FoxType p_28929_) {
        this.entityData.set(DATA_TYPE, p_28929_.type);
    }

    @Override
    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FlyingFoxEntityAttackStrategyGoal());
        this.goalSelector.addGoal(2, new FlyingFoxEntitySweepAttackGoal());
        this.goalSelector.addGoal(3, new FlyingFoxEntityCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new FlyingFoxEntityAttackPlayerTargetGoal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 0);
        this.entityData.define(DATA_TYPE, FoxType.BROWN.type);
    }

    private void updateFlyingFoxEntitySizeInfo() {
        this.refreshDimensions();
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(6 + this.getFlyingFoxEntitySize());
    }

    public int getFlyingFoxEntitySize() {
        return this.entityData.get(ID_SIZE);
    }

    public void setFlyingFoxEntitySize(int p_33109_) {
        this.entityData.set(ID_SIZE, Mth.clamp(p_33109_, 0, 64));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.35F;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> p_33134_) {
        if (ID_SIZE.equals(p_33134_)) {
            this.updateFlyingFoxEntitySizeInfo();
        }

        super.onSyncedDataUpdated(p_33134_);
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            float f = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            float f1 = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.FOX_SNIFF, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }
        }
        if (this.hasCustomName() && "tails".equals(this.getName().getString())) {
            this.setFoxType(FoxType.YELLOW);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor p_33126_, @NotNull DifficultyInstance p_33127_, @NotNull MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setFlyingFoxEntitySize(this.random.nextInt(5)); //SIZE RANDOM
        return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag p_33132_) {
        super.readAdditionalSaveData(p_33132_);
        this.setFoxType(FoxType.byType(p_33132_.getString("Type")));
        if (p_33132_.contains("AX")) {
            this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
        }

        this.setFlyingFoxEntitySize(p_33132_.getInt("Size"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag p_33141_) {
        super.addAdditionalSaveData(p_33141_);
        p_33141_.putString("Type", this.getFoxType().type);
        p_33141_.putInt("AX", this.anchorPoint.getX());
        p_33141_.putInt("AY", this.anchorPoint.getY());
        p_33141_.putInt("AZ", this.anchorPoint.getZ());
        p_33141_.putInt("Size", this.getFlyingFoxEntitySize());
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent == SoundEvents.FOX_SCREECH) {
            this.playSound(soundevent, 2.0F, this.getVoicePitch());
        } else {
            super.playAmbientSound();
        }

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return SoundEvents.FOX_SLEEP;
        } else {
            if (!this.level().isDay() && this.random.nextFloat() < 0.1F) {
                List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D), EntitySelector.NO_SPECTATORS);
                if (list.isEmpty()) {
                    return SoundEvents.FOX_SCREECH;
                }
            }

            return SoundEvents.FOX_AMBIENT;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_28548_) {
        return SoundEvents.FOX_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public boolean canAttackType(@NotNull EntityType<?> p_33111_) {
        return true;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose p_33113_) {
        int i = this.getFlyingFoxEntitySize();
        EntityDimensions entitydimensions = super.getDimensions(p_33113_);
        float f = (entitydimensions.width + 0.2F * (float) i) / entitydimensions.width;
        return entitydimensions.scale(f);
    }

    public enum FoxType {
        YELLOW("yellow"),
        BROWN("brown");

        final String type;

        FoxType(String p_28967_) {
            this.type = p_28967_;
        }

        static FoxType byType(String p_28977_) {
            for (FoxType mushroomcow$mushroomtype : values()) {
                if (mushroomcow$mushroomtype.type.equals(p_28977_)) {
                    return mushroomcow$mushroomtype;
                }
            }

            return BROWN;
        }
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    static class FlyingFoxEntityLookControl extends LookControl {
        public FlyingFoxEntityLookControl(Mob p_33235_) {
            super(p_33235_);
        }

        public void tick() {
        }
    }

    class FlyingFoxEntityAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
                return false;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Animal> list = FlyingFoxEntity.this.level().getNearbyEntities(Animal.class, this.attackTargeting, FlyingFoxEntity.this, FlyingFoxEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for (Animal animal : list) {
                        if (animal instanceof Chicken || animal instanceof Rabbit) {
                            if (FlyingFoxEntity.this.canAttack(animal, TargetingConditions.DEFAULT)) {
                                FlyingFoxEntity.this.setTarget(animal);
                                return true;
                            }
                        }
                    }
                }

                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = FlyingFoxEntity.this.getTarget();
            return livingentity != null && FlyingFoxEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    class FlyingFoxEntityAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        public boolean canUse() {
            LivingEntity livingentity = FlyingFoxEntity.this.getTarget();
            return livingentity != null && FlyingFoxEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            FlyingFoxEntity.this.attackPhase = AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            FlyingFoxEntity.this.anchorPoint = FlyingFoxEntity.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, FlyingFoxEntity.this.anchorPoint).above(10 + FlyingFoxEntity.this.random.nextInt(20));
        }

        public void tick() {
            if (FlyingFoxEntity.this.attackPhase == AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    FlyingFoxEntity.this.attackPhase = AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + FlyingFoxEntity.this.random.nextInt(4)) * 20);
                    FlyingFoxEntity.this.playSound(SoundEvents.FOX_SCREECH, 1.0F, 0.95F + FlyingFoxEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            FlyingFoxEntity.this.anchorPoint = Objects.requireNonNull(FlyingFoxEntity.this.getTarget()).blockPosition().above(20 + FlyingFoxEntity.this.random.nextInt(20));
            if (FlyingFoxEntity.this.anchorPoint.getY() < FlyingFoxEntity.this.level().getSeaLevel()) {
                FlyingFoxEntity.this.anchorPoint = new BlockPos(FlyingFoxEntity.this.anchorPoint.getX(), FlyingFoxEntity.this.level().getSeaLevel() + 1, FlyingFoxEntity.this.anchorPoint.getZ());
            }

        }
    }

    class FlyingFoxEntityCircleAroundAnchorGoal extends FlyingFoxEntityMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        public boolean canUse() {
            return FlyingFoxEntity.this.getTarget() == null || FlyingFoxEntity.this.attackPhase == AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + FlyingFoxEntity.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + FlyingFoxEntity.this.random.nextFloat() * 9.0F;
            this.clockwise = FlyingFoxEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (FlyingFoxEntity.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + FlyingFoxEntity.this.random.nextFloat() * 9.0F;
            }

            if (FlyingFoxEntity.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (FlyingFoxEntity.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = FlyingFoxEntity.this.random.nextFloat() * 2.0F * (float) Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (FlyingFoxEntity.this.moveTargetPoint.y < FlyingFoxEntity.this.getY() && !FlyingFoxEntity.this.level().isEmptyBlock(FlyingFoxEntity.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (FlyingFoxEntity.this.moveTargetPoint.y > FlyingFoxEntity.this.getY() && !FlyingFoxEntity.this.level().isEmptyBlock(FlyingFoxEntity.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(FlyingFoxEntity.this.anchorPoint)) {
                FlyingFoxEntity.this.anchorPoint = FlyingFoxEntity.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
            FlyingFoxEntity.this.moveTargetPoint = Vec3.atLowerCornerOf(FlyingFoxEntity.this.anchorPoint).add(this.distance * Mth.cos(this.angle), -4.0F + this.height, this.distance * Mth.sin(this.angle));
        }
    }

    class FlyingFoxEntityMoveControl extends MoveControl {
        private float speed = 0.1F;

        public FlyingFoxEntityMoveControl(Mob p_33241_) {
            super(p_33241_);
        }

        public void tick() {
            if (FlyingFoxEntity.this.horizontalCollision) {
                FlyingFoxEntity.this.setYRot(FlyingFoxEntity.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            double d0 = FlyingFoxEntity.this.moveTargetPoint.x - FlyingFoxEntity.this.getX();
            double d1 = FlyingFoxEntity.this.moveTargetPoint.y - FlyingFoxEntity.this.getY();
            double d2 = FlyingFoxEntity.this.moveTargetPoint.z - FlyingFoxEntity.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > (double) 1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = FlyingFoxEntity.this.getYRot();
                float f1 = (float) Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(FlyingFoxEntity.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180F / (float) Math.PI));
                FlyingFoxEntity.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                FlyingFoxEntity.this.yBodyRot = FlyingFoxEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(f, FlyingFoxEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f4 = (float) (-(Mth.atan2(-d1, d3) * (double) (180F / (float) Math.PI)));
                FlyingFoxEntity.this.setXRot(f4);
                float f5 = FlyingFoxEntity.this.getYRot() + 90.0F;
                double d6 = (double) (this.speed * Mth.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double) (this.speed * Mth.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double) (this.speed * Mth.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = FlyingFoxEntity.this.getDeltaMovement();
                FlyingFoxEntity.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }

    abstract class FlyingFoxEntityMoveTargetGoal extends Goal {
        public FlyingFoxEntityMoveTargetGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return FlyingFoxEntity.this.moveTargetPoint.distanceToSqr(FlyingFoxEntity.this.getX(), FlyingFoxEntity.this.getY(), FlyingFoxEntity.this.getZ()) < 4.0D;
        }
    }

    class FlyingFoxEntitySweepAttackGoal extends FlyingFoxEntityMoveTargetGoal {
        private boolean isScaredOfCat;
        private int catSearchTick;

        public boolean canUse() {
            return FlyingFoxEntity.this.getTarget() != null && FlyingFoxEntity.this.attackPhase == AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = FlyingFoxEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player player) {
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (FlyingFoxEntity.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = FlyingFoxEntity.this.tickCount + 20;
                        List<Fox> list = FlyingFoxEntity.this.level().getEntitiesOfClass(Fox.class, FlyingFoxEntity.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        public void start() {
        }

        public void stop() {
            FlyingFoxEntity.this.setTarget(null);
            FlyingFoxEntity.this.attackPhase = AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingentity = FlyingFoxEntity.this.getTarget();
            if (livingentity != null) {
                FlyingFoxEntity.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
                if (FlyingFoxEntity.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                    FlyingFoxEntity.this.doHurtTarget(livingentity);
                    FlyingFoxEntity.this.attackPhase = AttackPhase.CIRCLE;
                    if (!FlyingFoxEntity.this.isSilent()) {
                        FlyingFoxEntity.this.level().levelEvent(1039, FlyingFoxEntity.this.blockPosition(), 0);
                    }
                } else if (FlyingFoxEntity.this.horizontalCollision || FlyingFoxEntity.this.hurtTime > 0) {
                    FlyingFoxEntity.this.attackPhase = AttackPhase.CIRCLE;
                }

            }
        }
    }
}
