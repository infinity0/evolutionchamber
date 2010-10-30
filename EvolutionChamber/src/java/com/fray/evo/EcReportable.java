package com.fray.evo;

public interface EcReportable
{

	void bestScore(EcState finalState, int intValue, String detailedText, String simpleText, String yabotText);

	void threadScore(int threadIndex, String output);

}
