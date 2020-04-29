package me.greenadine.echobot;

import me.greenadine.echobot.actions.ReplyAction;
import me.greenadine.echobot.commands.Commands;
import me.greenadine.echobot.commands.general.*;
import me.greenadine.echobot.handlers.*;
import me.greenadine.echobot.handlers.mute.MuteHandler;
import me.greenadine.echobot.handlers.shop.Inventories;
import me.greenadine.echobot.handlers.shop.Shop;
import me.greenadine.echobot.handlers.slots.SlotMachine;
import me.greenadine.echobot.handlers.trivia.Trivia;
import me.greenadine.echobot.handlers.valentine.Valentine;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.listeners.*;
import me.greenadine.echobot.logging.*;
import me.greenadine.echobot.handlers.mute.MuteDurationTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class EchoBot {

    public static DiscordApi bot;
    public static String prefix = "e!";
    public static String versionID = "0.2.5";
    public static String launchDate = "12/25/2019";
    public static String lastUpdate = "4/19/2020";

    private static String tokenFile = "main";

    public static long serverId = 595246997762605065L; // Unofficial Repeat Fan Server
    // public static long serverId = 695318989659963423L; // Test server

    public static Settings settings;
    public static Commands commands;

    public static Economy econ;
    public static Levels lvl;
    public static BanHandler bans;
    public static WarningsHandler warnings;
    public static MuteHandler mute;
    public static LockHandler lock;
    public static Notifier notifier;
    // public static TimezoneHandler timezones; TODO

    public static Inventories inventories;
    public static Shop shop;
    public static SlotMachine slots;
    public static Valentine valentine;

    public static Phrases future;
    public static Phrases poke;
    public static Phrases roast;
    public static Phrases roast_self;
    public static Phrases roast_echo;
    public static Phrases wish;

    public static Trivia trivia;

    public static void main(String[] args) {
        System.out.println("Loading EchoBot v" + versionID + "...");

        String token;

        try {
            Scanner scanner = new Scanner(new File("tokens/" + tokenFile + ".txt"));

            token = scanner.nextLine();

            scanner.close();
        } catch (IOException ex) {
            System.out.println("Failed to read token from file '" + tokenFile + ".txt'. Reason: IOException");
            ex.printStackTrace();
            return;
        }

        setup(token);

        System.out.println("EchoBot v" + versionID + " online.");
    }

    private static void setup(String token) {
        // Connect bot to bot user
        bot = new DiscordApiBuilder().setToken(token).login().join();

        // Bot setup
        settings = new Settings();
        econ = new Economy();
        lvl = new Levels();
        bans = new BanHandler();
        warnings = new WarningsHandler();
        mute = new MuteHandler();
        lock = new LockHandler();
        notifier = new Notifier();
        // timezones = new TimezoneHandler(); TODO

        inventories = new Inventories();
        shop = new Shop();
        slots = new SlotMachine();
        valentine = new Valentine();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Activity activity = Activity.getRandom();

                bot.updateActivity(activity.getType(), activity.getValue());
            }
        }, 0, 1800000);

        // Load phrases from files
        future = new Phrases("assets/future.txt");
        poke = new Phrases("assets/poke.txt");
        roast = new Phrases("assets/roasts/roast.txt");
        roast_self = new Phrases("assets/roasts/roast-self.txt");
        roast_echo = new Phrases("assets/roasts/roast-echo.txt");
        wish = new Phrases("assets/wish.txt");

        // Load trivia questions from files
        trivia = new Trivia("assets/trivia-repeat.txt");

        // Schedule mute duration reducer
        timer.scheduleAtFixedRate(new MuteDurationTask(), 0, 1000);

        // Register commands
        commands = new Commands();
        bot.addListener(new HelpCommand());

        // Register listeners
        bot.addListener(new JoinListener());
        bot.addListener(new MessageCreateListener());
        bot.addListener(new BanListener());
        bot.addListener(new NotifyListener());
        bot.addListener(new TriviaListener());

        // Register log listeners
        bot.addListener(new ChatLogger());
        bot.addListener(new ModerationLogger());
        bot.addListener(new UserLogger());
        bot.addListener(new BanLogger());
        bot.addListener(new LeaveJoinLogger());

        // Register action listeners
        bot.addListener(new ReplyAction());
    }
}