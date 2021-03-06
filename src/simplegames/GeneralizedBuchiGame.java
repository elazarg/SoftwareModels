package simplegames;

import static simplegames.helper.Helper.*;

import java.util.function.UnaryOperator;

import simplegames.helper.Game;

import net.sf.javabdd.BDD;
import tau.smlab.syntech.jtlv.Env;

public class GeneralizedBuchiGame extends Game {
	public BDD computeWiningStates() {
		return ν(Z -> bigAndJustices(Fi -> attractor0(Fi.and(pre0(Z)))));
	}
	
	public static void main(String[] args) {
		GeneralizedBuchiGame game = new GeneralizedBuchiGame("games/TelescopeGeneralizedBuchi.spectra");
		game.printBuchiStates();
		BDD win = game.computeWiningStates();
		game.printResults(win);
	}

	public void printBuchiStates() {
		System.out.println("Buchi states:");
		for (int i = 0; i < sys.justiceNum(); i++)
			System.out.println(sys.justiceAt(i));
	}

	public GeneralizedBuchiGame(String m) {
		super(m);
	}

	private BDD bigAndJustices(UnaryOperator<BDD> op) {
		BDD Z = Env.TRUE(); 
		for (int i = 0; i < sys.justiceNum(); i++)
			Z = Z.and(op.apply(sys.justiceAt(i)));
		return Z;
	}
}
