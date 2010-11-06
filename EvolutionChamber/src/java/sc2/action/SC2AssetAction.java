package sc2.action;

import sc2.asset.SC2AssetType;
import static sc2.ArgUtils.nonNull;

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

	public enum Action {
		BUILD, MORPH, WARPIN, CSTR_P, CSTR_T;
		public static Action fromString(String s) { return valueOf(s.toUpperCase()); }
	}

}
