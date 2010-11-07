package sc2.require;

/**
** Thrown when the required resources are not available.
*/
public class SC2CostException extends SC2RequireException {

	final public double need_m;
	final public double need_v;

	public SC2CostException(double need_m, double need_v) {
		super("Not enough resources; need " + need_m + " minerals and " + need_v + " gas.", true);
		assert need_m > 0 || need_v > 0;
		this.need_m = need_m;
		this.need_v = need_v;
	}

	public SC2CostException(int cost_m, int cost_v, double curr_m, double curr_v) {
		this(cost_m-curr_m, cost_v-curr_v);
	}

}
