package org.rhm.burnable_cobwebs.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class BurnableCobwebsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		BurnableCobwebsModCommon.customLighters = () -> { // should only be called once
			TagKey<Item> igniters = TagKey.create(
					BuiltInRegistries.ITEM.key(),
					ResourceLocation.tryBuild("c", "tools/igniter")
			);

			Set<Item> custom = new HashSet<>();

			BuiltInRegistries.ITEM.stream().filter(i -> i.getDefaultInstance().is(igniters)).forEach(custom::add);

			return custom;
		};
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
		BurnableCobwebsModCommon.init();
	}
}
