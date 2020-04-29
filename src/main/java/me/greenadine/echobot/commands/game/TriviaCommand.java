package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.trivia.Trivia;
import me.greenadine.echobot.listeners.TriviaListener;
import me.greenadine.echobot.handlers.trivia.TriviaQuestion;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TriviaCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "trivia";
    }

    public String getDescription() {
        return "Play a game of trivia.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!trivia";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    private Trivia trivia = EchoBot.trivia;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Type ``e!help trivia`` for command information.");
            return;
        }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (trivia.getSize() == 0) {
                handler.reply("There currently are no trivia question available.");
            } else {
                if (TriviaListener.questions.containsKey(user.getId())) {
                    handler.reply("Please finish your current game of trivia before starting a new one.");
                    return;
                }

                TriviaQuestion question = trivia.getRandom();

                handler.reply("Question: " + question.getQuestion());

                final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
                executor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        TriviaListener.questions.put(user.getId(), question);
                    }
                }, 100, TimeUnit.MILLISECONDS);
            }
        }
    }
}
