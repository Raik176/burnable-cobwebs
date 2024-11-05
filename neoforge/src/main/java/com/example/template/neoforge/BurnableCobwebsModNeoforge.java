package com.example.template.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;

@Mod(BurnableCobwebsModCommon.MOD_ID)
public class BurnableCobwebsModNeoforge {
	public BurnableCobwebsModNeoforge(IEventBus bus, ModContainer mod) {
		NeoForge.EVENT_BUS.addListener(this::onBlockRightClick);
		BurnableCobwebsModCommon.init();
	}

	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
		event.setCancellationResult(BurnableCobwebsModCommon.blockInteractEvent(event.getEntity(), event.getHand(), event.getLevel(), event.getPos()));
		event.setCanceled(true); //idk if i should do this but eh
	}
}
