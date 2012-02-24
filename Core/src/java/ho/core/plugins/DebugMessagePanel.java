// %1930709824:de.hattrickorganizer.gui.pluginWrapper%
package ho.core.plugins;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.util.HOLogger;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;



class DebugMessagePanel extends ImagePanel {
	
	private static final long serialVersionUID = -3522133930680570812L;
	
    //~ Static fields/initializers -----------------------------------------------------------------

	private static String HTMLKOPF = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Frameset//EN\"\"http://www.w3.org/TR/REC-html40/frameset.dtd\"><html><head><title></title></head><body>";
    private static String HTMLFUSS = "</body></html>";

    //~ Instance fields ----------------------------------------------------------------------------

    private JEditorPane m_jepTextModusEditorPane;
    private JScrollPane m_jscTextModusScrollPane;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DebugMessagePanel object.
     */
    DebugMessagePanel() {
        initComponents();
    }

    final void append(String text) {
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

        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"DebugMessagePanel.append : " + e);
        }
    }

    final void clear() {
        m_jepTextModusEditorPane.setText("");
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        m_jepTextModusEditorPane = new JEditorPane("text/html", HTMLKOPF + HTMLFUSS);
        m_jepTextModusEditorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        m_jepTextModusEditorPane.setEditable(false);

        m_jscTextModusScrollPane = new JScrollPane(m_jepTextModusEditorPane);
        m_jscTextModusScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(m_jscTextModusScrollPane, BorderLayout.CENTER);
    }
}
