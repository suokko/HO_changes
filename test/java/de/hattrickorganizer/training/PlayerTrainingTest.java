package de.hattrickorganizer.training;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import plugins.IFuturePlayer;
import plugins.ISpieler;
import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.logik.FutureTrainingManager;
import de.hattrickorganizer.model.FutureTrainingWeek;

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
public class PlayerTrainingTest extends HOSetup {

	public static void main(String[] args) {
		new PlayerTrainingTest();			
	}

	private PlayerTrainingTest() {
		testTraining();
	}
	
	private void testTraining() {
		List trainings = new ArrayList();
//		for(int i = 28; i < 33; i++) {
//			for(int w = 1; w <= 16; w++) {
//				trainings.add(getWeek(i,w,8));							
//			}
//		}				
		ISpieler sp = MODEL.getSpieler(66523343);
		for(int i = 17; i < 40; i++) {
			sp.setAlter(i);
			System.out.println(i + " " + sp.getTrainingLength(ISpieler.SPIELAUFBAU,9,1,7,100));
		}		
//		trainings = MODEL.getFutureTrainingWeeks();
//		FutureTrainingManager ftm = new FutureTrainingManager(sp, trainings,9,1,5);
//		for (Iterator iter = ftm.getFutureSkillups().iterator(); iter.hasNext();) {
//			System.out.println(iter.next());			
//		}
//		IFuturePlayer fp = ftm.previewPlayer(40);
//		System.out.println("Result " + fp.getPlaymaking());						
	}

	private FutureTrainingWeek getWeek(int i, int j, int k) {
		FutureTrainingWeek ftw = new FutureTrainingWeek();
		ftw.setIntensitaet(100);
		ftw.setSeason(i);
		ftw.setWeek(j);
		ftw.setTyp(k);
		return ftw;
	}

}
