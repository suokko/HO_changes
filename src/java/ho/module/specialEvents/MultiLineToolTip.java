package ho.module.specialEvents;

import javax.swing.JToolTip;

public class MultiLineToolTip extends JToolTip
{

	private static final long serialVersionUID = -7517709600215738835L;

	public MultiLineToolTip()
    {
        setUI(new MultiLineToolTipUI());
    }
}
