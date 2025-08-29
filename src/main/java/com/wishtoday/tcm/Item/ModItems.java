package com.wishtoday.tcm.Item;

import com.wishtoday.tcm.TranslationCommandMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item MODIFYCOMMANDTEXT = Registry.register(Registries.ITEM, Identifier.of(TranslationCommandMod.MOD_ID,"modifycommandtext"),new ModIfyCommandText(new Item.Settings()));
    public static void init() {}
}
