package ho.core.gui.comp.tabbedPane;


import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JTabbedPane;


final class TabCloseIcon implements Icon {
	private final Icon mIcon = ThemeManager.getIcon(HOIconName.TABBEDPANE_CLOSE);
	private transient Rectangle mPosition = null;
	private int xOffset = 0;
	
	TabCloseIcon(final JTabbedPane mTabbedPane, int xOffset){
		this.xOffset = xOffset;
		mTabbedPane.addMouseListener( new MouseAdapter()
		{
			@Override 
			public void mouseReleased( MouseEvent e )
			{
				// asking for isConsumed is *very* important, otherwise more than one tab might get closed!
				if ( !e.isConsumed()  &&   mPosition.contains( e.getX(), e.getY() ) )
				{
					final int index = mTabbedPane.getSelectedIndex();
					mTabbedPane.remove( index );
					e.consume();
				}
			}
		});
	}
	
	
	/**
	 * when painting, remember last position painted.
	 * add extra xOffset pixels to allow for Nimbus skin
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y){
		mPosition = new Rectangle( x + xOffset, y, getIconWidth(), getIconHeight() );
		mIcon.paintIcon(c, g, x + xOffset, y );
	}
	
	
	/**
	 * just delegate
	 */
	@Override
	public int getIconWidth(){
		return mIcon.getIconWidth();
	}
	
	/**
	 * just delegate
	 */
	@Override
	public int getIconHeight(){
		return mIcon.getIconHeight();
	}
	
}

