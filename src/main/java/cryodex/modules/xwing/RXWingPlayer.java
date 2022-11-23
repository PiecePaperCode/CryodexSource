package cryodex.modules.xwing;

public class RXWingPlayer {
    public final String name;
    public final String groupName;
    public final String email;
    public final String squadronId;
    public final RXWingFaction faction;

    public RXWingPlayer(
        String name,
        String groupName,
        String email,
        String squadronId,
        RXWingFaction faction
    ) {
        this.name = name;
        this.groupName = groupName;
        this.email = email;
        this.squadronId = squadronId;
        this.faction = faction;
    }

    public RXWingPlayer(String name) {
        this.name = name;
        this.groupName = null;
        this.email = null;
        this.squadronId = null;
        this.faction = null;
    }
}
