package sc2.action;

import sc2.asset.SC2AssetType;
import sc2.require.SC2Requires;
import static sc2.action.SC2AssetAction.Action;
import static sc2.ArgUtils.non_null;
import static sc2.ArgUtils.non_null_copy;

import java.util.List;
import java.util.Arrays;

/**
** Represents meta-data for {@link sc2.asset.SC2AssetAction}, such as resource
** cost and requirements.
*/
public class SC2AssetActionSchema {

	final public Action act;

	/**
	** Possible sources of the action. This means different things for
	** different actions, * e.g.:
	**
	** - {@link SC2Build}: this is the production structure / unit
	** - {@link SC2Morph}: this is the original structure / unit
	** - {@link SC2WarpIn}: this is the warping structure
	*/
	final protected SC2AssetType[] src;

	/** action requirements. eg. Cybernetics Core for Stalker. */
	final protected SC2Requires[] req;

	/** mineral cost */
	final public int cost_m;
	/** vespene cost */
	final public int cost_v;
	/** time cost */
	final public int cost_t;

	/** number of source assets. used by {@link SC2Morph} e.g. when morphing Archons */
	final public int num_src;
	/** number of target assets. used by {@link SC2Morph} e.g. when morphing Zerglings */
	final public int num_dst;
	/** prepare-asset. used by {@link SC2Morph} e.g. to make a Larva into a Cocoon, when morphing Zerg units */
	final public SC2AssetType pre;

	public SC2AssetActionSchema(Action act, SC2AssetType[] src, SC2Requires[] req,
	  int cost_m, int cost_v, int cost_t,
	  int num_src, int num_dst, SC2AssetType pre) {
		this.act = non_null("action", act);
		this.src = non_null_copy(src, new SC2AssetType[0]);
		this.req = non_null_copy(req, new SC2Requires[0]);
		this.cost_m = cost_m;
		this.cost_v = cost_v;
		this.cost_t = cost_t;
		this.num_src = num_src;
		this.num_dst = num_dst;
		this.pre = pre;
	}

	public SC2AssetActionSchema(Action act, SC2AssetType[] src, SC2Requires[] req,
	  int cost_m, int cost_v, int cost_t) {
		this(act, src, req, cost_m, cost_v, cost_t, 1, 1, null);
	}

	public List<SC2AssetType> src() { return Arrays.asList(src); }

	public List<SC2Requires> req() { return Arrays.asList(req); }

}
