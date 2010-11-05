package sc2;

import sc2.annot.Immutable;

/**
** Represents an asset's health or shield stats.
*/
@Immutable
public class SC2HealthSchema {

	final public int max;
	final public double regen;
	/** regen delay after taking damage */
	final public double regen_delay;
	final public int armor;
	/** armor upgrade increment */
	final public int armor_up;

	public SC2HealthSchema(int max, double regen, double regen_delay, int armor, int armor_up) {
		this.max = max;
		this.regen = regen;
		this.regen_delay = regen_delay;
		this.armor = armor;
		this.armor_up = armor_up;
	}

	public SC2HealthSchema(int max, int armor, int armor_up) {
		this(max, 0, 0, armor, armor_up);
	}

	final public static SC2HealthSchema newProtossShields(int max, int armor, int armor_up) {
		// TODO actually get the correct numbers
		return new SC2HealthSchema(max, 2, 2, armor, armor_up);
	}

	final public static SC2HealthSchema newZergHealth(int max, int armor, int armor_up) {
		// TODO actually get the correct numbers
		return new SC2HealthSchema(max, 0.5, 0, armor, armor_up);
	}

}
