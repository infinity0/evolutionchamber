package sc2.require;

import sc2.asset.SC2Asset;
import sc2.SC2State;

/**
** Represents a requirement.
*/
public interface SC2Requires {

	/**
	** Whether the game state statisfies the requirements.
	*/
	public void require(SC2State game) throws SC2RequireException;

}
