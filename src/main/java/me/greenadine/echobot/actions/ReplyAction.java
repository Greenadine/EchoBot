package me.greenadine.echobot.actions;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.MessageHandler;
import me.greenadine.echobot.handlers.Phrases;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ReplyAction implements MessageCreateListener {

    private Phrases shut_up = new Phrases("assets/actions/reply/shut_up.txt");
    private Phrases mention = new Phrases("assets/actions/reply/mention.txt");
    private Phrases hate_you = new Phrases("assets/actions/reply/hate_you.txt");
    private Phrases love_you = new Phrases("assets/actions/reply/love_you.txt");
    private Phrases you_suck = new Phrases("assets/actions/reply/you_suck.txt");

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);

        handler.getOptionalUser().ifPresent(user -> {
            if (user.isBot()) { return; }

            if (handler.containsIgnoreCase("Echo") || handler.contains(EchoBot.bot.getYourself().getNicknameMentionTag())) {
                if (handler.containsIgnoreCase("shut up") || handler.containsIgnoreCase("be silent") || handler.containsIgnoreCase("silence")) {
                    if (shut_up.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Nah! <:PhillipSmug:646513374074765352>");
                    } else {
                        handler.reply(shut_up.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("hate you") || handler.containsIgnoreCase("despise you")) {
                    if (hate_you.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Hate you too! <:PhillipSmug:646513374074765352>");
                    } else {
                        handler.reply(hate_you.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("love you")) {
                    if (love_you.isEmpty()) {
                        handler.reply("Aww, love you too " + user.getNicknameMentionTag() + "! ");
                    } else {
                        handler.reply(love_you.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("you suck")) {
                    if (you_suck.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + " and you suck! :sunglasses:");
                    } else {
                        handler.reply(you_suck.getRandom(user));
                    }
                }

                else {
                    if (handler.containsIgnoreCase(EchoBot.bot.getYourself().getNicknameMentionTag())) {
                        if (mention.isEmpty()) {
                            handler.reply(user.getNicknameMentionTag() + " What's up? <:Echo:595416010874421250>");
                        } else {
                            handler.reply(mention.getRandom(user));
                        }
                    }
                }
            }





            /*
            if (handler.containsIgnoreCase(EchoBot.bot.getYourself().getNicknameMentionTag()) || handler.containsIgnoreCase("Echo")) {
                if (handler.containsIgnoreCase("shut up")) {
                    if (shut_up.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Nah! <:PhillipSmug:646513374074765352>");
                    } else {
                        handler.reply(shut_up.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("hate you") || handler.containsIgnoreCase("despise you")) {
                    if (hate_you.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Hate you too! <:PhillipSmug:646513374074765352>");
                    } else {
                        handler.reply(hate_you.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("love you")) {
                    if (love_you.isEmpty()) {
                        handler.reply("Aww, love you too " + user.getNicknameMentionTag() + "! ");
                    } else {
                        handler.reply(love_you.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("you suck")) {
                    if (you_suck.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + " and you suck! :sunglasses:");
                    } else {
                        handler.reply(you_suck.getRandom(user));
                    }
                }

                else {
                    if (handler.containsIgnoreCase(EchoBot.bot.getYourself().getNicknameMentionTag())) {
                        if (mention.isEmpty()) {
                            handler.reply(user.getNicknameMentionTag() + " What's up? <:Echo:595416010874421250>");
                        } else {
                            handler.reply(mention.getRandom(user));
                        }
                    }
                }
            }

            if ((handler.contains(EchoBot.bot.getYourself().getNicknameMentionTag()) || handler.equalsIgnoreCase("Echo") )) { // If Echo is being mentioned or tagged (and the sender is not a bot).
                if (handler.containsIgnoreCase("shut up")) {
                    if (shut_up.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Nah! <:PhillipSmug:646513374074765352>");
                    }

                    else {
                        handler.reply(shut_up.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("hate you")) {
                    if (hate_you.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + "Hate you too! <:PhillipSmug:646513374074765352>");
                    }

                    else {
                        handler.reply(hate_you.getRandom(user));
                    }
                }

                else if (handler.containsIgnoreCase("love you")) {
                    if (love_you.isEmpty()) {
                        handler.reply("Aww, love you too " + user.getNicknameMentionTag() + "! ");
                    } else {
                        handler.reply(love_you.getRandom(user));
                    }
                }

                else {
                    if (mention.isEmpty()) {
                        handler.reply(user.getNicknameMentionTag() + " What's up? <:Echo:595416010874421250>");
                    }

                    else {
                        handler.reply(mention.getRandom(user));
                    }
                }
            }
            */

        });
    }
}
