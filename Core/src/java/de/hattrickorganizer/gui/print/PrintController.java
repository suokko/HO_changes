// %2567997551:de.hattrickorganizer.gui.print%
/*
 * Created on 29.02.2004
 *
 */
package de.hattrickorganizer.gui.print;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Schmidt
 */
public final class PrintController {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static PrintController printController;

    //~ Instance fields ----------------------------------------------------------------------------

    private Book book;
    private PageFormat pf;
    private PrinterJob job;
    private int page = 1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PrintController object.
     */
    private PrintController() {
        initialize();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static PrintController getInstance() {
        if (printController == null) {
            printController = new PrintController();
        }

        return printController;
    }

    /**
     * DOCUMENT ME!
     *
     * @param format
     */
    public void setPf(PageFormat format) {
        pf = format;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public PageFormat getPf() {
        return pf;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param printObject TODO Missing Method Parameter Documentation
     *
     * @throws Exception TODO Missing Method Exception Documentation
     */
    public void add(PrintObject printObject) throws Exception {
        book.append(printObject, pf, page);
        page++;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @throws PrinterException TODO Missing Method Exception Documentation
     */
    public void print() throws PrinterException {
        job.setPageable(book);

        final HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();

        //job.pageDialog(attributes);
        if (job.printDialog(attributes)) {
            final de.hattrickorganizer.gui.login.LoginWaitDialog waitDialog = new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                                                                                 .instance());
            waitDialog.setVisible(true);

            job.print(attributes);

            waitDialog.setVisible(false);
        }

        initialize();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initialize() {
        job = PrinterJob.getPrinterJob();
        job.setJobName("HO! - Printing");
        pf = job.defaultPage();
        pf.setOrientation(PageFormat.LANDSCAPE);
        book = new Book();
        page = 1;
    }
}
