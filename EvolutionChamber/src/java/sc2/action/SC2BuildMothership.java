package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents a mothership build task. This has the special requirement that
** no other mothership exists.
*/
public class SC2BuildMothership extends SC2Build {

	public SC2BuildMothership(SC2AssetType type) {
		super(type);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO check no other mothership exists
		super.launch();
	}

}
