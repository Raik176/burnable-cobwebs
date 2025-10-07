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
        //? if >=1.21.6 {
        /*PlayerInteractEvent.RightClickBlock.BUS.addListener(this::intermediaryHandler);
        *///?} else {
		MinecraftForge.EVENT_BUS.<PlayerInteractEvent.RightClickBlock>addListener((event) -> {
            if (intermediaryHandler(event)) {
                event.setCanceled(true);
            }
        });
        //?}
    }

    public boolean intermediaryHandler(PlayerInteractEvent.RightClickBlock event) {
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

        return result == InteractionResult.SUCCESS;
    }
}
