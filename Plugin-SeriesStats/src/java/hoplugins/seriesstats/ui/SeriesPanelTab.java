// %1117898112781:hoplugins.seriesstats.ui%
/*
 * Created on 2-giu-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoplugins.seriesstats.ui;

import hoplugins.Commons;
import hoplugins.SeriesStats;

import hoplugins.seriesstats.GraphicData;
import hoplugins.seriesstats.GraphicPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SeriesPanelTab extends JPanel implements RefreshablePanel {
	//~ Instance fields ----------------------------------------------------------------------------

	// Division Panel
	private GraphicPanel PanelDivisionGP;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new SeriesPanelTab object.
	 */
	public SeriesPanelTab() {
		// Panel for Serie tabbed panel
		JPanel PanelGeneral = Commons.getModel().getGUI().createImagePanel();
		PanelGeneral.setOpaque(false);

		//LegendePanel
		GridBagLayout gridbaglayout = new GridBagLayout();
		GridBagConstraints gridbagconstraints = new GridBagConstraints();
		PanelGeneral.setLayout(gridbaglayout);

		//LegendePanel
		gridbagconstraints.fill = 1;
		gridbagconstraints.gridx = 0;
		gridbagconstraints.gridy = 0;
		gridbagconstraints.weightx = 0.1;
		gridbagconstraints.weighty = 0.4;
		gridbagconstraints.anchor = GridBagConstraints.PAGE_START;
		gridbagconstraints.insets = new Insets(2, 0, 2, 0);
		PanelGeneral.add(new LegendaPanel(this, false), gridbagconstraints);

		//dummyPanel
		JPanel PanelDummy = Commons.getModel().getGUI().createImagePanel();
		gridbagconstraints.gridx = 0;
		gridbagconstraints.gridy = 1;
		gridbagconstraints.weighty = 1.0D;
		gridbagconstraints.weightx = 0D;
		gridbagconstraints.anchor = 11;
		gridbagconstraints.fill = 1;
		PanelGeneral.add(PanelDummy, gridbagconstraints);

		//GraphicsPanel
		gridbagconstraints.fill = 1;
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 0;
		gridbagconstraints.gridheight = 2;
		gridbagconstraints.weighty = 1.0D;
		gridbagconstraints.weightx = 1.0D;
		gridbagconstraints.anchor = 11;
		PanelGeneral.add(PanelDivisionGraphic(), gridbagconstraints);

		//AvgMaxPanel
		gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
		gridbagconstraints.gridx = 0;
		gridbagconstraints.gridy = 2;
		gridbagconstraints.gridwidth = 2;
		gridbagconstraints.weighty = 0;
		gridbagconstraints.weightx = 0;

		PanelGeneral.add(new BottomPanel(), gridbagconstraints);

		setLayout(new BorderLayout());
		add(PanelGeneral, BorderLayout.CENTER);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 */
	public void refresh() {
		// TODO Implement this
		System.out.println("SeriesPanel Tab Refresh");
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private JPanel PanelDivisionGraphic() {
		JPanel d2 = new JPanel();
		d2.setLayout(new BorderLayout());
		PanelDivisionGP = new GraphicPanel(true);

		try {
			DecimalFormat format = new DecimalFormat("#0.00");

			GraphicData[] ax = new GraphicData[8];

			double[] x = { Math.random()*10d, Math.random()*10d, Math.random()*10d, Math.random()*10d };

			ax[0] = new GraphicData(x, "Bewertung", true, Color.black, format);
			ax[1] = new GraphicData(x, "Mittelfeld", true, Color.black, format);
			ax[2] = new GraphicData(x, "RechteAbwehr", true, Color.black, format);
			ax[3] = new GraphicData(x, "Abwehrzentrum", true, Color.black, format);
			ax[4] = new GraphicData(x, "LinkeAbwehr", false, Color.black, format);
			ax[5] = new GraphicData(x, "RechterAngriff", false, Color.black, format);
			ax[6] = new GraphicData(x, "Angriffszentrum", false, Color.black, format);
			ax[7] = new GraphicData(x, "LinkerAngriff", false, Color.black, format);

			String[] as = { "String1", "String2" };
			PanelDivisionGP.setAllValues(ax, as, format, Commons.getModel().getLanguageString("Spiele") + "", "", true, true);
			
		} catch (Exception e) {
			if (true) {
				SeriesStats.getIDB().append("---ooo---");
				SeriesStats.getIDB().append(e);
			}
		}

		d2.add(PanelDivisionGP);
		return d2;
	}
}
