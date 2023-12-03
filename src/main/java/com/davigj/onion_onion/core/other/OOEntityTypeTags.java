package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OOEntityTypeTags {
    public static final TagKey<EntityType<?>> UNAFFECTED_BY_ONIONS = entityTypeTag("unaffected_by_onions");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagUtil.entityTypeTag(OnionOnion.MOD_ID, name);
    }
}
