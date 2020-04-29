package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.LockHandler;
import me.greenadine.echobot.handlers.MessageHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class GodCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "god";
    }

    public String getDescription() {
        return "Lock a channel, only allowing the **god** to speak.";
    }

    public String getDetails() {
        return "Invoke the power of the gods.\n\n" +
                "Use ``e!god`` to enter god mode in the channel where the command is invoked.\n" +
                "Use ``e!god [taggedchannel]`` to enter god mode in a specific channel.\n" +
                "Use ``e!god all`` to enter god mode the channels on the server.\n" +
                "Use ``e!god clear`` to exit god mode for all channels.";
    }

    public String getUsage() {
        return "e!god [all]";
    }

    public String getArguments() { return null; }

    public String getAliases() { return null; }

    private LockHandler lock = EchoBot.lock;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            if (handler.getUser().get().getId() != 173051548635627520L) {
                handler.reply(handler.getUser().get().getNicknameMentionTag() + " Nice try buddy, but you do not have permission to use this command.");
                return;
            }
        } else {
            handler.reply("Failed to execute command. Reason: Permission check fail (User empty).");
            return;
        }

        if (handler.length() == 0) {
            if (lock.isFullLocked(e.getChannel())) {
                boolean success = lock.fullUnlockChannel(e.getChannel());

                if (success) {
                    handler.reply("*Exited god mode.*");
                } else {
                    handler.reply("An internal error has occurred while trying to exit god mode for channel..");
                }
                return;
            } else {
                boolean success = lock.fullLockChannel(e.getChannel());

                if (success) {
                    handler.reply("***Entered god mode.***");
                    handler.reply("Okay, listen up, you *peasants*. The god is speaking now, and y'all gotta be silent. ***Capisce?***");
                } else {
                    handler.reply("An internal error has occurred while trying to enter god mode for channel..");
                }
                return;
            }
        }

        if (handler.getArg(0).equalsIgnoreCase("server")) {
            if (handler.length() == 1) {
                lock.fullLockAll();

                handler.reply("***Entered server-wide god mode.***");
                handler.reply("*Y'all are ***NOTHING**!*");
                return;
            }
        }

        else if (handler.getArg(0).equalsIgnoreCase("exit")) {
            if (handler.length() == 1) {
                lock.fullUnlockAll();

                handler.reply("*Exited server-wide god mode.*");
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

                if (lock.isFullLocked(channel)) {
                    lock.fullUnlockChannel(channel);

                    handler.reply("*Channel <#" + channel.getId() + "> is no longer in god mode.*");
                } else {
                    lock.fullLockChannel(channel);

                    handler.reply("*Channel <#" + channel.getId() + "> is now in god mode.*");
                }
                return;
            } else {
                handler.reply("Couldn't resolve server channel from ID/tag.");
                return;
            }
        }

        handler.reply("Invalid command usage. Type ``e!help god`` for command information.");
    }
}
