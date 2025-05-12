package com.davigj.onion_onion.core;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlatformMethods {
    @ExpectPlatform
    public static boolean isFakePlayer(Player player) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerCompostable(Item item, float v) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getRemainder(ItemStack serving) {
        throw new AssertionError();
    }
}
