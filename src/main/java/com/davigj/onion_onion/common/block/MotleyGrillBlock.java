package com.davigj.onion_onion.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.function.Supplier;

public class MotleyGrillBlock extends FeastBlock {
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    protected static final VoxelShape[] GRILL_SHAPE;

    public MotleyGrillBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockState platform = level.getBlockState(pos.below());
        BlockState subPlatform = level.getBlockState(pos.below().below());
        if (random.nextInt(3) == 0 && state.getValue(SERVINGS) == 4 &&
                (platform.is(ModTags.HEAT_SOURCES) || (platform.is(ModTags.HEAT_CONDUCTORS) && subPlatform.is(ModTags.HEAT_SOURCES)))) {
            level.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5, (double) pos.getY() + 0.9, (double) pos.getZ()+ 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return level.isClientSide && this.takeServing(level, pos, state, player, hand).consumesAction() ? InteractionResult.SUCCESS : this.takeServing(level, pos, state, player, hand);
    }

    protected InteractionResult takeServing(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        int servings = (Integer)state.getValue(this.getServingsProperty());
        if (servings == 0) {
            level.playSound((Player)null, pos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            level.destroyBlock(pos, true);
            return InteractionResult.SUCCESS;
        } else {
            ItemStack serving = this.getServingItem(state);
            ItemStack heldStack = player.getItemInHand(hand);
            if (servings > 0) {
                if (!serving.hasCraftingRemainingItem() || heldStack.sameItem(serving.getCraftingRemainingItem())) {
                    BlockState platform = level.getBlockState(pos.below());
                    BlockState subPlatform = level.getBlockState(pos.below().below());
                    if (servings == 4 && (platform.is(ModTags.HEAT_SOURCES)
                            || (platform.is(ModTags.HEAT_CONDUCTORS) && subPlatform.is(ModTags.HEAT_SOURCES)))) {
                        for (int i = 0; i < 4; i++) {
                            level.addParticle(ParticleTypes.ASH, pos.getX() + 0.5 + level.getRandom().nextDouble() - 0.5,
                                    pos.getY() + level.getRandom().nextDouble(), pos.getZ() + level.getRandom().nextDouble() - 0.5, 0, 0, 0);
                        }
                    }
                    level.setBlock(pos, (BlockState)state.setValue(this.getServingsProperty(), servings - 1), 3);
                    if (!player.getAbilities().instabuild && serving.hasCraftingRemainingItem()) {
                        heldStack.shrink(1);
                    }

                    if (!player.getInventory().add(serving)) {
                        player.drop(serving, false);
                    }

                    if ((Integer)level.getBlockState(pos).getValue(this.getServingsProperty()) == 0 && !this.hasLeftovers) {
                        level.removeBlock(pos, false);
                    }

                    level.playSound((Player)null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }

                player.displayClientMessage(TextUtils.getTranslation("block.feast.use_container", new Object[]{serving.getCraftingRemainingItem().getHoverName()}), true);
            }

            return InteractionResult.PASS;
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (Integer)state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : GRILL_SHAPE[state.getValue(SERVINGS)];
    }

    static {
        GRILL_SHAPE = new VoxelShape[]{
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 0.0, 3.0, 13.0, 5.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 0.0, 3.0, 13.0, 5.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 0.0, 3.0, 13.0, 5.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 0.0, 3.0, 13.0, 5.0, 13.0), BooleanOp.OR)};
    }
}
