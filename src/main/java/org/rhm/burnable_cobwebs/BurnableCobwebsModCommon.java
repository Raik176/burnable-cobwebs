package org.rhm.burnable_cobwebs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;

public class BurnableCobwebsModCommon {
	public static final String MOD_ID = "burnable_cobwebs";

	public static boolean isLighter(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof BlockItem bi)
			return bi.getBlock() instanceof TorchBlock;

        return item instanceof FlintAndSteelItem;
    }

	public static InteractionResult blockInteractEvent(Player player, InteractionHand hand, Level world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() != Blocks.COBWEB) return InteractionResult.PASS;
		ItemStack itemStack = player.getItemInHand(hand);
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

			if (!world.isClientSide()) {
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
							(/*? if >=1.21 {*//*plr*//*?}*/) -> {}
					);
					//?}
				}
			}
			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.PASS;
		}
	}
}