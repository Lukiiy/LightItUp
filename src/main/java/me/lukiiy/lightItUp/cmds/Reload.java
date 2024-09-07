package me.lukiiy.lightItUp.cmds;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.lukiiy.lightItUp.LightItUp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class Reload implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        if (!canUse(sender)) {
            sender.sendMessage(Bukkit.permissionMessage());
            return;
        }

        LightItUp.getInstance().setupConfig();
        sender.sendMessage(Component.text(LightItUp.getInstance().getName() + " messages reload complete!").color(NamedTextColor.GREEN));
    }

    @Override
    public boolean canUse(@NotNull CommandSender sender) {return BasicCommand.super.canUse(sender);}

    @Override
    public @Nullable String permission() {return "lightitup.reload";}
}
