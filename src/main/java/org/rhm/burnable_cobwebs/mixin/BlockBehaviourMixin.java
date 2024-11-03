package org.rhm.burnable_cobwebs.mixin;

import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.16 {
/*import net.minecraft.world.level.block.Block;
*///?} else {
import net.minecraft.world.level.block.state.BlockBehaviour;
//?}


@Debug(export = true)
//? if <1.16 {
/*@Mixin(Block.class)
*///?} else
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
	//? if <=1.20.1 {
	/*@Inject(method = "use", at = @At("TAIL"), cancellable = true)
	public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
	*///?} else {
	@Inject(method = "useItemOn", at = @At("TAIL"), cancellable = true)
	protected void useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
	//?}
		if (state.getBlock() != Blocks.COBWEB) return;
		ItemStack itemStack;
		//? if <=1.20.1 {
		/*itemStack = player.getItemInHand(hand);
		*///?} else
		itemStack = stack;
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
				if (itemStack.isDamageableItem()) {
					//? if <=1.20.1 {
					/*itemStack.hurt(
							1,
							serverWorld.getRandom(),
							(ServerPlayer) player
					);
					*///?} else {
					itemStack.hurtAndBreak(
							1,
							//? if >1.21 {
							/*serverWorld,
							*///?} elif >1.20.1
							serverWorld.getRandom(),
							(ServerPlayer) player,
							(/*? if >=1.21 {*//*plr*//*?}*/) -> {
							}
					);
					//?}
				}
			}
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}