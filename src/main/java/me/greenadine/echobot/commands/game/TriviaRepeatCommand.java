package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Trivia;
import me.greenadine.echobot.listeners.TriviaListener;
import me.greenadine.echobot.objects.TriviaQuestion;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class TriviaRepeatCommand implements MessageCreateListener {

    private Trivia trivia_repeat = EchoBot.trivia_repeat;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("trivia-repeat")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help trivia-repeat`` for command information.");
                return;
            }

            User user = handler.getUser();

            if (TriviaListener.questions.containsKey(user.getId())) {
                handler.reply("Please finish your current game of trivia before starting a new one.");
                return;
            }

            TriviaQuestion question = trivia_repeat.getRandom();

            handler.reply("Question: " + question.getQuestion());
            TriviaListener.questions.put(user.getId(), question);
        }
    }
}
