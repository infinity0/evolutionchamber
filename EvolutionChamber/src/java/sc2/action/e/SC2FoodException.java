package sc2.action.e;

import sc2.action.SC2ActionException;
import sc2.action.SC2Action;

/**
** Thrown when the required supply is not available. We distinguish this from
** {@link SC2CostException} because this can't be satisfied just by waiting.
**
** On second thoughts this might never be needed since supply checking is done
** in advance() and not init()...
*/
public class SC2FoodException extends SC2ActionException {

	final public int need_f;

	public SC2FoodException(SC2Action action, int need_f) {
		super(action, "Not enough supply; need " + need_f + " more.");
		assert need_f > 0;
		this.need_f = need_f;
	}

	public SC2FoodException(SC2Action action, int cost_f, int curr_f, int cap_f) {
		this(action, cost_f-cap_f+curr_f);
	}

}
