package org.rhm.burnable_cobwebs;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.TorchBlock;

import java.util.List;
import java.util.function.Supplier;

public class BurnableCobwebsModCommon {
	public static final String MOD_ID = "burnable_cobwebs";
	// if tags/instances don't pick up i can use this supplier to manually add them
	public static Supplier<List<Item>> customLighters;

	public static void init() {
		ItemRegistry.init();
	}

	public static boolean isLighter(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof BlockItem bi)
			return bi.getBlock() instanceof TorchBlock;
		else if (item instanceof FlintAndSteelItem)
			return true;
		else
			return customLighters.get().contains(item);
	}
}