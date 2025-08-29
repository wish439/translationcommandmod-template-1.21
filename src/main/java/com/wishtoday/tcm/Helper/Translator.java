package com.wishtoday.tcm.Helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wishtoday.tcm.Util.Translation;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class Translator {
    private Parser parser;
    private String needTranslate;
    private Text needTranslateText;
    private String translate;
    private final CommandBlockBlockEntity commandBlockEntity;
    private String command;
    private MinecraftServer server;
    public Translator(MinecraftServer server, CommandBlockBlockEntity commandBlockEntity) {
        this.parser = new Parser(server, commandBlockEntity);
        this.commandBlockEntity = commandBlockEntity;
        this.server = server;
        this.command = commandBlockEntity.getCommandExecutor().getCommand();
    }

    public void translationAndReplace() {
        this.getNeedTranslateFromCommandBlock();
        this.getNeedTranslateTextFromCommandBlock();
        String s = parseJsonFromText(needTranslateText);
        needTranslate = getNeedTranslationFromJson(s);
        this.translate();
        //command = command.replace(needTranslate, translate);
        this.replace(command);
        System.out.println("$Translator#translationAndReplace: " + command);
    }
    private void replace(String command) {
        command = command.replace(needTranslate, translate);
        this.commandBlockEntity.getCommandExecutor().setCommand(command);
    }
    private String getNeedTranslationFromJson(String json) {
        System.out.println("json:" + json);
        if (json.startsWith("{") && json.endsWith("}")) {
            JsonObject jsonObject = (JsonObject)JsonParser.parseString(json);
            JsonElement text = jsonObject.get("text");
            if (text == null) return json;
            return text.getAsString();
        }
        if (json.startsWith("\"") && json.endsWith("\"")) {
            needTranslate = json.substring(1, json.length() - 1);
            return needTranslate;
        }
        return json;
    }
    private String parseJsonFromText(Text text) {
        String s = new Text.Serializer(server.getRegistryManager()).serialize(text, null, null).toString();
        if (s.startsWith("{") && s.endsWith("}")) return s;
        if (s.equals(text.getString())) return s;
        return text.getString();
    }

    private void getNeedTranslateFromCommandBlock() {
        String s = parser.parseStringFromCommandBlock();
        if (s == null) return;
        this.needTranslate = s;
    }
    private void getNeedTranslateTextFromCommandBlock() {
        Text s = parser.parseTextFromCommandBlock();
        if (s == null) return;
        this.needTranslateText = s;
    }
    private void translate() {
        this.translate = Translation.translate(this.needTranslate);
    }

    public Parser getParser() {
        return parser;
    }

    public String getNeedTranslate() {
        return needTranslate;
    }

    public String getTranslate() {
        return translate;
    }


    public CommandBlockBlockEntity getCommandBlockEntity() {
        return commandBlockEntity;
    }
}