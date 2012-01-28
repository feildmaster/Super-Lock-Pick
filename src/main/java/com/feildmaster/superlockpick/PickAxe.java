package com.feildmaster.superlockpick;

import com.feildmaster.lib.configuration.PluginWrapper;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.*;

public class PickAxe extends PluginWrapper implements Listener {

    public void onEnable() {
        new Pix(this);
        getConfig().loadDefaults();
        if (!getConfig().checkDefaults() || !getConfig().fileExists()) {
            saveDefaultConfig();
        }
    }

    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You are using: " + this.toString());
            return true;
        }

        if (args[0].equalsIgnoreCase("debug")) {
            sender.sendMessage("Debuggin.");
            for (String s : getConfig().getKeys(true)) {
                Object o = getConfig().get(s);
                if (o instanceof ConfigurationSection) {
                    continue;
                }
                sender.sendMessage(s + ": " + o);
            }
            return true;
        }

        if (!args[0].equalsIgnoreCase("reload")) {
            return true;
        }

        if (!sender.hasPermission("can.pick.reload")) {
            sender.sendMessage(format("Lacking Permission..."));
            return true;
        }

        getConfig().load();
        sender.sendMessage(format("Reloaded Config"));
        return true;
    }

    private String format(String string) {
        return String.format("[%s] %s", getDescription().getName(), string);
    }
}
