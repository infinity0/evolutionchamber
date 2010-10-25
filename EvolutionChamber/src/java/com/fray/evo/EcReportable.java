package com.fray.evo;

public interface EcReportable
{

	void bestScore(EcState finalState, int intValue, String detailedText, String simpleText);

	void threadScore(int threadIndex, String output);

}
