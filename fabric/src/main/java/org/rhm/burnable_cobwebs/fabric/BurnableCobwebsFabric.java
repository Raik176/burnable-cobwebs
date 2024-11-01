package org.rhm.burnable_cobwebs.fabric;

import net.fabricmc.api.ModInitializer;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;

public class BurnableCobwebsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		BurnableCobwebsModCommon.init();
	}
}
