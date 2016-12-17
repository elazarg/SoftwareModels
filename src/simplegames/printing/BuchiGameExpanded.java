package simplegames.printing;

import net.sf.javabdd.BDD;
import simplegames.helper.Helper;
import tau.smlab.syntech.gamemodel.GameModel;
import tau.smlab.syntech.gamemodel.PlayerModule;
import tau.smlab.syntech.jtlv.Env;
import tau.smlab.syntech.jtlv.lib.FixPoint;

public class BuchiGameExpanded {

	private static PlayerModule sys, env;

	public static void main(String[] args) {
		GameModel m = Helper.getGameModel("../SatelliteSpec/Telescope1SideBuchi.spectra");
		env = m.getEnv();
		sys = m.getSys();

		BDD buchiStates = m.getSys().justiceAt(0);
		BDD win = computeWiningStates(m, buchiStates);
		System.out.println("Buchi states:");
		System.out.println(buchiStates);
		System.out.println("System wins on:");
		System.out.println(win);
		System.out.println("Environment wins on:");
		System.out.println(win.not());
	}

	private static BDD computeWiningStates(GameModel m, BDD buchiStates) {
		FixPoint fZ = new FixPoint(true);
		BDD Z = Env.TRUE(), X = null;
		while (fZ.advance(Z)) {
			System.out.println("Z=" + Z);
			X = Env.FALSE();
			FixPoint fX = new FixPoint(true);
			BDD start = buchiStates.and(pre0(Z));
			System.out.println("pre0(Z) & F=" + start);
			while (fX.advance(X)) {
				System.out.println("X=" + X);	
				X = start.or(pre0(X));
			}
			Z = X;
		}
		return Z;
	}

	private static BDD pre0(BDD to) {
		return env.yieldStates(sys, to);
	}
}
