package com.davigj.onion_onion.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;

public class OOConstants {
    public static final Map<Block, Block> OBBY_MAP = new HashMap<>();
    public static void initializeObbyMap() {
        OBBY_MAP.put(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);
        if (ModList.get().isLoaded("frame_changer")) {
        /*
        OBBY_MAP.put(FCBlocks.OBSIDIAN_BRICKS.get(), FCBlocks.CRYING_OBSIDIAN_BRICKS.get());
        OBBY_MAP.put(FCBlocks.OBSIDIAN_PILLAR.get(), FCBlocks.CRYING_OBSIDIAN_PILLAR.get());
        OBBY_MAP.put(FCBlocks.CHISELED_OBSIDIAN.get(), FCBlocks.CRYING_CHISELED_OBSIDIAN.get());
        OBBY_MAP.put(FCBlocks.POLISHED_OBSIDIAN.get(), FCBlocks.CRYING_POLISHED_OBSIDIAN.get());

        OBBY_MAP.put(FCBlocks.OBSIDIAN_BRICK_SLAB.get(), FCBlocks.CRYING_OBSIDIAN_BRICK_SLAB.get());
        OBBY_MAP.put(FCBlocks.POLISHED_OBSIDIAN_SLAB.get(), FCBlocks.CRYING_POLISHED_OBSIDIAN_SLAB.get());

        OBBY_MAP.put(FCBlocks.OBSIDIAN_BRICK_STAIRS.get(), FCBlocks.CRYING_OBSIDIAN_BRICK_STAIRS.get());
        OBBY_MAP.put(FCBlocks.POLISHED_OBSIDIAN_STAIRS.get(), FCBlocks.CRYING_POLISHED_OBSIDIAN_STAIRS.get());

        OBBY_MAP.put(FCBlocks.OBSIDIAN_BRICK_WALL.get(), FCBlocks.CRYING_OBSIDIAN_BRICK_WALL.get());
        OBBY_MAP.put(FCBlocks.POLISHED_OBSIDIAN_WALL.get(), FCBlocks.CRYING_POLISHED_OBSIDIAN_WALL.get());
         */
        }
    }

}
