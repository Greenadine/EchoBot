package me.greenadine.echobot.objects;

import java.util.Random;

public enum SlotEntry {

    CECILIA("<:Cecilia:595447177807921152>", 10),
    ECHO("<:Echo:595416010874421250>", 25),
    EUCA("<:Euca:595414247157268480>", 15),
    HALLEY("<:Halley:595432722584043520>", 25),
    HERSHEL("<:Hershel:595432821955362826>", 10),
    JINNY("<:Jinny:595432761439944705>", 20),
    OWEN("<:Owen:595414289586585600>", 20),
    PHILLIP("<:Phillip:595414326697787393>", 20),
    SAMUEL("<:Samuel:595447195625586701>", 10),
    SISSEL("<:Sissel:595414393496272916>", 20),
    HERSHELWTF("<:HershelWtf:646513396879196161>", 30),
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
        Random r = new Random();

        switch(r.nextInt(10)) {
            case 0:
                return CECILIA;
            case 1:
                return ECHO;
            case 2:
                return HALLEY;
            case 3:
                return HERSHEL;
            case 4:
                return JINNY;
            case 5:
                return OWEN;
            case 6:
                return PHILLIP;
            case 7:
                return SAMUEL;
            case 8:
                return SISSEL;
            case 9:
                return HERSHELWTF;
            default:
                return CECILIA;
        }
    }
}
