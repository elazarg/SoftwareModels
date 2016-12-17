package simplegames.helper;

import static simplegames.helper.Helper.getGameModel;
import static simplegames.helper.Helper.greatestFixedPoint;
import static simplegames.helper.Helper.sysWinAllInitial;

import net.sf.javabdd.BDD;
import tau.smlab.syntech.gamemodel.GameModel;
import tau.smlab.syntech.gamemodel.PlayerModule;

public abstract class Game {
	
	public Game(GameModel m) {
		this.env = m.getEnv();
		this.sys = m.getSys();
	}
	public Game(String modelName) {
		this(getGameModel(modelName));
	}

	public final PlayerModule env;
	public final PlayerModule sys;
	
	/**
	 * computes Player 0 (sys) controlled predecessors of states <code>to</code>
	 * 
	 * @param to
	 *            states to force to
	 * @return states where sys can force going to <code>to</code> in one step
	 */
	protected BDD pre0(BDD to) {
		return env.yieldStates(sys, to);
	}

	protected BDD attractor0(BDD to) {
		return greatestFixedPoint(attr -> to.or(pre0(attr)));
	}

	protected void printResults(BDD win) {
		// checking realizability
		if (sysWinAllInitial(sys, env, win)) {
			System.out.println("Game is realizable.");
		} else {
			System.out.println("Game is unrealizable.");
		}
		System.out.println("System wins on:");
		System.out.println(win);
		System.out.println("Environment wins on:");
		System.out.println(win.not());
	}
}
