package me.lukiiy.lightItUp.cmds;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.lukiiy.lightItUp.LightItUp;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class Toggle implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        if (!(sender instanceof Player player)) {
            sender.sendMessage(LightItUp.getInstance().msg("nonPlayer"));
            return;
        }

        boolean isToggled = LightItUp.getInstance().toggle(player);
        sender.sendMessage(LightItUp.getInstance().msg(isToggled ? "toggleOn" : "toggleOff"));

        String sfxId = LightItUp.getInstance().getConfig().getString("sfx.id");
        if (sfxId != null && !sfxId.isEmpty()) player.playSound(player, sfxId, validFloat("volume"), isToggled ? validFloat("pitchOn") : validFloat("pitchOff"));
    }

    private float validFloat(String path) {
        return (float) Math.clamp(LightItUp.getInstance().getConfig().getDouble("sfx." + path), 0, 1);
    }
}
