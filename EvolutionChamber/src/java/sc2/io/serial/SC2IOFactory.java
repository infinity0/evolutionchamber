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
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import static com.google.common.collect.ImmutableList.copyOf;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static java.lang.Integer.parseInt;
import static java.lang.Float.parseFloat;
import static java.lang.Double.parseDouble;

/**
** Handles serialisation of base data types such as {@link SC2AssetType}.
**
** TODO use a parser generator like ANTLR or something.
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
				curr_builder.provide(parseInt(str));
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
		// turn h into 0.5 for zerglings
		float cost_f = stats.get(0).equals("h")? 0.5f: parseFloat(stats.get(0));
		// turn A into -1 for air units
		int cg_size = stats.get(1).equals("A")? -1: parseInt(stats.get(1));
		int cg_cap = parseInt(stats.get(2));
		curr_builder.unit(cost_f, cg_size, cg_cap);
	}

	protected void parseAssetActionSchema(Action act, List<String> args) {
		List<String> cost = copyOf(SEP_STATS.split(args.get(0)));
		int cost_m = parseInt(cost.get(0));
		int cost_v = parseInt(cost.get(1));
		double cost_t = parseDouble(cost.get(2));

		SC2AssetType[] src = (args.size() > 1)? getAssetTypes(copyOf(SEP_ITEMS.split(args.get(1)))): null;
		SC2Requires[] req = (args.size() > 2)? getRequires(copyOf(SEP_ITEMS.split(args.get(2)))): null;

		if (args.size() > 3) {
			SC2AssetType prep = (args.get(3).length() == 0)? null: getAssetType(args.get(3));
			int num_src = 1, num_dst = 1;
			if (args.size() > 4) {
				List<String> num = copyOf(SEP_STATS.split(args.get(4)));
				num_src = parseInt(num.get(0));
				num_dst = parseInt(num.get(1));
			}
			curr_builder.add(new SC2AssetActionSchema(act, src, req, cost_m, cost_v, cost_t, prep, num_src, num_dst));
		} else {
			curr_builder.add(new SC2AssetActionSchema(act, src, req, cost_m, cost_v, cost_t));
		}
	}

	protected SC2AssetType[] getAssetTypes(List<String> items) {
		List<SC2AssetType> types = Lists.transform(items, new Function<String, SC2AssetType>() {
			@Override public SC2AssetType apply(String name) { return getAssetType(name); }
		});
		return types.toArray(new SC2AssetType[types.size()]);
	}

	protected SC2Requires[] getRequires(List<String> items) {
		// TODO
		return null;
	}

	protected SC2AssetType getAssetType(String name) {
		SC2AssetType type = world.stat.get(name);
		if (type == null) { throw new NoSuchElementException("asset " + name + " has not yet been defined"); }
		return type;
	}

	/**
	** Some morphs happen in cycles, so we can't create the types "in order".
	*/
	public void handleMorphCycles() {
		// TODO
	}

	final public static Splitter SEP_ASSET = Splitter.on('|').trimResults().omitEmptyStrings();
	final public static Splitter SEP_PARTS = Splitter.on(':').trimResults();
	final public static Splitter SEP_ITEMS = Splitter.on(',').trimResults();
	final public static Splitter SEP_STATS = Splitter.on(' ').omitEmptyStrings();

}
