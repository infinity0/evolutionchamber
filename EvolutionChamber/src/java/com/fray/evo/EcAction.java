package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


final class EcActionUpgradeFlyerArmor3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(300, 300, 220, "Flyer Armor +3");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.flyerArmor2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerArmor3 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeFlyerAttacks2());
		return l;
	}
}

final class EcActionUpgradeFlyerArmor2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(225, 225, 190, "Flyer Armor +2");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0)
			return true;
		if (s.flyerArmor1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerArmor2 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionUpgradeFlyerAttacks1());
		return l;
	}
}

final class EcActionUpgradeFlyerArmor1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 160, "Flyer Armor +1");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerArmor1 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		return l;
	}
}

final class EcActionUpgradeFlyerAttacks3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(250, 250, 220, "Flyer Attacks +3");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.flyerAttack2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerAttack3 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeFlyerAttacks2());
		return l;
	}
}

final class EcActionUpgradeFlyerAttacks2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(175, 175, 190, "Flyer Attacks +2");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0)
			return true;
		if (s.flyerAttack1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerAttack2 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionUpgradeFlyerAttacks1());
		return l;
	}
}

final class EcActionUpgradeFlyerAttacks1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 160, "Flyer Attacks +1");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerAttack1 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		return l;
	}
}

final class EcActionUpgradeVentralSacs extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 130, "Ventral Sacs");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.ventralSacs = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}

final class EcActionUpgradePneumatizedCarapace extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 60, "Pneumatized Carapace");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.pneumatizedCarapace = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}

final class EcActionUpgradeBurrow extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 100, "Burrow");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.burrow = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}

final class EcActionUpgradeChitinousPlating extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Chitinous Plating");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.ultraliskCavern == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.chitinousPlating = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildUltraliskCavern());
		return l;
	}
}

final class EcActionUpgradePathogenGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Pathogen Glands");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.infestationPit == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.pathogenGlands = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}

final class EcActionUpgradeNeuralParasite extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Neural Parasite");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.infestationPit == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.neuralParasite = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}

final class EcActionUpgradeCarapace3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 220, "Carapace +3");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.armor2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.armor3 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeCarapace2());
		return l;
	}
}

final class EcActionUpgradeCarapace2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 190, "Carapace +2");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0)
			return true;
		if (s.armor1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.armor2 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildLair());
		l.add(new EcActionUpgradeCarapace1());
		return l;
	}
}

final class EcActionUpgradeCarapace1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 160, "Carapace +1");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.armor1 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		return l;
	}
}

final class EcActionUpgradeMissile3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 220, "Missile +3");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.missile2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.missile3 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeMissile2());
		return l;
	}
}

final class EcActionUpgradeMissile2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 190, "Missile +2");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0)
			return true;
		if (s.missile1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.missile2 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildLair());
		l.add(new EcActionUpgradeMissile1());
		return l;
	}
}

final class EcActionUpgradeMissile1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 160, "Missile +1");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.missile1 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		return l;
	}
}

final class EcActionUpgradeMelee3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 220, "Melee +3");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.melee2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.melee3 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeMelee2());
		return l;
	}
}

final class EcActionUpgradeMelee2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 190, "Melee +2");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0)
			return true;
		if (s.melee1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.melee2 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildLair());
		l.add(new EcActionUpgradeMelee1());
		return l;
	}
}

final class EcActionUpgradeMelee1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 160, "Melee +1");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.melee1 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		return l;
	}
}

final class EcActionUpgradeCentrifugalHooks extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Centrifugal Hooks");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.banelingNest == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.centrifugalHooks = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildBanelingNest());
		return l;
	}
}

final class EcActionUpgradeGroovedSpines extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 80, "Grooved Spines");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hydraliskDen == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.groovedSpines = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildHydraliskDen());
		return l;
	}
}

final class EcActionUpgradeGlialReconstitution extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 110, "Glial Reconstitution");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		if (s.lairs == 0)// Need to account for evolving lairs->hives
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.glialReconstitution = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildRoachWarren());
		l.add(new EcActionBuildLair());
		return l;
	}
}

final class EcActionUpgradeTunnelingClaws extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Tunneling Claws");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		if (s.lairs == 0)// Need to account for evolving lairs->hives
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.tunnelingClaws = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildRoachWarren());
		l.add(new EcActionBuildLair());
		return l;
	}
}

final class EcActionUpgradeAdrenalGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 130, "Adrenal Glands");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.hives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.adrenalGlands = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		l.add(new EcActionBuildHive());
		return l;
	}
}

final class EcActionUpgradeMetabolicBoost extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 110, "Metabolic Boost");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.metabolicBoost = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}
}

public abstract class EcAction implements Serializable
{
	static int							max	= 0;
	public static Map<Integer, Class>	actions;

	public abstract void execute(EcBuildOrder s, EcEvolver e);

	@Override
	public String toString()
	{
		return getClass().getSimpleName().replace("EcAction", "");
	}
	
	public boolean canExecute(EcBuildOrder s)
	{
		if (isPossible(s))
			return true;
		s.seconds += 1;
		Collection<Runnable> futureActions = s.getFutureActions(s.seconds);
		if (futureActions != null)
			for (Runnable r : futureActions)
				r.run();
		s.accumulateMaterials();
		return false;
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		return false;
	}

	public abstract boolean isPossible(EcBuildOrder s);

	public abstract List<EcAction> requirements();

	static Integer findAllele(EcAction a)
	{
		Integer allele = null;
		for (Integer i : actions.keySet())
		{
			Class a2 = actions.get(i);
			if (!actions.containsValue(a.getClass()))
				break;
			if (a2.getName().equals(a.getClass().getName()))
			{
				allele = i;
				break;
			}
		}
		return allele;
	}

	static void setup(EcState target)
	{
		actions = new HashMap<Integer, Class>();
		max = 0;
		EcRequirementTree.execute(target);
//		actions.put(max++, EcActionBuildDrone.class);
//		actions.put(max++, EcActionBuildOverlord.class);
//		actions.put(max++, EcActionBuildSpawningPool.class);
//		actions.put(max++, EcActionBuildZergling.class);
//		actions.put(max++, EcActionBuildQueen.class);
//		actions.put(max++, EcActionBuildHatchery.class);
//		actions.put(max++, EcActionBuildExtractor.class);
//		actions.put(max++, EcActionBuildRoachWarren.class);
//		actions.put(max++, EcActionBuildRoach.class);
//		actions.put(max++, EcActionWait.class);
//		actions.put(max++, EcActionBuildBanelingNest.class);
//		actions.put(max++, EcActionBuildBaneling.class);
//		actions.put(max++, EcActionBuildLair.class);
//		actions.put(max++, EcActionBuildHydraliskDen.class);
//		actions.put(max++, EcActionBuildSpire.class);
//		actions.put(max++, EcActionBuildHydralisk.class);
//		actions.put(max++, EcActionBuildMutalisk.class);
//		actions.put(max++, EcActionBuildCorruptor.class);
//		actions.put(max++, EcActionBuildInfestationPit.class);
//		actions.put(max++, EcActionBuildInfestor.class);
//		actions.put(max++, EcActionBuildHive.class);
//		actions.put(max++, EcActionBuildGreaterSpire.class);
//		actions.put(max++, EcActionBuildUltraliskCavern.class);
//		actions.put(max++, EcActionBuildUltralisk.class);
//		actions.put(max++, EcActionBuildEvolutionChamber.class);
//		actions.put(max++, EcActionBuildBroodLord.class);
//		actions.put(max++, EcActionUpgradeMetabolicBoost.class);
//		actions.put(max++, EcActionUpgradeAdrenalGlands.class);
//		actions.put(max++, EcActionUpgradeTunnelingClaws.class);
//		actions.put(max++, EcActionUpgradeGlialReconstitution.class);
//		actions.put(max++, EcActionUpgradeGroovedSpines.class);
//		actions.put(max++, EcActionUpgradeCentrifugalHooks.class);
//		actions.put(max++, EcActionUpgradeMelee1.class);
//		actions.put(max++, EcActionUpgradeMelee2.class);
//		actions.put(max++, EcActionUpgradeMelee3.class);
//		actions.put(max++, EcActionUpgradeMissile1.class);
//		actions.put(max++, EcActionUpgradeMissile2.class);
//		actions.put(max++, EcActionUpgradeMissile3.class);
//		actions.put(max++, EcActionUpgradeCarapace1.class);
//		actions.put(max++, EcActionUpgradeCarapace2.class);
//		actions.put(max++, EcActionUpgradeCarapace3.class);
//		actions.put(max++, EcActionUpgradeNeuralParasite.class);
//		actions.put(max++, EcActionUpgradePathogenGlands.class);
//		actions.put(max++, EcActionUpgradeChitinousPlating.class);
//		actions.put(max++, EcActionUpgradeBurrow.class);
//		actions.put(max++, EcActionUpgradePneumatizedCarapace.class);
//		actions.put(max++, EcActionUpgradeVentralSacs.class);
//		actions.put(max++, EcActionUpgradeFlyerAttacks1.class);
//		actions.put(max++, EcActionUpgradeFlyerAttacks2.class);
//		actions.put(max++, EcActionUpgradeFlyerAttacks3.class);
//		actions.put(max++, EcActionUpgradeFlyerArmor1.class);
//		actions.put(max++, EcActionUpgradeFlyerArmor2.class);
//		actions.put(max++, EcActionUpgradeFlyerArmor3.class);
	}

}
