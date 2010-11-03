package sc2;

/**
** Represents an asset's energy stats.
*/
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

	final public static SC2EnergySchema NONE = new SC2EnergySchema(0, 0, 0, 0);
	final public static SC2EnergySchema DEFAULT = new SC2EnergySchema(200, 0.5625, 50, 50);
	final public static SC2EnergySchema DEFAULT_UP = new SC2EnergySchema(200, 0.5625, 50, 75);

}
