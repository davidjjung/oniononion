package com.davigj.onion_onion.core.other.tags;

import com.davigj.onion_onion.core.OnionOnion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class OOItemTags {
    public static final TagKey<Item> ONION_PROOF = itemTag("onion_proof_helmets");
    public static final TagKey<Item> TEARJERKERS = itemTag("tearjerkers");
    public static final TagKey<Item> OWF_TEARJERKERS = itemTag("owf_tearjerkers");

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(OnionOnion.MOD_ID, name));
    }
}
