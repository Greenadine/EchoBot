package me.greenadine.echobot.handlers.valentine;

import java.awt.*;
import java.io.File;
import java.util.Random;

public enum RepeatCharacter {

    EUCA("Euca", "Arctic Fox", "Euca is generally a friendly and amiable individual, but is described as incredibly unmotivated by many of his friends and teachers. He's a closet pervert and enjoys peeping and flirting with his male friends.", "19", Color.getHSBColor(0, 0, 100)),
    OWEN("Owen Lorelei", "Red Panda", "Owen has an openly firtly personality. He's very friendly and warm to everyone he meets, and offers help to those who need it. He's a massive flirt, an exhibitionist, and a self-admitted pervert.", "19", Color.getHSBColor(24, 66, 100)),
    PHILLIP("Phillip Tan", "Cat", "Phillip is a very friendly and light-hearted individual, often telling bad puns and making playful banter. He's very kind as well, not hesitating to offer a helping hand when needed. Phillip also has a satirical, sassy side, once described as a 'blunt asshole' by Jinny.", "19", Color.getHSBColor(359, 63, 60)),
    SISSEL("Sissel", "Rabbit", "Sissel has a rough and tough outside, but once you get to know him he reveals his more soft and friendly side. He cares deeply for all his friends, even though he might not always show it.", "19", Color.getHSBColor(29, 43, 87)),
    ECHO("Echo", "Arctic Fox", "Echo is a rather playful nd easy-going wish, often joking and engaging in friendly bickering. He's also incredibly helpful and doesn't mind helping out or giving advice in times of need. He is a dependable wish, *most of the time*.", "~19", Color.getHSBColor(186, 22, 85)),
    HERSHEL("Hershel", "Rabbit", "Hershel is very jovial, and is always seen smiling. He's very kind and encouraging, and also enjoys teasing others from time to time. He has a tendency to listen to and pop in and out of conversations.", "37", Color.getHSBColor(0, 10, 73)),
    SAMUEL("Samuel Clark", "Doberman", "Samuel is a very kind and dependable butler. He takes care of anyone that crosses his path without hesitation, and does his best to make others be comfortable when he's there.", "Late 30s", Color.getHSBColor(18, 43, 90)),
    ;

    private String name;
    private String species;
    private String bio;
    private String age;
    private Color color;

    RepeatCharacter(String name, String species, String bio, String age, Color color) {
        this.name = name;
        this.species = species;
        this.bio = bio;
        this.age = age;
        this.color = color;
    }

    /**
     * Get the character's name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the character's species.
     * @return String
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Get the character's bio.
     * @return String
     */
    public String getBio() {
        return bio;
    }

    /**
     * Get the character's age.
     * @return int
     */
    public String getAge() {
        return age;
    }

    /**
     * Get the characters's color.
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the character's image.
     * @return File
     */
    public File getImage() {
        File image = new File("assets/images/characters/" + this.name().toLowerCase() + ".png");

        if (!image.exists()) {
            return null;
        } else {
            return image;
        }
    }

    /**
     * Get a random character.
     * @return RepeatCharacter.
     */
    public static RepeatCharacter getRandom() {
        int i = new Random().nextInt(values().length - 1);

        return values()[i];
    }
}
