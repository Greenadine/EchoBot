package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Phrases;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class WishCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "wish";
    }

    public String getDescription() {
        return "Let Echo grant you a wish. Or not...";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!wish <wish>";
    }

    public String getArguments() {
        return "``wish`` - Your wish.";
    }

    public String getAliases() { return null; }

    private Phrases phrases = EchoBot.wish;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() == 0) {
            handler.reply("Invalid command usage. Type ``e!help wish`` for command information.");
            return;
        }

        if (handler.getArguments().equalsIgnoreCase("Owen Cut Content")) {
            handler.reply("*Really?* Of __all__ the things you can wish for, you wish for **this**? Geez. I will not grant you your wish, but I will give you something else...");

            if (handler.getUser().isPresent()) {
                User user = handler.getUser().get();

                if (EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
                    Server server = EchoBot.bot.getServerById(EchoBot.serverId).get();

                    user.updateNickname(server, "Sinnamon Thot");
                    handler.reply("*Name changed to 'Sinnamon Thot'.*");
                } else {
                    System.out.println("Failed to execute wish command for user " + user.getDiscriminatedName() + ". Reason: Server empty.");
                }
            } else {
                System.out.println("Failed to execute wish command. Reason: User empty.");
            }
        }

        if (handler.getArguments().equalsIgnoreCase("Kevin") || handler.getArguments().equalsIgnoreCase("Kevin Zuman") || handler.getArguments().equalsIgnoreCase("Kevin Mitchell Zuman") || handler.getArguments().equalsIgnoreCase("Greenadine")) {
            handler.reply("You cannot wish for our **supreme leader**.");
            return;
        }

        if (handler.getArguments().equalsIgnoreCase("Aleks") || handler.getArguments().equalsIgnoreCase("Aleksander") || handler.getArguments().equalsIgnoreCase("Aleksander Marek Rothmeier")) {
            handler.reply("I am sorry, but I am forbidden by our great supreme overlord Kevin to do this. You better watch out for him now.");
            return;
        }

        if (handler.getArguments().equalsIgnoreCase("flowers")) {
            handler.reply("Well well well, *Owen*. Fine then, *here*, your flowers. Now go get 'em.");
            return;
        }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (phrases.getSize() == 0) {
                handler.reply("Nah, I don't think I will.");
            } else {
                handler.reply(phrases.getRandom(user));
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}