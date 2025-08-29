package com.wishtoday.tcm.Item;

import com.wishtoday.tcm.Helper.Translator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ModIfyCommandText extends Item {

    public ModIfyCommandText(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;
        @SuppressWarnings("DataFlowIssue") @NotNull
        MinecraftServer server = context.getWorld().getServer();
        assert server != null;
        BlockState block = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!block.isOf(Blocks.COMMAND_BLOCK) &&
                !block.isOf(Blocks.CHAIN_COMMAND_BLOCK) &&
                !block.isOf(Blocks.REPEATING_COMMAND_BLOCK)) return ActionResult.SUCCESS;
        CommandBlockBlockEntity commandBlockBlockEntity = (CommandBlockBlockEntity) blockEntity;
        if (blockEntity == null) return ActionResult.FAIL;
        new Translator(server,commandBlockBlockEntity).translationAndReplace();
        return super.useOnBlock(context);
    }
}
