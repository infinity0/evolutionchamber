package sc2.action;

/**
** Represents an ability. This class implements a cooldown timer.
*/
abstract public class SC2AbstractAbility implements SC2Ability {

	protected double cooldown;

	@Override public int getCooldown() {
		// TODO
		return -1;
	}

}
