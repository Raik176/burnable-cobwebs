package org.rhm.burnable_cobwebs;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BurnableCobwebsModCommon {
	public static final String MOD_ID = "burnable_cobwebs";
	public static BiFunction<String, Function<Item.Properties, Item>, Item> itemRegisterFunc;
	public static Supplier<List<Item>> customLighters;

    public static void init() {
		ItemRegistry.init();

	}

	public static Set<Item> lightersCache;
	public static boolean isLighter(ItemStack item) {
		if (lightersCache == null) { // this is probably not a good way to do it but eh
			lightersCache = new HashSet<>();

			BuiltInRegistries.BLOCK.stream()
					.filter((b) -> b instanceof TorchBlock)
					.map(Block::asItem).forEach(lightersCache::add);
			BuiltInRegistries.ITEM.stream().filter((i -> i instanceof FlintAndSteelItem)).forEach(lightersCache::add);
			if (customLighters != null)
				lightersCache.addAll(customLighters.get());
		}
		return lightersCache.contains(item.getItem());
	}
}
