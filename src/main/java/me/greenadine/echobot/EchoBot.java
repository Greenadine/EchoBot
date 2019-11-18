package me.greenadine.echobot;

import me.greenadine.echobot.commands.economy.*;
import me.greenadine.echobot.commands.fun.FutureCommand;
import me.greenadine.echobot.commands.fun.PokeCommand;
import me.greenadine.echobot.commands.fun.RoastCommand;
import me.greenadine.echobot.commands.general.*;
import me.greenadine.echobot.commands.moderation.*;
import me.greenadine.echobot.handlers.*;
import me.greenadine.echobot.listeners.MessageGiveStatListener;
import me.greenadine.echobot.objects.MuteDurationTask;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.Timer;

public class EchoBot {

    public static DiscordApi bot;
    public static Economy econ;
    public static Levels lvl;
    public static WarningHandler warnings;
    public static MuteHandler mute;

    public static String prefix = "e!";

    public static Phrases future;
    public static Phrases poke;
    public static Phrases roast;
    public static Phrases roast_self;
    public static Phrases roast_echo;
    public static Phrases wish;

    public static void main(String[] args) {
        String token = "NjQyMTkyMjQ5NTYzOTA2MDUw.XcrxcQ.BiYCt-0pZ4fAmCRsG3pyp0VR1O4";

        bot = new DiscordApiBuilder().setToken(token).login().join();
        econ = new Economy();
        lvl = new Levels();
        warnings = new WarningHandler();
        mute = new MuteHandler();

        bot.updateActivity("Repeat (e!help)");

        // Load phrases from files
        future = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/future.txt");
        poke = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/poke.txt");
        roast = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/roasts/roast.txt");
        roast_self = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/roasts/roast-self.txt");
        roast_echo = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/roasts/roast-echo.txt");
        wish = new Phrases("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/wish.txt");

        // Schedule mute duration reducer
        Timer timer = new Timer(true);
        timer.schedule(new MuteDurationTask(), 0, 1000);

        // Add message listeners //
        // Economy listeners
        bot.addListener(new MessageGiveStatListener());

        // Add command listeners //
        // General commands
        bot.addListener(new HelpCommand());
        bot.addListener(new PingCommand());
        bot.addListener(new WarningsCommand());
        bot.addListener(new RankCommand());
        bot.addListener(new TopRankCommand());

        // Fun commands
        bot.addListener(new PokeCommand());
        bot.addListener(new FutureCommand());
        bot.addListener(new RoastCommand());

        // Economy commands
        bot.addListener(new BalanceCommand());
        bot.addListener(new PayCommand());
        bot.addListener(new EconSetCommand());
        bot.addListener(new EconAddCommand());
        bot.addListener(new EconWithdrawCommand());
        bot.addListener(new EconClearCommand());
        bot.addListener(new TopEconCommand());

        // Moderation commands
        bot.addListener(new StatusCommand());
        bot.addListener(new MuteCommand());
        bot.addListener(new UnmuteCommand());
        bot.addListener(new WarningGiveCommand());
        bot.addListener(new WarningRemoveCommand());
        bot.addListener(new WarningClearCommand());
        bot.addListener(new WarningListCommand());
    }
}