package sc2;

import sc2.asset.SC2AssetType;
import sc2.asset.SC2Asset;
import static sc2.asset.SC2AssetType.Group;

import java.util.HashMap;

/**
** Holds data for in-game asset types.
*/
public class SC2StatDB extends HashMap<String, SC2AssetType> {

	public SC2StatDB() { }

	public Group getType(String key) {
		return get(key).type();
	}

	public static SC2StatDB getDefault() {
		// TODO
		return null;
	}

	/**
	** Create an appropriate subclass of {@link SC2Asset} for the given type.
	*/
	public SC2Asset createAsset(SC2AssetType type) {
		// TODO
		return null;
	}

}
