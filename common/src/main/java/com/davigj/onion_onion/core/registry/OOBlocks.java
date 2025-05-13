package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.common.block.MotleyGrillBlock;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

import static com.davigj.onion_onion.core.OnionOnion.MOD_ID;

public class OOBlocks {
    public static final DeferredRegister<Block> HELPER = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static final Supplier<Block> MOTLEY_GRILL_BLOCK = HELPER.register("motley_grill_block", () -> new MotleyGrillBlock(
            Block.Properties.ofFullCopy(Blocks.CAKE), OOItems.MOTLEY_GRILL, true));

}