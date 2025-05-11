package com.davigj.onion_onion.neoforge;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.other.OOEvents;
import com.davigj.onion_onion.core.registry.OOItems;
import fuzs.forgeconfigapiport.neoforge.api.forge.v4.ForgeConfigRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.function.Supplier;

import static com.davigj.onion_onion.core.OnionOnion.MOD_ID;

@Mod(MOD_ID)
public final class OnionOnionNeoForge {
    public OnionOnionNeoForge(IEventBus bus, ModContainer container) {
        // Run our common setup.
        OnionOnion.init();
        bus.addListener(this::commonSetup);
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, OOConfig.COMMON_SPEC);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(OnionOnion::commonSetup);
    }

    @SubscribeEvent
    private void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (Supplier<Item> itemSupplier : OOItems.FOOD) {
                event.insertAfter(Items.RABBIT_STEW.getDefaultInstance(), itemSupplier.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }
}
