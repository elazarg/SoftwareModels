package simplegames;

import static simplegames.helper.Helper.*;
import simplegames.helper.Game;

import net.sf.javabdd.BDD;
import tau.smlab.syntech.jtlv.Env;

public class SafetyGame extends Game {
	public BDD computeWiningStates() {
		BDD safeStates = Env.TRUE(); // Unneeded here; We can do ν(this::pre0) 
		return ν(X -> safeStates.and(pre0(X)));
	}

	public static void main(String[] args) {
		SafetyGame game = new SafetyGame("games/Oven.spectra");
		BDD win = game.computeWiningStates();		
		game.printResults(win);
	}

	public SafetyGame(String modelName) {
		super(modelName);
	}
}
