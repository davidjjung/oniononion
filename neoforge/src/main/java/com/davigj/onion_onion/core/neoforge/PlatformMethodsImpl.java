package com.davigj.onion_onion.core.neoforge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.FakePlayer;

public class PlatformMethodsImpl {
    public static boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }

    public static void registerCompostable(Item item, float v) {
        // neo insists on this being a datamap
        return;
    }

    public static ItemStack getRemainder(ItemStack serving) {
        return serving.getCraftingRemainingItem();
    }
}
