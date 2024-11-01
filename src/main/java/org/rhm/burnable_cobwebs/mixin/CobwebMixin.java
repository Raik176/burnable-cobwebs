package org.rhm.burnable_cobwebs.mixin;

import org.jetbrains.annotations.NotNull;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;

//? if <=1.20.1 {
import net.minecraft.world.InteractionResult;
//?} else {
/*import net.minecraft.world.ItemInteractionResult;

import java.util.List;
import java.util.Optional;
*///?}

@Mixin(WebBlock.class)
public abstract class CobwebMixin extends Block {
	public CobwebMixin(Properties settings) {
		super(settings);
	}

	@Override
	//? if <=1.20.1 {
	@SuppressWarnings("deprecation")
	public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
	//?} elif <1.21 {
	/*protected ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
	*///?} else
	/*protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {*/
		ItemStack itemStack;
		//? if <=1.20.1 {
		itemStack = player.getItemInHand(hand);
		//?} else
        /*itemStack = stack;*/
		//? if <=1.20.1 {
		InteractionResult result = super.use(state, world, pos, player, hand, hit);
		//?} elif <1.21 {
		/*ItemInteractionResult result = super.useItemOn(stack, blockState, world, pos, player, interactionHand, blockHitResult);
        *///?} else
		/*ItemInteractionResult result = super.useItemOn(stack, blockState, world, pos, player, hand, blockHitResult);*/
		if (BurnableCobwebsModCommon.isLighter(itemStack)) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

			world.playSound(
					null,
					pos,
					SoundEvents.GENERIC_EXTINGUISH_FIRE,
					SoundSource.BLOCKS,
					0.75f,
					world.random.nextFloat() * 0.25F + 0.75F
			);

            for (int i = 0; i < world.getRandom().nextInt(10, 25); i++) {
				world.addParticle(
						ParticleTypes.SMOKE,
						pos.getX() + 0.5 + world.getRandom().nextFloat(),
						pos.getY() + world.getRandom().nextFloat(),
						pos.getZ() + 0.5 + world.getRandom().nextFloat(),
						0, 0, 0
				);
			}

			if (!world.isClientSide) {
				ServerLevel serverWorld = (ServerLevel) world;
				if (itemStack.isDamageableItem())
					//? if <=1.20.1 {
					itemStack.hurt(
							1,
							serverWorld.getRandom(),
							(ServerPlayer) player
					);
					//?} else {
					/*itemStack.hurtAndBreak(
							1,
							//? if >1.21 {
							/^serverWorld,
							^///?} elif >1.20.1
							serverWorld.getRandom(),
							(ServerPlayer) player,
							(/^? if >=1.21 {^//^plr^//^?}^/) -> {}
					);
					*///?}


				/*
				for (int i=0; i<serverWorld.getRandom().nextInt(3); i++) {
					world.addFreshEntity(new ItemEntity(serverWorld, pos.getX(), pos.getY(), pos.getZ(), Items.FLINT_AND_STEEL.getDefaultInstance()));
				}
				 */
			}

			//? if <=1.20.1 {
			result = InteractionResult.SUCCESS;
			 //?} else
			/*result = ItemInteractionResult.SUCCESS;*/
		}

		return result;
	}
}