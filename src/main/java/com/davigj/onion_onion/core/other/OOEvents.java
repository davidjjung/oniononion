package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.registry.OODamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID)
public class OOEvents {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);
        BlockState clickedBlockState = player.level.getBlockState(event.getPos());
        if (clickedBlockState.getBlock() == ModBlocks.CUTTING_BOARD.get()) {
            BlockEntity tileEntity = player.level.getBlockEntity(event.getPos());
            if (tileEntity instanceof CuttingBoardBlockEntity board && board.getStoredItem().getItem() == vectorwing.farmersdelight.common.registry.ModItems.ONION.get()) {
                if (heldItem.getItem() instanceof KnifeItem) {
                    affectLivingEntities(event.getPos(), player, true);
                    affectBlocks(event.getPos(), player, true);
                }
            }
        }
    }

    public static void affectLivingEntities(BlockPos pos, Player player, boolean cuttingBoard) {
        AABB boundingBox = new AABB(pos).inflate(2.0, 2.0, 2.0);
        List<LivingEntity> livingEntities = player.level.getEntitiesOfClass(LivingEntity.class, boundingBox,
                (living) -> living != null && living.isAlive());
        for (LivingEntity livingEntity : livingEntities) {

            if (!livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(OOItemTags.ONION_PROOF)) {
                livingEntity.hurt(OODamageSources.ONION, 1.0F);
            }

            if (livingEntity instanceof Ghast) {
                ItemEntity ghastTearEntity = new ItemEntity(player.level, livingEntity.getX(), livingEntity.getY(),
                        livingEntity.getZ(), new ItemStack(Items.GHAST_TEAR));
                player.level.addFreshEntity(ghastTearEntity);
            }
        }
        if (cuttingBoard) {
            // TODO: add advancement trigger
        }
    }

    public static void affectBlocks(BlockPos blockPos, Player player, boolean cuttingBoard) {
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = blockPos.offset(x, y, z);
                    BlockState state = player.level.getBlockState(pos);
                    BlockState underState = player.level.getBlockState(pos.below());
                    RandomSource random = player.getRandom();
                    if (state.is(Blocks.WEEPING_VINES) && underState.isAir()) {
                        if (player.level.isClientSide) {
                            for (int i = 0; i < 4; i++) {
                                player.level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.below().getX() + random.nextDouble() - 0.5,
                                        pos.below().getY() + random.nextDouble(), pos.below().getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                        player.level.setBlockAndUpdate(pos.below(), Blocks.WEEPING_VINES.defaultBlockState());
                    } else if (state.is(Blocks.OBSIDIAN)) {
                        player.level.setBlockAndUpdate(pos, Blocks.CRYING_OBSIDIAN.defaultBlockState());
                        if (player.level.isClientSide) {
                            for (int i = 0; i < 4; i++) {
                                player.level.addParticle(ParticleTypes.DRAGON_BREATH, pos.getX() + random.nextDouble() - 0.5,
                                        pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}