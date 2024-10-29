package com.example.template;

import net.minecraft.sounds.SoundEvent;

public interface BurnableWebMod {
    default SoundEvent registerSound(String name) { return null; }
}
