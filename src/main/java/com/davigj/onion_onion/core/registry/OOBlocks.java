package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.common.block.MotleyGrillBlock;
import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.FeastBlock;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OOBlocks {
    public static final BlockSubRegistryHelper HELPER = OnionOnion.REGISTRY_HELPER.getBlockSubHelper();
    public static final RegistryObject<Block> MOTLEY_GRILL_BLOCK = HELPER.createBlockNoItem("motley_grill_block", () -> new MotleyGrillBlock(
            Block.Properties.copy(Blocks.CAKE), OOItems.MOTLEY_GRILL, true));

}