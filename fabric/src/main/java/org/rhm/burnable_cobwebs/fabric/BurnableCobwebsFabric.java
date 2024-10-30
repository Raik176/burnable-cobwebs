package org.rhm.burnable_cobwebs.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class BurnableCobwebsFabric implements ModInitializer {
	public static final TagKey<Item> igniters = TagKey.create(
			BuiltInRegistries.ITEM.key(),
			ResourceLocation.tryBuild("c", "tools/igniter")
	);
	public static final TagKey<Item> torches = TagKey.create(
			BuiltInRegistries.ITEM.key(),
			ResourceLocation.tryBuild("c", "torches/coal")
	);
	@Override
	public void onInitialize() {
		BurnableCobwebsModCommon.itemRegisterFunc = (name, factory) -> {
			ResourceLocation identifier = ResourceLocation.tryBuild(BurnableCobwebsModCommon.MOD_ID, name);
			Item.Properties properties = new Item.Properties();
			//? if >1.21.1
			/*properties = properties.setId(ResourceKey.create(Registries.ITEM, identifier));*/

			return Registry.register(
					BuiltInRegistries.ITEM,
					identifier,
					factory.apply(properties)
			);
		};

		BurnableCobwebsModCommon.customLighters = () -> BuiltInRegistries.BLOCK.stream()
                .map(Block::asItem)
                .filter((i) -> i != Items.AIR && (i.getDefaultInstance().is(torches) || i.getDefaultInstance().is(igniters)))
                .toList();
		BurnableCobwebsModCommon.init();
	}
}
