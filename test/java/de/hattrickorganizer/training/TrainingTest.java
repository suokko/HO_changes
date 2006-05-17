package de.hattrickorganizer.training;

import plugins.ITrainingWeek;
import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.logik.TrainingsWeekManager;
import de.hattrickorganizer.model.HOVerwaltung;

/*
 * Created on 5-lug-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TrainingTest extends HOSetup {

	public static void main(String[] args) {
		//TrainingsManager tm = (TrainingsManager) model.getTrainingsManager();
		testSubskillRecalc();
//		HOModel model2 =HOVerwaltung.instance().loadModel(102);			
//		ITrainingPerPlayer trainingPerPlayer2 = tm.calculateDataForPlayer( model.getSpieler(35015095), logik.TrainingsManager.instance().getTrainingsVector(), model2.getBasics().getDatum() );
//		System.out.println(trainingPerPlayer2 + " " + trainingPerPlayer2.getSpieler().getVerteidigung());		
//		HOModel model1 =HOVerwaltung.instance().loadModel(111);			
//		ITrainingPerPlayer trainingPerPlayer = tm.calculateDataForPlayer( model.getSpieler(35015095), logik.TrainingsManager.instance().getTrainingsVector(), model1.getBasics().getDatum() );
//		System.out.println(trainingPerPlayer + " " + trainingPerPlayer.getSpieler().getVerteidigung());				
			
	}

	private static void testTrainingWeekCalc() {
		TrainingsWeekManager twm = TrainingsWeekManager.instance();
		for(int i = 0; i < 200; i++ ) {
			ITrainingWeek itw = twm.getTrainingWeek(i);
			System.out.println(i + " " + itw);
		}
	}

//	private static void testOldCalculation(IHOMiniModel model) {
//		for (Iterator iter = model.getTrainingsManager().calculateData(model.getTrainingsManager().getTrainingsVector(),null).iterator(); iter.hasNext();) {
//			ITrainingPerPlayer tpp = (ITrainingPerPlayer) iter.next();	
//			System.out.println(tpp.getSpieler().getName() + " " + tpp);
//		}
//	}

	private static void testSubskillRecalc() {
		HOVerwaltung.instance().recalcSubskills(false,null);
	}
}
