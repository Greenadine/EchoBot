package me.greenadine.echobot.handlers;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class PermissionsHandler {

    public static boolean hasPermission(User user, Server server, PermissionType permission) {
        return server.hasPermission(user, permission);
    }
}