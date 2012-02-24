// %71932679:hoplugins.toTW%
/*
 * Created on 17-dic-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.teamOfTheWeek;

import ho.core.gui.comp.panel.RasenPanel;

import java.awt.BorderLayout;

import plugins.LineupPanel;



class MyLineupPanel extends RasenPanel {

	private static final long serialVersionUID = 5900818563415056137L;

	private LineupPanel p = null;

    MyLineupPanel(boolean arg0) {
        super();
        setLayout(new BorderLayout());
        p = new LineupPanel(arg0);
        p.setOpaque(false);
        add(p, BorderLayout.CENTER);
    }

    LineupPanel getLineup() {
        return p;
    }
}
