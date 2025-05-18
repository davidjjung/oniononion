package com.davigj.onion_onion.neoforge;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class OONFDataMapUtil {
    private final static Logger LOGGER = LogManager.getLogger(OnionOnion.MOD_ID);

    public record CryingData(String result) {
        public static final Codec<CryingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("result").forGetter(CryingData::result)
        ).apply(instance, CryingData::new));
    }

    public static final DataMapType<Block, CryingData> WEEPING_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath("onion_onion", "crying_conversions"), Registries.BLOCK, CryingData.CODEC
    ).build();

    private static Supplier<Block> getConversionBlock(String fullId) {
        String[] parts = fullId.split(":");
        if (parts.length != 2) {
            LOGGER.warn("Improperly formatted config for crying conversion block. String should be formatted 'modid:blockID'. Defaulting to minecraft:crying_obsidian");
            return () -> Blocks.CRYING_OBSIDIAN;
        }
        String modid = parts[0];
        String blockID = parts[1];
        if (!ModList.get().isLoaded(modid) && modid != null) {
            LOGGER.warn("Mod '" + modid + "' not loaded, invalid crying conversion blockID. String should be formatted 'modID:blockID'. Defaulting to minecraft:crying_obsidian");
            return () -> Blocks.CRYING_OBSIDIAN;
        }
        assert modid != null;
        ResourceLocation block = ResourceLocation.fromNamespaceAndPath(modid, blockID);
        if (BuiltInRegistries.BLOCK.get(block) == Blocks.AIR) {
            LOGGER.warn("Invalid crying conversion blockID. String should be formatted 'modID:blockID'. Defaulting to minecraft:crying_obsidian");
            return () -> Blocks.CRYING_OBSIDIAN;
        }
        return (ModList.get().isLoaded(modid) ? () -> BuiltInRegistries.BLOCK.get(block) : () -> null);
    }

    public static void weepingConversions(BlockPos blockPos, Level level) {
        int radius = OOConfig.COMMON.onionAOE.get();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = blockPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    RandomSource random = level.getRandom();
                    Holder<Block> holder = state.getBlockHolder();
                    CryingData data = holder.getData(WEEPING_DATA);
                    if (data != null && random.nextDouble() <= OOConfig.COMMON.cryingObby.get()) {
                        if (level instanceof ServerLevel server) {
                            for (int i = 0; i < 4; i++) {
                                server.sendParticles(ParticleTypes.DRAGON_BREATH, pos.getX() + random.nextDouble() - 0.5,
                                        pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble() - 0.5, 1, 0, 0, 0, 0.0);
                            }
                            level.setBlock(pos, getConversionBlock(data.result).get().withPropertiesOf(state), 3);
                        }
                    }
                }
            }
        }
    }
}
