package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.LockHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class LockCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "lock";
    }

    public String getDescription() {
        return "Lock one or all channels, only allowing Staff members to chat in the locked channel(s).";
    }

    public String getDetails() {
        return "Lock one or all channels, only allowing Staff members to chat in the locked channel(s).\n\n" +
                "Use ``e!lock`` to lock/unlock the channel where the command is invoked.\n" +
                "Use ``e!lock [taggedchannel]`` to lock/unlock a specific channel.\n" +
                "Use ``e!lock all`` to lock all the channels on the server.\n" +
                "Use ``e!lock clear`` to unlock all the channels on the server.";
    }

    public String getUsage() {
        return "e!lock [all]";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    private LockHandler lock = EchoBot.lock;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            if (!PermissionsHandler.isModerator(handler.getUser().get())) {
                handler.reply(handler.getUser().get().getNicknameMentionTag() + " Nice try buddy, but you do not have permission to use this command.");
                return;
            }
        } else {
            handler.reply("Failed to execute command. Reason: Permission check fail (User empty).");
            return;
        }

        if (handler.length() == 0) {
            if (lock.isLocked(e.getChannel())) {
                boolean success = lock.unlockChannel(e.getChannel());

                if (success) {
                    handler.reply("This channel is no longer locked.");
                } else {
                    handler.reply("An internal error has occurred while trying to unlock channel..");
                }
                return;
            } else {
                boolean success = lock.lockChannel(e.getChannel());

                if (success) {
                    handler.reply("This channel is now locked.");
                } else {
                    handler.reply("An internal error has occurred while trying to lock channel..");
                }
                return;
            }
        }

        if (handler.getArg(0).equalsIgnoreCase("all")) {
            if (handler.length() == 1) {
                lock.lockAll();

                handler.reply("Locked all text channels.");
                return;
            }
        }

        else if (handler.getArg(0).equalsIgnoreCase("clear")) {
            if (handler.length() == 1) {
                lock.unlockAll();

                handler.reply("All channels were unlocked.");
                return;
            }
        }

        else {
            Optional<ServerTextChannel> optChannel;

            if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
                long id;

                try {
                    id = Long.valueOf(handler.getArg(0));
                } catch (NumberFormatException ex) {
                    handler.reply("Please either give a channel's ID or tag a channel like this: <#" + 676391123761233930L + ">.");
                    return;
                }

                optChannel = EchoBot.bot.getServerTextChannelById(id);
            } else {
                optChannel = TagUtils.getTextChannel(handler.getArg(0));
            }

            if (optChannel.isPresent()) {
                TextChannel channel = optChannel.get();

                if (lock.isLocked(channel)) {
                    lock.unlockChannel(channel);

                    handler.reply("Channel <#" + channel.getId() + "> is no longer locked.");
                } else {
                    lock.lockChannel(channel);

                    handler.reply("Channel <#" + channel.getId() + "> is now locked.");
                }
                return;
            } else {
                handler.reply("Couldn't resolve server channel from ID/tag.");
                return;
            }
        }

        handler.reply("Invalid command usage. Type ``e!help lock`` for command information.");
    }
}
