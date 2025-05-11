package com.davigj.onion_onion.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;

import static com.davigj.onion_onion.core.OnionOnion.MOD_ID;

public class OOCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> HELPER = DeferredRegister.create(MOD_ID, Registries.TRIGGER_TYPE);
    public static final RegistrySupplier<PlayerTrigger> ONION_NINJA = HELPER.register("onion_ninja", PlayerTrigger::new);
    public static final RegistrySupplier<PlayerTrigger> ONION_GHAST = HELPER.register("onion_ghast", PlayerTrigger::new);
}