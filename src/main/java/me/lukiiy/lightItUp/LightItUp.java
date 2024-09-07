package me.lukiiy.lightItUp;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import me.lukiiy.lightItUp.cmds.Reload;
import me.lukiiy.lightItUp.cmds.Toggle;
import me.lukiiy.lightItUp.cmds.ToggledList;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public final class LightItUp extends JavaPlugin {
    @Getter private static LightItUp instance;
    @Getter private static NamespacedKey TOGGLED_KEY;

    private FileConfiguration config;

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    public static final PotionEffect EFFECT = new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1, false, false);

    @Override
    public void onEnable() {
        instance = this;
        TOGGLED_KEY = new NamespacedKey(this, "state");
        setupConfig();

        getServer().getPluginManager().registerEvents(new Listen(), this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, e -> {
            final Commands cmds = e.registrar();
            cmds.register("lightitup", "Reloads the plugin's messages", new Reload());
            cmds.register("light", "Toggle night vision", aliases("toggle"), new Toggle());
            cmds.register("lightlist", "Lists those who have their lights on", aliases("lightlist"), new ToggledList());
        });
    }

    @Override
    public void onDisable() {}

    // Config
    public void setupConfig() {
        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

    public static FileConfiguration configFile() {return instance.config;}

    public static String confString(String path) {return configFile().getString(path);}
    public static List<String> aliases(String cmd) {return configFile().getStringList("aliases." + cmd);}
    public static float floatValue(String path) {return (float) configFile().getDouble(path);}
    public static Component msg(String path) {return miniMessage.deserialize(confString("msg." + path));}

    // cool stuff
    public static boolean isPlayerToggled(Player p) {
        return p.getPersistentDataContainer().has(LightItUp.TOGGLED_KEY);
    }

    public static boolean togglePlayer(Player p) {
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (data.has(LightItUp.TOGGLED_KEY)) {
            data.remove(TOGGLED_KEY);
            return false;
        } else {
            data.set(TOGGLED_KEY, PersistentDataType.BOOLEAN, true);
            return true;
        }
    }
}
