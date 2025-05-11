package com.davigj.onion_onion.core.fabric;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.function.Supplier;

public class PlatformMethodsImpl {
    public static boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }

    public static void registerCompostable(Item item, float v) {
        CompostingChanceRegistry.INSTANCE.add(item, v);
    }
}
