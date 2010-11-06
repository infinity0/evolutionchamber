package sc2.asset;

import sc2.SC2Player;

import java.util.Set;
import java.util.HashSet;
import java.util.Formatter;

/**
** Represents a base structure, eg. Nexus, Orbital Command, Hive.
*/
public class SC2Base extends SC2Structure {

	final public int patch_m;
	final public int patch_v;
	final public boolean gold;

	final protected Set<SC2Worker> work_m;
	final protected Set<SC2Worker> work_v;

	final protected SC2Asset[] col_v;

	public SC2Base(SC2Player play, SC2AssetType type, int patch_m, int patch_v, boolean gold) {
		super(play, type);
		this.patch_m = patch_m;
		this.patch_v = patch_v;
		this.gold = gold;
		this.work_m = new HashSet<SC2Worker>();
		this.work_v = new HashSet<SC2Worker>();
		this.col_v = new SC2Asset[patch_v];
		// TODO
	}

	public SC2Base(SC2Player play, SC2AssetType type) {
		this(play, type, 8, 2, false);
	}

	public int getPatchV() {
		int v = 0;
		for (SC2Asset a: col_v) { if (a != null) { ++v; } }
		return v;
	}

	public void remGatherer(SC2Worker work) {
		work_m.remove(work);
		work_v.remove(work);
	}

	public void addGathererM(SC2Worker work) {
		work_m.add(work);
	}

	public void addGathererV(SC2Worker work) {
		work_v.add(work);
	}

	public double estIncomeM() {
		return gold? estIncomeG(work_m.size(), patch_m): estIncomeM(work_m.size(), patch_m);
	}

	public double estIncomeV() {
		return estIncomeV(work_v.size(), getPatchV());
	}

	public String getDesc() {
		return new Formatter().format(
		  "%s @ %6.2f%s+:%d %6.2fv+:%d",
		  super.toString(), estIncomeM(), gold?"g":"m", patch_m, estIncomeV(), getPatchV()).toString();
	}

	@Override protected void advance(double rate) {
		super.advance(rate);
		// add income to player state, at a normal rate
		play.res_m += estIncomeM();
		play.res_v += estIncomeV();
	}

	public static double estIncomeM(int workers, int patch_m) {
		return estIncome(workers, patch_m, RATE_M);
	}

	public static double estIncomeV(int workers, int patch_v) {
		return estIncome(workers, patch_v, RATE_V);
	}

	public static double estIncomeG(int workers, int patch_m) {
		return estIncome(workers, patch_m, RATE_G);
	}

	public static double estIncome(int workers, int patches, double[] rate) {
		if (patches == 0) { return 0; }
		int max = rate.length;

		// minimum number of workers per patch
		int patch_less = workers / patches;
		// number of patches with 1 more worker on
		int count_more = workers % patches;
		int count_less = patches - count_more;
		int patch_more = patch_less + 1;

		return ((patch_less < max)? rate[patch_less]: rate[max-1]) * count_less +
		       ((patch_more < max)? rate[patch_more]: rate[max-1]) * count_more;
	}

	/** average mineral income (per patch per second) with increasing workers */
	protected static double[] RATE_M = new double[]{0, 0.70, 1.40, 1.70};
	/** average vespene income (per patch per second) with increasing workers */
	protected static double[] RATE_V = new double[]{0, 0.65, 1.30, 1.90};
	/** average gold mineral income (per patch per second) with increasing workers */
	protected static double[] RATE_G = new double[]{0, 1.00, 2.00, 2.40};

	public static void main(String[] args) {
		for (int i=0; i<30; ++i) {
			System.out.printf("%2d | %06.2f %06.2f %06.2f\n", i,
			  sc2.asset.SC2Base.estIncomeM(i, 8)*60,
			  sc2.asset.SC2Base.estIncomeV(i, 2)*60,
			  sc2.asset.SC2Base.estIncomeG(i, 6)*60);
		}
	}

}
