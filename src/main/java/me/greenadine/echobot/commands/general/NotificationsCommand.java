package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Notifier;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class NotificationsCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "notifications";
    }

    public String getDescription() {
        return "Check or manage your notification settings.";
    }

    public String getDetails() {
        return "Check or manage your notification settings.\n\n" +
                "**Notification types**\n" +
                "``repeat``: Get a notification when a new Repeat build is available to the public.\n" +
                "``repeat-patron``: Get a notification when a new Repeat build is initially released, and only available for patrons.\n" +
                "``patreon``: Get a notification when Shirokoi publishes a new post on his Patreon.\n" +
                "``all``: Opt-in or out of all notifications.";
    }

    public String getUsage() {
        return "e!notifications [type] [on/off|toggle]";
    }

    public String getArguments() {
        return "``type`` - Which type of notifications to manage.";
    }

    public String getAliases() { return "not"; }

    private Notifier notifier = EchoBot.notifier;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (user.isBot()) {
                return;
            }

            if (handler.length() == 0) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Your notification settings")
                        .setDescription("``e!help notifications`` for help with managing your settings.")
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                if (notifier.isInRepeat(user)) {
                    embed.addInlineField("Repeat updates", "Yes");
                } else {
                    embed.addInlineField("Repeat updates", "No");
                }

                if (notifier.isInRepeatPatron(user)) {
                    embed.addInlineField("Repeat updates (patron)", "Yes");
                } else {
                    embed.addInlineField("Repeat updates (patron)", "No");
                }

                if(notifier.isInPatreon(user)) {
                    embed.addInlineField("Patreon posts", "Yes");
                } else {
                    embed.addInlineField("Patreon posts", "No");
                }

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                String category = handler.getArg(0);

                if (category.equalsIgnoreCase("all")) {
                    handler.reply("Please specify whether to opt-in or out of all notifications.");
                    return;
                }

                else if (category.equalsIgnoreCase("repeat")) {
                    if (notifier.isInRepeat(user)) {
                        notifier.removeFromRepeat(user);

                        handler.reply("Opted out of notifications for Repeat releases.");
                    } else {
                        notifier.addToRepeat(user);

                        handler.reply("Opted in for notifications for Repeat releases.");
                    }
                }

                else if (category.equalsIgnoreCase("repeat-patron")) {
                    if (notifier.isInRepeatPatron(user)) {
                        notifier.removeFromRepeatPatron(user);

                        handler.reply("Opted out of notifications for patron-early Repeat releases.");
                    } else {
                        notifier.addToRepeatPatron(user);

                        handler.reply("Opted in for notifications for patron-early Repeat releases.");
                    }
                }

                else if (category.equalsIgnoreCase("patreon")) {
                    if (notifier.isInPatreon(user)) {
                        notifier.removeFromPatreon(user);

                        handler.reply("Opted out of notifications for Shirokoi Patreon posts.");
                    } else {
                        notifier.addToPatreon(user);

                        handler.reply("Opted in for notifications for Shirokoi Patreon posts.");
                    }
                }

                else {
                    handler.reply("Invalid notifications type. See ``e!help notifications`` for help.");
                }
            }

            else if (handler.length() == 2) {
                String category = handler.getArg(0);
                String value = handler.getArg(1);

                if (!(value.equalsIgnoreCase("on") || value.equalsIgnoreCase("off"))) {
                    handler.reply("Valid values are ``on`` and ``off``.");
                    return;
                }

                if (category.equalsIgnoreCase("all")) {
                    if (value.equalsIgnoreCase("off")) {
                        notifier.removeFromAll(user);

                        handler.reply("Opted-out for all notifications.");
                        return;
                    } else {
                        notifier.addToAll(user);

                        handler.reply("Opted-in for all notifications.");
                        return;
                    }
                }

                if (category.equalsIgnoreCase("repeat")) {
                    if (value.equalsIgnoreCase("off")) {
                        if (!notifier.isInRepeat(user)) {
                            handler.reply("You are already opted-out for Repeat release notifications.");
                            return;
                        }

                        notifier.removeFromRepeat(user);

                        handler.reply("Opted out of notifications for Repeat releases.");
                    } else {
                        if (notifier.isInRepeat(user)) {
                            handler.reply("You are already opted-in for Repeat release notifications.");
                            return;
                        }

                        notifier.addToRepeat(user);

                        handler.reply("Opted in for notifications for Repeat releases.");
                    }
                }

                else if (category.equalsIgnoreCase("repeat-patron")) {
                    if (value.equalsIgnoreCase("off")) {
                        if (!notifier.isInRepeatPatron(user)) {
                            handler.reply("You are already opted-out for patron-early Repeat release notifications.");
                            return;
                        }

                        notifier.removeFromRepeatPatron(user);

                        handler.reply("Opted out of notifications for patron-early Repeat releases.");
                    } else {
                        if (notifier.isInRepeatPatron(user)) {
                            handler.reply("You are already opted-in for patron-early Repeat release notifications.");
                            return;
                        }

                        notifier.addToRepeatPatron(user);

                        handler.reply("Opted in for notifications for patron-early Repeat releases.");
                    }
                }

                else if (category.equalsIgnoreCase("patreon")) {
                    if (value.equalsIgnoreCase("off")) {
                        if (!notifier.isInPatreon(user)) {
                            handler.reply("You are already opted-out for Shirokoi Patreon post notifications.");
                            return;
                        }

                        notifier.removeFromPatreon(user);

                        handler.reply("Opted out of notifications for Shirokoi Patreon posts.");
                    } else {
                        if (!notifier.isInPatreon(user)) {
                            handler.reply("You are already opted-in for Shirokoi Patreon post notifications.");
                            return;
                        }

                        notifier.addToPatreon(user);

                        handler.reply("Opted in for notifications for Shirokoi Patreon posts.");
                    }
                }

                else {
                    handler.reply("Invalid notifications type. See ``e!help notifications`` for help.");
                }
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help notifications`` for command information.");
            }
        }
    }
}
