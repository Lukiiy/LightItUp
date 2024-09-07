package me.lukiiy.lightItUp;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Listen implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void potionRemoval(PlayerItemConsumeEvent e) {
        if (!(e.getItem().getType() == Material.MILK_BUCKET)) return;
        Bukkit.getScheduler().runTaskLater(LightItUp.getInstance(), () -> {
            Player p = e.getPlayer();
            if (LightItUp.isPlayerToggled(p)) p.addPotionEffect(LightItUp.EFFECT);
        }, 5L);
    }

    @EventHandler
    public void respawn(PlayerPostRespawnEvent e) {
        Player p = e.getPlayer();
        if (LightItUp.isPlayerToggled(p)) p.addPotionEffect(LightItUp.EFFECT);
    }
}
