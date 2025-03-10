package me.lukiiy.lightItUp;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.lukiiy.lightItUp.cmds.Reload;
import me.lukiiy.lightItUp.cmds.Toggle;
import me.lukiiy.lightItUp.cmds.ToggledList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class LightItUp extends JavaPlugin {
    public NamespacedKey KEY;

    public static final PotionEffect EFFECT = new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1, false, false, false);
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        setupConfig();
        KEY = new NamespacedKey(this, "state");

        getServer().getPluginManager().registerEvents(new Listen(), this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, e -> {
            final Commands cmds = e.registrar();
            cmds.register("lightitup", "Reloads the plugin's messages", new Reload());
            cmds.register("light", "Toggle night vision", getConfig().getStringList("aliases.toggle"), new Toggle());
            cmds.register("lightlist", "Lists those who have their lights on", getConfig().getStringList("aliases.list"), new ToggledList());
        });
    }

    public static LightItUp getInstance() {
        return JavaPlugin.getPlugin(LightItUp.class);
    }

    // Config
    public void setupConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public Component msg(String path) {
        return miniMessage.deserialize(getConfig().getString("msg." + path, "<red><i>Message not set.").replace("§", ""));
    }

    // API
    public boolean isPlayerToggled(Player p) {
        return p.getPersistentDataContainer().has(KEY);
    }

    public boolean toggle(Player p) {
        PersistentDataContainer data = p.getPersistentDataContainer();

        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        boolean toggled = !data.has(KEY);

        if (toggled) {
            data.set(KEY, PersistentDataType.BOOLEAN, true);
            EFFECT.apply(p);
        } else data.remove(KEY);

        return toggled;
    }
}
