package me.greenadine.echobot.objects;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.Serializable;

public class Warning implements Serializable {

    private Long user;
    private Long staff;

    private String description;

    public Warning(Long u, Long s, String d) {
        user = u;
        staff = s;
        description = d;
    }

    /**
     * Get the user that received the warning.
     * @return User
     */
    public User getUser() {
        return EchoBot.bot.getCachedUserById(user).get();
    }

    /**
     * Get the staff member that issued the warning.
     * @return User
     */
    public User getStaff() {
        return EchoBot.bot.getCachedUserById(staff).get();
    }

    /**
     * Returns the description of the warning.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return an embed containing information about the warning.
     * @return EmbedBuilder
     */
    public EmbedBuilder toEmbed() {
        User u = EchoBot.bot.getCachedUserById(user).get();
        User s = EchoBot.bot.getCachedUserById(staff).get();

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Warning")
                .setDescription("Issued to " + u.getNicknameMentionTag())
                .setColor(Color.CYAN)
                .setThumbnail(u.getAvatar())
                .setFooter("User ID: " + u.getId())
                .addField("Issued by", s.getNicknameMentionTag() + " (ID: " + staff + ")")
                .addField("Description:", description);

        return builder;
    }
}
