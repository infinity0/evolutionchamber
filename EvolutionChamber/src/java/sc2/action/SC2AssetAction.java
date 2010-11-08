package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.asset.SC2Command;
import sc2.asset.SC2Worker;
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

	protected SC2Asset source;

	public SC2AssetAction(SC2AssetType type, SC2AssetActionSchema schema) {
		super(nonNull(schema, "action scheme").cost_t);
		this.type = nonNull(type, "asset type");
		this.schema = schema;
	}

	public SC2AssetAction(SC2AssetType type, SC2AssetAction.Action act) {
		this(type, type.getSchema(act));
	}

	/**
	** {@inheritDoc}
	**
	** This implementation checks all requirements, selects a source asset,
	** and binds this action to it.
	*/
	@Override protected void launch() throws SC2ActionException {
		try {
			checkRequirements();
		} catch (SC2RequireException e) {
			throw new SC2ActionException(this, "build requirements not satisfied: " + e.getMessage(), e);
		}

		try {
			getSourceAsset().bind(this);
		} catch (SC2AssetException e) {
			throw new SC2ActionException(this, e.getMessage(), e);
		} catch (SC2RequireException e) {
			throw new SC2ActionException(this, "general execution error: " + e.getMessage(), e);
		}
	}

	/**
	** {@inheritDoc}
	**
	** This implementation checks and deducts resources, and saves the referenc
	** to the source asset.
	*/
	@Override public void evt_init(SC2Asset source) throws SC2ActionException {
		try {
			deductResources();
			this.source = source;
		} catch (SC2CostException e) {
			throw new SC2ActionException(this, e.getMessage(), e);
		}
	}

	/**
	** {@inheritDoc}
	**
	** This implementation creates an asset and adds it to the game state, and
	** increments the max supply appropriately.
	**
	** If a worker was created, it is sent to work.
	*/
	@Override public void evt_done() {
		SC2Asset asset = play.world.createAsset(play, type);
		play.addAsset(asset);
		play.max_f += type.prov_f;

		if (asset instanceof SC2Worker && source instanceof SC2Command) {
			// TODO better mining rules, e.g. pick gas, pick least-saturated
			// TODO for zerg, source won't be a SC2Command, it will be a larva. fix this.
			((SC2Worker)asset).setGatherM((SC2Command)source);
		}
	}

	public void checkRequirements() throws SC2RequireException {
		if (play == null) { throw new NullPointerException(); }
		for (SC2Requires req: schema.req()) {
			req.require(play);
		}
	}

	public SC2Asset getSourceAsset() throws SC2AssetException {
		List<SC2AssetType> src = schema.src();
		List<SC2Asset> sources = Lists.newArrayList(Iterables.concat(
		Iterables.transform(src, new Function<SC2AssetType, Set<SC2Asset>>() {
			@Override public Set<SC2Asset> apply(SC2AssetType type) {
				return play.getAssets(type);
			}
		})));
		switch (sources.size()) {
		case 0:
			// TODO check the queues for future satisfiability
			throw new SC2AssetException(src.toArray(new SC2AssetType[src.size()]), false);
		case 1:
			return sources.get(0);
		default:
			// get first idle asset found, sorted by type as it appears in schema.src
			// then as the asset (of that type) was added to the game state
			// TODO better algorithm
			for (SC2Asset source: sources) { if (!source.isActive()) { return source; } }
			throw new SC2AssetException(sources.toArray(new SC2Asset[sources.size()]), false);
			//throw new UnsupportedOperationException("implict source not implemented, need to choose between: " + sources);
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
