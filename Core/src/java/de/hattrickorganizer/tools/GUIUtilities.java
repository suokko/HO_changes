package de.hattrickorganizer.tools;

import java.awt.Component;
import java.awt.Dimension;

public class GUIUtilities {

	private GUIUtilities() {
	}
	
    /**
     * Equalizes the sizes of the given components. All components will have the height of the highest and the
     * width of the widest component. The sizes will be set with {@link Component#setMinimumSize(java.awt.Dimension) }
     * and {@link Component#setPreferredSize(java.awt.Dimension) }.
     * 
     * @param components the components to equalize in size.
     */
    public static void equalizeComponentSizes( Component... components ) {
        Dimension size = getMaximumPreferredSize( components );
        for ( Component component : components ) {
            component.setPreferredSize( size );
            component.setMinimumSize( size );
        }
    }

    /**
     * Gets the maximum preferred size from a group of components. The maximum preferred size is estimated the following way:
     * the maximum preferred height and the maximum preferred width is determined (over all components) an {@link Dimension}
     * object is created with this height and width.
     * 
     * @param components the components to get the maximum preferred width and maximum preferred height from.
     * @return the maximum preferred width and maximum preferred height as a dimension object.
     */
    public static Dimension getMaximumPreferredSize( Component... components ) {
        int maxWidth = 0;
        int maxHeight = 0;

        for ( Component component : components ) {
            Dimension preferredSize = component.getPreferredSize();
            if ( preferredSize.getWidth() > maxWidth ) {
                maxWidth = ( int ) preferredSize.getWidth();
            }
            if ( preferredSize.getHeight() > maxHeight ) {
                maxHeight = ( int ) preferredSize.getHeight();
            }
        }
        return new Dimension( maxWidth, maxHeight );
    }

}
