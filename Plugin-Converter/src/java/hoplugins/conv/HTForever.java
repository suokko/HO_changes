// %1117664848906:hoplugins.conv%
/*
 * Created on 16.05.2005
 *
 */
package hoplugins.conv;

import org.w3c.dom.Document;

import java.io.File;

import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Dietz
 */
public class HTForever extends HrfMaker {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HTForever object.
     */
    protected HTForever() {
        type = "HTF";
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param selectedFiles TODO Missing Method Parameter Documentation
     * @param targetDir TODO Missing Method Parameter Documentation
     */
    protected void start(File[] selectedFiles, File targetDir) {
        try {
            String[] filter = null;
            File[][] xmls = null;

            filter = getName(selectedFiles);
            xmls = getFiles(filter, selectedFiles);

            for (int i = 0; i < xmls.length; i++) {
                if ((xmls[i][0] == null)
                    || (xmls[i][1] == null)
                    || (xmls[i][2] == null)
                    || (xmls[i][3] == null)
                    || (xmls[i][4] == null)
                    || (xmls[i][5] == null)
                    || (xmls[i][6] == null)) {
                    handleException(null,
                                    RSC.PROP_FILE_NOT_FOUND + " :" + filter[i]
                                    + RSC.HTFOREVER_EXTENSION[i]);
                    return;
                }
            } // for xml

            if (targetDir != null) {
                Document doc = null;

                for (int i = 0; i < xmls.length; i++) {
                    clearArrays();

                    addBasics();
                    doc = getDocument(xmls[i][1]);
                    analyzeClub(doc.getDocumentElement().getChildNodes());

                    doc = getDocument(xmls[i][0]);
                    analyzeArena(doc.getDocumentElement().getChildNodes());

                    doc = getDocument(xmls[i][5]);
                    analyzeTraining(doc.getDocumentElement().getChildNodes());

                    doc = getDocument(xmls[i][2]);
                    analyzeEconomy(doc.getDocumentElement().getChildNodes());

                    doc = getDocument(xmls[i][4]);
                    analyzeTeamDetails(doc.getDocumentElement().getChildNodes());

                    doc = getDocument(xmls[i][3]);
                    initPlayersArray(doc.getDocumentElement().getChildNodes());

                    //
                    doc = getDocument(xmls[i][6]);
                    analyzeWorldDetail(doc.getDocumentElement().getChildNodes());
                    filter[i] = filter[i].replaceAll("-", "");
                    writeHrf(filter[i], targetDir);
                }
            }

            //           } // if ok-Button
            JOptionPane.showMessageDialog(null, RSC.getProperty("finished"));
        } catch (Exception e1) {
            handleException(e1, RSC.PROP_DEFAULT_ERROR_MESSAGE);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static String[] getName(File[] f) {
        String[] names = new String[f.length];

        for (int i = 0; i < names.length; i++) {
            names[i] = f[i].getName().substring(0, 10);
        }

        return names;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param filter TODO Missing Method Parameter Documentation
     * @param selectedFiles TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private File[][] getFiles(String[] filter, File[] selectedFiles) {
        File[][] xmls = new File[selectedFiles.length][9];
        File[] tmp = new File[0];

        File dir = selectedFiles[0].getParentFile();
        tmp = dir.listFiles();

        for (int i = 0; i < selectedFiles.length; i++) {
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[0])) {
                    xmls[i][0] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[1])) {
                    xmls[i][1] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[2])) {
                    xmls[i][2] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[3])) {
                    xmls[i][3] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[4])) {
                    xmls[i][4] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[5])) {
                    xmls[i][5] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[6])) {
                    xmls[i][6] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[7])) {
                    xmls[i][7] = tmp[j];
                }

                if (tmp[j].getName().equals(filter[i] + RSC.HTFOREVER_EXTENSION[8])) {
                    xmls[i][8] = tmp[j];
                }
            }
        }

        return xmls;
    }
}
