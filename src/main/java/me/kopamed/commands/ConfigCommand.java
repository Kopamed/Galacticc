package me.kopamed.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "config";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "ok";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ChatComponentText msg = new ChatComponentText("Args: ");
        for (String i : args) {
            msg.appendText(i);
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        List<String> autocomplete = new ArrayList<String>();
        autocomplete.add("action [load/save])");
        autocomplete.add("configName");
        return autocomplete;
    }
}
