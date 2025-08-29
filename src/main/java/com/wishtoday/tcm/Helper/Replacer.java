package com.wishtoday.tcm.Helper;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import java.util.Objects;

public class Replacer {
    private String originalNode;
    private String replacementNode;
    private MinecraftServer server;
    public Replacer() {

    }
    public Replacer(String originalNode, String replacementNode) {
        this.originalNode = originalNode;
        this.replacementNode = replacementNode;
    }
    public Replacer(Text originalNode, String replacementNode, MinecraftServer server) {
        this.originalNode = new Text.Serializer(server.getRegistryManager()).serialize(originalNode, null, null).toString();
        this.replacementNode = replacementNode;
    }

    public void setOriginalNode(String originalNode) {
        this.originalNode = originalNode;
    }

    public void setOriginalNode(Text originalNode, MinecraftServer server) {
        this.originalNode = new Text.Serializer(server.getRegistryManager()).serialize(originalNode, null, null).toString();
    }

    public void setReplacementNode(String replacementNode) {
        this.replacementNode = replacementNode;
    }

    public String getOriginalNode() {
        return originalNode;
    }

    public String getReplacementNode() {
        return replacementNode;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public void setServer(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Replacer replacer = (Replacer) object;
        return Objects.equals(originalNode, replacer.originalNode) && Objects.equals(replacementNode, replacer.replacementNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalNode, replacementNode);
    }
}
