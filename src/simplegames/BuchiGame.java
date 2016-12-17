package simplegames;

import static simplegames.helper.Helper.*;
import simplegames.helper.Game;

import net.sf.javabdd.BDD;

public class BuchiGame extends Game {
	public BDD computeWiningStates(BDD buchiStates) {
		BDD to = ν(Z -> pre0(attractor0(Z)).and(buchiStates));
		return attractor0(to);
	}

	public static void main(String[] args) {
		BuchiGame game = new BuchiGame("games/RequestGranter.spectra");
		BDD buchiStates = game.sys.justiceAt(0);
		BDD win = game.computeWiningStates(buchiStates);

		System.out.println("Buchi states:");
		System.out.println(buchiStates);
		
		game.printResults(win);
	}

	
	public BuchiGame(String gameModel) {
		super(gameModel);
	}
}
