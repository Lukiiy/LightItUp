package me.lukiiy.lightItUp.cmds;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.lukiiy.lightItUp.LightItUp;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToggledList implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        if (!canUse(sender)) {
            sender.sendMessage(Bukkit.permissionMessage());
            return;
        }

        boolean[] hasPlayers = {false};
        sender.sendMessage(LightItUp.msg("listHeader"));

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (LightItUp.isPlayerToggled(player)) {
                hasPlayers[0] = true;
                sender.sendMessage(LightItUp.msg("listDisplay").replaceText(
                        TextReplacementConfig.builder()
                                .matchLiteral("(player)")
                                .replacement(player.displayName())
                                .build())
                );
            }
        });

        if (!hasPlayers[0]) sender.sendMessage(LightItUp.msg("listEmpty"));
    }

    @Override
    public boolean canUse(@NotNull CommandSender sender) {return BasicCommand.super.canUse(sender);}

    @Override
    public @Nullable String permission() {return "lightitup.list";}
}
