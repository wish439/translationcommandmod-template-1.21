package com.wishtoday.tcm.Util;


import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.wishtoday.tcm.TranslationCommandMod;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CastToTextUtils {
    public static Text toText(MessageArgumentType.MessageFormat format, ServerCommandSource source) {
        Text text = Text.of("");
         try {
            text = format.format(source,true);
        } catch (CommandSyntaxException e) {
            TranslationCommandMod.LOGGER.error(e.getMessage());
        }
         return text;
    }
}
