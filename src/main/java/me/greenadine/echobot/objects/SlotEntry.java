package me.greenadine.echobot.objects;

import java.util.Random;

public enum SlotEntry {

    ECHO("<:Echo:595416010874421250>", 25),
    EUCA("<:Euca:595414247157268480>", 15),
    HERSHEL("<:Hershel:595432821955362826>", 10),
    JINNY("<:Jinny:595432761439944705>", 30),
    OWEN("<:Owen:595414289586585600>", 20),
    PHILLIP("<:Phillip:595414326697787393>", 20),
    SISSEL("<:Sissel:595414393496272916>", 20),
    HERSHELWTF("<:HershelWtf:646513396879196161>", 50),
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

        switch(r.nextInt(8)) {
            case 0:
                return ECHO;
            case 1:
                return EUCA;
            case 2:
                return HERSHEL;
            case 3:
                return JINNY;
            case 4:
                return OWEN;
            case 5:
                return PHILLIP;
            case 6:
                return SISSEL;
            case 7:
                return HERSHELWTF;
            default:
                return ECHO;
        }
    }
}
