package sc2.asset;

import sc2.SC2World;
import sc2.SC2Attack;
import sc2.SC2HealthSchema;
import sc2.SC2EnergySchema;
import sc2.action.SC2AssetAction;
import sc2.action.SC2AssetActionSchema;
import static sc2.SC2World.Race;
import static sc2.ArgUtils.non_null;
import static sc2.ArgUtils.non_null_copy;
import static sc2.ArgUtils.non_null_immute_set;

import java.util.EnumSet;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
** Represents an asset type.
*/
public class SC2AssetType {

	final public String name;
	final public Race race;

	/** possible actions to obtain an asset of this type */
	final protected SC2AssetActionSchema[] actions;

	final public SC2HealthSchema stat_hp;
	final public SC2HealthSchema stat_sp;
	final public SC2EnergySchema stat_ep;

	final public double speed;
	final public int range;
	final public int sight;

	/** immutable by contract. TODO use ImmutableSet or something */
	final public Set<Modifier> mods;

	final public SC2Attack atk_g;
	final public SC2Attack atk_a;

	/** provided food. */
	final public int prov_f;
	/** supply cost. units have >=0 cost, tech/structs have null cost. */
	final public Float cost_f;
	/** cargo size. -1 means air unit. TODO use a better representation. */
	final public int cg_size;
	/** cargo capacity. */
	final public int cg_cap;

	public Group group() {
		return (this instanceof Guard)? Group.G: (cost_f != null)? Group.U:
		  (mods.contains(Modifier.STRUCTURE))? Group.S: Group.T;
	}

	/** Set reference cycles after construction. */
	public void cycles(SC2World world) {
		for (SC2AssetActionSchema a: actions) { a.cycles(world); }
	}

	/**
	** Return the food cost, or 0 if this asset type does not use supply
	*/
	public float supply() {
		return cost_f == null? 0: (float)cost_f;
	}

	/**
	** Return the first schema for the given type. In the current SC2, this
	** will be the only schema for the type.
	*/
	public SC2AssetActionSchema getSchema(SC2AssetAction.Action act) {
		for (SC2AssetActionSchema s: actions) {
			if (s.act == act) { return s; }
		}
		throw new NoSuchElementException("action " + act + " not available for asset type " + this.name);
	}

	/**
	** Return all schemas for the given action. This is provided in cases
	** future patches introduce this capability.
	*/
	public SC2AssetActionSchema[] getAllSchemas(SC2AssetAction.Action act) {
		SC2AssetActionSchema[] all = new SC2AssetActionSchema[actions.length];
		int i = 0;
		for (SC2AssetActionSchema s: actions) {
			if (s.act == act) { all[i++] = s; }
		}
		return Arrays.copyOf(all, i);
	}

	/**
	** Default constructor, used by {@link Builder}. Performs various sanity
	** checks on the input. Protected to encourage use of the builder instead.
	*/
	protected SC2AssetType(
		String name, Race race, SC2AssetActionSchema[] actions,
		SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep,
		double speed, int range, int sight,
		EnumSet<Modifier> mods, SC2Attack atk_g, SC2Attack atk_a,
		int prov_f, Float cost_f, int cg_size, int cg_cap
	) {
		this(true,
		  non_null("name", name), non_null("race", race),
		  non_null_copy(actions, new SC2AssetActionSchema[0]),
		  stat_hp, stat_sp, (stat_ep == null)? SC2EnergySchema.NONE: stat_ep,
		  speed, range, sight,
		  non_null_immute_set(mods), atk_g, atk_a,
		  prov_f, cost_f, cg_size, cg_cap);
	}

	/**
	** Private constructor that bypasses all checks. For hacky stuff like
	** {@link Guard} only.
	*/
	private SC2AssetType(
		boolean lock, // makes it easier to differentiate the constructors
		String name, Race race, SC2AssetActionSchema[] actions,
		SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep,
		double speed, int range, int sight,
		Set<Modifier> mods, SC2Attack atk_g, SC2Attack atk_a,
		int prov_f, Float cost_f, int cg_size, int cg_cap
	) {
		this.race = race;
		this.name = name;

		this.actions = actions;

		this.stat_hp = stat_hp;
		this.stat_sp = stat_sp;
		this.stat_ep = stat_ep;

		this.speed = speed;
		this.range = range;
		this.sight = sight;

		this.mods = mods;
		this.atk_g = atk_g;
		this.atk_a = atk_a;

		this.prov_f = prov_f;
		this.cost_f = cost_f;
		this.cg_size = cg_size;
		this.cg_cap = cg_cap;
	}

	/** Start defining an {@link SC2AssetType}. */
	public static Builder initAssetType(String name, Race race) {
		return new Builder(name, race);
	}

	public static class Builder {

		final String name;
		final Race race;

		List<SC2AssetActionSchema> actions = new java.util.ArrayList<SC2AssetActionSchema>();

		SC2HealthSchema stat_hp;
		SC2HealthSchema stat_sp;
		SC2EnergySchema stat_ep;

		double speed;
		int range;
		int sight;

		EnumSet<Modifier> mods;

		SC2Attack atk_g;
		SC2Attack atk_a;

		int prov_f;
		Float cost_f;
		int cg_size;
		int cg_cap;


		public Builder(String name, Race race) {
			this.name = name;
			this.race = race;
		}

		public Builder add(SC2AssetActionSchema ... acts) {
			actions.addAll(Arrays.asList(acts));
			return this;
		}

		public Builder defence(SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep) {
			this.stat_hp = stat_hp;
			this.stat_sp = stat_sp;
			this.stat_ep = stat_ep;
			return this;
		}

		public Builder physical(double speed, int range, int sight) {
			this.speed = speed;
			this.range = range;
			this.sight = sight;
			return this;
		}

		public Builder modifier(Modifier mod_0, Modifier ... mods) {
			this.mods = EnumSet.of(mod_0, mods);
			return this;
		}

		public Builder attacks(SC2Attack atk_g, SC2Attack atk_a) {
			this.atk_g = atk_g;
			this.atk_a = atk_a;
			return this;
		}

		/** provide some supply */
		public Builder provide(int prov_f) {
			this.prov_f = prov_f;
			return this;
		}

		public Builder struct() {
			if (mods == null) {
				mods.add(Modifier.STRUCTURE);
			} else {
				mods = EnumSet.of(Modifier.STRUCTURE);
			}
			return this;
		}

		public Builder tech() {
			mods = EnumSet.of(Modifier.TECH);
			return this;
		}

		public Builder unit(float cost_f, int cg_size, int cg_cap) {
			this.cost_f = cost_f;
			this.cg_size = cg_size;
			this.cg_cap = cg_cap;
			return this;
		}

		public SC2AssetType build() {
			return new SC2AssetType(name, race, actions.<SC2AssetActionSchema>toArray(new SC2AssetActionSchema[actions.size()]),
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  mods, atk_g, atk_a, prov_f, cost_f, cg_size, cg_cap);
		}

	}

	/** Create a {@link Guard non-functional stand-in asset type}. */
	public static SC2AssetType guard(String name) {
		return new Guard(name);
	}

	public static class Guard extends SC2AssetType {
		public Guard(String name) {
			super(true, name, null, null, null, null, null, 0, 0, 0, null, null, null, 0, null, 0, 0);
		}
	}

	public enum Group {
		S("struct"), T("tech"), U("unit"), G("guard");
		final public String name;
		Group(String name) {
			this.name = name;
		}
	}

	public enum Modifier { TECH, STRUCTURE, ARMORED, BIOLOGICAL, LIGHT, MASSIVE,
	  MECHANICAL, PSIONIC, DETECTOR; }

}
