package sc2;

import sc2.annot.Idempotent;
import sc2.annot.PostImmutable;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.asset.SC2Structure;
import sc2.asset.SC2Command;
import sc2.asset.SC2ZergCommand;
import sc2.asset.SC2Worker;
import static sc2.asset.SC2AssetType.Group;

import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;

import java.util.Map;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
** Holds data for in-game asset types.
*/
//@PostImmutable(post="cycles") need to make stat/macro protected
public class SC2World {

	final public Map<String, SC2AssetType> stat = new HashMap<String, SC2AssetType>();
	final public Map<Race, SC2Macro> macro = new EnumMap<Race, SC2Macro>(Race.class);

	public SC2World() { }

	public Group getAssetGroup(String key) {
		return stat.get(key).group();
	}

	/** alias for {@link #getAssetType(String)} */
	public SC2AssetType type(String name) {
		return getAssetType(name);
	}

	public SC2AssetType getAssetType(String name) {
		SC2AssetType type = stat.get(name);
		if (type == null) { throw new NoSuchElementException("asset " + name + " has not yet been defined"); }
		return type;
	}

	/** Set reference cycles after construction and population. */
	@Idempotent
	public void cycles() {
		for (SC2AssetType type: stat.values()) {
			type.cycles(this);
		}
	}

	/**
	** Create an appropriate subclass of {@link SC2Asset} for the given type.
	*/
	public SC2Asset createAsset(SC2Player play, SC2AssetType type) {
		switch (type.group()) {
		case S:
			return new SC2Structure(play, type);
		case T:
			throw new IllegalArgumentException("cannot create an asset from a tech type");
		case U:
			if (isWorker(type)) { return new SC2Worker(play, type); }
			if (isCommand(type)) { return (type.race == Race.Z)? new SC2ZergCommand(play, type): new SC2Command(play, type); }
			return new SC2Asset(play, type);
		default:
			throw new IllegalArgumentException("cannot create an asset from group: " + type.group().name);
		}
	}

	public boolean isWorker(final SC2AssetType type) {
		return Iterables.any(macro.values(), new Predicate<SC2Macro>() {
			@Override public boolean apply(SC2Macro macro) {
				return macro.getAllWorker().contains(type);
			}
		});
	}

	public boolean isCommand(final SC2AssetType type) {
		return Iterables.any(macro.values(), new Predicate<SC2Macro>() {
			@Override public boolean apply(SC2Macro macro) {
				return macro.getAllCommand().contains(type);
			}
		});
	}

	public enum Race {
		P("protoss"), Z("zerg"), T("terran");
		final public String name;
		Race(String name) {
			this.name = name;
		}
	}

}
