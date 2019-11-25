package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.MessageHandler;
import me.greenadine.echobot.objects.TriviaQuestion;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.HashMap;
import java.util.Random;

public class TriviaListener implements MessageCreateListener {

    public static HashMap<Long, TriviaQuestion> questions = new HashMap<>();

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);

        e.getMessageAuthor().asUser().ifPresent(user -> {
            if (questions.containsKey(user.getId())) {
                TriviaQuestion question = questions.get(user.getId());
                String answer = e.getMessageContent();

                if (question.isCorrectAnswer(answer)) {
                    int reward = randomReward();
                    boolean success = econ.add(user, reward); // Award player 2 to 5 Gold for getting the correct answer.

                    handler.reply("Correct! You've been awarded " + reward + " Gold!");
                } else {
                    handler.reply("Incorrect answer. The correct answer was: " + question.getCorrectAnswer() + ".");
                }

                questions.remove(user.getId());
            }
        });
    }

    private int randomReward() {
        Random random = new Random();
        int reward = random.nextInt(5);

        if (reward < 2) {
            reward = 2;
        }

        return reward;
    }
}
