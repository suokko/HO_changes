package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PositionChooser extends JPanel {

	private static final long serialVersionUID = 7378929734827883010L;
	private static final Color COLOR_BG = new Color( 39, 127, 49 );
    private final Color COLOR_POS_DEFAULT = COLOR_BG;
    private final Color COLOR_POS_OCCUPIED = Color.LIGHT_GRAY;
    private final Color COLOR_POS_FOCUSED = Color.YELLOW;
    private int positionChoosen = 0;

    public PositionChooser() {
        initComponents();
    }

    private void setPositionChoosen( int pos ) {
        this.positionChoosen = pos;
        System.out.println( "####-setPositionChoosen " + this.positionChoosen );
    }

    private void initComponents() {
        setLayout( new GridBagLayout() );
        setBorder( BorderFactory.createLineBorder( Color.WHITE ) );
        setBackground( COLOR_BG );

        MouseListener myMouseListener = new MyMouseListener();

        int space = 4;
        int position = 1;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( space, space, 0, 0 );
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        PositionPanel pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );

        gbc.gridx = 0;
        gbc.gridy = 1;
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        gbc.gridx = GridBagConstraints.RELATIVE;
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        gbc.insets = new Insets( space, space, 0, space );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );

        gbc.gridy = 2;
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        gbc.insets = new Insets( space, space, 0, space );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );

        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.insets = new Insets( space, space, space, 0 );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        gbc.gridx = GridBagConstraints.RELATIVE;
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
        pos = new PositionPanel( position++ );
        pos.addMouseListener( myMouseListener );
        add( pos, gbc );
    }

    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable() {

            public void run() {
                JDialog dlg = new JDialog();
                dlg.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
                dlg.getContentPane().setLayout( new BorderLayout() );
                dlg.getContentPane().add( new PositionChooser(), BorderLayout.CENTER );
                dlg.pack();
                dlg.setVisible( true );
            }
        } );
    }

    private class PositionPanel extends JPanel {

		private static final long serialVersionUID = 6025107478898829134L;
		private boolean occupied;
        private int position;

        public PositionPanel( int position ) {
            this( position, false );
        }

        public PositionPanel( int position, boolean occupied ) {
            this.position = position;
            this.occupied = occupied;
            initComponents();
        }

        public int getPosition() {
            return this.position;
        }

        public boolean isOccupied() {
            return this.occupied;
        }

        private void initComponents() {
            setBorder( BorderFactory.createLineBorder( Color.WHITE ) );
            Dimension size = new Dimension( 14, 10 );
            setMinimumSize( size );
            setMaximumSize( size );
            setPreferredSize( size );
            if ( !this.occupied ) {
                setBackground( COLOR_POS_DEFAULT );
            } else {
                setBackground( COLOR_POS_OCCUPIED );
            }
        }
    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseEntered( MouseEvent e ) {
            setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        }

        @Override
        public void mouseExited( MouseEvent e ) {
            setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
        }

        @Override
        public void mouseClicked( MouseEvent e ) {
            for ( int i = 0; i < PositionChooser.this.getComponentCount(); i++ ) {
                Component c = PositionChooser.this.getComponent( i );
                if ( c instanceof PositionPanel ) {

                    if ( c.getBackground() == COLOR_POS_FOCUSED ) {
                        if ( ( ( PositionPanel ) c ).isOccupied() ) {
                            c.setBackground( COLOR_POS_OCCUPIED );
                        } else {
                            c.setBackground( COLOR_POS_DEFAULT );
                        }
                    }
                }
            }

            PositionPanel posPanel = ( PositionPanel ) e.getSource();
            posPanel.setBackground( COLOR_POS_FOCUSED );
            setPositionChoosen( posPanel.getPosition() );
        }
    }
}

