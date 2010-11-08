package sc2;

import sc2.annot.Immutable;

/**
** Represents an asset's energy stats.
*/
@Immutable
public class SC2EnergySchema {

	final public int max;
	final public double regen;
	final public int init;
	/** upgraded starting energy */
	final public int init_up;

	public SC2EnergySchema(int max, double regen, int init, int init_up) {
		this.max = max;
		this.regen = regen;
		this.init = init;
		this.init_up = init_up;
	}

	/**
	** Return the regenerated energy after 1s, given the starting energy.
	*/
	public double regen(double energy) {
		double next = energy + regen;
		return (next < max)? next: (double)max;
	}

	public enum Presets {
		NONE(0, 0, 0, 0), DEFAULT(200, 0.5625, 50, 50), DEFAULT_UP(200, 0.5625, 50, 75);
		final public SC2EnergySchema stat_ep;
		Presets(int max, double regen, int init, int init_up) {
			this.stat_ep = new SC2EnergySchema(max, regen, init, init_up);
		}
	}

}
