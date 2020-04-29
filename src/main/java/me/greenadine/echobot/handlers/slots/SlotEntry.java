package me.greenadine.echobot.handlers.slots;

import java.util.Random;

public enum SlotEntry {

    BLACKCAT("<:BlackCat:659203536705290251>", 25),
    CECILIA("<:Cecilia:595447177807921152>", 10),
    ECHO("<:Echo:595416010874421250>", 25),
    EUCA("<:Euca:595414247157268480>", 15),
    HALLEY("<:Halley:595432722584043520>", 25),
    HERSHEL("<:Hershel:595432821955362826>", 10),
    HERSHELTHUMBSUP("<:HershelThumbsUp:637685868236111882>", 20),
    HERSHELWTF("<:HershelWtf:646513396879196161>", 30),
    JINNY("<:Jinny:595432761439944705>", 20),
    OWEN("<:Owen:595414289586585600>", 20),
    OWENSMILE("<:OwenSmile:659202511533637642>", 30),
    PHILLIP("<:Phillip:595414326697787393>", 20),
    PHILLIPSMUG("<:PhillipSmug:646513374074765352>", 30),
    REMNANT("<:Remnant:659388874262446100>", 5),
    SAMUEL("<:Samuel:595447195625586701>", 10),
    SISSEL("<:Sissel:595414393496272916>", 20),
    SISSELBLUSH("<:SisselBlush:659381194521051174>", 25),
    SISSELHAPPY("<:SisselHappy:659386429939515392>", 25),
    SISSELSHY("<:SisselShy:659207949847232523>", 30)
    ;

    String emoji;
    int points;

    SlotEntry(String e, int p) {
        emoji = e;
        points = p;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getPoints() {
        return points;
    }

    public static SlotEntry getRandom() {
        return values()[new Random().nextInt(values().length)];
    }
}
