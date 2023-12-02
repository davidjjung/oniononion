package com.davigj.onion_onion.core.registry;

import com.davigj.onion_onion.core.OnionOnion;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OOBlocks {
    public static final BlockSubRegistryHelper HELPER = OnionOnion.REGISTRY_HELPER.getBlockSubHelper();
    //	public static final RegistryObject<Block> TEMPLATE_BLOCK = HELPER.createBlock("template_block", () -> new Block(Block.Properties.copy(Blocks.STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
}