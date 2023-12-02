package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class OOItemTags {
    public static final TagKey<Item> ONION_PROOF = itemTag("onion_proof_helmets");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(OnionOnion.MOD_ID, name);
    }
}
