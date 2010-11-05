package sc2.require;

import sc2.annot.Immutable;

import sc2.asset.SC2Asset;
import sc2.SC2Player;

/**
** Represents a requirement. Implementations must be immutable.
*/
@Immutable
public interface SC2Requires {

	/**
	** Whether the player's game state statisfies the requirements.
	*/
	public void require(SC2Player play) throws SC2RequireException;

}
