package me.lukiiy.lightItUp.cmds;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.lukiiy.lightItUp.LightItUp;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToggledList implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        boolean hasPlayers = false;

        sender.sendMessage(LightItUp.getInstance().msg("listHeader"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (LightItUp.getInstance().isPlayerToggled(player)) {
                hasPlayers = true;
                sender.sendMessage(LightItUp.getInstance().msg("listDisplay").replaceText(TextReplacementConfig.builder().matchLiteral("(player)").replacement(player.displayName()).build()));
            }
        }

        if (!hasPlayers) sender.sendMessage(LightItUp.getInstance().msg("listEmpty"));
    }

    @Override
    public boolean canUse(@NotNull CommandSender sender) {
        return BasicCommand.super.canUse(sender);
    }

    @Override
    public @Nullable String permission() {
        return "lightitup.list";
    }
}
