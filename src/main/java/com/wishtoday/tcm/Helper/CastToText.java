package com.wishtoday.tcm.Helper;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public interface CastToText {
    Text toText(ServerCommandSource source);
}
