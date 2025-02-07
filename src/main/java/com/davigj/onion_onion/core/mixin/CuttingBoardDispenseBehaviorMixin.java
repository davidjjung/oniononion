package com.davigj.onion_onion.core.mixin;

import com.davigj.onion_onion.core.OOConfig;
import com.davigj.onion_onion.core.other.tags.OOItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.dispenser.CuttingBoardDispenseBehavior;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import static com.davigj.onion_onion.core.other.OnionCutUtil.affectBlocks;
import static com.davigj.onion_onion.core.other.OnionCutUtil.affectLivingEntities;

@Mixin(CuttingBoardDispenseBehavior.class)
public class CuttingBoardDispenseBehaviorMixin extends OptionalDispenseItemBehavior {
    @Inject(method = "tryDispenseStackOnCuttingBoard", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"), remap = false)
    private void dispenserBehavior(BlockSource source, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!OOConfig.COMMON.onionFun.get()) return;
        Level level = source.getLevel();
        BlockPos pos = source.getPos().relative((Direction)source.getBlockState().getValue(DispenserBlock.FACING));
        CuttingBoardBlockEntity board = (CuttingBoardBlockEntity) level.getBlockEntity(pos);
        if (board != null) {
            boolean activateTears = false;
            ItemStack victim = board.getStoredItem();
            if (ModList.get().isLoaded("overweight_farming") && OOConfig.COMMON.bigOnion.get()) {
                if (stack.getItem() instanceof AxeItem && victim.is(OOItemTags.OWF_TEARJERKERS)) {
                    activateTears = true;
                }
            }
            if (stack.is(ForgeTags.TOOLS_KNIVES) && victim.is(OOItemTags.TEARJERKERS)) {
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
