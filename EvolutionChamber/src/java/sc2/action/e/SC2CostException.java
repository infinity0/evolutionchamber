package sc2.action.e;

import sc2.action.SC2ActionException;
import sc2.action.SC2Action;

/**
** Thrown when the required resources are not available.
*/
public class SC2CostException extends SC2ActionException {

	final public int need_m;
	final public int need_v;

	public SC2CostException(SC2Action action, int need_m, int need_v) {
		super(action, "Not enough resources; need " + need_m + " minerals and " + need_v + " gas.");
		assert need_m > 0 || need_v > 0;
		this.need_m = need_m;
		this.need_v = need_v;
	}

	public SC2CostException(SC2Action action, int cost_m, int cost_v, int curr_m, int curr_v) {
		this(action, cost_m-curr_m, cost_v-curr_v);
	}

}
