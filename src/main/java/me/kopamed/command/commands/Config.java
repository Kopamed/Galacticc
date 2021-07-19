package me.kopamed.command.commands;

import me.kopamed.command.Command;
import org.lwjgl.Sys;

public class Config extends Command {
    public void execute(String[] args) {
        String finalMsg = "";
        for (String i : args) {
            finalMsg += i + " ";
        }
        msg(finalMsg);
    }

    public String getName() {
        return "config";
    }

    public String getSyntax() {
        return ".config <action> <filename>";
    }

    public String getDesc() {
        return "Loads and saves configs";
    }

    public String getAll() {
        return getSyntax() + " - " + getDesc();
    }
}
