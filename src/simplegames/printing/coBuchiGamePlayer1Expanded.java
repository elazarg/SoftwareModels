package simplegames.printing;

import net.sf.javabdd.BDD;
import simplegames.helper.Helper;
import tau.smlab.syntech.gamemodel.GameModel;
import tau.smlab.syntech.gamemodel.PlayerModule;
import tau.smlab.syntech.jtlv.Env;
import tau.smlab.syntech.jtlv.lib.FixPoint;

public class coBuchiGamePlayer1Expanded {

	private static PlayerModule sys, env;

	public static void main(String[] args) {
		GameModel m = Helper.getGameModel("../SatelliteSpec/Telescope1SideCoBuchi.spectra");
		env = m.getEnv();
		sys = m.getSys();

		BDD buchiStates = m.getSys().justiceAt(0);
		BDD win = computeWiningStates(m, buchiStates);
		System.out.println("Buchi states:");
		System.out.println(buchiStates);
		System.out.println("Environment wins on:");
		System.out.println(win);
		System.out.println("System wins on:");
		System.out.println(win.not());
	}

	private static BDD computeWiningStates(GameModel m, BDD buchiStates) {
		FixPoint fZ = new FixPoint(true);
		BDD Z = Env.FALSE(), X = null;

		X = Env.TRUE();
		FixPoint fX = new FixPoint(true);
		while (fX.advance(X)) {
			System.out.println("X=" + X);	
			X = (buchiStates.not().and(pre1(X)));
		}

		while (fZ.advance(Z)) {
			System.out.println("Z=" + Z);
			BDD pre1Z = pre1(Z);
			
			System.out.println("pre1(Z)=" + pre1Z);
			Z = X.or(pre1Z);
		}
		return Z;
	}

	private static BDD pre1(BDD to) {
		return env.controlStates(sys, to);
	}
}
