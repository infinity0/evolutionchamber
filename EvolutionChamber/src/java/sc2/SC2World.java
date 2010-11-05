package sc2;

import sc2.asset.SC2AssetType;
import sc2.asset.SC2Asset;
import static sc2.asset.SC2AssetType.Group;

import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
** Holds data for in-game asset types.
*/
public class SC2World {

	final public Map<String, SC2AssetType> stat = new HashMap<String, SC2AssetType>();

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

	public SC2AssetType getAssetType(String name) {
		SC2AssetType type = stat.get(name);
		if (type == null) { throw new NoSuchElementException("asset " + name + " has not yet been defined"); }
		return type;
	}

	public enum Race {
		P("protoss"), Z("zerg"), T("terran");
		final public String name;
		Race(String name) {
			this.name = name;
		}
	}

}
