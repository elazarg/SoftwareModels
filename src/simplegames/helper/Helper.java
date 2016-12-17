package simplegames.helper;

import java.util.function.*;
import net.sf.javabdd.BDD;
import tau.smlab.syntech.bddgenerator.BDDGenerator;
import tau.smlab.syntech.bddgenerator.BDDGenerator.TraceInfo;
import tau.smlab.syntech.gameinput.model.GameInput;
import tau.smlab.syntech.gameinputtrans.TranslationProvider;
import tau.smlab.syntech.gamemodel.GameModel;
import tau.smlab.syntech.gamemodel.PlayerModule;
import tau.smlab.syntech.jtlv.Env;
import tau.smlab.syntech.jtlv.lib.FixPoint;
import tau.smlab.syntech.spectragameinput.SpectraInputProviderNoIDE;

public class Helper {
	/**
	 * read a spectra game model from a spectra file
	 * 
	 * @param fileName
	 *          relative to project root
	 * @return
	 */
	public static GameModel getGameModel(String fileName) {
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		GameInput gi = sip.getGameInput(fileName);
		TranslationProvider.translate(gi);
		return BDDGenerator.generateGameModel(gi, TraceInfo.ALL);
	}

	/**
	 * check whether the system player wins from all initial states if
	 * <code>winSys</code> are its winning states
	 * 
	 * @param winSys
	 * @return
	 */
	public static boolean sysWinAllInitial(PlayerModule sys, PlayerModule env, BDD winSys) {
		BDD sysWin = winSys.and(sys.initial());
		BDD result = env.initial().id().impWith(sysWin.exist(sys.moduleUnprimeVars())).forAll(env.moduleUnprimeVars());
		sysWin.free();

		boolean allIni = result.isOne();
		result.free();
		return allIni;
	}
	
	public static BDD fixedPoint(BDD start, UnaryOperator<BDD> op) {
		BDD Z = start;
		FixPoint fZ = new FixPoint(true);
		while (fZ.advance(Z))
			Z = op.apply(Z);
		return Z;
	}
	
	public static BDD leastFixedPoint(UnaryOperator<BDD> op) {
		return fixedPoint(Env.FALSE(), op);
	}
	
	public static BDD greatestFixedPoint(UnaryOperator<BDD> op) {
		return fixedPoint(Env.TRUE(), op);
	}
	
	public static BDD μ(UnaryOperator<BDD> op) {
		return leastFixedPoint(op);
	}
	
	public static BDD ν(UnaryOperator<BDD> op) {
		return greatestFixedPoint(op);
	}
}
