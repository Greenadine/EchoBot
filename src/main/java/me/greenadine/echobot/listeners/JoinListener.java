package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class JoinListener implements ServerMemberJoinListener {

    private long welcome_channel = EchoBot.settings.getWelcomeChannel();
    private long introductions_channel = 639437003724685313L; // #introductions
    private long rules_channel = 595246997775188106L; // #rules
    private long roles_channel = 595265125313806356L; // #roles

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        if (e.getUser().isBot()) {
            return;
        }

        // Welcome the user in the set welcome channel.
        EchoBot.bot.getServerChannelById(welcome_channel).ifPresent(channel ->
                channel.asTextChannel().ifPresent(welcomeChannel ->
                        welcomeChannel.sendMessage("**Hey there " + e.getUser().getNicknameMentionTag() + ", welcome to the " + e.getServer().getName() + "!**\n\n"
                                + "Feel free to introduce yourself in <#" + introductions_channel + "> to tell more about yourself. Head on over to <#" + roles_channel + "> to grab some roles of your favorite characters and games! Also make sure you have read the <#" + rules_channel + ">.")
                )
        );

        // Give user default roles if required.
        for (Long id : EchoBot.settings.getDefaultRoles()) {
            if (!EchoBot.bot.getRoleById(id).isPresent()) { // If the given ID cannot be resolved as a role.
                System.out.println("Failed to give user default role. Reason: Unable to retrieve role from ID '" + id + "'.");
                continue;
            }

            Role role = EchoBot.bot.getRoleById(id).get(); // Get the role from the ID

            if (!role.getUsers().contains(e.getUser())) {
                EchoBot.bot.getRoleById(id).get().addUser(e.getUser());
            }
        }
    }
}
