package com.example.template;

import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TemplateModCommon {
	public static final String MOD_ID = "template";

	public static SoundEvent BURN_SOUND; //yikes, but doesnt work any other way

	public static void init() {

	}


	private static final List<Item> lighters = List.of(
			Items.TORCH,
			Items.FLINT_AND_STEEL
			//? if >1.16
			,Items.SOUL_TORCH
	);
	public static boolean isLighter(ItemStack item) {
		return lighters.contains(item.getItem());
	}
}
