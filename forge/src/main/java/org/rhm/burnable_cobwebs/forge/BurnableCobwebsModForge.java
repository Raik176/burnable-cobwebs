package org.rhm.burnable_cobwebs.forge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import org.rhm.burnable_cobwebs.BurnableCobwebsModCommon;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod(BurnableCobwebsModCommon.MOD_ID)
public class BurnableCobwebsModForge {
	public BurnableCobwebsModForge() {
		BurnableCobwebsModCommon.init();
	}
}
