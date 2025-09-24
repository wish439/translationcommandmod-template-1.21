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
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        KeysConfig.config();
        ModItems.init();
	}
}