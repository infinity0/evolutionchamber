package sc2.io.serial;

import sc2.SC2World;
import sc2.asset.SC2AssetType;
import sc2.action.SC2AssetActionSchema;

/**
** Handles serialisation of base data types such as {@link SC2AssetType}.
*/
public class SC2IOFactory {

	final protected SC2World world;

	public SC2IOFactory(SC2World world) {
		this.world = world;
	}

	public void addAssetType(String s) {
		SC2AssetType newtype = makeAssetType(s);
		world.stat.put(newtype.name, newtype);
	}

	public SC2AssetType makeAssetType(String s) {
		// TODO
		return null;
	}

	public SC2AssetActionSchema makeAssetActionSchema(String s) {
		// TODO
		return null;
	}

	/**
	** Some morphs happen in cycles, so we can't create the types "in order".
	*/
	public void handleMorphCycles() {
		// TODO
	}

}
