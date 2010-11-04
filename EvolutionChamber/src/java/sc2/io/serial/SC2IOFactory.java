package sc2.io.serial;

import sc2.SC2World;
import sc2.asset.SC2AssetType;
import sc2.action.SC2AssetActionSchema;
import static sc2.SC2World.Race;
import static sc2.asset.SC2AssetType.Group;
import static sc2.asset.SC2AssetType.Builder;
import static sc2.action.SC2AssetAction.Action;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

/**
** Handles serialisation of base data types such as {@link SC2AssetType}.
*/
public class SC2IOFactory {

	final protected SC2World world;

	public SC2IOFactory(SC2World world) {
		this.world = world;
	}

	public void addAssetType(Race race, Group group, String s) {
		SC2AssetType newtype = makeAssetType(race, group, s);
		//world.stat.put(newtype.name, newtype);
	}

	public SC2AssetType makeAssetType(Race race, Group group, String s) {
		Iterator<String> parts = SEP_ASSET.split(s).iterator();

		String name = parts.next();
		System.out.println(name);
		Builder b = SC2AssetType.initAssetType(name, race);
		while (parts.hasNext()) {
			String part = parts.next();
			Iterator<String> cmpts = SEP_PARTS.split(part).iterator();

			String head = cmpts.next();
			System.out.println("  " + head);

			if (head.length() == 0) {
				throw new IllegalArgumentException("empty head on part: " + part);

			} else if (head.equals("prov_f")) {
				// provide supply
				String str = SEP_STATS.split(cmpts.next()).iterator().next();
				// TODO needs to account for zergling = 0.5 supply
				b.provide(Integer.parseInt(str));
				System.out.println("    providing " + str + " supply");

			} else if (head.equals("u")) {
				// unit stats
				List<String> stats = ImmutableList.copyOf(SEP_STATS.split(cmpts.next()));
				System.out.println(stats);

			} else {
				System.out.println(ImmutableList.copyOf(cmpts));
				processAssetAction(race, group, Action.fromString(head), cmpts);
			}
		}
		// TODO
		return null;
	}

	protected void processAssetAction(Race race, Group group, Action act, Iterator<String> cmpts) {
		// TODO
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

	final public static Splitter SEP_ASSET = Splitter.on('|').trimResults();
	final public static Splitter SEP_PARTS = Splitter.on(':').trimResults();
	final public static Splitter SEP_ITEMS = Splitter.on(',').trimResults();
	final public static Splitter SEP_STATS = Splitter.on(' ').omitEmptyStrings();

}
