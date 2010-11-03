package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.asset.SC2AssetType;

/**
** Executes a build order.
*/
public class SC2BuildOrderExecutor {

	final protected SC2State game;

	public SC2BuildOrderExecutor(SC2State game) {
		this.game = game;
	}

	public void executeAll(Iterable<SC2Action> build_order) {
		for (SC2Action action: build_order) {
			execute(action);
		}
	}

	public void execute(SC2Action action) {
		try {
			action.launch(game);

		} catch (SC2ActionException e) {
			if (e.canSatisfyByWaiting()) {
				// TODO wait
			} else {
				// TODO skip
			}
		}
	}

}
