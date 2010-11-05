package sc2;

import sc2.annot.Immutable;

import sc2.asset.SC2AssetType;
import static sc2.SC2World.Race;

import java.util.Arrays;
import java.util.EnumMap;

/**
** Holds macro control information for a race.
*/
@Immutable
public class SC2Macro {

	final public Race race;

	final protected SC2AssetType[] command;
	final protected SC2AssetType[] worker;
	final protected SC2AssetType[] supply;

	public SC2Macro(Race race, EnumMap<Macro, SC2AssetType[]> data) {
		this.race = race;
		this.command = data.get(Macro.COMMAND);
		this.worker = data.get(Macro.WORKER);
		this.supply = data.get(Macro.SUPPLY);
	}

	public enum Macro { COMMAND, WORKER, SUPPLY; }

}
