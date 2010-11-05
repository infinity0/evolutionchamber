package sc2.io.serial;

import sc2.SC2World;
import sc2.asset.SC2AssetType;
import sc2.action.SC2AssetActionSchema;
import sc2.require.SC2Requires;
import sc2.require.SC2RequiresAsset;
import sc2.require.SC2RequiresTech;
import static sc2.SC2World.Race;
import static sc2.asset.SC2AssetType.Group;
import static sc2.asset.SC2AssetType.Builder;
import static sc2.action.SC2AssetAction.Action;
import static sc2.ArgUtils.isFAIAPEmpty;
import static sc2.ArgUtils.enumFromFirstChar;

import com.google.common.base.Splitter;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import static com.google.common.collect.ImmutableList.copyOf;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
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

	public void setSection(String s) {
		Iterator<String> it = SEP_STATS.split(s.trim()).iterator();
		curr_race = enumFromFirstChar(Race.class, it.next());
		curr_group = enumFromFirstChar(Group.class, it.next());
	}

	public void addAssetType(String s) {
		SC2AssetType newtype = makeAssetType(s);
		world.stat.put(newtype.name, newtype);
	}

	public SC2AssetType makeAssetType(String s) {
		if (curr_race == null || curr_group == null) {
			throw new IllegalStateException("current race/group not set");
		}

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
		curr_builder = null;
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
		SC2Requires[] req = (args.size() > 2)? makeRequires(copyOf(SEP_ITEMS.split(args.get(2)))): null;

		if (args.size() > 3) {
			// don't getAssetTypeOrGuard() to since morph-prepare assets can't be set with cycles()
			SC2AssetType prep = (args.get(3).length() == 0)? null: world.getAssetType(args.get(3));
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
		if (isFAIAPEmpty(items)) { return null; }
		List<SC2AssetType> types = Lists.transform(items, new Function<String, SC2AssetType>() {
			@Override public SC2AssetType apply(String name) { return getAssetTypeOrGuard(name); }
		});
		return types.toArray(new SC2AssetType[types.size()]);
	}

	protected SC2Requires[] makeRequires(List<String> items) {
		if (isFAIAPEmpty(items)) { return null; }
		List<SC2Requires> reqs = Lists.transform(items, new Function<String, SC2Requires>() {
			@Override public SC2Requires apply(String name) { return makeRequires(name); }
		});
		return reqs.toArray(new SC2Requires[reqs.size()]);
	}

	protected SC2Requires makeRequires(String item) {
		// let it throw NPE
		if (item.length() == 0) {
			throw new IllegalArgumentException("invalid requirement (empty string)");
		}

		boolean less = false;
		int req = 1;
		switch (item.charAt(0)) {
		case '<':
			less = true;
		case '>':
			Scanner sc = new Scanner(item.substring(1));
			req = sc.nextInt();
			item = sc.next();
			break;
		}

		SC2AssetType type = getAssetTypeOrGuard(item);
		switch (type.group()) {
		case T:
			return new SC2RequiresTech(type);
		default:
			return new SC2RequiresAsset(type, req, less);
		}
	}

	protected SC2AssetType getAssetTypeOrGuard(String item) {
		// TODO make this more restrictive
		try {
			return world.getAssetType(item);
		} catch (NoSuchElementException e) {
			return SC2AssetType.guard(item);
		}
	}

	/**
	** Some morphs happen in cycles, so we can't create the types "in order".
	*/
	public void cycles() {
		resetAll();
		for (SC2AssetType type: world.stat.values()) {
			type.cycles(world);
		}
	}

	final public static Splitter SEP_ASSET = Splitter.on('|').trimResults().omitEmptyStrings();
	final public static Splitter SEP_PARTS = Splitter.on(':').trimResults();
	final public static Splitter SEP_ITEMS = Splitter.on(',').trimResults();
	final public static Splitter SEP_STATS = Splitter.on(' ').omitEmptyStrings();

}
