package com.feildmaster.superlockpick;

import java.util.Random;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;

public class Pix implements Listener {
    private PickAxe plugin;
    private Random r = new Random();

    public Pix(PickAxe p) {
        p.getServer().getPluginManager().registerEvents(this, p);
        plugin = p;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void pickLocks(PlayerInteractEvent event) {
        if (!event.isCancelled() || // Is event canceled?
                !event.getPlayer().hasPermission("can.pick.locks") || // Can player pick locks?
                (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) || // Are they right clicking a block?
                !plugin.getConfig().getList("pickable.items").contains(event.getClickedBlock().getTypeId()) || // Is the item "pickable"?
                event.getPlayer().getItemInHand().getTypeId() != plugin.getConfig().getInt("general.item")) { // Do they have the "lock-pick" item?
            return;
        }

        if (event.getClickedBlock().getState().getData() instanceof Door && ((Door) event.getClickedBlock().getState().getData()).isOpen()) {
            return;
        }

        if (r.nextInt(100) < plugin.getConfig().getInt("pickable.percent")) {
            event.getPlayer().sendMessage(plugin.getConfig().getString("general.fail"));
            event.getPlayer().damage(plugin.getConfig().getInt("pickable.damage"));
        } else {
            event.getPlayer().sendMessage(plugin.getConfig().getString("general.success"));
            event.setCancelled(false);
        }

        if (plugin.getConfig().getBoolean("general.use-item")) {
            ItemStack i = event.getPlayer().getItemInHand();
            if (i.getAmount() == 1) {
                event.getPlayer().getInventory().remove(i);
            } else {
                i.setAmount(i.getAmount()-1);
            }
            
        }
    }
}
