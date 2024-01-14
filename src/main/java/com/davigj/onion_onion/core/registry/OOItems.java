package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OOItems {
    public static final ItemSubRegistryHelper HELPER = OnionOnion.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> ONION_SLICE = HELPER.createItem("onion_slice", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.6F).build())));

    public static final RegistryObject<Item> ONION_RINGS = HELPER.createItem("onion_rings", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.7F).build())));

    public static final RegistryObject<Item> MOTLEY_GRILL_BLOCK = HELPER.createItem("motley_grill_block", () -> new BlockItem(
            OOBlocks.MOTLEY_GRILL_BLOCK.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.IRON_INGOT).tab(CreativeModeTab.TAB_FOOD)));

    public static final RegistryObject<Item> MOTLEY_GRILL = HELPER.createItem("motley_grill", () -> new ConsumableItem(
            new Item.Properties().food((new FoodProperties.Builder()).nutrition(9).saturationMod(0.7F)
                    .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 180 * 20), 1.0F).build())
                    .craftRemainder(Items.BOWL).stacksTo(16).tab(CreativeModeTab.TAB_FOOD), true));
}
