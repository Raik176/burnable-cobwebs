package org.rhm.burnable_cobwebs.forge;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;
import net.minecraftforge.fml.common.Mod;

@Mod(BurnableCobwebsModCommon.MOD_ID)
public class BurnableCobwebsModForge {
	public BurnableCobwebsModForge() {
		MinecraftForge.EVENT_BUS.addListener(this::onBlockRightClick);
    }

	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
        InteractionResult result = BurnableCobwebsModCommon.blockInteractEvent(
                (Player) event.getEntity(),
                event.getHand(),
                //? if <1.19 {
                /*event.getWorld(),
                 *///?} else
                event.getLevel(),
                event.getPos()
        );
        event.setCancellationResult(result);

        if (result == InteractionResult.SUCCESS) {
            event.setCanceled(true);
        }
	}
}
