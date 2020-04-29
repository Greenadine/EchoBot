package me.greenadine.echobot.commands;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.economy.*;
import me.greenadine.echobot.commands.fun.*;
import me.greenadine.echobot.commands.game.*;
import me.greenadine.echobot.commands.general.*;
import me.greenadine.echobot.commands.moderation.*;
import org.javacord.api.DiscordApi;

import java.util.*;

public class Commands {

    private DiscordApi bot = EchoBot.bot;

    private Map<String, EchobotCommand> commands;
    private Map<CommandCategory, List<EchobotCommand>> categories;

    public Commands() {
        commands = new HashMap<>();
        categories = new HashMap<>();

        for (CommandCategory category : CommandCategory.values()) {
            categories.putIfAbsent(category, new ArrayList<>());
        }

        // Register commands //
        // General commands
        registerCommand(new InfoCommand(), CommandCategory.GENERAL);
        registerCommand(new PingCommand(), CommandCategory.GENERAL);
        registerCommand(new WarningsCommand(), CommandCategory.GENERAL);
        registerCommand(new RankCommand(), CommandCategory.GENERAL);
        registerCommand(new NotificationsCommand(), CommandCategory.GENERAL);
        registerCommand(new TopCommand(), CommandCategory.GENERAL);
        registerCommand(new ShopCommand(), CommandCategory.GENERAL);
        registerCommand(new BuyCommand(), CommandCategory.GENERAL);
        registerCommand(new InventoryCommand(), CommandCategory.GENERAL);

        // Moderation commands
        registerCommand(new SettingsCommand(), CommandCategory.MODERATION);
        registerCommand(new BanCommand(), CommandCategory.MODERATION);
        registerCommand(new UnbanCommand(), CommandCategory.MODERATION);
        registerCommand(new MuteCommand(), CommandCategory.MODERATION);
        registerCommand(new UnmuteCommand(), CommandCategory.MODERATION);
        registerCommand(new WarnCommand(), CommandCategory.MODERATION);
        registerCommand(new WarningRemoveCommand(), CommandCategory.MODERATION);
        registerCommand(new WarningClearCommand(), CommandCategory.MODERATION);
        registerCommand(new WarningListCommand(), CommandCategory.MODERATION);
        registerCommand(new LockCommand(), CommandCategory.MODERATION);
        registerCommand(new StatusCommand(), CommandCategory.MODERATION);
        registerCommand(new StatisticsCommand(), CommandCategory.MODERATION);
        registerCommand(new GodCommand(), CommandCategory.MODERATION);
        registerCommand(new ChangelogCommand(), CommandCategory.MODERATION);

        // Fun commands
        registerCommand(new PokeCommand(), CommandCategory.FUN);
        registerCommand(new FutureCommand(), CommandCategory.FUN);
        registerCommand(new RoastCommand(), CommandCategory.FUN);
        registerCommand(new WishCommand(), CommandCategory.FUN);
        registerCommand(new ReverseCommand(), CommandCategory.FUN);
        registerCommand(new BigCommand(), CommandCategory.FUN);
        registerCommand(new EmojiCommand(), CommandCategory.FUN);
        registerCommand(new OwoCommand(), CommandCategory.FUN);
        registerCommand(new ValentineCommand(), CommandCategory.FUN);

        // Game commands
        registerCommand(new TriviaCommand(), CommandCategory.GAME);
        // registerCommand(new TriviaRepeatCommand(), CommandCategory.GAME);
        registerCommand(new CoinflipCommand(), CommandCategory.GAME);
        registerCommand(new SlotsCommand(), CommandCategory.GAME);

        // Economy commands
        registerCommand(new BalanceCommand(), CommandCategory.ECONOMY);
        registerCommand(new PayCommand(), CommandCategory.ECONOMY);
        registerCommand(new EconSetCommand(), CommandCategory.ECONOMY);
        registerCommand(new EconAddCommand(), CommandCategory.ECONOMY);
        registerCommand(new EconWithdrawCommand(), CommandCategory.ECONOMY);
        registerCommand(new EconClearCommand(), CommandCategory.ECONOMY);

        // Register unknown command listener
        bot.addListener(new UnknownCommandListener());
    }

    private void registerCommand(EchobotCommand command, CommandCategory category) {
        bot.addListener(command);
        commands.put(command.getName(), command);
        categories.get(category).add(command);
    }

    /**
     * Get a command from string.
     * @param s The string
     * @return Optional<EchobotCommand>
     */
    public Optional<EchobotCommand> getCommand(String s) {
        for (EchobotCommand command : commands.values()) {
            if (command.getName().equalsIgnoreCase(s)) {
                return Optional.of(command);
            } else {
                if (command.getAliases() != null) {
                    String[] aliases = command.getAliases().split(",");

                    if (Arrays.stream(aliases).anyMatch(s::equalsIgnoreCase)) {
                        return Optional.of(command);
                    }
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Get the commands associated with a certain category.
     * @param category The category
     * @return List<EchobotCommand>
     */
    public List<EchobotCommand> getCategoryCommands(CommandCategory category) {
        return categories.get(category);
    }
}