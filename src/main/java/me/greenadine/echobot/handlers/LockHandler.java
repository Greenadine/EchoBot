package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.permission.*;
import org.javacord.api.entity.server.Server;

import java.util.ArrayList;
import java.util.List;

public class LockHandler {

    private List<Long> fullLock = new ArrayList<>();

    private List<Long> lock = new ArrayList<>();

    /**
     * Fully lock a channel, only allowing the Principal to chat in the channel. Returns true if action was a success, false if otherwise.
     * @param ch The channel
     * @return boolean
     */
    public boolean fullLockChannel(TextChannel ch) {
        if (!ch.asServerTextChannel().isPresent()) {
            System.out.println("Failed to lock channel " + ch.getIdAsString() + ". Reason: ServerChannel empty.");
            return false;
        }

        if (!EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            System.out.println("Failed to full-lock channel. Reason: Server empty.");
            return false;
        }

        ServerTextChannel channel = ch.asServerTextChannel().get();

        if (!channel.getServer().getRoleById(596442440894775319L).isPresent()) {
            System.out.println("Failed to full-lock channel. Reason: Role empty.");
            return false;
        }

        Role studentsRole = channel.getServer().getRoleById(596442440894775319L).get(); // @Students
        Role shirokoiRole = channel.getServer().getRoleById(653227730867060737L).get(); // @Shirokoi
        Role assistantsRole = channel.getServer().getRoleById(595257686640033802L).get(); // @Assistants
        Role teachersRole = channel.getServer().getRoleById(595257620747517953L).get(); // @Teachers
        Role viceprincipalRole = channel.getServer().getRoleById(644994808754077706L).get(); // @Vice-Principal

        ServerChannelUpdater updater = channel.createUpdater();

        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setDenied(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setDenied(PermissionType.ADD_REACTIONS).build());

        updater.addPermissionOverwrite(assistantsRole, new PermissionsBuilder().setDenied(PermissionType.MANAGE_MESSAGES).build());
        updater.addPermissionOverwrite(teachersRole, new PermissionsBuilder().setDenied(PermissionType.MANAGE_MESSAGES).build());
        updater.addPermissionOverwrite(viceprincipalRole, new PermissionsBuilder().setDenied(PermissionType.MANAGE_MESSAGES).build());

        updater.update();

        fullLock.add(ch.getId());
        return true;
    }

    /**
     * Unlock a fully locked channel. Returns true if action was a success, false if otherwise.
     * @param ch The channel
     * @return boolean
     */
    public boolean fullUnlockChannel(TextChannel ch) {
        if (!ch.asServerTextChannel().isPresent()) {
            System.out.println("Failed to lock channel " + ch.getIdAsString() + ". Reason: ServerChannel empty.");
            return false;
        }

        if (!EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            System.out.println("Failed to full-unlock channel. Reason: Server empty.");
            return false;
        }

        ServerTextChannel channel = ch.asServerTextChannel().get();

        if (!channel.getServer().getRoleById(596442440894775319L).isPresent()) {
            System.out.println("Failed to full-unlock channel. Reason: Role empty.");
            return false;
        }

        Role studentsRole = channel.getServer().getRoleById(596442440894775319L).get(); // @Students
        Role assistantsRole = channel.getServer().getRoleById(595257686640033802L).get(); // @Assistants
        Role teachersRole = channel.getServer().getRoleById(595257620747517953L).get(); // @Teachers
        Role viceprincipalRole = channel.getServer().getRoleById(644994808754077706L).get(); // @Vice-Principal

        ServerChannelUpdater updater = channel.createUpdater();

        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setAllowed(PermissionType.ADD_REACTIONS).build());

        updater.addPermissionOverwrite(assistantsRole, new PermissionsBuilder().setUnset(PermissionType.MANAGE_MESSAGES).build());
        updater.addPermissionOverwrite(teachersRole, new PermissionsBuilder().setUnset(PermissionType.MANAGE_MESSAGES).build());
        updater.addPermissionOverwrite(viceprincipalRole, new PermissionsBuilder().setUnset(PermissionType.MANAGE_MESSAGES).build());

        updater.update();

        fullLock.remove(ch.getId());
        return true;
    }

    /**
     * Returns if a channel is fully locked.
     * @param ch The channel
     * @return boolean
     */
    public boolean isFullLocked(TextChannel ch) {
        return fullLock.contains(ch.getId());
    }

    /**
     * Lock all the channels on the server.
     */
    public void fullLockAll() {
        if (EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            for (ServerChannel channel : EchoBot.bot.getServerById(EchoBot.serverId).get().getChannels()) {
                if (!EchoBot.settings.getExcludedChannels().contains(channel.getId())) {
                    if (channel.asTextChannel().isPresent()) {
                        fullLockChannel(channel.asTextChannel().get());
                    }
                }
            }
        }
    }

    /**
     * Unlock all the channels on the server.
     */
    public void fullUnlockAll() {
        for (Long id : fullLock) {
            if (!EchoBot.bot.getTextChannelById(id).isPresent()) {
                System.out.println("ServerTextChannel with id '" + id + "' not found while full-unlocking channels.");
                continue;
            }

            TextChannel channel = EchoBot.bot.getTextChannelById(id).get();
            unlockChannel(channel);
        }

        lock.clear();
    }

    /**
     * Lock a channel. Returns true if action was a success, false if otherwise.
     * @param ch The channel
     * @return boolean
     */
    public boolean lockChannel(TextChannel ch) {
        if (!ch.asServerTextChannel().isPresent()) {
            System.out.println("Failed to lock channel " + ch.getIdAsString() + ". Reason: ServerChannel empty.");
            return false;
        }

        if (!EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            System.out.println("Failed to lock channel. Reason: Server empty.");
            return false;
        }

        ServerTextChannel channel = ch.asServerTextChannel().get();

        if (!channel.getServer().getRoleById(596442440894775319L).isPresent()) {
            System.out.println("Failed to lock channel. Reason: Role empty.");
            return false;
        }

        Role studentsRole = channel.getServer().getRoleById(596442440894775319L).get(); // @Students
        Role shirokoiRole = channel.getServer().getRoleById(653227730867060737L).get(); // @Shirokoi
        Role assistantsRole = channel.getServer().getRoleById(595257686640033802L).get(); // @Assistants
        Role teachersRole = channel.getServer().getRoleById(595257620747517953L).get(); // @Teachers
        Role viceprincipalRole = channel.getServer().getRoleById(644994808754077706L).get(); // @Vice-Principal

        ServerChannelUpdater updater = channel.createUpdater();

        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setDenied(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setDenied(PermissionType.ADD_REACTIONS).build());
        updater.addPermissionOverwrite(shirokoiRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(assistantsRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(teachersRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(viceprincipalRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());

        updater.update();
        lock.add(ch.getId());
        return true;
    }

    /**
     * Unlock a currently locked channel. Returns true if action was a success, false if otherwise.
     * @param ch The channel
     * @return boolean
     */
    public boolean unlockChannel(TextChannel ch) {
        if (!ch.asServerTextChannel().isPresent()) {
            System.out.println("Failed to lock channel " + ch.getIdAsString() + ". Reason: ServerChannel empty.");
            return false;
        }

        if (!EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            System.out.println("Failed to unlock channel. Reason: Server empty.");
            return false;
        }

        ServerTextChannel channel = ch.asServerTextChannel().get();

        if (!channel.getServer().getRoleById(596442440894775319L).isPresent()) {
            System.out.println("Failed to unlock channel. Reason: Role empty.");
            return false;
        }

        Role studentsRole = channel.getServer().getRoleById(596442440894775319L).get(); // @Students

        ServerChannelUpdater updater = channel.createUpdater();

        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build());
        updater.addPermissionOverwrite(studentsRole, new PermissionsBuilder().setUnset(PermissionType.ADD_REACTIONS).build());

        updater.update();
        lock.remove(ch.getId());
        return true;
    }

    /**
     * Returns whether the given channel is locked.
     * @param ch The channel
     * @return boolean
     */
    public boolean isLocked(TextChannel ch) {
        return lock.contains(ch.getId());
    }

    /**
     * Lock all the channels on the server.
     */
    public void lockAll() {
        if (EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            for (ServerChannel channel : EchoBot.bot.getServerById(EchoBot.serverId).get().getChannels()) {
                if (!EchoBot.settings.getExcludedChannels().contains(channel.getId())) {
                    if (channel.asTextChannel().isPresent()) {
                        lockChannel(channel.asTextChannel().get());
                    }
                }
            }
        }
    }

    /**
     * Unlock all the channels on the server.
     */
    public void unlockAll() {
        for (Long id : lock) {
            if (!EchoBot.bot.getTextChannelById(id).isPresent()) {
                System.out.println("ServerTextChannel with id '" + id + "' not found while unlocking channels.");
                continue;
            }

            TextChannel channel = EchoBot.bot.getTextChannelById(id).get();
            unlockChannel(channel);
        }

        lock.clear();
    }
}
