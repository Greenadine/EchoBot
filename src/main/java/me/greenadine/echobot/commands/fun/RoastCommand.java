package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Phrases;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class RoastCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "roast";
    }

    public String getDescription() {
        return "Let Echo roast someone for you.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!roast <user>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private Phrases roast = EchoBot.roast;
    private Phrases roast_self = EchoBot.roast_self;
    private Phrases roast_echo = EchoBot.roast_echo;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) {return;}

        // Command requires 1 argument to be given.
        if (handler.length() != 1) {
            handler.reply("Invalid command usage. Type ``e!help roast`` for command information.");
            return;
        }

        // If 1st argument given is not a tag (e.g. @Greenadine#0687).
        if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
            handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
            return;
        }

        // If user optional is empty, do not execute command.
        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        User user = handler.getUser().get();

        // If target optional is empty, do not execute command.
        if (!TagUtils.getUser(handler.getArg(0)).isPresent()) {
            handler.reply("Failed to execute command. Reason: Target empty.");
            return;
        }

        User target = TagUtils.getUser(handler.getArg(0)).get();

        // If the user is trying to roast themselves.
        if (user.getId() == target.getId()) {
            if (roast_self.getSize() == 0) {
                handler.reply("You do know that you are trying to roast yourself right now, huh?");
            } else {
                handler.reply(roast_self.getRandom(user));
            }
            return;
        }

        /*
        // If the user is trying to roast *me*, Kevin, the almighty overlord, of all people.
        if (TagUtils.isUserTagged(handler.getArg(0), 173051548635627520L) || TagUtils.isUserTagged(handler.getArg(0), 498462419954434048L)) {
            if (user.getId() == 340307125135867914L) { // If Aleks roasts Kevin
                if (roast.getSize() == 0) {
                    handler.reply(target.getNicknameMentionTag() + " You're lame. Lmao get rekt, son.");
                } else {
                    handler.reply(roast.getRandom(target));
                }
            } else { // If someone else roasts Kevin. That ain't ever happening, buddy.
                handler.reply("I am sorry, I can not roast our supreme overlord Kevin. Even attempting to do so is punishable by death. You better watch out yourself, too...");
            }

            return;
        }
        // If the user is trying to roast my love, Aleks.
        if (TagUtils.isUserTagged(handler.getArg(0), 340307125135867914L)) {
            if (user.getId() == 173051548635627520L) { // If Kevin roasts Aleks
                if (roast.getSize() == 0) {
                    handler.reply(target.getNicknameMentionTag() + " You're lame. Lmao get rekt, son.");
                } else {
                    handler.reply(roast.getRandom(target));
                }
            } else { // If someone else roasts Aleks. That ain't happening, bubbo.
                handler.reply("I am sorry, but I am forbidden by our great supreme overlord Kevin to do this. You better watch out for him now...");
            }
            return;
        }
        */

        // If the user is trying to roast Echo.
        if (TagUtils.isUserTagged(handler.getArg(0), 642192249563906050L)) {
            if (roast_echo.getSize() == 0) {
                handler.reply(user.getNicknameMentionTag() + "Nice try, buddy.");
            } else {
                handler.reply(roast_echo.getRandom(user));
            }
            return;
        }

        // Bots cannot be roasted.
        if (target.isBot()) {
            handler.reply("That user is a bot.");
            return;
        }

        // Roast the targeted user.
        if (roast.getSize() == 0) {
            handler.reply(target.getNicknameMentionTag() + "You're lame. Lmao get rekt, son.");
        } else {
            handler.reply(roast.getRandom(target));
        }
    }
}