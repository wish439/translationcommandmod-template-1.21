package com.wishtoday.tcm.Helper;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static com.wishtoday.tcm.Util.CastToTextUtils.*;

public class Parser {
    private MinecraftServer server;
    private CommandBlockBlockEntity blockEntity;

    public Parser(MinecraftServer server, CommandBlockBlockEntity blockEntity) {
        this.server = server;
        this.blockEntity = blockEntity;
    }
    @Nullable
    public Text parseTextFromCommandBlock() {
        String s = this.blockEntity.getCommandExecutor().getCommand();
        CommandDispatcher<ServerCommandSource> dispatcher = this.server.getCommandManager().getDispatcher();
        ParseResults<ServerCommandSource> result = dispatcher.parse(s, this.blockEntity.getCommandExecutor().getSource());
        CommandContextBuilder<ServerCommandSource> context = result.getContext();
        return getTextNodeFromCommand(context);
    }
    private ParsedCommandNode<ServerCommandSource> getExecuteText(CommandContextBuilder<ServerCommandSource> context) {
        return getExecuteContext(context).getNodes().getFirst();
    }
    private CommandContextBuilder<ServerCommandSource> getExecuteContext(CommandContextBuilder<ServerCommandSource> context) {
        CommandContextBuilder<ServerCommandSource> ctx = context;
        while (ctx.getChild() != null) {
            ctx = ctx.getChild();
        }
        return ctx;
    }
    @Nullable
    private Text getTextNodeFromCommand(
            CommandContextBuilder<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        ctx = getExecuteContext(ctx);
        for (Map.Entry<String, ParsedArgument<ServerCommandSource, ?>> entry : ctx.getArguments().entrySet()) {
            Object result = entry.getValue().getResult();
            if (result instanceof Text text) {
                return text;
            }
            if (result instanceof MessageArgumentType.MessageFormat format) {
                return toText(format, source);
            }
        }
        return null;
    }

    @Nullable
    public String parseStringFromCommandBlock() {
        Text text = parseTextFromCommandBlock();
        return text == null ? null : text.getString();
    }
}