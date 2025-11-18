package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import dev.architectury.event.EventResult;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.overweightfarming.blocks.OverweightOnionBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.CommonTags;

import static com.davigj.onion_onion.core.other.OnionCutUtil.affectBlocks;
import static com.davigj.onion_onion.core.other.OnionCutUtil.affectLivingEntities;

public class OOEvents {
    public static EventResult onPlayerRightClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction direction) {
        if (!OOConfig.COMMON.onionFun.get()) {
            return null;
        }
        boolean OWFLoaded = Platform.isModLoaded("overweight_farming");
        ItemStack heldItem = player.getItemInHand(hand);
        BlockState clickedBlockState = player.level().getBlockState(pos);
        RandomSource random = player.getRandom();
        if (OWFLoaded && OOConfig.COMMON.bigOnion.get()) {
            if (clickedBlockState.getBlock() instanceof OverweightOnionBlock &&
                    heldItem.getItem() instanceof AxeItem) {
                if (player.level().isClientSide) {
                    for (int i = 0; i < 3; i++) {
                        player.level().addParticle(ParticleTypes.SNEEZE, pos.getX() + (2 * random.nextDouble()),
                                pos.getY() + 0.5, pos.getZ() + (2 * random.nextDouble()), 0, 0, 0);
                    }
                }
                affectLivingEntities(pos, player, player.level(),false);
                affectBlocks(pos, player.level());
            }
        }
        if (clickedBlockState.getBlock() == ModBlocks.CUTTING_BOARD.get() && !player.isShiftKeyDown()) {
            BlockEntity tileEntity = player.level().getBlockEntity(pos);
            if (tileEntity instanceof CuttingBoardBlockEntity board) {
                boolean activateTears = false;
                if (OWFLoaded && OOConfig.COMMON.bigOnion.get()) {
                    if (heldItem.getItem() instanceof AxeItem && board.getStoredItem().is(OOItemTags.OWF_TEARJERKERS)) {
                        activateTears = true;
                    }
                }
                if (heldItem.is(CommonTags.TOOLS_KNIFE) && board.getStoredItem().is(OOItemTags.TEARJERKERS)) {
                    activateTears = true;
                }
                if (activateTears) {
                    if (player.level().isClientSide) {
                        for (int i = 0; i < 3; i++) {
                            player.level().addParticle(ParticleTypes.SNEEZE, pos.getX() + random.nextDouble(),
                                    pos.getY() + 0.5, pos.getZ() + random.nextDouble(), 0, 0, 0);
                        }
                    }
                    affectLivingEntities(pos, player, player.level(), true);
                    affectBlocks(pos, player.level());
                }
            }
        }
        return EventResult.pass();
    }
}
