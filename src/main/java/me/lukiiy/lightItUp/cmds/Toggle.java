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
            sender.sendMessage(LightItUp.msg("nonPlayer"));
            return;
        }

        boolean isToggled = LightItUp.togglePlayer(player);

        String sfxId = LightItUp.confString("sfx.id");
        float sfxVolume = (float) LightItUp.configFile().getDouble("sfx.volume");
        float sfxPitch = isToggled ? LightItUp.floatValue("sfx.pitchOn") : LightItUp.floatValue("sfx.pitchOff");

        sender.sendMessage(LightItUp.msg(isToggled ? "toggleOn" : "toggleOff"));
        if (!sfxId.isEmpty()) player.playSound(player, sfxId, sfxVolume, sfxPitch);

        if (isToggled) player.addPotionEffect(LightItUp.EFFECT);
        else player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
