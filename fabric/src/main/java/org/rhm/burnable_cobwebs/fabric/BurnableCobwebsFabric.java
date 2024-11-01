package org.rhm.burnable_cobwebs.fabric;

import net.fabricmc.api.ModInitializer;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;

public class BurnableCobwebsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		//BurnableCobwebsModCommon.itemRegisterFunc = (name, factory) -> {
		//	ResourceLocation identifier = ResourceLocation.tryBuild(BurnableCobwebsModCommon.MOD_ID, name);
		//	Item.Properties properties = new Item.Properties();
		//	//? if >1.21.1
		//	/*properties = properties.setId(ResourceKey.create(Registries.ITEM, identifier));*/
		//
		//	return Registry.register(
		//			BuiltInRegistries.ITEM,
		//			identifier,
		//			factory.apply(properties)
		//	);
		//};


		BurnableCobwebsModCommon.init();
	}
}
