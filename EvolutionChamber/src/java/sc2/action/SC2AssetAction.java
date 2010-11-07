package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.require.SC2Requires;
import sc2.require.SC2RequireException;
import sc2.require.SC2AssetException;
import sc2.require.SC2CostException;
import sc2.require.SC2RequireException;
import static sc2.ArgUtils.nonNull;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
** Represents a task on some sort of {@link sc2.asset.SC2Asset}.
*/
abstract public class SC2AssetAction extends SC2Action {

	final public SC2AssetType type;
	final public SC2AssetActionSchema schema;

	public SC2AssetAction(SC2AssetType type, SC2AssetActionSchema schema) {
		super(nonNull(schema, "action scheme").cost_t);
		this.type = nonNull(type, "asset type");
		this.schema = schema;
	}

	public SC2AssetAction(SC2AssetType type, SC2AssetAction.Action act) {
		this(type, type.getSchema(act));
	}

	public void checkRequirements() throws SC2RequireException {
		if (play == null) { throw new NullPointerException(); }
		for (SC2Requires req: schema.req()) {
			req.require(play);
		}
	}

	public SC2Asset getSourceAsset() throws SC2AssetException {
		List<SC2Asset> sources = Lists.newArrayList(Iterables.concat(
		Iterables.transform(schema.src(), new Function<SC2AssetType, Set<SC2Asset>>() {
			@Override public Set<SC2Asset> apply(SC2AssetType type) {
				return play.getAssets(type);
			}
		})));
		switch (sources.size()) {
		case 0:
			// TODO check the queues for future satisfiability
			throw new SC2AssetException(schema.src().toArray(new SC2AssetType[0]), false);
		case 1:
			return sources.get(0);
		default:
			throw new UnsupportedOperationException("implict source not implemented, need to choose between: " + sources);
		}
	}

	public void deductResources() throws SC2CostException {
		if (play.res_m < schema.cost_m || play.res_v < schema.cost_v) {
			throw new SC2CostException(schema.cost_m, schema.cost_v, play.res_m, play.res_v);
		}
		play.res_m -= schema.cost_m;
		play.res_v -= schema.cost_v;
	}

	public enum Action {
		BUILD, MORPH, WARPIN, CSTR_P, CSTR_T;
		public static Action fromString(String s) { return valueOf(s.toUpperCase()); }
	}

}
