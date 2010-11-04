package sc2.action;

import sc2.asset.SC2AssetType;
import static sc2.ArgUtils.non_null;

/**
** Represents a task on some sort of {@link sc2.asset.SC2Asset}.
*/
abstract public class SC2AssetAction extends SC2Action {

	final protected SC2AssetType type;
	final protected SC2AssetActionSchema schema;

	public SC2AssetAction(SC2AssetType type, SC2AssetActionSchema schema) {
		super(non_null("action scheme", schema).cost_t);
		this.type = non_null("asset type", type);
		this.schema = schema;
	}

	public SC2AssetAction(SC2AssetType type, SC2AssetAction.Action act) {
		this(type, type.getSchema(act));
	}

	public enum Action {
		BUILD, MORPH, WARPIN, CSTR_P, CSTR_T;
		public static Action fromString(String s) { return valueOf(s.toUpperCase()); }
	}

}
