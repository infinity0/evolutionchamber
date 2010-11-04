package sc2;

import sc2.asset.SC2AssetType;
import sc2.asset.SC2Asset;
import static sc2.asset.SC2AssetType.Group;

import java.util.HashMap;

/**
** Holds data for in-game asset types.
*/
public class SC2World {

	public enum Race {
		P, Z, T;

		public static Race fromString(String s) {
			return s.length() == 0? fromChar('\0'): fromChar(s.charAt(0));
		}

		public static Race fromChar(char c) {
			switch (c) {
			case 'P': return Race.P;
			case 'Z': return Race.Z;
			case 'T': return Race.P;
			default: throw new IllegalArgumentException("invalid race");
			}
		}
	}

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

}
