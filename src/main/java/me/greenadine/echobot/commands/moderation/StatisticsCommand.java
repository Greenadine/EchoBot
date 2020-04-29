package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.PermissionsHandler;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class StatisticsCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "statistics";
    }

    public String getDescription() {
        return "See the statistics of the server.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!statistics";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return "stats"; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (e.getServer().isPresent()) {
            if (handler.getUser().isPresent()) {
                if (!PermissionsHandler.isModerator(handler.getUser().get())) {
                    handler.reply(handler.getUser().get().getNicknameMentionTag() + " Nice try buddy, but you do not have permission to use this command.");
                    return;
                }
            } else {
                handler.reply("Failed to execute command. Reason: Permission check fail (User empty).");
                return;
            }
        } else {
            handler.reply("Failed to execute command. Reason: Permission check fail (Server empty).");
            return;
        }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Type ``e!help stats`` for command information.");
        }

        if (!e.getServer().isPresent()) {
            handler.reply("Failed to retrieve statistics from server. Reason: Server empty.");
            return;
        }

        Server server = e.getServer().get();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Statistics of " + server.getName())
                .setColor(Color.cyan);

        if (server.getIcon().isPresent()) {
            embed.setThumbnail(server.getIcon().get());
        }

        // Member/bot counts
        int botsCount = 0;
        int membersCount = 0;

        for (User member : server.getMembers()) {
            if (member.isBot()) {
                botsCount++;
            } else {
                membersCount++;
            }
        }

        embed.addField("Users",
                "Members: " + membersCount + ".\n" +
                        "Bots: " + botsCount + ".\n" +
                        "Total: " + (membersCount + botsCount) + ".");

        // Channel counts
        int textChannelsCount = 0;
        int voiceChannelsCount = 0;
        int channelCategoriesCount = 0;
        int nsfwCount = 0;

        for (ServerChannel channel : server.getChannels()) {
            if (channel.asServerTextChannel().isPresent()) {
                textChannelsCount++;

                if (channel.asServerTextChannel().get().isNsfw()) {
                    nsfwCount++;
                }
            }
            else if (channel.asServerVoiceChannel().isPresent()) {
                voiceChannelsCount++;
            }
            else if (channel.asChannelCategory().isPresent()) {
                channelCategoriesCount++;
            }
        }

        embed.addField("Channels",
                "Categories: " + channelCategoriesCount + ".\n" +
                        "Text channels: " + textChannelsCount + ".\n" +
                        "Voice channels: " + voiceChannelsCount + ".\n" +
                        "NSFW: " + nsfwCount + ".\n" +
                        "Total: " + (channelCategoriesCount + textChannelsCount + voiceChannelsCount) + ".");

        // Roles counts
        embed.addField("Roles", server.getRoles().size() + ".");

        // Custom emoji count
        embed.addField("Custom emoji's", server.getCustomEmojis().size() + ".");

        // Show statistics
        handler.reply(embed);
    }
}
