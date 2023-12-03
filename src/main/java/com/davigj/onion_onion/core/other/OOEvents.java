package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.registry.OODamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.overweightfarming.blocks.OverweightOnionBlock;
import net.orcinus.overweightfarming.init.OFBlocks;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID)
public class OOEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!OOConfig.COMMON.onionFun.get()) {
            return;
        }
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        BlockState clickedBlockState = player.level.getBlockState(event.getPos());
        RandomSource random = player.getRandom();
        if (ModList.get().isLoaded("overweight_farming") && OOConfig.COMMON.bigOnion.get()) {
            if (clickedBlockState.getBlock() == OFBlocks.OVERWEIGHT_ONION.get() &&
                    heldItem.getItem() instanceof AxeItem) {
                if (player.level.isClientSide) {
                    for (int i = 0; i < 3; i++) {
                        player.level.addParticle(ParticleTypes.SNEEZE, event.getPos().getX() + (2 * random.nextDouble()),
                                event.getPos().getY() + 0.5, event.getPos().getZ() + (2 * random.nextDouble()), 0, 0, 0);
                    }
                }
                affectLivingEntities(event.getPos(), player, false);
                affectBlocks(event.getPos(), player);
            }
        }
        if (clickedBlockState.getBlock() == ModBlocks.CUTTING_BOARD.get()) {
            BlockEntity tileEntity = player.level.getBlockEntity(event.getPos());
            if (tileEntity instanceof CuttingBoardBlockEntity board && board.getStoredItem().getItem() == vectorwing.farmersdelight.common.registry.ModItems.ONION.get()) {
                if (heldItem.getItem() instanceof KnifeItem) {
                    if (player.level.isClientSide) {
                        for (int i = 0; i < 3; i++) {
                            player.level.addParticle(ParticleTypes.SNEEZE, event.getPos().getX() + random.nextDouble(),
                                    event.getPos().getY() + 0.5, event.getPos().getZ() + random.nextDouble(), 0, 0, 0);
                        }
                    }
                    affectLivingEntities(event.getPos(), player, true);
                    affectBlocks(event.getPos(), player);
                }
            }
        }
    }

    public static void affectLivingEntities(BlockPos pos, Player player, boolean cuttingBoard) {
        RandomSource random = player.getRandom();
        int radius = Math.min(OOConfig.COMMON.onionAOE.get(), 16);
        AABB boundingBox = new AABB(pos).inflate(radius, radius, radius);
        int advancementWorthy = 0;
        List<LivingEntity> livingEntities = player.level.getEntitiesOfClass(LivingEntity.class, boundingBox,
                (living) -> living != null && living.isAlive());
        for (LivingEntity livingEntity : livingEntities) {
            if (!livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(OOItemTags.ONION_PROOF)) {
                if (!livingEntity.getType().is(OOEntityTypeTags.UNAFFECTED_BY_ONIONS) && OOConfig.COMMON.onionDamage.get()) {
                    livingEntity.hurt(OODamageSources.ONION, 1.0F);
                    advancementWorthy++;
                    if (player.level.isClientSide) {
                        for (int i = 0; i < 4; i++) {
                            player.level.addParticle(ParticleTypes.SPLASH, livingEntity.getX() + random.nextDouble() - 0.5,
                                    livingEntity.getEyeY(), livingEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                        }
                    }
                }
            }

            if (livingEntity instanceof Ghast && random.nextDouble() <= OOConfig.COMMON.ghastCry.get()) {
                ItemEntity ghastTearEntity = new ItemEntity(player.level, livingEntity.getX(), livingEntity.getY(),
                        livingEntity.getZ(), new ItemStack(Items.GHAST_TEAR));
                player.level.addFreshEntity(ghastTearEntity);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (!player.getCommandSenderWorld().isClientSide()) {
                        OOCriteriaTriggers.ONION_GHAST.trigger((serverPlayer));
                    }
                }
            }
        }
        if (cuttingBoard && advancementWorthy >= 10 && player.hasEffect(MobEffects.INVISIBILITY)) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (!player.getCommandSenderWorld().isClientSide()) {
                    OOCriteriaTriggers.ONION_NINJA.trigger((serverPlayer));
                }
            }
        }
    }

    public static void affectBlocks(BlockPos blockPos, Player player) {
        int radius = Math.min(OOConfig.COMMON.onionAOE.get(), 16);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = blockPos.offset(x, y, z);
                    BlockState state = player.level.getBlockState(pos);
                    BlockState underState = player.level.getBlockState(pos.below());
                    RandomSource random = player.getRandom();
                    if (state.is(Blocks.WEEPING_VINES) && underState.isAir() && random.nextDouble() <= OOConfig.COMMON.weepingVines.get()) {
                        if (player.level.isClientSide) {
                            for (int i = 0; i < 4; i++) {
                                player.level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.below().getX() + random.nextDouble() - 0.5,
                                        pos.below().getY() + random.nextDouble(), pos.below().getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        } else {
                            player.level.setBlockAndUpdate(pos.below(), Blocks.WEEPING_VINES.defaultBlockState());
                        }
                    } else if (state.is(Blocks.OBSIDIAN) && random.nextDouble() <= OOConfig.COMMON.cryingObby.get()) {
                        if (player.level.isClientSide) {
                            for (int i = 0; i < 4; i++) {
                                player.level.addParticle(ParticleTypes.DRAGON_BREATH, pos.getX() + random.nextDouble() - 0.5,
                                        pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        } else {
                            player.level.setBlockAndUpdate(pos, Blocks.CRYING_OBSIDIAN.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}