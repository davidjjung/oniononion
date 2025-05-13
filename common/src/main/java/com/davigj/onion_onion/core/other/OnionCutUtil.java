package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.PlatformMethods;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import com.davigj.onion_onion.core.registry.OOCriteriaTriggers;
import com.davigj.onion_onion.core.registry.OODamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.davigj.onion_onion.core.other.OOConstants.OBBY_MAP;

public class OnionCutUtil {
    public static void affectLivingEntities(BlockPos pos, Player player, Level level, boolean cuttingBoard) {
        RandomSource random = level.getRandom();
        int radius = Math.min(OOConfig.COMMON.onionAOE.get(), 16);
        AABB boundingBox = new AABB(pos).inflate(radius, radius, radius);
        int advancementWorthy = 0;
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, boundingBox,
                (living) -> living != null && living.isAlive());
        for (LivingEntity livingEntity : livingEntities) {
            if (!livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(OOItemTags.ONION_PROOF)) {
                if (!livingEntity.getType().is(OOEntityTypeTags.UNAFFECTED_BY_ONIONS) && OOConfig.COMMON.onionDamage.get()) {
                    livingEntity.hurt(livingEntity.damageSources().source(OODamageSources.ONION), 1.0F);
                    advancementWorthy++;
                    if (level instanceof ServerLevel server) {
                        for (int i = 0; i < 4; i++) {
                            server.sendParticles(ParticleTypes.SPLASH, livingEntity.getX() + random.nextDouble() - 0.5,
                                    livingEntity.getEyeY(), livingEntity.getZ() + random.nextDouble() - 0.5, 1, 0, 0, 0, 0.0);
                        }
                    }
                }
            }

            if (livingEntity instanceof Ghast && random.nextDouble() <= OOConfig.COMMON.ghastCry.get()) {
                ItemEntity ghastTearEntity = new ItemEntity(level, livingEntity.getX(), livingEntity.getY(),
                        livingEntity.getZ(), new ItemStack(Items.GHAST_TEAR));
                level.addFreshEntity(ghastTearEntity);
                if (!(PlatformMethods.isFakePlayer(player)) && player instanceof ServerPlayer serverPlayer) {
                    if (!player.getCommandSenderWorld().isClientSide()) {
                        OOCriteriaTriggers.ONION_GHAST.get().trigger((serverPlayer));
                    }
                }
            }
        }
        if (player != null && cuttingBoard && advancementWorthy >= 10 && player.hasEffect(MobEffects.INVISIBILITY)) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (!player.getCommandSenderWorld().isClientSide()) {
                    OOCriteriaTriggers.ONION_NINJA.get().trigger((serverPlayer));
                }
            }
        }
    }

    public static void affectBlocks(BlockPos blockPos, Level level) {
        int radius = OOConfig.COMMON.onionAOE.get();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = blockPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    BlockState underState = level.getBlockState(pos.below());
                    RandomSource random = level.getRandom();
                    if (state.is(Blocks.WEEPING_VINES) && underState.isAir() && random.nextDouble() <= OOConfig.COMMON.weepingVines.get()) {
                        if (level instanceof ServerLevel server) {
                            for (int i = 0; i < 4; i++) {
                                server.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.below().getX() + random.nextDouble() - 0.5,
                                        pos.below().getY() + random.nextDouble(), pos.below().getZ() + random.nextDouble() - 0.5, 1, 0, 0, 0, 0.0);
                            }
                            level.setBlockAndUpdate(pos.below(), Blocks.WEEPING_VINES.defaultBlockState());
                        }
                    } else if (OBBY_MAP.containsKey(state.getBlock()) && random.nextDouble() <= OOConfig.COMMON.cryingObby.get()) {
                        if (level instanceof ServerLevel server) {
                            for (int i = 0; i < 4; i++) {
                                server.sendParticles(ParticleTypes.DRAGON_BREATH, pos.getX() + random.nextDouble() - 0.5,
                                        pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble() - 0.5, 1, 0, 0, 0, 0.0);
                            }
                            level.setBlock(pos, OBBY_MAP.get(state.getBlock()).withPropertiesOf(state), 3);
                        }
                    }
                }
            }
        }
    }
}
