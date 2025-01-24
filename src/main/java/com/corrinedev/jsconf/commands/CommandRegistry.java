package com.corrinedev.jsconf.commands;

import com.corrinedev.jsconf.JSONConfigMain;
import com.corrinedev.jsconf.api.ConfigValue;
import com.corrinedev.jsconf.api.LoadedConfigs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.EnumArgument;

@Mod.EventBusSubscriber
public class CommandRegistry {

    @SubscribeEvent
    public static void JSConfCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("jsconf").then(Commands.argument("fileName", StringArgumentType.word()).suggests((context, builder) -> {for( String name : LoadedConfigs.CONFIGS.keySet()) { builder.suggest(name);}return builder.buildFuture();}).then(Commands.argument("type", EnumArgument.enumArgument(ConfigEnum.class)).executes(arg -> {

            if(arg.getSource().getEntity() instanceof Player player && arg.getArgument("type", ConfigEnum.class) == ConfigEnum.GET) {
                for (ConfigValue<?> value : LoadedConfigs.CONFIGS.get(arg.getArgument("fileName", String.class)).VALUES.values()) {
                    player.displayClientMessage(
                            Component.literal(
                                    "The value of " + value.element + " is " + value.get()
                            ), false
                    );
                }
            } else if (!(arg.getSource().getEntity() instanceof Player) && arg.getArgument("type", ConfigEnum.class) == ConfigEnum.GET) {
                JSONConfigMain.LOGGER.warn("This command can only be performed by a player!");
            } else {
                LoadedConfigs.CONFIGS.get(arg.getArgument("fileName", String.class)).update();
            }
            return 0;
        }))
        ));
    }
    @SubscribeEvent
    public static void reloadConfigCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("rejsconf").then(Commands.argument("fileName", StringArgumentType.word()).executes(arg -> {
                    LoadedConfigs.CONFIGS.get(arg.getArgument("fileName", String.class)).update();
                    return 0;
                }))
        );
    }
}
