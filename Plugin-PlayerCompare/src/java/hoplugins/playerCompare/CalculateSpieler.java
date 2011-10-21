/*
 * Created on 13.07.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.playerCompare;

//import gui.UserParameter;
import java.sql.*;

//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.SwingConstants;

import plugins.*;

/**
 * @author KickMuck
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalculateSpieler
{
	private IHOMiniModel m_iHoMiniModel;
	private int[] oldValues;
	private int[] newValues;
	private ISpieler tmpSpieler;
	private ResultSet m_rsPlayer;
	
	public CalculateSpieler(IHOMiniModel miniModel)
	{
		m_iHoMiniModel = miniModel;
	}

	public CalcPlayer setPlayer(ISpieler spieler, boolean recalculate)
	{
		tmpSpieler = spieler;
		CalcPlayer tmpCalcPlayer = new CalcPlayer();
		oldValues = new int[10];
		newValues = new int[10];
		
		//PlayerCompare.appendText("Wert newValues Nr.1: "+newValues[0]);
		try
		{
			m_rsPlayer=m_iHoMiniModel.getAdapter().executeQuery("select * from playerCompare_CalcPlayer where id="+tmpSpieler.getSpielerID());
			m_rsPlayer.first();
		}
		catch(Exception e)
		{
			m_iHoMiniModel.getAdapter().executeUpdate("insert into playerCompare_CalcPlayer (id) values ("+tmpSpieler.getSpielerID()+")");
		}
		
		int[] newStaerke;
		int[] changedStaerke;
		
		oldValues[0]=tmpSpieler.getErfahrung();
		oldValues[1]=tmpSpieler.getForm();
		oldValues[2]=tmpSpieler.getKondition();
		oldValues[3]=tmpSpieler.getTorwart();
		oldValues[4]=tmpSpieler.getVerteidigung();
		oldValues[5]=tmpSpieler.getSpielaufbau();
		oldValues[6]=tmpSpieler.getPasspiel();
		oldValues[7]=tmpSpieler.getFluegelspiel();
		oldValues[8]=tmpSpieler.getTorschuss();
		oldValues[9]=tmpSpieler.getStandards();
		
		
		
		if(recalculate == true)
		{
			newStaerke = hoplugins.PlayerCompare.getNewStaerke();
			
//			*** 13.08.04 ***
			changedStaerke = hoplugins.PlayerCompare.getChangeStaerkeBy();
			for(int i = 0; i < 10; i++)
			{
				if(newStaerke[i] == 0 && changedStaerke[i] != 0)
				{
					newValues[i] = oldValues[i] + changedStaerke[i];
					if(i == 1 && newValues[i] > 8)
					{
						newValues[i] = 8;
					}
					else if(i == 2 && newValues[i] > 9)
					{
						newValues[i] = 9;
					}
					else if(newValues[i] > 20)
					{
						newValues[i] = 20;
					}
				}
				else if(newStaerke[i] == 0 && changedStaerke[i] == 0)
				{
					newValues[i] = oldValues[i];
				}
				else if(newStaerke[i] != 0 && changedStaerke[i] == 0)
				{
					newValues[i] = newStaerke[i];
				}
			}
//			*** 13.08.04 ***
		}
		else
		{
			newValues = oldValues;
		}
		//***** Set the Basics of each player *****
		tmpCalcPlayer.setName(tmpSpieler.getName());
		tmpCalcPlayer.setAlter(tmpSpieler.getAlter());
		tmpCalcPlayer.setNation(tmpSpieler.getNationalitaet());
		tmpCalcPlayer.setGruppe(tmpSpieler.getTeamInfoSmilie());
		tmpCalcPlayer.setSpezialitaet(tmpSpieler.getSpezialitaet());
		tmpCalcPlayer.setGehalt(tmpSpieler.getGehalt());
		tmpCalcPlayer.setTSI(tmpSpieler.getTSI());
		tmpCalcPlayer.setID(tmpSpieler.getSpielerID());
		tmpCalcPlayer.setFuehrung(tmpSpieler.getFuehrung());
		
		//***** Set old trainable skills ***** 
		tmpCalcPlayer.setOldErfahrung(tmpSpieler.getErfahrung());
		tmpCalcPlayer.setOldForm(tmpSpieler.getForm());
		tmpCalcPlayer.setOldKondi(tmpSpieler.getKondition());
		tmpCalcPlayer.setOldTorwart(tmpSpieler.getTorwart());
		tmpCalcPlayer.setOldVerteidigung(tmpSpieler.getVerteidigung());
		tmpCalcPlayer.setOldSpielaufbau(tmpSpieler.getSpielaufbau());
		tmpCalcPlayer.setOldPasspiel(tmpSpieler.getPasspiel());
		tmpCalcPlayer.setOldFluegel(tmpSpieler.getFluegelspiel());
		tmpCalcPlayer.setOldTorschuss(tmpSpieler.getTorschuss());
		tmpCalcPlayer.setOldStandards(tmpSpieler.getStandards());
		
		//***** Set old strength for positions *****
		tmpCalcPlayer.setOldTW(tmpSpieler.calcPosValue(ISpielerPosition.KEEPER,true));
		tmpCalcPlayer.setOldAV(tmpSpieler.calcPosValue(ISpielerPosition.BACK,true));
		tmpCalcPlayer.setOldAVdef(tmpSpieler.calcPosValue(ISpielerPosition.BACK_DEF,true));
		tmpCalcPlayer.setOldAVoff(tmpSpieler.calcPosValue(ISpielerPosition.BACK_OFF,true));
		tmpCalcPlayer.setOldAVinnen(tmpSpieler.calcPosValue(ISpielerPosition.BACK_TOMID,true));
		tmpCalcPlayer.setOldIV(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER,true));
		tmpCalcPlayer.setOldIVaussen(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_TOWING,true));
		tmpCalcPlayer.setOldIVoff(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_OFF,true));
		tmpCalcPlayer.setOldMI(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER,true));
		tmpCalcPlayer.setOldMIaussen(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_TOWING,true));
		tmpCalcPlayer.setOldMIdef(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_DEF,true));
		tmpCalcPlayer.setOldMIoff(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_OFF,true));
		tmpCalcPlayer.setOldFL(tmpSpieler.calcPosValue(ISpielerPosition.WINGER,true));
		tmpCalcPlayer.setOldFLdef(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_DEF,true));
		tmpCalcPlayer.setOldFLinnen(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_TOMID,true));
		tmpCalcPlayer.setOldFLoff(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_OFF,true));
		tmpCalcPlayer.setOldST(tmpSpieler.calcPosValue(ISpielerPosition.FORWARD,true));
		tmpCalcPlayer.setOldSTdef(tmpSpieler.calcPosValue(ISpielerPosition.FORWARD_DEF,true));
		tmpCalcPlayer.setOldBestPosition(tmpSpieler.getIdealPosition());
		tmpCalcPlayer.setOldBestPositionStaerke(tmpSpieler.getIdealPosStaerke(true));
		
		
		changeValues(newValues);
		
		//***** Set new trainable skills *****
		tmpCalcPlayer.setErfahrung(tmpSpieler.getErfahrung());
		tmpCalcPlayer.setForm(tmpSpieler.getForm());
		tmpCalcPlayer.setKondi(tmpSpieler.getKondition());
		tmpCalcPlayer.setTorwart(tmpSpieler.getTorwart());
		tmpCalcPlayer.setVerteidigung(tmpSpieler.getVerteidigung());
		tmpCalcPlayer.setSpielaufbau(tmpSpieler.getSpielaufbau());
		tmpCalcPlayer.setPasspiel(tmpSpieler.getPasspiel());
		tmpCalcPlayer.setFluegel(tmpSpieler.getFluegelspiel());
		tmpCalcPlayer.setTorschuss(tmpSpieler.getTorschuss());
		tmpCalcPlayer.setStandards(tmpSpieler.getStandards());
		
		//***** Set new strength for positions *****
		tmpCalcPlayer.setTW(tmpSpieler.calcPosValue(ISpielerPosition.KEEPER,true));
		tmpCalcPlayer.setAV(tmpSpieler.calcPosValue(ISpielerPosition.BACK,true));
		tmpCalcPlayer.setAVdef(tmpSpieler.calcPosValue(ISpielerPosition.BACK_DEF,true));
		tmpCalcPlayer.setAVoff(tmpSpieler.calcPosValue(ISpielerPosition.BACK_OFF,true));
		tmpCalcPlayer.setAVinnen(tmpSpieler.calcPosValue(ISpielerPosition.BACK_TOMID,true));
		tmpCalcPlayer.setIV(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER,true));
		tmpCalcPlayer.setIVaussen(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_TOWING,true));
		tmpCalcPlayer.setIVoff(tmpSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_OFF,true));
		tmpCalcPlayer.setMI(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER,true));
		tmpCalcPlayer.setMIaussen(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_TOWING,true));
		tmpCalcPlayer.setMIdef(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_DEF,true));
		tmpCalcPlayer.setMIoff(tmpSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_OFF,true));
		tmpCalcPlayer.setFL(tmpSpieler.calcPosValue(ISpielerPosition.WINGER,true));
		tmpCalcPlayer.setFLdef(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_DEF,true));
		tmpCalcPlayer.setFLinnen(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_TOMID,true));
		tmpCalcPlayer.setFLoff(tmpSpieler.calcPosValue(ISpielerPosition.WINGER_OFF,true));
		tmpCalcPlayer.setST(tmpSpieler.calcPosValue(ISpielerPosition.FORWARD,true));
		tmpCalcPlayer.setSTdef(tmpSpieler.calcPosValue(ISpielerPosition.FORWARD_DEF,true));
		tmpCalcPlayer.setBestPosition(tmpSpieler.getIdealPosition());
		tmpCalcPlayer.setBestPositionStaerke(tmpSpieler.getIdealPosStaerke(true));
		
		changeValues(oldValues);
		
		return tmpCalcPlayer;
	}
	
	public void changeValues(int [] value )
	{
		for(int i = 0; i < 10; i++)
		{
			switch(i)
			{
				case 0:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setErfahrung(value[i]);
					}
					break;
				}
				case 1:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setForm(value[i]);
					}
					break;
				}
				case 2:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setKondition(value[i]);
					}
					break;
				}
				case 3:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setTorwart(value[i]);
					}
					break;
				}
				case 4:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setVerteidigung(value[i]);
					}
					break;
				}
				case 5:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setSpielaufbau(value[i]);
					}
					break;
				}
				case 6:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setPasspiel(value[i]);
					}
					break;
				}
				case 7:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setFluegelspiel(value[i]);
					}
					break;
				}
				case 8:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setTorschuss(value[i]);
					}
					break;
				}
				case 9:
				{
					if(value[i] != 0)
					{
						tmpSpieler.setStandards(value[i]);
					}
					break;
				}
				
			}
		}
	}
}
