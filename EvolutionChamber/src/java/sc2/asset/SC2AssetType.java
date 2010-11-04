package sc2.asset;

import sc2.SC2Attack;
import sc2.SC2HealthSchema;
import sc2.SC2EnergySchema;
import sc2.action.SC2AssetAction;
import sc2.action.SC2AssetActionSchema;
import static sc2.SC2World.Race;
import static sc2.ArgUtils.non_null;
import static sc2.ArgUtils.non_null_copy;
import static sc2.ArgUtils.non_null_immute_set;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
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

	final public Set<Modifier> mods;

	final public SC2Attack atk_g;
	final public SC2Attack atk_a;

	/** provided food, will be 0 for most things */
	final public int prov_f;

	/** supply cost. units have >=0 supply, tech/structs have null supply */
	final public Integer cost_f;
	/** cargo size */
	final public int size;
	/** cargo capacity */
	final public int cargo;

	public Group group() {
		return (cost_f != null)? Group.U: (mods.contains(Modifier.STRUCTURE))? Group.S: Group.T;
	}

	/**
	** Return the food cost, or 0 if this asset type does not use supply
	*/
	public int supply() {
		return cost_f == null? 0: (int)cost_f;
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

	public static Builder initAssetType(String name, Race race) {
		return new Builder(name, race);
	}

	/** custom constructor */
	protected SC2AssetType(
		String name, Race race, SC2AssetActionSchema[] actions,
		SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep,
		double speed, int range, int sight,
		EnumSet<Modifier> mods, SC2Attack atk_g, SC2Attack atk_a,
		int prov_f, Integer cost_f, int size, int cargo
	) {
		this.name = non_null("name", name);
		this.race = non_null("race", race);

		this.actions = non_null_copy(actions, new SC2AssetActionSchema[0]);

		this.stat_hp = stat_hp;
		this.stat_sp = stat_sp;
		this.stat_ep = (stat_ep == null)? SC2EnergySchema.NONE: stat_ep;

		this.speed = speed;
		this.range = range;
		this.sight = sight;

		this.mods = non_null_immute_set(mods);
		this.atk_g = atk_g;
		this.atk_a = atk_a;

		this.prov_f = prov_f;
		this.cost_f = cost_f;
		this.size = size;
		this.cargo = cargo;
	}

	public static class Builder {

		final String name;
		final Race race;

		SC2AssetActionSchema[] actions;

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

		public Builder(String name, Race race) {
			this.name = name;
			this.race = race;
		}

		public Builder actions(SC2AssetActionSchema ... actions) {
			this.actions = actions;
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

		public SC2AssetType buildStructure() {
			if (mods != null) { mods.add(Modifier.STRUCTURE); } else { mods = EnumSet.of(Modifier.STRUCTURE); }
			return new SC2AssetType(name, race, actions,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  mods, atk_g, atk_a, prov_f, null, 0, 0);
		}

		public SC2AssetType buildUnit(int cost_f, int size, int cargo) {
			return new SC2AssetType(name, race, actions,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  mods, atk_g, atk_a, prov_f, cost_f, size, cargo);
		}

		public SC2AssetType buildTech() {
			return new SC2AssetType(name, race, actions,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  EnumSet.of(Modifier.TECH), atk_g, atk_a, prov_f, null, 0, 0);
		}

	}

	public enum Group {
		U("unit"), S("struct"), T("tech");
		final public String name;
		Group(String name) {
			this.name = name;
		}
	}

	public enum Modifier { TECH, STRUCTURE, ARMORED, BIOLOGICAL, LIGHT, MASSIVE,
	  MECHANICAL, PSIONIC, DETECTOR; }

}
