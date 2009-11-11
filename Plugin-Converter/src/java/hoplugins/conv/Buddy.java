/*
 * Created on 20.11.2004
 *
 */
package hoplugins.conv;





import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;



/**
 * @author Thorsten Dietz
 *  01.03.2005
 */
final class Buddy extends HrfMaker{

    boolean isLeague = false;

    private String currentLeague = "0";

    protected Buddy(){
        type = "Buddy";
    }

    protected void start(File[] selectedFiles,File targetDir) {
        
        try {

            String htString = null;
            String[] filter = null;
            File[][] xmls = null;
                filter = getName(selectedFiles);
                
                xmls = getFiles(filter, selectedFiles);
                for (int i = 0; i < xmls.length; i++) {
                    if (xmls[i][0] == null || xmls[i][1] == null
                            || xmls[i][2] == null || xmls[i][3] == null
                            || xmls[i][4] == null || xmls[i][5] == null) {
                        handleException(null, RSC.PROP_FILE_NOT_FOUND + " :"
                                + filter + RSC.BUDDY_EXTENSION[i]);
                        return;
                    }
                } // for xml
               
                if (targetDir != null) {

                    JOptionPane.showMessageDialog(null, RSC
                            .getProperty("download_supplemental_files"));
                    htString = new String(
                            RSC.MINIMODEL
                                    .getDownloadHelper()
                                    .getHattrickXMLFile("/chppxml.axd?file=worlddetails")
                                    .getBytes(), "UTF-8"); //"/Common/worldDetails.asp?outputType=XML&actionType=leagues")
                    Document doc = null;

                    for (int i = 0; i < xmls.length; i++) {

                        clearArrays();

                        addBasics();
                        doc = getDocument(getUTF8String(xmls[i][1]));

                        analyzeClub(doc.getDocumentElement().getChildNodes());
                        doc = getDocument(getUTF8String(xmls[i][0]));

                        analyzeArena(doc.getDocumentElement().getChildNodes());
                        doc = getDocument(getUTF8String(xmls[i][5]));

                        analyzeTraining(doc.getDocumentElement()
                                .getChildNodes());
                        doc = getDocument(getUTF8String(xmls[i][2]));

                        analyzeEconomy(doc.getDocumentElement().getChildNodes());
                        doc = getDocument(getUTF8String(xmls[i][4]));

                        analyzeTeamDetails(doc.getDocumentElement()
                                .getChildNodes());
                        doc = getDocument(getUTF8String(xmls[i][3]));
                        
                       
                        initPlayersArray(doc.getDocumentElement()
                                .getChildNodes());

                        doc = getDocument(htString);
                        analyzeWorldDetails(doc.getDocumentElement()
                                .getChildNodes());

                        writeHrf(filter[i], targetDir);
                    }
                }
 //           } // if ok-Button
            JOptionPane.showMessageDialog(null, RSC.getProperty("finished"));

        } catch (Exception e1) {
            handleException(e1, RSC.PROP_DEFAULT_ERROR_MESSAGE);
        }

    }

    private static File[][] getFiles(String[] filter, File[] selectedFiles) {
        File[][] xmls = new File[selectedFiles.length][6];
        File[] tmp = new File[0];

        File dir = selectedFiles[0].getParentFile();
        tmp = dir.listFiles();
        for (int i = 0; i < selectedFiles.length; i++) {
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[0]))
                    xmls[i][0] = tmp[j];
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[1]))
                    xmls[i][1] = tmp[j];
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[2]))
                    xmls[i][2] = tmp[j];
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[3]))
                    xmls[i][3] = tmp[j];
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[4]))
                    xmls[i][4] = tmp[j];
                if (tmp[j].getName().equals(filter[i] + RSC.BUDDY_EXTENSION[5]))
                    xmls[i][5] = tmp[j];
            }
        }
        return xmls;
    }

    private final String[] getName(File[] f) {
        String[] names = new String[f.length];
        int index = 0;
        for (int i = 0; i < names.length; i++) {
            index = f[i].getName().indexOf(".");
            if (index > 0)
                names[i] = f[i].getName().substring(0, index);
        }
        return names;
    }

    private final void analyzeWorldDetails(NodeList nodelist) {
        for (int i = 0; i < nodelist.getLength(); i++) {
            if (nodelist.item(i).hasChildNodes()) {
                analyzeWorldDetails(nodelist.item(i).getChildNodes());
            } // if Node

            if (nodelist.item(i) instanceof Element) {
                Element element = (Element) nodelist.item(i);
                Text txt = (Text) element.getFirstChild();

                if (txt == null)
                    continue;
                String tagValue = txt.getData();

                if (tagValue == null)
                    continue;

                if (element.getTagName().equals("LeagueID")) {
                    if (basicsValue[13][1].equals(tagValue)) {
                        currentLeague = tagValue;

                    }
                    continue;
                }
                if (element.getTagName().equals("CountryID")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        basicsValue[12][1] = tagValue;
                    }
                    continue;
                }
                if (element.getTagName().equals("Season")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        basicsValue[4][1] = tagValue;
                    }
                    continue;
                }
                if (element.getTagName().equals("MatchRound")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        basicsValue[5][1] = tagValue;
                    }
                    continue;
                }
                if (element.getTagName().equals("CurrencyName")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        xtraValue[4][1] = tagValue;

                    }
                    continue;
                }
                if (element.getTagName().equals("CurrencyRate")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        xtraValue[5][1] = tagValue.replaceAll(",", ".");
                    }
                    continue;
                }
                if (element.getTagName().equals("TrainingDate")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        // currentLeague = "0";
                        boolean otherDay = true;
                        boolean first = true;
                        Date nextTrainingsDate = createDate(tagValue);
                        Date hrfNextTraining = createDate(basicsValue[3][1]);

                        while (otherDay) {
                            if (!first
                                    && hrfNextTraining.getDay() == nextTrainingsDate
                                            .getDay())
                                otherDay = false;
                            else
                                hrfNextTraining.setDate(hrfNextTraining
                                        .getDate() + 1);
                            first = false;
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(hrfNextTraining);
                        xtraValue[1][1] = hrfNextTraining.getYear()
                                + "-"
                                + convertNumber((hrfNextTraining.getMonth() + 1))
                                + "-"
                                + convertNumber(calendar
                                        .get(Calendar.DAY_OF_MONTH)) + ""
                                + tagValue.substring(10, 19);
                    }
                    continue;
                }
                if (element.getTagName().equals("EconomyDate")) {
                    
                    if (basicsValue[13][1].equals(currentLeague)) {
                        
                        boolean otherDay = true;
                        boolean first = true;
                        Date nextEconomyDate = createDate(tagValue);
                        Date hrfNextEconomy = createDate(basicsValue[3][1]);

                        while (otherDay) {
                            if (!first
                                    && hrfNextEconomy.getDay() == nextEconomyDate
                                            .getDay())
                                otherDay = false;
                            else
                                hrfNextEconomy
                                        .setDate(hrfNextEconomy.getDate() + 1);
                            first = false;
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(hrfNextEconomy);
                        xtraValue[2][1] = hrfNextEconomy.getYear()
                                + "-"
                                + convertNumber((hrfNextEconomy.getMonth() + 1))
                                + "-"
                                + convertNumber(calendar
                                        .get(Calendar.DAY_OF_MONTH)) + ""
                                + tagValue.substring(10, 19);
                       
                    }
                    continue;
                }
                if (element.getTagName().equals("SeriesMatchDate")) {
                    if (basicsValue[13][1].equals(currentLeague)) {
                        currentLeague = "0";
                        boolean otherDay = true;
                        boolean first = true;
                        Date nextSeriesMatchDate = createDate(tagValue);
                        Date hrfNextMatch = createDate(basicsValue[3][1]);

                        while (otherDay) {
                            if (!first
                                    && hrfNextMatch.getDay() == nextSeriesMatchDate
                                            .getDay())
                                otherDay = false;
                            else
                                hrfNextMatch
                                        .setDate(hrfNextMatch.getDate() + 1);
                            first = false;
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(hrfNextMatch);
                        xtraValue[3][1] = hrfNextMatch.getYear()
                                + "-"
                                + convertNumber((hrfNextMatch.getMonth() + 1))
                                + "-"
                                + convertNumber(calendar
                                        .get(Calendar.DAY_OF_MONTH)) + ""
                                + tagValue.substring(10, 19);
                    }
                    continue;
                }
            }// if Element
            isLeague = false;
        } // for
    }


    private static Date createDate(String input) {
        int day = Integer.parseInt(input.substring(8, 10));
        int month = Integer.parseInt(input.substring(5, 7));
        int year = Integer.parseInt(input.substring(0, 4));
        int hour = Integer.parseInt(input.substring(11, 13));
        int minutes = Integer.parseInt(input.substring(14, 16));
        int seconds = Integer.parseInt(input.substring(17, 19));

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year + 1900);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}

