package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.Settings;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.channel.server.ServerChannelDeleteEvent;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.StringJoiner;

public class SettingsCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "settings";
    }

    public String getDescription() {
        return "Manage the settings of Echo.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!settings <setting> [arguments]";
    }

    public String getArguments() {
        return "``setting`` - Which setting to check/manage.\n"
                + "``arguments`` - The action to perform on managing the setting.";
    }

    public String getAliases() { return null; }

    private Settings settings = EchoBot.settings;

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

        if (handler.length() == 0) {
            handler.reply("Invalid command usage. Type ``e!help settings`` for command information.");
            return;
        }

        // Check or add/remove roles from the defaultRoles setting
        if (handler.getArg(0).equalsIgnoreCase("defaultRoles")) {
            if (handler.length() == 1) {
                if (settings.getDefaultRoles().isEmpty()) {
                    handler.reply("There currently are no default roles.");
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("List of default roles")
                        .setColor(Color.cyan);

                StringJoiner joiner = new StringJoiner("\n");

                for (Long id : settings.getDefaultRoles()) {
                    Optional<Role> optRole = EchoBot.bot.getRoleById(id);

                    if (optRole.isPresent()) {
                        Role role = optRole.get();
                        joiner.add("<@&" + role.getId() + "> | ID: " + id);
                    } else {
                        joiner.add("Not found | ID: " + id);
                    }
                }

                embed.addField("Roles", joiner.toString());

                handler.reply(embed);
            }

            else if (handler.length() == 2) {
                if (handler.getArg(1).equalsIgnoreCase("add") || handler.getArg(1).equalsIgnoreCase("remove")) {
                    handler.reply("Please give an ID of a role to add/remove.");
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings defaultRoles [add|remove] [id]``.");
                }
            }

            else if (handler.length() == 3) {
                if (handler.getArg(1).equalsIgnoreCase("add")) {
                    Optional<Role> optRole;

                    if (!TagUtils.isRoleTag(handler.getArg(2))) {
                        long id;

                        try {
                            id = Long.valueOf(handler.getArg(2));
                        } catch (NumberFormatException  ex) {
                            handler.reply("Please either give a role's ID or tag a role like this: <@&595257719494017035>.");
                            return;
                        }

                        if (EchoBot.bot.getCachedUserById(id).isPresent()) {
                            optRole = EchoBot.bot.getRoleById(id);
                        } else {
                            handler.reply("Role with ID '" + handler.getArg(0)+ "' could not be found.");
                            return;
                        }
                    } else {
                        optRole = TagUtils.getRole(handler.getArg(2));
                    }

                    if (!optRole.isPresent()) {
                        handler.reply("Failed to add role to default roles list. Reason: Role empty.");
                        return;
                    }

                    Role role = optRole.get();

                    if (settings.isDefaultRole(role.getId())) {
                        handler.reply("This role is already in the default roles list.");
                        return;
                    }

                    boolean success = settings.addDefaultRole(role.getId());

                    if (success) {
                        handler.reply("Added role <@&" + role.getId() + "> to default roles list.");
                    } else {
                        handler.reply("An internal error has occurred while trying to add role to default role list.");
                    }
                }

                else if (handler.getArg(1).equalsIgnoreCase("remove")) {
                    if (TagUtils.isRoleTag(handler.getArg(2))) {
                        Optional<Role> optRole = TagUtils.getRole(handler.getArg(2));

                        if (!optRole.isPresent()) {
                            handler.reply("Could not find given role.");
                            return;
                        }

                        Role role = optRole.get();

                        if (!settings.isDefaultRole(role.getId())) {
                            handler.reply("Role <@&" + role.getId() + "> is not in the default roles list.");
                            return;
                        }

                        boolean success = settings.removeDefaultRole(role.getId());

                        if (success) {
                            handler.reply("Removed role <@&" + role.getId() + "> from default roles list.");
                        } else {
                            handler.reply("An internal error has occurred while trying to remove role from default role list.");
                        }
                    } else {
                        long id;

                        try {
                            id = Long.valueOf(handler.getArg(2));
                        } catch (NumberFormatException  ex) {
                            handler.reply("Please either give a role's ID or tag a role like this: <@&595257719494017035>.");
                            return;
                        }

                        if (!settings.isDefaultRole(id)) {
                            if (EchoBot.bot.getRoleById(id).isPresent()) {
                                handler.reply("Role <@&" + id + "> is not in the default roles list.");
                            } else {
                                handler.reply("Role with given ID is not in default roles list, or given ID is not that of a role.");
                            }

                            return;
                        }

                        boolean success = settings.removeDefaultRole(id);

                        if (success) {
                            if (EchoBot.bot.getRoleById(id).isPresent()) {
                                handler.reply("Removed role <@&" + id + "> from default roles list.");
                            } else {
                                handler.reply("Removed role with id '" + id + "' from default roles list.");
                            }
                        } else {
                            handler.reply("An internal error has occurred while trying to remove role from default role list.");
                        }
                    }
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings defaultRoles [add|remove] [id]``.");
                }
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings defaultRoles [add|remove] [id]``.");
            }
        }

        // Check or change the value of the economyGoldLimit setting
        else if (handler.getArg(0).equalsIgnoreCase("economyGoldLimit")) {
            if (handler.length() == 1) {
                handler.reply("Current value of 'economyGoldLimit': " + settings.getEconomyGoldLimit() + ".");
            }

            else if (handler.length() == 2) {
                int limit;

                try {
                    limit = Integer.valueOf(handler.getArg(1));
                } catch (NumberFormatException ex) {
                    handler.reply("Value of 'economyGoldLimit' has to be a number (max value: " + Integer.MAX_VALUE + ").");
                    return;
                }

                if (limit <= 0) {
                    handler.reply("Minimum value of 'economyGoldLimit' is 1.");
                    return;
                }

                try {
                    settings.setEconomyGoldLimit(limit);
                } catch (Exception ex) {
                    handler.reply("An error has occurred while trying to change value of 'economyGoldLimit'.");
                    ex.printStackTrace();
                    return;
                }

                handler.reply("Changed value of 'economyGoldLimit' to **" + limit + "**.");
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings economyGoldLimit [newValue]``.");
            }
        }

        // Change the value of the warningWeightThreshold setting
        else if (handler.getArg(0).equalsIgnoreCase("warningWeightThreshold")) {
            if (handler.length() == 1) {
                handler.reply("Current value of 'warningWeightThreshold': " + settings.getWarningWeightThreshold() + ".");
            }

            else if (handler.length() == 2) {
                double threshold;

                try {
                    threshold = Double.valueOf(handler.getArg(1));
                } catch (NumberFormatException ex) {
                    handler.reply("Value of 'warningWeightThreshold' has to be a (decimal) number (max value: " + Double.MAX_VALUE + ").");
                    return;
                }

                if (threshold < 1) {
                    handler.reply("Minimum value of 'warningWeightThreshold' is 1.");
                    return;
                }

                try {
                    settings.setWarningWeightThreshold(threshold);
                } catch (Exception ex) {
                    handler.reply("An error has occurred while trying to change value of 'warningWeightThreshold'.");
                    ex.printStackTrace();
                    return;
                }

                handler.reply("Changed value of 'warningWeightThreshold' set to **" + threshold + "**.");
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings warningWeightThreshold [newValue]``.");
            }
        }

        else if (handler.getArg(0).equalsIgnoreCase("welcomeChannel")) {
            if (handler.length() == 1) {
                long id = settings.getWelcomeChannel();

                if (EchoBot.bot.getServerTextChannelById(id).isPresent()) {
                    handler.reply("Current welcoming channel is <#" + id + ">.");
                } else {
                    handler.reply("Current value is '" + id + "'. **Warning**: This is not a valid ID for a channel.");
                }
            }

            else if (handler.length() == 2) {
                Optional<ServerTextChannel> optChannel;

                if (!TagUtils.isChannelTag(handler.getArg(1))) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(1));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please either give a channel's ID or tag a channel like this: <#" + 676391123761233930L + ">.");
                        return;
                    }

                    optChannel = EchoBot.bot.getServerTextChannelById(id);
                } else {
                    optChannel = TagUtils.getTextChannel(handler.getArg(1));
                }

                if (!optChannel.isPresent()) {
                    handler.reply("Failed to set channel as welcoming channel. Reason: Channel empty.");
                    return;
                }

                ServerTextChannel channel = optChannel.get();

                settings.setWelcomeChannel(channel.getId());

                handler.reply("Set welcoming channel to <#" + channel.getId() + ">.");
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings welcomeChannel [newValue]``.");
            }
        }

        // Check or add/remove channels in the excludedChannels setting
        else if (handler.getArg(0).equalsIgnoreCase("excludedChannels")) {
            if (handler.length() == 1) {
                if (settings.getExcludedChannels().isEmpty()) {
                    handler.reply("There currently are no excluded channels.");
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("List of excluded channels")
                        .setColor(Color.cyan);

                StringJoiner joiner = new StringJoiner("\n");

                for (Long id : settings.getExcludedChannels()) {
                    Optional<ServerChannel> optChannel = EchoBot.bot.getServerChannelById(id);

                    if (optChannel.isPresent()) {
                        ServerChannel channel = optChannel.get();

                        joiner.add("#" + channel.getName() + " | ID: " + id);
                    } else {
                        joiner.add("*Not found* | ID: " + id);
                    }
                }

                embed.addField("Channels", joiner.toString());

                handler.reply(embed);
            }

            else if (handler.length() == 2) {
                if (handler.getArg(1).equalsIgnoreCase("add") || handler.getArg(1).equalsIgnoreCase("remove")) {
                    handler.reply("Please give the ID of the channel to add/remove.");
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings excludedChannels [add|remove] [id]``.");
                }
            }

            else if (handler.length() == 3) {
                if (handler.getArg(1).equalsIgnoreCase("add")) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(2));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please give a valid ID.");
                        return;
                    }

                    Optional<ServerChannel> optChannel = EchoBot.bot.getServerChannelById(id);

                    if (optChannel.isPresent()) {
                        if (settings.isExcludedChannel(id)) {
                            handler.reply("Channel is already being excluded.");
                        }

                        try {
                            settings.addExcludedChannel(id);
                        } catch (Exception ex) {
                            handler.reply("An error has occurred while trying to exclude channel.");
                            ex.printStackTrace();
                            return;
                        }

                        handler.reply("Channel <#" + id + "> is now excluded.");
                    } else {
                        handler.reply("Could not find channel with given ID '" + id + "'.");
                    }
                }

                else if (handler.getArg(1).equalsIgnoreCase("remove")) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(2));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please give a valid ID.");
                        return;
                    }

                    if (!settings.isExcludedChannel(id)) {
                        handler.reply("Channel isn't currently being excluded.");
                    }

                    try {
                        settings.removeExcludedChannel(id);
                    } catch (Exception ex) {
                        handler.reply("An error has occurred while trying to remove channel from exclusion.");
                        ex.printStackTrace();
                        return;
                    }

                    if (EchoBot.bot.getServerTextChannelById(id).isPresent()) {
                        handler.reply("Channel <#" + id + "> is no longer excluded.");
                    } else {
                        handler.reply("Channel with id '" + id + "' removed from list.");
                    }
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings excludedChannels [add|remove] [id]``.");
                }
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings excludedChannels [add|remove] [id]``.");
            }
        }

        // Check or add/remove words from the bannedWords setting
        else if (handler.getArg(0).equalsIgnoreCase("bannedWords")) {
            if (handler.length() == 1) {
                if (settings.getBannedWords().isEmpty()) {
                    handler.reply("There currently are no banned words.");
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("List of banned words")
                        .setColor(Color.cyan);

                StringJoiner joiner = new StringJoiner("\n");

                for (String word : settings.getBannedWords()) {
                    joiner.add(word);
                }

                embed.addField("Words", joiner.toString());

                handler.reply(embed);
            }

            else if (handler.length() == 2) {
                if (handler.getArg(1).equalsIgnoreCase("add") || handler.getArg(1).equalsIgnoreCase("remove")) {
                    handler.reply("Please give a word.");
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings logExcludedChannels [add|remove] [id]``.");
                }
            }

            else if (handler.length() == 3) {
                if (handler.getArg(1).equalsIgnoreCase("add")) {
                    String word = handler.getArg(2);

                    if (settings.isBannedWord(word)) {
                        handler.reply("Word is already on the banned list.");
                        return;
                    }

                    try {
                        settings.addBannedWord(word);
                    } catch (Exception ex) {
                        handler.reply("An error has occurred while trying to add banned word.");
                        ex.printStackTrace();
                        return;
                    }

                    handler.reply("Added word '**" + word + "**' to banned list.");
                }

                else if (handler.getArg(1).equalsIgnoreCase("remove")) {
                    String word = handler.getArg(2);

                    if (!settings.isBannedWord(word)) {
                        handler.reply("Word is not on the banned list.");
                        return;
                    }

                    try {
                        settings.removeBannedWord(word);
                    } catch (Exception ex) {
                        handler.reply("An error has occurred while trying to remove banned word.");
                        ex.printStackTrace();
                        return;
                    }

                    handler.reply("Removed word '**" + word + "**' from banned list.");
                }

                else {
                    handler.reply("Invalid command usage. Usage: ``e!settings logExcludedChannels [add|remove] [id]``.");
                }
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings bannedWords [add|remove] [id]``.");
            }
        }

        // Check or change the value of the valentineEnabled setting
        else if (handler.getArg(0).equalsIgnoreCase("valentineEnabled")) {
            if (handler.length() == 1) {
                handler.reply("Current value of 'valentineEnabled': " + settings.isValentineEnabled() + ".");
            }

            else if (handler.length() == 2) {
                if (handler.getArg(1).equalsIgnoreCase("true")) {
                    if (settings.isValentineEnabled()) {
                        handler.reply("'valentineEnabled' is already set to **true**.");
                        return;
                    }

                    settings.setValentineEnabled(true);
                    handler.reply("Changed value of 'valentineEnabled' to **true**.");
                }
                else if (handler.getArg(1).equalsIgnoreCase("false")) {
                    if (!settings.isValentineEnabled()) {
                        handler.reply("'valentineEnabled' is already set to **false**.");
                        return;
                    }

                    try {
                        settings.setValentineEnabled(false);
                    } catch (Exception ex) {
                        handler.reply("An error has occurred while trying to change value of 'valentineEnabled'.");
                        ex.printStackTrace();
                        return;
                    }

                    handler.reply("Changed value of 'valentineEnabled' to **false**.");
                }
                else {
                    handler.reply("Value of 'valentineEnabled' has to be either **true** or **false**.");
                }
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings valentineEnabled [newValue]``.");
            }
        }

        // Check or change the value of the valentineCurrent setting
        else if (handler.getArg(0).equalsIgnoreCase("valentineCurrent")) {
            if (handler.length() == 1) {
                handler.reply("Current value of 'valentineCurrent': " + settings.getCurrentValentine() + ".");
            }

            else if (handler.length() == 2) {
                File valentine = new File("data/valentine/" + handler.getArg(1) + ".ser");

                if (!valentine.exists()) {
                    handler.reply("Failed to find valentine file with id '" + handler.getArg(1) + "'.");
                    return;
                }

                try {
                    settings.setCurrentValentine(handler.getArg(1));
                } catch (Exception ex) {
                    handler.reply("An error has occurred while trying to change value of 'valentineCurrent'.");
                    ex.printStackTrace();
                    return;
                }

                handler.reply("Changed value of 'valentineCurrent' to **" + handler.getArg(1) + "**.");
                EchoBot.valentine.update();
            }

            else {
                handler.reply("Invalid command usage. Usage: ``e!settings valentineCurrent [newValue]``.");
            }
        }

        else {
            handler.reply("Unknown setting '" + handler.getArg(0) + "'.");
        }
    }
}
