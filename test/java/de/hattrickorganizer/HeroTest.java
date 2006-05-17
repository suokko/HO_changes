// %1912333325:de.hattrickorganizer.credits%
package de.hattrickorganizer;

import java.awt.Graphics;

import de.hattrickorganizer.credits.DynamischesObjekt;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class HeroTest implements DynamischesObjekt {
    //~ Instance fields ----------------------------------------------------------------------------

    private int beinframe;
    private int beinstellungszeit = 3000;
    private int hochzaehler = 1;
    private int kopfframe;
    private int vergangeneZeit;
    private int vergangeneZeit2;
    private int x;
    private int y;
    private int zeitProFrame = 150;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HeroTest object.
     *
     * @param x TODO Missing Constructuor Parameter Documentation
     * @param y TODO Missing Constructuor Parameter Documentation
     */
    public HeroTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     */
    public void setTime(long time) {
        //ignore
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public long getTime() {
        //ignore
        return 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     * @param gesamtZeit TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean action(int time, long gesamtZeit) {
        vergangeneZeit += time;
        vergangeneZeit2 += time;

        //Andere Beinstellung
        if (vergangeneZeit2 > beinstellungszeit) {
            vergangeneZeit2 = 0;
            beinframe++;

            if (beinframe > 19) {
                beinframe = 0;
            }
        }

        //Neue Bewegung
        if (vergangeneZeit > zeitProFrame) {
            vergangeneZeit = 0;

            //vergangeneZeit -= zeitProFrame;
            kopfframe += hochzaehler;

            if ((kopfframe >= 7) && (hochzaehler == 1)) {
                hochzaehler = -1;
            } else if ((kopfframe <= 0) && (hochzaehler == -1)) {
                hochzaehler = 1;
            }
        }

        return true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param xx TODO Missing Method Parameter Documentation
     * @param yy TODO Missing Method Parameter Documentation
     */
    public void render(Graphics g, int xx, int yy) {
        /*
           Graphics2D g2d = (Graphics2D) g;
        
           Image kopfimage      = null;
                   Image koerperimage   = null;
                   Image beinimage      = null;
                   Image waffenimage    = null;
        
                   int id = beinframe + kopfframe - 3;
                   if ( id > 19 ) id -= 20;
                   if ( id < 0  ) id += 20;
                   kopfimage = GraphicLibrary.hero_kopf[ id ];
                   id = Math.round( ( ( beinframe * 1.5f ) + ( beinframe + kopfframe - 3 ) ) / 2.5f );
                   if ( id > 19 ) id -= 20;
                   if ( id < 0  ) id += 20;
                   koerperimage = GraphicLibrary.hero_koerper1[ id ];
                   waffenimage = GraphicLibrary.gatling[ id ];
        
                   beinimage = GraphicLibrary.hero_beine1[ beinframe ];
        
        
           //g2d.setComposite(alpha);
           g2d.drawImage( beinimage, x - xx + 7, y - yy + 7, null );
                   g2d.drawImage( koerperimage, x - xx , y - yy, null );
                   g2d.drawImage( waffenimage, x - xx - 37 , y - yy - 37, null );
                   g2d.drawImage( kopfimage, x - xx + 26, y - yy + 26, null );
         */
    }
}
