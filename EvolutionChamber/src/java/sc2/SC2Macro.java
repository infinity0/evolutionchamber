package sc2;

import sc2.annot.Immutable;

import sc2.asset.SC2AssetType;
import static sc2.SC2World.Race;
import static sc2.ArgUtils.nonEmpty;
import static sc2.ArgUtils.copyOf;

/**
** Holds macro control information for a race.
*/
@Immutable
public class SC2Macro {

	final public Race race;

	final protected SC2AssetType[] command;
	final protected SC2AssetType[] worker;
	final protected SC2AssetType[] supply;

	public SC2Macro(Race race, SC2AssetType[] command, SC2AssetType[] worker, SC2AssetType[] supply) {
		this.race = race;
		this.command = copyOf(nonEmpty(command, "command"));
		this.worker = copyOf(nonEmpty(worker, "worker"));
		this.supply = copyOf(nonEmpty(supply, "supply"));
	}

	/** Return the default command structure type */
	public SC2AssetType command() { return command[0]; }
	/** Return the default worker type */
	public SC2AssetType worker() { return worker[0]; }
	/** Return the default supply type */
	public SC2AssetType supply() { return supply[0]; }

	public enum Macro { COMMAND, WORKER, SUPPLY; }

}
