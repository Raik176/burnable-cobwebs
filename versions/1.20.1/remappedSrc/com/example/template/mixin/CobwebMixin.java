package com.example.template.mixin;

import com.example.template.TemplateModCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WebBlock.class)
public abstract class CobwebMixin extends Block {
	public CobwebMixin(Properties settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack != null && TemplateModCommon.isLighter(itemStack)) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

			if (itemStack.isDamageableItem())
				itemStack.hurtAndBreak(1, player, (plr) -> {});

			world.playSound(
					null,
					pos,
					SoundEvents.REDSTONE_TORCH_BURNOUT,
					SoundSource.BLOCKS,
					0.75f,
					world.random.nextFloat() * 0.1F + 0.75F
			);
			return InteractionResult.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, hit);
	}
}