package com.davigj.onion_onion.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OOConfig {
    public static class Common {
        public final ModConfigSpec.ConfigValue<Boolean> onionFun;
        public final ModConfigSpec.ConfigValue<Boolean> bigOnion;
        public final ModConfigSpec.ConfigValue<Integer> onionAOE;
        public final ModConfigSpec.ConfigValue<Boolean> onionDamage;
        public final ModConfigSpec.ConfigValue<Double> ghastCry;
        public final ModConfigSpec.ConfigValue<Double> cryingObby;
        public final ModConfigSpec.ConfigValue<Double> weepingVines;

        Common (ModConfigSpec.Builder builder) {
            builder.push("config");
            onionFun = builder.comment("Do onions have block or entity reactions when cut").translation("onion_onion.configuration.onion_fun").define("Onion fun", true);
            bigOnion = builder.comment("Do Big Onion blocks from Overweight Farming trigger the effects of cutting onions when peeled").translation("onion_onion.configuration.big_onion").define("Big onion reactions", true);
            onionAOE = builder.comment("The range in which blocks and entities are affected by cutting onions.").translation("onion_onion.configuration.onion_aoe").defineInRange("Onion chop radius", 2, 0, 16);
            builder.push("entity_changes");
            onionDamage = builder.comment("Do entities take damage from cutting onions nearby").translation("onion_onion.configuration.onion_damage").define("Onion damage", true);
            ghastCry = builder.comment("Chance that ghasts drop a tear when cutting onions nearby").translation("onion_onion.configuration.ghast_cry").define("Ghasts cry", 0.5);
            builder.pop();
            builder.push("block_changes");
            cryingObby = builder.comment("Chance that obsidian converts into crying obsidian").translation("onion_onion.configuration.crying_obby").define("Crying obby", 0.25);
            weepingVines = builder.comment("Chance that weeping vines grow").translation("onion_onion.configuration.weeping_vines").define("Vines weep", 0.75);
            builder.pop();
            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final OOConfig.Common COMMON;


    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(OOConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
