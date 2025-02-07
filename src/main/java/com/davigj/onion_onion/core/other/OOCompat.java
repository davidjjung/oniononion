package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.registry.OOItems;
import com.teamabnormals.blueprint.core.util.DataUtil;

public class OOCompat {
    public static void registerCompat() {
        registerCompostables();
    }

    private static void registerCompostables() {
        DataUtil.registerCompostable(OOItems.ONION_RINGS.get(), 0.3F);
    }
}
