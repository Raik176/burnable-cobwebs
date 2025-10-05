package org.rhm.burnable_cobwebs.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;

public class BurnableCobwebsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register((player,world,hand,hitResult) -> BurnableCobwebsModCommon.blockInteractEvent(player, hand, world, hitResult.getBlockPos()));
	}
}
