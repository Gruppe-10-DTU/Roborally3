package dk.dtu.compute.se.pisd.roborally.model.Cards;

public enum TemporaryUpgrade {

    BOINK("Boink", "Move to an adjacent space. Do not change direction.", 1),
    HACK("Hack", "Execute the programming in your current register again. This action does not replace any existing programming.", 1),
    VIRUS_MODULE("Virus Module", "When you push a robot, give that player a virus damage card.", 2),
    TELEPORTER("Teleporter", "You may pay one energy to ignore obstacles when moving. This includes walls, pits, the priority antenna, and robots; however, you may not end your move on a wall, pit, or the priority antenna. If you move to a space with another robot, swap places with that robot.", 3),
    ENERGY_ROUTINE("Energy Routine", "Add the Energy Routine programming card to your discard pile. It is now permanently part of your deck. The Energy Routine programming card acts as an extra Power Up card in your programming deck.", 3),
    MANUAL_SORT("Manual Sort", "You may give your robot priority for this register. This card overrides Admin Privilege.", 1),
    REPEAT_ROUTINE("Repeat Routine", "Add the Repeat Routine programming card to your discard pile. It is now permanently part of your deck. The Repeat Routine programming card acts as an extra Again card in your programming deck. ", 3),
    MEMORY_SWAP("Memory Swap", "Draw three cards. Then choose three from your hand to put on top of your deck.", 1),
    SPEED_ROUTINE("Speec Routine", "Add the Speed Routine programming card to your discard pile. It is now permanently part of your deck. The Speed Routine programming card acts as an extra Move 3 card in your programming deck.", 3),
    RECHARGE("Recharge", "Gain three energy.",  0),
    RECOMPILE("Recompile", "Discard your entire hand. Draw a new one. If you need to reshuffle your discard pile to replenish your deck, you may.", 1),
    SPAM_BLOCKER("Spam Blocker", "Replace each SPAM damage card in your hand with a card from the top of your deck. Immediately discard the SPAM damage cards by placing them in the SPAM damage card draw pile. If you draw new SPAM damage cards from your deck, keep them in your hand for this round.", 3),
    REBOOT("Reboot", "Reboot your robot, but take no damage.", 1),
    SANDBOX_ROUTINE("Sandbox Routine" , "Add the Sandbox Routine programming card to your discard pile. It is now permanently part of your deck. The Sandbox Routine programming card allows you to choose one of the following actions to perform in the register where it is programmed: Move 1, 2, or 3; Back Up; Turn Left; Turn Right; U-Turn.", 5),
    SPAM_FOLDER_ROUTINE("Spam Folder Routine", "Add the SPAM Folder programming card to your discard pile. It is now permanently part of your deck. The SPAM Folder programming card allows you to permanently discard one SPAM damage card from your discardpile.", 2),
    REFRESH("Refresh", "Change the programming in your current register to any of the following: Move 1, 2, or 3; Turn Left; Turn Right; U-Turn; Back Up; Again. If you’re replacing a damage card, you may permanently discard the damage card.", 2),
    WEASEL_ROUTINE("Weasel Routine", "Add the Weasel Routine programming card to your discard pile. It is now permanently part of your deck. The Weasel Routine programming card allows you to choose one of the following actions to perform in the register where it is programmed: Turn Left, Turn Right, U-Turn", 3),
    ZOOP( "Zoop", "Rotate to face any direction.", 1)
    ;
    private final String name;
    private final String effect;
    private final int cost;

    TemporaryUpgrade(String name, String effect, int cost) {
        this.name = name;
        this.effect = effect;
        this.cost = cost;
    }
}
