package me.greenadine.echobot.commands;

public enum CommandCategory {

    GENERAL("General", "Shows a list of general-use commands."),
    FUN("Fun", "Shows a list of funny commands."),
    GAME("Game", "Shows a list of game commands."),
    ECONOMY("Economy", "Shows a list of economy-related commands."),
    MODERATION("Moderation", "Shows a list of moderative commands."),
    ;

    String name;
    String description;

    CommandCategory(String n, String d) {
        name = n;
        description = d;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static CommandCategory fromString(String s) {
        for (CommandCategory category : values()) {
            if (s.equalsIgnoreCase(category.getName())) {
                return category;
            }
        }

        return null;
    }
}
