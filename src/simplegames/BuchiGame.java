package simplegames;

import static simplegames.helper.Helper.*;
import simplegames.helper.Game;

import net.sf.javabdd.BDD;

public class BuchiGame extends Game {
	public BDD computeWiningStates() {
		BDD buchiStates = sys.justiceAt(0);
		BDD to = ν(Z -> pre0(attractor0(Z)).and(buchiStates));
		return attractor0(to);
	}

	public static void main(String[] args) {
		BuchiGame game = new BuchiGame("games/RequestGranter.spectra");
		game.printBuchiStates();
		BDD win = game.computeWiningStates();
		game.printResults(win);
	}

	public void printBuchiStates() {
		System.out.println("Buchi states:");
		for (int i = 0; i < sys.justiceNum(); i++)
			System.out.println(sys.justiceAt(i));
	}
	
	public BuchiGame(String gameModel) {
		super(gameModel);
	}
}
