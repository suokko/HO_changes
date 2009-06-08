// %3228255379:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Zeigt den Spielstand an
 */
public class ChatMessagePanel extends ImagePanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -2885090836223147286L;
	private static String HTMLKOPF = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Frameset//EN\"\"http://www.w3.org/TR/REC-html40/frameset.dtd\"><html><head><title></title></head><body>";
    private static String HTMLFUSS = "</body></html>";

    //~ Instance fields ----------------------------------------------------------------------------

    private JEditorPane m_jepTextModusEditorPane;
    private JScrollPane m_jscTextModusScrollPane;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ChatMessagePanel object.
     */
    public ChatMessagePanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param trainer TODO Missing Method Parameter Documentation
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void append(String trainer, String text) {
        try {
            text = "<br>" + trainer + " : " + text + "</br>";

            final HTMLEditorKit kit = (HTMLEditorKit) m_jepTextModusEditorPane.getEditorKit();
            m_jepTextModusEditorPane.setCaretPosition(m_jepTextModusEditorPane.getDocument()
                                                                              .getLength());
            kit.insertHTML(((HTMLDocument) m_jepTextModusEditorPane.getDocument()),
                           ((HTMLDocument) m_jepTextModusEditorPane.getDocument()).getLength(),
                           text, 0, 0, HTML.Tag.BR);
            m_jepTextModusEditorPane.setCaretPosition(m_jepTextModusEditorPane.getDocument()
                                                                              .getLength());

        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"ChatMessagePanel.append : " + e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        m_jepTextModusEditorPane = new JEditorPane("text/html", HTMLKOPF + HTMLFUSS);
        m_jepTextModusEditorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        m_jepTextModusEditorPane.setEditable(false);
        m_jepTextModusEditorPane.setBackground(new Color(240, 240, 230));

        //m_jepTextModusEditorPane.addHyperlinkListener(this);
        m_jscTextModusScrollPane = new JScrollPane(m_jepTextModusEditorPane);
        m_jscTextModusScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(m_jscTextModusScrollPane, BorderLayout.CENTER);
    }
}
