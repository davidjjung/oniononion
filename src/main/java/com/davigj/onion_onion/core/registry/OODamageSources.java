package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.core.OnionOnion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class OODamageSources {
    public static final ResourceKey<DamageType> ONION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(OnionOnion.MOD_ID, new DamageType("onion_damage", DamageScaling.NEVER, 0).msgId()));

}
