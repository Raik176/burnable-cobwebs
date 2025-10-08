package org.rhm.burnable_cobwebs.fabric;

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;

@Entrypoint
public class BurnableCobwebsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register((player,world,hand,hitResult) -> BurnableCobwebsModCommon.blockInteractEvent(player, hand, world, hitResult.getBlockPos()));
	}
}
