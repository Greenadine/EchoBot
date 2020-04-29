package me.greenadine.echobot.cardgame;

public enum Card {

    // Euca cards
    EUCA(1, Category.EUCA, "Euca", Rarity.COMMON),
    EUCA_BLUSH(2, Category.EUCA, "Blushing Euca", Rarity.RARE),
    EUCA_ID(3, Category.EUCA, "Euca's School ID", Rarity.UNCOMMON),

    // Owen cards
    OWEN(4, Category.OWEN, "Owen", Rarity.COMMON),
    OWEN_GRIN(5, Category.OWEN, "Grinning Owen", Rarity.UNCOMMON),
    OWEN_NERVOUS(6, Category.OWEN, "Owen Lorelei, At Your Service!", Rarity.UNCOMMON),
    OWEN_SURPISED(7, Category.OWEN, "Surprised Owen", Rarity.RARE),
    OWEN_DANCEOUTFIT(8, Category.OWEN, "Owen (Dance Outfit)", Rarity.VERY_RARE),

    // Phillip cards
    PHILLIP(9, Category.PHILLIP, "Phillip", Rarity.COMMON),
    PHILLIP_ANNOYED(10, Category.PHILLIP, "Annoyed Phillip", Rarity.COMMON),
    PHILLIP_NO(11, Category.PHILLIP, "Hell No!", Rarity.COMMON),
    PHILLIP_SHRUG(12, Category.PHILLIP, "Shrugging Phillip", Rarity.UNCOMMON),
    PHILLIP_SHRUG_BLUSH(13, Category.PHILLIP, "Blushing Phillip", Rarity.UNCOMMON),
    PHILLIP_SMILE(13, Category.PHILLIP, "Happy Phillip", Rarity.UNCOMMON),
    PHILLIP_SMUG(14, Category.PHILLIP, "Heheheh~", Rarity.RARE),
    PHILLIP_SURPISED(16, Category.PHILLIP, "W- What?", Rarity.UNCOMMON),
    PHILLIP_SHORTS(17, Category.PHILLIP, "Shirtless Phillip", Rarity.VERY_RARE),

    BLACK_CAT(18, Category.PHILLIP, "The Black Cat", Rarity.LEGENDARY),

    // Sissel cards
    SISSEL(19, Category.SISSEL, "Sissel", Rarity.COMMON),
    SISSEL_ANNOYED(20, Category.SISSEL, "Annoyed Sissel", Rarity.COMMON),
    SISSEL_INDIGNANT(21, Category.SISSEL, "Shy Sissel", Rarity.UNCOMMON),
    SISSEL_SURPRISED(22, Category.SISSEL, "Surprised Sissel", Rarity.COMMON),
    SISSEL_EMBARRASED(23, Category.SISSEL, "Embarrassed Sissel", Rarity.UNCOMMON),
    SISSEL_EMBARRASSED_SHIRTLESS(24, Category.SISSEL, "Shirtless Sissel", Rarity.VERY_RARE),
    SISSEL_SMILE(25, Category.SISSEL, "Smiling Sissel", Rarity.COMMON),
    SISSEL_HAPPY(26, Category.SISSEL, "Happy Sissel", Rarity.UNCOMMON),
    SISSEL_WTF(27, Category.SISSEL, "WTF Sissel", Rarity.UNCOMMON),
    SISSEL_WTF_BLUSH(28, Category.SISSEL, "Blushing Sissel", Rarity.RARE),
    SISSEL_LIFEJACKET(29, Category.SISSEL, "Sissel (Lifejacket)", Rarity.UNCOMMON),
    SISSEL_WORKUNIFORM(30, Category.SISSEL, "Sissel (Work Uniform)", Rarity.COMMON),
    SISSEL_DANCEOUTFIT(31, Category.SISSEL, "Sissel (Dance Outfit)", Rarity.UNCOMMON),
    SISSEL_JACKETOUTFIT(32, Category.SISSEL, "Sissel (Jacket)", Rarity.VERY_RARE),
    SISSEL_CHOCOLATE_SCULPTURE(33, Category.SISSEL, "", Rarity.VERY_RARE),

    // Jinny cards
    JINNY(34, Category.JINNY, "Jinny", Rarity.COMMON),
    JINNY_WTF(35, Category.JINNY, "Oh God No...", Rarity.RARE),
    JINNY_DANCEOUTFIT(36, Category.JINNY, "You Guys Ready?", Rarity.COMMON),
    JINNY_SHIRTOUTFIT(37, Category.JINNY, "Jinny (Shirt)", Rarity.UNCOMMON),
    JINNY_SHIRTOUTFIT_SMIRK(38, Category.JINNY, "Smirking Jinny (Shirt)", Rarity.LEGENDARY),

    // Hershel cards
    HERSHEL(39, Category.HERSHEL, "Hershel", Rarity.COMMON),
    HERSHEL_GRIN(40, Category.HERSHEL, "Grinning Hershel", Rarity.RARE),
    HERSHEL_SURPRISED(41, Category.HERSHEL, "Surprised Hershel", Rarity.UNCOMMON),
    HERSHEL_WTF(42, Category.HERSHEL, "Hershel: WTF", Rarity.LEGENDARY),

    // Wishes cards
    ECHO(43, Category.ECHO, "Your Friendly Camera Ghost", Rarity.COMMON),
    ECHO_WISH(44, Category.ECHO, "Your Wish Is My Command", Rarity.UNCOMMON),
    HALLEY(45, Category.HALLEY, "Halley", Rarity.UNCOMMON),
    BRADLEY(46, Category.BRADLEY, "The Black Lady of Bradley Woods", Rarity.UNCOMMON),
    REMNANT(47, Category.REMNANT, "Corrupted Wish", Rarity.RARE),
    REMNANT_APPEARANCE(48, Category.REMNANT, "", Rarity.VERY_RARE),

    // Other cards

    CECILIA(47, Category.CECILIA, "Cecilia", Rarity.VERY_RARE),
    OLEANDER(48, Category.OLEANDER, "Oleander", Rarity.COMMON),
    SAMUEL(49, Category.SAMUEL, "Samuel", Rarity.COMMON),
    SAMUEL_BOXERS(50, Category.SAMUEL, "Daddy Doberman", Rarity.LEGENDARY),
    GRAHAM(51, Category.GRAHAM, "Graham / Morse", Rarity.UNCOMMON),
    ;

    int id;
    Category character;
    String name;
    Rarity rarity;

    Card(int i, Category c, String n, Rarity r) {
        id = i;
        character = c;
        name = n;
        rarity = r;
    }
}
