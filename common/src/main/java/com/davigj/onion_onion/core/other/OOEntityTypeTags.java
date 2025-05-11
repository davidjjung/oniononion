package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OnionOnion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OOEntityTypeTags {
    public static final TagKey<EntityType<?>> UNAFFECTED_BY_ONIONS = entityTypeTag("unaffected_by_onions");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(OnionOnion.MOD_ID, name) );
    }
}
