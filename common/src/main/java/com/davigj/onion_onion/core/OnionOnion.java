package com.davigj.onion_onion.core;

import com.davigj.onion_onion.core.other.OOCompat;
import com.davigj.onion_onion.core.other.OOEvents;
import com.davigj.onion_onion.core.registry.OOBlocks;
import com.davigj.onion_onion.core.registry.OOCriteriaTriggers;
import com.davigj.onion_onion.core.registry.OOItems;
import dev.architectury.event.events.common.InteractionEvent;

import static com.davigj.onion_onion.core.other.OOConstants.initializeObbyMap;

public class OnionOnion {
    public static final String MOD_ID = "onion_onion";

    public static void init() {
        OOBlocks.HELPER.register();
        OOItems.HELPER.register();
        OOCriteriaTriggers.HELPER.register();
        InteractionEvent.RIGHT_CLICK_BLOCK.register(OOEvents::onPlayerRightClickBlock);
    }

    public static void commonSetup() {
        OOCompat.registerCompostables();
        initializeObbyMap();
    }
}