package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents an add-on build task. This can be thought of as a 1:2 morph from
** structure to structure plus add-on. Like morphs, it can only be started when
** the asset queue is empty, and whilst in progress, nothing else can be placed
** onto the queue.
*/
public class SC2MorphTerranAddon extends SC2Morph {

	public SC2MorphTerranAddon(SC2AssetType type) {
		super(type);
	}

	// TODO

}
