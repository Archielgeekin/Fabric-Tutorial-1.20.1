package net.archie.tutorial.entity;

import net.archie.tutorial.mod.TutorialMod;
import net.archie.tutorial.entity.custom.TutorialEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TutorialEntity> TUTORIAL_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("tutorial-mod", "tutorial_entity"), // Ensure "tutorial-mod" matches your MOD_ID
            EntityType.Builder.create(TutorialEntity::new, SpawnGroup.MONSTER)
                    .setDimensions(1f, 2f) // Change 'dimensions' to 'setDimensions'
                    .build("tutorial_entity")

    );

    public static void registerModEntities() {
        TutorialMod.LOGGER.info("Registering Entities for " + "tutorial-mod");
    }
}
