package com.davigj.onion_onion.core.other;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.overweightfarming.init.OFBlocks;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.CommonTags;

import static com.davigj.onion_onion.core.other.OnionCutUtil.affectBlocks;
import static com.davigj.onion_onion.core.other.OnionCutUtil.affectLivingEntities;

@Mod.EventBusSubscriber(modid = OnionOnion.MOD_ID)
public class OOEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!OOConfig.COMMON.onionFun.get()) {
            return;
        }
        boolean OWFLoaded = ModList.get().isLoaded("overweight_farming");
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        BlockState clickedBlockState = player.level().getBlockState(event.getPos());
        RandomSource random = player.getRandom();
        if (OWFLoaded && OOConfig.COMMON.bigOnion.get()) {
            if (clickedBlockState.getBlock() == OFBlocks.OVERWEIGHT_ONION.get() &&
                    heldItem.getItem() instanceof AxeItem) {
                if (player.level().isClientSide) {
                    for (int i = 0; i < 3; i++) {
                        player.level().addParticle(ParticleTypes.SNEEZE, event.getPos().getX() + (2 * random.nextDouble()),
                                event.getPos().getY() + 0.5, event.getPos().getZ() + (2 * random.nextDouble()), 0, 0, 0);
                    }
                }
                affectLivingEntities(event.getPos(), player, player.level(),false);
                affectBlocks(event.getPos(), player.level());
            }
        }
        if (clickedBlockState.getBlock() == ModBlocks.CUTTING_BOARD.get() && !player.isShiftKeyDown()) {
            BlockEntity tileEntity = player.level().getBlockEntity(event.getPos());
            if (tileEntity instanceof CuttingBoardBlockEntity board) {
                boolean activateTears = false;
                if (OWFLoaded && OOConfig.COMMON.bigOnion.get()) {
                    if (heldItem.getItem() instanceof AxeItem && board.getStoredItem().is(OOItemTags.OWF_TEARJERKERS)) {
                        activateTears = true;
                    }
                }
                if (heldItem.is(CommonTags.Items.TOOLS_KNIVES) && board.getStoredItem().is(OOItemTags.TEARJERKERS)) {
                    activateTears = true;
                }
                if (activateTears) {
                    if (player.level().isClientSide) {
                        for (int i = 0; i < 3; i++) {
                            player.level().addParticle(ParticleTypes.SNEEZE, event.getPos().getX() + random.nextDouble(),
                                    event.getPos().getY() + 0.5, event.getPos().getZ() + random.nextDouble(), 0, 0, 0);
                        }
                    }
                    affectLivingEntities(event.getPos(), player, player.level(), true);
                    affectBlocks(event.getPos(), player.level());
                }
            }
        }
    }
}