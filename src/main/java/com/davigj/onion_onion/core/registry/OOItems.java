package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OOItems {
    public static final ItemSubRegistryHelper HELPER = OnionOnion.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> ONION_SLICE = HELPER.createItem("onion_slice", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.6F).build())));

    public static final RegistryObject<Item> ONION_RINGS = HELPER.createItem("onion_rings", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.7F).build())));
}
