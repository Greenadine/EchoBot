package me.greenadine.echobot.commands.game;

public class LotteryCommand {

    /*


    private Economy econ = EchoBot.econ;
    private Lottery lottery = EchoBot.lottery;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("lottery")) {
            User user = handler.getUser();

            if (handler.length() == 0) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Lottery Draw")
                        .setDescription("The draw of " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                        .setFooter("Every draw will be at 12:00 AM (EST)")
                        .setThumbnail(EchoBot.bot.getYourself().getAvatar())
                        .setColor(Color.CYAN)
                        .addInlineField("Entries", String.valueOf(lottery.getSize()))
                        .addInlineField("Current prize pot", (lottery.getSize() * 50) + " Gold");

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                if (handler.getArg(0).equalsIgnoreCase("enter")) {
                    if (lottery.isEntered(user)) {
                        handler.reply("You've already entered for today's draw.");
                        return;
                    }

                    if (!econ.hasBalance(user, 50)) {
                        handler.reply("You need 50 Gold to enter the lottery draw.");
                        return;
                    }

                    econ.withdraw(user, 50);
                    lottery.enter(user);

                    handler.reply("You've been entered into the lottery draw.");
                }

                else {
                    handler.reply("Invalid command usage. Type ``e!help lottery`` for command information.");
                }
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help lottery`` for command information.");
            }
        }
    }

    */
}
