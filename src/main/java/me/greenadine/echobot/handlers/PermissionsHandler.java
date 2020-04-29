package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class PermissionsHandler {

    private static Server server = EchoBot.bot.getServerById(595246997762605065L).get();

    private static Role trusted = EchoBot.bot.getRoleById(667795907424813056L).get();

    public static boolean hasPermission(User user, Server server, PermissionType permission) {
        return server.hasPermission(user, permission);
    }

    public static boolean isModerator(User user) {
        return server.hasPermission(user, PermissionType.MANAGE_ROLES);
    }

    public static boolean isAssistant(User user) {
        return server.hasPermission(user, PermissionType.KICK_MEMBERS);
    }


    public static boolean isTrusted(User user) {
        return trusted.getUsers().contains(user);
    }
}