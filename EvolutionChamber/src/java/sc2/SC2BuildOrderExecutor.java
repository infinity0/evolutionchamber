package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.asset.SC2AssetType;

/**
** Executes a build order.
*/
public class SC2BuildOrderExecutor {

	final protected SC2Player play;

	public SC2BuildOrderExecutor(SC2Player play) {
		this.play = play;
	}

	public int executeAll(Iterable<SC2Action> build_order) {
		int skipped = 0;
		for (SC2Action action: build_order) {
			if (execute(action)) { skipped++; }
		}
		return skipped;
	}

	public boolean execute(SC2Action action) {
		while (true) {
			try {
				action.launch(play);
				return true;

			} catch (SC2ActionException e) {
				if (e.pleaseTryLater()) {
					play.advance();
					continue;

				} else {
					return false;
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		System.out.println("crashing ... ");
		System.out.println("done! program will exit");
	}

}
