package sc2.action;

/**
** Represents an ability. This is an interface so that e.g. {@link SC2WarpIn}
** can be an ability; most classes should extend {@link SC2AbstractAbility}
** for an automatic cooldown timer.
*/
public interface SC2Ability {

	public int getCooldown();

}
