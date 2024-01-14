package com.davigj.onion_onion.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OOConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> onionFun;
        public final ForgeConfigSpec.ConfigValue<Boolean> bigOnion;
        public final ForgeConfigSpec.ConfigValue<Integer> onionAOE;
        public final ForgeConfigSpec.ConfigValue<Boolean> onionDamage;
        public final ForgeConfigSpec.ConfigValue<Double> ghastCry;
        public final ForgeConfigSpec.ConfigValue<Double> cryingObby;
        public final ForgeConfigSpec.ConfigValue<Double> weepingVines;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("config");
            onionFun = builder.comment("Do onions have block or entity reactions when cut").define("Onion fun", true);
            bigOnion = builder.comment("Do Big Onion blocks from Overweight Farming trigger the effects of cutting onions when peeled").define("Big onion reactions", true);
            onionAOE = builder.comment("The range in which blocks and entities are affected by cutting onions. Capped at 16").define("Onion chop radius", 2);
            builder.push("entity_changes");
            onionDamage = builder.comment("Do entities take damage from cutting onions nearby").define("Onion damage", true);
            ghastCry = builder.comment("Chance that ghasts drop a tear when cutting onions nearby").define("Ghasts cry", 0.5);
            builder.pop();
            builder.push("block_changes");
            cryingObby = builder.comment("Chance that obsidian converts into crying obsidian").define("Crying obby", 0.25);
            weepingVines = builder.comment("Chance that weeping vines grow").define("Vines weep", 0.75);
            builder.pop();
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final OOConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(OOConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
