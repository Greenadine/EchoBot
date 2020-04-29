package me.greenadine.echobot.handlers.warning;

public enum Rule {

    RULE_1(1, "Be respectful to each other. Annoying or being rude to other members is not allowed.", 1),
    RULE_2(2, "Listen to the Staff. If any Staff member asks you to quit doing something, listen to them.", 1),
    RULE_3(3, "Avoid controversial and dramatic things, such as politics. Keep these conversations to DMs.", 1),
    RULE_4(4, "Use the correct channels. Keep NSFW in <#595266470565380151> and <#595266543563309056>. Repeat spoilers are limited to <#595267029221507101>", 0.5),
    RULE_5(5, "Use English in all channels. That way everyone here can participate in conversations. Occasional phrases in other languages are okay.", 0.5),
    RULE_6(6, "Don't spam in chats or mention Staff members or Shirokoi unnecessarily.", 0.5),
    RULE_7(7, "Do not post real-life pornography, including nudes, photos and videos.", 2),
    RULE_8(8, "Do not post shota, loli, incest or cub porn anywhere on the server. This violates Discord's Terms of Service.", 1),
    RULE_9(9, "In addition to rule #7, do not roleplay anything shota, loli, incest or cub-related.", 1),
    RULE_10(10, "Do not post any kind of media containing violence, gore or self-harm. Discussions about self-harm (but not violence and/or gore) are fine, but keep these in the NSFW channels. This rule applies to both cartoon- and real-life media.", 2),
    RULE_10_CARTOON(10, "Do not post any kind of media containing violence, gore or self-harm. Discussions about self-harm (but not violence and/or gore) are fine, but keep these in the NSFW channels. This rule applies to both cartoon- and real-life media.", 1),
    RULE_11(11, "Deleting messages for trolling or to get around rules will not work and will get you muted.", 1),
    RULE_12(12, "No distributing Patron-only content/Repeat builds or discussions of doing so in any way.", 1),
    RULE_13(13, "Do not advertise without permission. If you would like to advertise something (e.g. Discord servers, YouTube/Twitch channels), please contact a Staff member. This rule does not apply with advertising art.", 1),
    RULE_14(14, "Health-threatening situations are to be taken seriously. Refer to <#660976878382874636> for potential instructions on how to aid an individual in need, or consult a <@&688524673708851252> for assistance.", 0),
    OTHER(15, "Other, specified reason.", 0),
    ;

    int number;
    String description;
    double weight;

    Rule(int number, String description, double weight) {
        this.number = number;
        this.description = description;
        this.weight = weight;
    }

    /**
     * Get the rule's number.
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get the description of the rule.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the weighting of the rule.
     * @return double
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "**" + number + ")** " + description;
    }

    /**
     * Get the rule from an integer.
     * @param number The number of the rule
     * @return Rule
     */
    public static Rule fromNumber(String number) {
        if (number.equalsIgnoreCase("10_cartoon")) {
            return Rule.RULE_10_CARTOON;
        }

        for (Rule rule : values()) {
            if (rule == Rule.RULE_10_CARTOON) {
                continue;
            }

            if (rule.number == Integer.valueOf(number)) {
                return rule;
            }
        }

        return null;
    }

    /**
     * Get the amount of rules.
     * @return int
     */
    public static int getRuleCount() {
        return values().length;
    }
}
