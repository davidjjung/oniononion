//? neoforge {
package com.davigj.onion_onion.neoforge;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.OnionOnion;
import com.davigj.onion_onion.core.other.tags.OOBlockTags;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.CommonTags;


@EventBusSubscriber(modid = OnionOnion.MOD_ID)
public class OONFEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!OOConfig.COMMON.onionFun.get()) {
            return;
        }
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        boolean OWFLoaded = Platform.isModLoaded("overweight_farming");
        ItemStack heldItem = player.getItemInHand(hand);
        BlockState clickedBlockState = player.level().getBlockState(pos);
        if (OWFLoaded && OOConfig.COMMON.bigOnion.get()) {
            if (clickedBlockState.is(OOBlockTags.TEARJERKERS) &&
                    heldItem.getItem() instanceof AxeItem) {
                OONFDataMapUtil.weepingConversions(pos, player.level());
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
                if (heldItem.is(CommonTags.Items.TOOLS_KNIFE) && board.getStoredItem().is(OOItemTags.TEARJERKERS)) {
                    activateTears = true;
                }
                if (activateTears) {
                    OONFDataMapUtil.weepingConversions(pos, player.level());
                }
            }
        }
    }
}
//?}