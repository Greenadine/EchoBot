package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.StringJoiner;

public class BigCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "big";
    }

    public String getDescription() {
        return "Enlarge a message.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!big <message>";
    }

    public String getArguments() {
        return "``message`` - The message to enlarge.";
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) {return;}

        if (!handler.getUser().isPresent()) { // User optional is empty
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        User user = handler.getUser().get();

        if (!PermissionsHandler.isTrusted(user)) {
            handler.reply(user.getNicknameMentionTag() + " Nice try buddy, you do not have permission to use this command.");
            return;
        }

        if(handler.length() == 0) {
            handler.reply("Give me something to **enlarge**.");
            return;
        }

        StringJoiner joiner = new StringJoiner(" ");

        if (handler.getArguments().length() > 50) {
            handler.reply("That is way too long. I'm a *wish*, not your slave.");
            return;
        }

        for (int i = 0; i < handler.getCharacters().length; i++) {
            Character c = Character.toUpperCase(handler.getCharacters()[i]);
            BigLetters r = BigLetters.fromCharacter(c);

            joiner.add(r.getEmoji());
        }

        handler.reply(new EmbedBuilder().setColor(Color.cyan).setAuthor(user).setDescription(joiner.toString()));

        // handler.reply(joiner.toString());
        e.deleteMessage();
    }

    enum BigLetters {

        A('A', "<:regional_indicator_a:667299220910505986>"),
        B('B', "<:regional_indicator_b:667300696177246228>"),
        C('C', "<:regional_indicator_c:667300748467372063>"),
        D('D', "<:regional_indicator_d:667300763244167198>"),
        E('E', "<:regional_indicator_e:667300816566353920>"),
        F('F', "<:regional_indicator_f:667300820919779328>"),
        G('G', "<:regional_indicator_g:667300827827929090>"),
        H('H', "<:regional_indicator_h:667300857255034880>"),
        I('I', "<:regional_indicator_i:667300860841295894>"),
        J('J', "<:regional_indicator_j:667300867602513920>"),
        K('K', "<:regional_indicator_k:667300879850012672>"),
        L('L', "<:regional_indicator_l:667300919527997471>"),
        M('M', "<:regional_indicator_m:667300933998346252>"),
        N('N', "<:regional_indicator_n:667300949169143838>"),
        O('O', "<:regional_indicator_o:667300954843906069>"),
        P('P', "<:regional_indicator_p:667300964318838796>"),
        Q('Q', "<:regional_indicator_q:667300974993604609>"),
        R('R', "<:regional_indicator_r:667300983315103765>"),
        S('S', "<:regional_indicator_s:667300991695323146>"),
        T('T', "<:regional_indicator_t:667300998389301249>"),
        U('U', "<:regional_indicator_u:667301045843656704>"),
        V('V', "<:regional_indicator_v:667301051497578506>"),
        W('W', "<:regional_indicator_w:667301060284514314>"),
        X('X', "<:regional_indicator_x:667301070770536488>"),
        Y('Y', "<:regional_indicator_y:667301078831726592>"),
        Z('Z', "<:regional_indicator_z:667301084246704128>"),
        EXCLAMATION_MARK('!', "<:exclamation:667304001347518484>"),
        QUESTION_MARK('?', "<:question:667303941662310412>"),
        SPACE(' ', "  "),
        EMPTY(' ', ""),
        ;

        Character character;
        String emoji;

        BigLetters(Character c, String e) {
            character = c;
            emoji = e;
        }

        protected Character getCharacter() {
            return character;
        }

        protected String getEmoji() {
            return emoji;
        }

        public static BigLetters fromCharacter(Character c) {
            for (BigLetters r : values()) {
                if (r.character == c) {
                    return r;
                }
            }

            return EMPTY;
        }
    }
}