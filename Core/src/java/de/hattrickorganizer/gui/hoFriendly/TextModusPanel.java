// %3206592644:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Zeigt den Spielstand an
 */
public class TextModusPanel extends ImagePanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7932485674476438111L;
	private JEditorPane m_jepTextModusEditorPane;
    private JScrollPane m_jscTextModusScrollPane;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TextModusPanel object.
     */
    public TextModusPanel() {
        setPreferredSize(new Dimension(300, 200));

        initComponents();

        //setPreferredSize ( new Dimension( 600, 500 ) );        
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void append(String text) {
        try {
            text = "<br>" + text + "</br>";

            final HTMLEditorKit kit = (HTMLEditorKit) m_jepTextModusEditorPane.getEditorKit();
            m_jepTextModusEditorPane.setCaretPosition(m_jepTextModusEditorPane.getDocument()
                                                                              .getLength());
            kit.insertHTML(((HTMLDocument) m_jepTextModusEditorPane.getDocument()),
                           ((HTMLDocument) m_jepTextModusEditorPane.getDocument()).getLength(),
                           text, 0, 0, HTML.Tag.BR);
            m_jepTextModusEditorPane.setCaretPosition(m_jepTextModusEditorPane.getDocument()
                                                                              .getLength());

            //HOLogger.instance().log(getClass(), "Insert : " + text + " @ " + ( (HTMLDocument)m_jepTextModusEditorPane.getDocument() ).getLength() );
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"TextModusPanel.append : " + e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        final StyleSheet style = new StyleSheet();
        style.addRule("a { color:#000000; font-weight:bold; }");
        style.addRule("BODY, P {font: " + gui.UserParameter.instance().schriftGroesse
                      + "pt sans-serif; color:#000000}");

        final javax.swing.text.html.HTMLEditorKit kit = new javax.swing.text.html.HTMLEditorKit();
        kit.setStyleSheet(style);

        m_jepTextModusEditorPane = new JEditorPane("text/html", "");
        m_jepTextModusEditorPane.setEditorKit(kit);
        m_jepTextModusEditorPane.setEditable(false);
        m_jepTextModusEditorPane.setBackground(new Color(240, 240, 230));

        //m_jepTextModusEditorPane.addHyperlinkListener(this);
        m_jscTextModusScrollPane = new JScrollPane(m_jepTextModusEditorPane);
        m_jscTextModusScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(m_jscTextModusScrollPane, BorderLayout.CENTER);
    }
}
