package me.greenadine.echobot.cardgame;

public enum Category {

    EUCA(1, "Euca"),
    OWEN(2, "Owen"),
    PHILLIP(3, "Phillip"),
    SISSEL(4, "Sissel"),
    JINNY(5, "Jinny"),
    HERSHEL(6, "Hershel"),
    ECHO(7, "Echo"),
    HALLEY(8, "Halley"),
    BRADLEY(9, "The Black Lady of Bradley Woods"),
    REMNANT(10, "Remnant"),
    CECILIA(11, "Cecilia"),
    OLEANDER(12, "Oleander"),
    SAMUEL(13, "Samuel"),
    GRAHAM(14, "Graham"),


    ;

    int id;
    String name;

    Category(int i, String n) {
        id = i;
        name = n;
    }
}
