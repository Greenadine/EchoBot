package me.greenadine.echobot.util;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.util.DiscordRegexPattern;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiUtils {

    private static final String unicodeEmojiRegex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";

    /**
     * Returns whether the given string a custom emoji.
     * @param arg The argument
     * @return boolean
     */
    public static boolean isCustomEmoji(String arg) {
        return DiscordRegexPattern.CUSTOM_EMOJI.matcher(arg).matches();
    }

    public static boolean isUnicodeEmoji(String arg) {
        Matcher matcher = Pattern.compile(unicodeEmojiRegex).matcher(arg);

        return matcher.matches();
    }

    /**
     * Returns the custom emoji given in the argument.
     * @param arg The argument
     * @return Optional<Emoji>
     */
    public static Optional<KnownCustomEmoji> getCustomEmoji(String arg) {
        String s1 = arg.substring(1, arg.length() - 1);

        String[] s2 = s1.split(":");

        return EchoBot.bot.getCustomEmojiById(s2[2]);
    }
}
