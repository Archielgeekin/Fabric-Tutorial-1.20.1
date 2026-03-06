package net.archie.tutorial.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity; // Added for targeting
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TutorialEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TutorialEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // 1. Updated Attributes: High Health + Fast Speed + Attack Damage
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D) // 50 Hearts (Big but not huge)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f) // Matches player sprint speed
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)   // 4 Hearts of damage per hit
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D); // Sees you from far away
    }

    // 2. Updated Goals: Make it Hostile
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        // This makes it actually swing and hit the player
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));

        // Hostile Targeting: Look for Players and target them
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    // 3. Animations (Kept the same logic)
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, event -> {
            if (event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("animation.tutorial_entity.walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.tutorial_entity.idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
