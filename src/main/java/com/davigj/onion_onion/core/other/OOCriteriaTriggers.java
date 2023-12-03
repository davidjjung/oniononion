package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID)
public class OOCriteriaTriggers {
    public static final EmptyTrigger ONION_NINJA = CriteriaTriggers.register(new EmptyTrigger(new ResourceLocation(OnionOnion.MOD_ID, "onion_ninja")));
    public static final EmptyTrigger ONION_GHAST = CriteriaTriggers.register(new EmptyTrigger(new ResourceLocation(OnionOnion.MOD_ID, "onion_ghast")));
}