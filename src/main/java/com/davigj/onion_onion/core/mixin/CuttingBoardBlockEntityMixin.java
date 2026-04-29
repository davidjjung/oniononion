package com.davigj.onion_onion.core.mixin;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.tag.CommonTags;

import static com.davigj.onion_onion.core.other.OnionCutUtil.affectBlocks;
import static com.davigj.onion_onion.core.other.OnionCutUtil.affectLivingEntities;

@Mixin(CuttingBoardBlockEntity.class)
public class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {
    public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "processStoredItemUsingTool", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"), remap = false)
    private void a(ItemStack stack, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!OOConfig.COMMON.onionFun.get()) return;
        Level level = this.getLevel();
        BlockPos pos = this.worldPosition;
        assert level != null;
        CuttingBoardBlockEntity board = (CuttingBoardBlockEntity) level.getBlockEntity(pos);
        if (board != null && player == null) {
            boolean activateTears = false;
            ItemStack victim = board.getStoredItem();
            if (Platform.isModLoaded("overweight_farming") && OOConfig.COMMON.bigOnion.get()) {
                if (stack.getItem() instanceof AxeItem && victim.is(OOItemTags.OWF_TEARJERKERS)) {
                    activateTears = true;
                }
            }
            if (stack.is(CommonTags.Items.TOOLS_KNIFE) && victim.is(OOItemTags.TEARJERKERS)) {
                activateTears = true;
            }
            if (activateTears) {
                RandomSource random = level.getRandom();
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 3; i++) {
                        serverLevel.sendParticles(ParticleTypes.SNEEZE, pos.getX() + random.nextDouble(),
                                pos.getY() + 0.5, pos.getZ() + random.nextDouble(), 1, 0, 0, 0, 0.0);
                    }
                }
                affectLivingEntities(pos, null, level, true);
                affectBlocks(pos, level);
            }
        }
    }
}
