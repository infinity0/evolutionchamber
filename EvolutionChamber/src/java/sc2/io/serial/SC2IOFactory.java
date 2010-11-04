package sc2.io.serial;

import sc2.SC2World;
import sc2.asset.SC2AssetType;
import sc2.action.SC2AssetActionSchema;
import sc2.require.SC2Requires;
import static sc2.SC2World.Race;
import static sc2.asset.SC2AssetType.Group;
import static sc2.asset.SC2AssetType.Builder;
import static sc2.action.SC2AssetAction.Action;

import com.google.common.base.Splitter;
import static com.google.common.collect.ImmutableList.copyOf;

import java.util.Iterator;
import java.util.List;

/**
** Handles serialisation of base data types such as {@link SC2AssetType}.
*/
public class SC2IOFactory {

	final protected SC2World world;

	protected Race curr_race;
	protected Group curr_group;
	protected Builder curr_builder;

	public SC2IOFactory(SC2World world) {
		this.world = world;
	}

	protected void resetAll() {
		curr_race = null;
		curr_group = null;
		curr_builder = null;
	}

	public void addAssetType(Race race, Group group, String s) {
		SC2AssetType newtype = makeAssetType(race, group, s);
		world.stat.put(newtype.name, newtype);
	}

	public SC2AssetType makeAssetType(Race race, Group group, String s) {
		curr_race = race;
		curr_group = group;

		Iterator<String> parts = SEP_ASSET.split(s).iterator();
		String name = parts.next();
		System.out.println(name);
		curr_builder = SC2AssetType.initAssetType(name, curr_race);

		while (parts.hasNext()) {
			String part = parts.next();
			Iterator<String> cmpts = SEP_PARTS.split(part).iterator();

			String head = cmpts.next();
			if (head.length() == 0) {
				throw new IllegalArgumentException("empty head on part: " + part);

			} else if (head.equals("prov_f")) {
				// provide supply
				String str = SEP_STATS.split(cmpts.next()).iterator().next();
				curr_builder.provide(Integer.parseInt(str));
				System.out.println("    provides " + str + " supply");

			} else if (head.equals("u")) {
				// unit stats
				List<String> stats = copyOf(SEP_STATS.split(cmpts.next()));
				parseUnitStats(stats);
				System.out.println("    unit stats " + stats);

			} else {
				List<String> args = copyOf(cmpts);
				System.out.println("    " + head + " " + args);
				parseAssetActionSchema(Action.fromString(head), args);
			}
		}
		// TODO

		SC2AssetType type = curr_builder.build();
		resetAll();
		return type;
	}

	protected void parseUnitStats(List<String> stats) {
		float cost_f = Float.parseFloat(stats.get(0));
		// turn A into -1 for air units
		int cg_size = stats.get(1).toUpperCase().equals("A")? -1: Integer.parseInt(stats.get(1));
		int cg_cap = Integer.parseInt(stats.get(2));
		curr_builder.unit(cost_f, cg_size, cg_cap);
	}

	protected void parseAssetActionSchema(Action act, List<String> args) {
		List<String> cost = copyOf(SEP_STATS.split(args.get(0)));
		int cost_m = Integer.parseInt(cost.get(0));
		int cost_v = Integer.parseInt(cost.get(1));
		double cost_t = Double.parseDouble(cost.get(2));

		SC2AssetType[] src = (args.size() <= 1)? null: getAssetTypes(copyOf(SEP_ITEMS.split(args.get(1))));
		SC2Requires[] req = (args.size() <= 2)? null: getRequires(copyOf(SEP_ITEMS.split(args.get(2))));

		if (args.size() <= 3) {
			curr_builder.add(new SC2AssetActionSchema(act, src, req, cost_m, cost_v, cost_t));
		} else {
			throw new UnsupportedOperationException("not implemented");
			// TODO
		}
	}

	protected SC2AssetType[] getAssetTypes(List<String> items) {
		return null;
	}

	protected SC2Requires[] getRequires(List<String> items) {
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
