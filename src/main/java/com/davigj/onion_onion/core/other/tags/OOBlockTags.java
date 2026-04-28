package com.davigj.onion_onion.core.other.tags;

import com.davigj.onion_onion.core.OnionOnion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class OOBlockTags {
    public static final TagKey<Block> TEARJERKERS = blockTag("tearjerkers");

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(OnionOnion.MOD_ID, name));
    }
}
