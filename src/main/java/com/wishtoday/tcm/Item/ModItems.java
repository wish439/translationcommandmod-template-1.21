package com.wishtoday.tcm.Item;

import com.wishtoday.tcm.TranslationCommandMod;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item MODIFYCOMMANDTEXT = register("translationcommandtext",new Item.Settings());
    public static void init() {}
    private static Item register(String name, Item.Settings settings) {
        return register(name,Item::new, settings);
    }
    private static Item register(String name, Function<Item.Settings, Item> settings, Item.Settings settingsIn) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TranslationCommandMod.MOD_ID, name));
        return Items.register(key, settings, settingsIn);
    }
}
