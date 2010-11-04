package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.asset.SC2AssetType;
import sc2.io.serial.SC2IOFactory;
import static sc2.SC2World.Race;
import static sc2.asset.SC2AssetType.Group;

import java.util.Arrays;

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

		SC2World world = new SC2World();
		SC2IOFactory factory = new SC2IOFactory(world);
		factory.addAssetType(Race.P, Group.S, "Nexus               | cstr_p : 400   0 100                        | prov_f: 10");
		factory.addAssetType(Race.P, Group.T, "Ground Weapons Level 2      | build : 175 175 190 : Forge : Twilight Council, Ground Weapons Level 1");
		factory.addAssetType(Race.P, Group.U, "Zealot          | u: 2 2 0 | build : 100   0  38 : Gateway                       | warpin : 100   0  28 : Warp Gate");
		factory.addAssetType(Race.P, Group.U, "Phoenix         | u: 2 A 0 | build : 150 100  45 : Stargate");
	}

}
