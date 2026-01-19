package com.davigj.onion_onion.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;
import java.util.function.Supplier;

import static com.davigj.onion_onion.core.OnionOnion.MOD_ID;

public class OOItems {
    public static final DeferredRegister<Item> HELPER = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static final Supplier<Item> ONION_SLICE = HELPER.register("onion_slice", () ->
            new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0.6F).build())));

    public static final Supplier<Item> ONION_RINGS = HELPER.register("onion_rings", () ->
            new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(2).saturationModifier(0.7F).build())));

    public static final Supplier<Item> MOTLEY_GRILL_BLOCK = HELPER.register("motley_grill_block", () -> new BlockItem(
            OOBlocks.MOTLEY_GRILL_BLOCK.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.IRON_INGOT)));

    public static final Supplier<Item> MOTLEY_GRILL = HELPER.register("motley_grill", () -> new ConsumableItem(
            new Item.Properties().food((new FoodProperties.Builder()).nutrition(9).saturationModifier(0.7F)
                    .effect(new MobEffectInstance(ModEffects.NOURISHMENT, 180 * 20), 1.0F).build())
                    .craftRemainder(Items.BOWL).stacksTo(16), true));


    public static List<Supplier<Item>> FOOD = List.of(ONION_SLICE, ONION_RINGS, MOTLEY_GRILL, MOTLEY_GRILL_BLOCK);
}
