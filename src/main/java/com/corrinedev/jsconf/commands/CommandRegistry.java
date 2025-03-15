package com.corrinedev.jsconf.commands;

import com.corrinedev.jsconf.JSConf;
import com.corrinedev.jsconf.api.ConfigValue;
import com.corrinedev.jsconf.api.SimpleExampleConfigClass;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.EnumArgument;

@Mod.EventBusSubscriber
public class CommandRegistry {

    @SubscribeEvent
    public static void JSConfCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("jsconf").then(Commands.argument("fileName", StringArgumentType.word()).suggests((context, builder) -> {for( String name : JSConf.CONFIGS.keySet()) { builder.suggest(name);}return builder.buildFuture();}).then(Commands.argument("type", EnumArgument.enumArgument(ConfigEnum.class)).executes(arg -> {

            if(arg.getSource().getEntity() instanceof Player player && arg.getArgument("type", ConfigEnum.class) == ConfigEnum.get) {
                for (ConfigValue<?> value : JSConf.CONFIGS.get(arg.getArgument("fileName", String.class)).VALUES.values()) {
                    player.displayClientMessage(
                            Component.literal(
                                    "The value of " + value.element + " is " + value.get()
                            ), false
                    );
                }
            } else if (!(arg.getSource().getEntity() instanceof Player) && arg.getArgument("type", ConfigEnum.class) == ConfigEnum.get) {
                JSConf.LOGGER.warn("This command can only be performed by a player!");
            } else {
                JSConf.CONFIGS.get(arg.getArgument("fileName", String.class)).update();
            }
            return 0;
        }))
        ));
    }
    @SubscribeEvent
    public static void reloadConfigCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("rejsconf").then(Commands.argument("fileName", StringArgumentType.word()).executes(arg -> {
                    JSConf.CONFIGS.get(arg.getArgument("fileName", String.class)).update();
                    return 0;
                }))
        );
    }
}
