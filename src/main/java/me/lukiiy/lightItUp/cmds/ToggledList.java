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

import java.util.List;
import java.util.stream.Collectors;

public class ToggledList implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        List<Player> toggled = Bukkit.getOnlinePlayers().stream().filter(LightItUp.getInstance()::isPlayerToggled).collect(Collectors.toUnmodifiableList());

        if (toggled.isEmpty()) {
            sender.sendMessage(LightItUp.getInstance().msg("listEmpty"));
            return;
        }

        sender.sendMessage(LightItUp.getInstance().msg("listHeader"));
        toggled.forEach(player -> sender.sendMessage(LightItUp.getInstance().msg("listDisplay").replaceText(TextReplacementConfig.builder().matchLiteral("(player)").replacement(player.displayName()).build())));
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
