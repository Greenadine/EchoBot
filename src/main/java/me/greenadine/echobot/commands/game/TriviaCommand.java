package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Trivia;
import me.greenadine.echobot.listeners.TriviaListener;
import me.greenadine.echobot.objects.TriviaQuestion;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class TriviaCommand implements MessageCreateListener {

    private Trivia trivia = EchoBot.trivia;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("trivia")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help trivia`` for command information.");
                return;
            }

            User user = handler.getUser();

            if (true) {
                handler.reply("There currently are no trivia question available.");
            }

            if (TriviaListener.questions.containsKey(user.getId())) {
                handler.reply("Please finish your current game of trivia before starting a new one.");
                return;
            }

            TriviaQuestion question = trivia.getRandom();

            handler.reply("Question: " + question.getQuestion());
            TriviaListener.questions.put(user.getId(), question);
        }
    }
}
