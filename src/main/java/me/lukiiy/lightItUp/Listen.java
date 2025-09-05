package me.lukiiy.lightItUp;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listen implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void potionRemoval(PlayerItemConsumeEvent e) {
        if (!(e.getItem().getType() == Material.MILK_BUCKET)) return;

        Player p = e.getPlayer();

        p.getScheduler().run(LightItUp.getInstance(), (task) -> {
            if (LightItUp.getInstance().isPlayerToggled(p)) p.addPotionEffect(LightItUp.EFFECT);
        }, null);
    }

    @EventHandler
    public void respawn(PlayerPostRespawnEvent e) {
        Player p = e.getPlayer();

        if (LightItUp.getInstance().isPlayerToggled(p)) p.addPotionEffect(LightItUp.EFFECT);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        LightItUp instance = LightItUp.getInstance();

        if (!instance.getConfig().getBoolean("save_on_quit") && instance.isPlayerToggled(p)) instance.toggle(p);
    }
}
