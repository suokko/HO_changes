// %2208660911:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;
import ho.core.util.HOLogger;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.StyleSheet;



/**
 * Zeigt den Spielstand an
 */
class MatchberichtEditorPanel extends ImagePanel {
	
	private static final long serialVersionUID = -6744361289975460222L;
	
    //~ Instance fields ----------------------------------------------------------------------------

    private JEditorPane m_jepTextModusEditorPane;
    private JScrollPane m_jscTextModusScrollPane;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchberichtEditorPanel object.
     */
    MatchberichtEditorPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        try {
            if ((text == null) || (text.trim().length() == 0)) {
                text = " --- ";
            }

            m_jepTextModusEditorPane.setText(text);
            m_jepTextModusEditorPane.setCaretPosition(0);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"MatchberichtPanel.append : " + e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_jepTextModusEditorPane.setText(" --- ");
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        final StyleSheet style = new StyleSheet();
        style.addRule("a { color:#006400; font-weight:bold; }");
        style.addRule("BODY, P {font: " + ho.core.model.UserParameter.instance().schriftGroesse
                      + "pt sans-serif; color:#000000}");
        String hexColor = Integer.toHexString(ThemeManager.getColor(HOColorName.PANEL_BG).getRGB());
        style.addRule("body { background: "+hexColor.substring(2)+" }");

        final javax.swing.text.html.HTMLEditorKit kit = new javax.swing.text.html.HTMLEditorKit();
        kit.setStyleSheet(style);

        m_jepTextModusEditorPane = new JEditorPane("text/html", " ");
        m_jepTextModusEditorPane.setEditorKit(kit);

        // TODO
        m_jepTextModusEditorPane.setBackground (ThemeManager.getColor(HOColorName.PANEL_BG) );
        //m_jepTextModusEditorPane.addHyperlinkListener(this);
        m_jscTextModusScrollPane = new JScrollPane(m_jepTextModusEditorPane);
        m_jscTextModusScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        m_jscTextModusScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(m_jscTextModusScrollPane, BorderLayout.CENTER);
    }
}
