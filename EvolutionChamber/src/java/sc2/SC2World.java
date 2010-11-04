package sc2;

import sc2.asset.SC2AssetType;
import sc2.asset.SC2Asset;
import static sc2.asset.SC2AssetType.Group;

import java.util.HashMap;

/**
** Holds data for in-game asset types.
*/
public class SC2World {

	final public HashMap<String, SC2AssetType> stat = new HashMap<String, SC2AssetType>();

	public SC2World() { }

	public Group getAssetGroup(String key) {
		return stat.get(key).group();
	}

	public static SC2World getDefaultWorld() {
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

	public enum Race {
		P("protoss"), Z("zerg"), T("terran");
		final public String name;
		Race(String name) {
			this.name = name;
		}
	}

}
