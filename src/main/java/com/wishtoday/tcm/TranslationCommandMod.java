package com.wishtoday.tcm;

import com.wishtoday.tcm.Config.KeysConfig;
import com.wishtoday.tcm.Item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TranslationCommandMod implements ModInitializer {
	public static final String MOD_ID = "translationcommandmod";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        try {
            KeysConfig.config();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModItems.init();
        LOGGER.info("Hello Fabric world!");
	}
}