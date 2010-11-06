package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.action.SC2Wait;

import java.util.Arrays;

/**
** Executes a build order.
*/
public class SC2BuildOrderExecutor {

	final protected SC2Player play;

	public SC2BuildOrderExecutor(SC2Player play) {
		System.out.println("executor started for " + play);
		this.play = play;
	}

	public int executeAll(SC2Action ... build_order) {
		return executeAll(Arrays.asList(build_order));
	}

	/**
	** Execute all actions in sequence. Instances of {@link SC2Wait} are taken
	** to mean to forcibly wait the subsequent action.
	*/
	public int executeAll(Iterable<SC2Action> build_order) {
		boolean force_wait = false;
		int skipped = 0;
		for (SC2Action action: build_order) {
			if (action instanceof SC2Wait) { force_wait = true; continue; }
			if (execute(action, force_wait)) { skipped++; }
			force_wait = false;
		}
		return skipped;
	}

	/**
	** @param force_wait Forcibly wait on the action even if it aborts with an
	**        {@link SC2ActionException} that does not recommend waiting. The
	**        wait occurs up to {@link #MAX_WAIT_CYCLES} then aborts.
	*/
	public boolean execute(SC2Action action, boolean force_wait) {
		for (int wait_cycles = 0; true; ++wait_cycles) {
			try {
				action.launch(play);
				System.out.println("[" + play.timestamp() + "] " + action);
				return true;

			} catch (SC2ActionException e) {
				if (e.pleaseTryLater() || force_wait && wait_cycles < MAX_WAIT_CYCLES) {
					play.advance();
					continue;

				} else {
					return false;
				}
			}
		}
	}

	/**
	** Maximum number of cycles the executor will block on an {@link SC2Wait}
	** before giving up and skipping the next action.
	*/
	final public static int MAX_WAIT_CYCLES = 120;

}
