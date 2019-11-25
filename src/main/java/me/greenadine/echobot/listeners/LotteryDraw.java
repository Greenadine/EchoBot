package me.greenadine.echobot.listeners;

public class LotteryDraw {

    /*


    private Lottery lottery = EchoBot.lottery;
    private Economy econ = EchoBot.econ;

    @Override
    public void run() {
        if (lottery.timeUntilDraw() >= 0) {
            EchoBot.bot.getCachedUserById(lottery.getRandomEntry()).ifPresent(user -> {
                EchoBot.bot.getTextChannelById(642394102314565632L).ifPresent(channel -> {
                    int prize = lottery.getSize() * 50;
                    channel.sendMessage("Congratulations " + user.getNicknameMentionTag() + "! Your number has been drawn in the lottery, and you've won " + prize + " Gold!");
                    econ.add(user, prize);
                    lottery.clear();
                    lottery.setNextTarget();
                });
            });
        }
    }

     */
}
