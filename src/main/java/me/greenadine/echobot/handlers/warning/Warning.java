package me.greenadine.echobot.handlers.warning;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.warning.Rule;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.Serializable;
import java.util.Optional;

public class Warning implements Serializable {

    private Long user;
    private Long staff;

    private Rule rule;
    private String reason;
    private String notes;

    public Warning(Long u, Long s, Rule r, String n) {
        user = u;
        staff = s;
        rule = r;
        reason = "";
        notes = n;
    }

    public Warning(Long u, Long s, String r, String n) {
        user = u;
        staff = s;
        rule = Rule.OTHER;
        reason = r;
        notes = n;
    }

    /**
     * Get the user that received the warning.
     * @return Optional<User>
     */
    public Optional<User> getUser() {
        return EchoBot.bot.getCachedUserById(user);
    }

    /**
     * Get the staff member that issued the warning.
     * @return Optional<User>
     */
    public Optional<User> getStaff() {
        return EchoBot.bot.getCachedUserById(staff);
    }

    /**
     * Get the rule that is associated with this warning.
     * @return Rule
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * Get the reason for the warning being handed out.
     * NOTE: Is only not null when getRule() = Rule.OTHER.
     * @return String
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns whether the warning has added notes.
     * @return boolean
     */
    public boolean hasNotes() {
        return !notes.equals("");
    }

    /**
     * Returns the additional notes added to the warning.
     * @return String
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Return an embed containing information about the warning.
     * @return EmbedBuilder
     */
    public EmbedBuilder toEmbed() {
        if (EchoBot.bot.getCachedUserById(user).isPresent() && EchoBot.bot.getCachedUserById(staff).isPresent()) {
            User u = EchoBot.bot.getCachedUserById(user).get();
            User s = EchoBot.bot.getCachedUserById(staff).get();

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Warning").setColor(Color.CYAN)
                    .setThumbnail(u.getAvatar())
                    .addInlineField("Issued to", u.getDiscriminatedName() + " (" + u.getNicknameMentionTag() + ")")
                    .addInlineField("Moderator", s.getDiscriminatedName() + " (" + s.getNicknameMentionTag() + ")")
                    .addField("Rule broken", "Rule #" + rule.getNumber() + "\n" + rule.getDescription())
                    .addField("Weight", String.valueOf(rule.getWeight()))
                    .setFooter("ID: " + u.getId())
                    .setTimestampToNow();

            if (!notes.equals("")) {
                embed.addField("Additional notes", notes);
            }

            return embed;
        }

        return null;
    }
}
