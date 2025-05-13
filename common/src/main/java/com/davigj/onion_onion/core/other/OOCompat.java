package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.PlatformMethods;
import com.davigj.onion_onion.core.registry.OOItems;

public class OOCompat {
    public static void registerCompostables() {
        PlatformMethods.registerCompostable(OOItems.ONION_SLICE.get(), 0.3F);
    }
}
