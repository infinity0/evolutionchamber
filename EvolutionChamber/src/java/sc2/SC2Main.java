package sc2;

import sc2.asset.SC2AssetType;
import sc2.action.SC2Action;
import sc2.action.SC2Build;
import sc2.io.serial.SC2WorldReader;
import static sc2.SC2World.Race;
import static sc2.asset.SC2AssetType.Group;

/**
** Executes a build order.
*/
public class SC2Main {

	public static void main(String[] args) throws Throwable {
		System.out.println("-- showing income rates (per base-minute)");
		sc2.asset.SC2Base.main(args);

		System.out.println("-- loading game data");
		SC2World world = SC2WorldReader.read("../data/sc2stats.txt");

		System.out.println("-- starting test build");
		SC2Player play = new SC2Player(world, Race.P);
		SC2BuildOrderExecutor exec = new SC2BuildOrderExecutor(play);
		play.initAssets(new int[]{6}, 50, 0);
		System.out.println(play.getDesc());

		exec.executeAll(
		  new SC2Build(world.getAssetType("Probe")),
		  new SC2Build(world.getAssetType("Probe")),
		  new SC2Build(world.getAssetType("Probe")),
		  new SC2Build(world.getAssetType("Probe")),
		  new SC2Build(world.getAssetType("Probe")),
		  new SC2Build(world.getAssetType("Probe"))
		);
	}

}
