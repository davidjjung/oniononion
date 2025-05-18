package com.davigj.onion_onion.neoforge;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.registry.OOItems;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.function.Supplier;

import static com.davigj.onion_onion.core.OnionOnion.MOD_ID;
import static com.davigj.onion_onion.neoforge.OONFDataMapUtil.WEEPING_DATA;

@Mod(MOD_ID)
public final class OnionOnionNeoForge {
    public OnionOnionNeoForge(IEventBus bus, ModContainer container) {
        OnionOnion.init();
        bus.addListener(this::commonSetup);
        bus.addListener(this::buildCreativeModeTabs);
        bus.addListener(this::registerDataMapTypes);
        container.registerConfig(ModConfig.Type.COMMON, OOConfig.COMMON_SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(OnionOnion::commonSetup);
    }

    @SubscribeEvent
    private void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (Supplier<Item> itemSupplier : OOItems.FOOD.reversed()) {
                event.insertAfter(Items.RABBIT_STEW.getDefaultInstance(), itemSupplier.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    @SubscribeEvent
    private void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(WEEPING_DATA);
    }

}
