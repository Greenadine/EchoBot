package me.greenadine.echobot;

import org.javacord.api.entity.activity.ActivityType;

import java.util.Random;

public enum Activity {

    GENERAL_1(ActivityType.PLAYING, "Repeat"),
    GENERAL_2(ActivityType.LISTENING, "Repeat theories"),
    GENERAL_3(ActivityType.STREAMING, "Repeat"),
    OWEN_1(ActivityType.PLAYING, "Owen's Route"),
    OWEN_2(ActivityType.WATCHING, "some Owen lewds"),
    OWEN_3(ActivityType.LISTENING, "Owen signing country songs"),
    PHILLIP_1(ActivityType.PLAYING, "Phillip's Route"),
    PHILLIP_2(ActivityType.WATCHING, "Phillip tase someone"),
    PHILLIP_3(ActivityType.WATCHING, "Phillip drink some coffee"),
    SISSEL_1(ActivityType.PLAYING, "Sissel's Route"),
    SISSEL_2(ActivityType.WATCHING, "Sissel get flustered"),
    SISSEL_3(ActivityType.STREAMING, "Sissel's cute face"),
    ;

    private static final Random random = new Random();

    protected ActivityType type;
    protected String value;

    Activity (ActivityType t, String s) {
        type = t;
        value = s;
    }

    /**
     * Get the activity's type.
     * @return ActivityType
     */
    public ActivityType getType() {
        return type;
    }

    /**
     * Get the activity's value.
     * @return String
     */
    public String getValue() {
        return value + " (e!help)";
    }

    /**
     * Get a random value of the enumerator.
     * @return Activity
     */
    public static Activity getRandom() {
        return Activity.values()[random.nextInt(Activity.values().length - 1)];
    }
}
