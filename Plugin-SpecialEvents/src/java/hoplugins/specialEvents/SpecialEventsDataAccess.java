package hoplugins.specialEvents;

import hoplugins.SpecialEvents;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import plugins.IMatchKurzInfo;

public class SpecialEventsDataAccess
{

    private static final SpecialEventsDataAccess CURRENT = new SpecialEventsDataAccess();
    private static int saveSeasons = 0;
    private static Vector<IMatchKurzInfo> kurzInfos = null;
    private static Vector<IMatchKurzInfo> aktKurzInfos = null;

    public static SpecialEventsDataAccess getCurrent()
    {
        return CURRENT;
    }

    private SpecialEventsDataAccess()
    {
    }

    /*private void getMatchKurzInfos(int saisons)
    {
        if(saisons != saveSeasons)
        {
            getMatchKurzInfosForSaisons(saisons);
        }
        saveSeasons = saisons;
    }*/

    public Vector<IMatchKurzInfo> getAktMatchKurzInfos(int saisons, boolean friendlies)
    {
        if(saisons != saveSeasons)
        {
            getMatchKurzInfosForSaisons(saisons);
        }
        return filterMatches(friendlies);
    }

    private Vector<IMatchKurzInfo> filterMatches(boolean friendlies)
    {
        if(friendlies)
        {
            aktKurzInfos = kurzInfos;
            return kurzInfos;
        }
        aktKurzInfos = new Vector<IMatchKurzInfo>();
        for(Iterator<IMatchKurzInfo> iter = kurzInfos.iterator(); iter.hasNext();)
        {
            IMatchKurzInfo element = (IMatchKurzInfo)iter.next();
            if(element.getMatchTyp() != 4 
            		&& element.getMatchTyp() != 5 
            		&& element.getMatchTyp() != 8 
            		&& element.getMatchTyp() != 9)
            {
                aktKurzInfos.add(element);
            }
        }

        return aktKurzInfos;
    }

    private void getMatchKurzInfosForSaisons(int saisons)
    {
        Timestamp datumAb = getDatumAb(saisons);
        IMatchKurzInfo modelKurzInfos[] = SpecialEvents.miniModel.getMatchesKurzInfo(SpecialEvents.miniModel.getBasics().getTeamId());
        kurzInfos = new Vector<IMatchKurzInfo>();
        for(int i = 0; i < modelKurzInfos.length; i++)
        {
            if(datumAb != null)
            {
                Timestamp spDate = modelKurzInfos[i].getMatchDateAsTimestamp();
                if(spDate.compareTo(datumAb) < 0)
                {
                    continue;
                }
            }
            if(modelKurzInfos[i].getMatchStatus() == 1)
            {
                kurzInfos.add(modelKurzInfos[i]);
            }
        }

    }

    private Timestamp getDatumAb(int saisonAnz)
    {
        Date date = null;
        if(saisonAnz == 1)
        {
            return null;
        }
        Calendar cal = Calendar.getInstance();
//        Date toDay = cal.getTime();
        int week = SpecialEvents.miniModel.getBasics().getSpieltag();
        int tag = cal.get(7);
        int corrTag = 0;
        if(tag != 7)
        {
            corrTag = tag;
        }
        cal.add(7, -corrTag);
        cal.add(3, -(week - 1));
        if(saisonAnz == 2)
        {
            cal.add(3, -16);
        }
        date = cal.getTime();
        return new Timestamp(date.getTime());
    }

    /*private void showDebug(String s)
    {
        IDebugWindow debugWindow = SpecialEvents.miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
        debugWindow.setVisible(true);
        debugWindow.append(s);
    }*/

}
