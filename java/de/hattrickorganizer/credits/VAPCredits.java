// %1314400181:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.hattrickorganizer.gui.utils.MP3PlayerWrapper;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Dies ist eine Test-Klasse für den Java-FullScreen-Mode
 */
public class VAPCredits implements java.awt.event.KeyListener, java.awt.event.MouseListener,
                                   java.awt.event.MouseMotionListener,
                                   java.awt.event.WindowListener, Runnable
{
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static int screenWidth = 800;

    /** TODO Missing Parameter Documentation */
    public static int screenHeight = 600;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public DynamicEffectLayer movieLayer;

    //public StaticEffectLayer    foregroundLayer;

    /** TODO Missing Parameter Documentation */
    public DynamicEffectLayer throwLayer;

    /** TODO Missing Parameter Documentation */
    public StaticEffectLayer backgroundLayer;

    /** TODO Missing Parameter Documentation */
    public Ball[] baelle = new Ball[100];

    /** TODO Missing Parameter Documentation */
    public int anzahlBaelle = 3;

    /** TODO Missing Parameter Documentation */
    JDialog window;
    private JFrame m_clOwner;

    /** TODO Missing Parameter Documentation */
    private boolean done;
    private float frameRate;
    private int frames;
    private int goRight;
    private int goUp = -1;
    private int mausklicksLinks;
    private int mausklicksRechts;
    private long creditlaenge = 48000;
    private long gesamtZeit;

    //Zeitpunkt des letzten gezeichneten Frames
    private long letztesFrame;
    private long startZeit;
    private long vergangeneZeit;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new VAPCredits object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     */
    public VAPCredits(JFrame owner) {
        m_clOwner = owner;

        done = false;

        final Container container = new Container();

        //Grafiken laden
        GraphicLibrary.load(owner);

        backgroundLayer = new StaticEffectLayer(container, false);
        backgroundLayer.addStatischenEffekt(new StatischesObjekt(GraphicLibrary.background, 0, 0));
        movieLayer = new DynamicEffectLayer(container, backgroundLayer);

        //foregroundLayer = new StaticEffectLayer( container, false );
        throwLayer = new DynamicEffectLayer(container, backgroundLayer);

        //Bälle generieren
        for (int i = 0; i < baelle.length; i++) {
            baelle[i] = new Ball(screenWidth, screenHeight);
        }

        window = new JDialog(m_clOwner, "HO - Credits ");

        window.setUndecorated(true);

        window.addKeyListener(this);
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        window.addWindowListener(this);

        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //Keinen Rahmen
        //window.setUndecorated(true);
        //Größe des Fensters unveränderbar
        window.setResizable(false);

        //Ignoriere alle Repaintanweisungen des Systems
        window.setIgnoreRepaint(true);

        //Grösse
        window.setSize(screenWidth, screenHeight);

        //Position
        window.setLocation((window.getToolkit().getScreenSize().width / 2)
                           - (window.getWidth() / 2),
                           (window.getToolkit().getScreenSize().height / 2)
                           - (window.getHeight() / 2));
        window.setVisible(true);

        try {
            new Thread(this).start();

            //renderingLoop();
        } catch (Exception e) {
            //Kein Fehler darf auftreten, sonst Credits beenden!
            //vapEngine.Verwaltung.instance ().writeLog( "VapCredits.<init> : " + e );
        }

        /*
           window.setVisible( false );
           window.dispose();
        
           window = null;
        
           done = false;//!!!
         */
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public final void keyPressed(java.awt.event.KeyEvent event) {
        if (event.getKeyCode() == java.awt.event.KeyEvent.VK_MINUS) {
            if (anzahlBaelle > 1) {
                anzahlBaelle -= 5;
            }
        } else if (event.getKeyCode() == java.awt.event.KeyEvent.VK_PLUS) {
            if (anzahlBaelle < baelle.length) {
                anzahlBaelle += 5;
            }
        } else if (event.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
            this.done = true;
        }
    }

    //unbenutzte Handler
    public void keyReleased(java.awt.event.KeyEvent event) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void keyTyped(java.awt.event.KeyEvent event) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void mouseClicked(java.awt.event.MouseEvent event) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseDragged(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(java.awt.event.MouseEvent event) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void mouseExited(java.awt.event.MouseEvent event) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseMoved(java.awt.event.MouseEvent mouseEvent) {
        //TODO "Mausspur"
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public final void mousePressed(java.awt.event.MouseEvent event) {
        if ((event.getModifiers() & java.awt.event.InputEvent.BUTTON3_MASK) == java.awt.event.InputEvent.BUTTON3_MASK) {
            mausklicksLinks++;
            throwLayer.addFirestorm(event.getX(), event.getY());
        } else {
            mausklicksRechts++;
            throwLayer.addDynamischenEffekt(event.getX(), event.getY());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void mouseReleased(java.awt.event.MouseEvent event) {
    }

    /**
     * Start eines neuen Threads
     */
    public final void run() {
        renderingLoop();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public final void windowClosing(java.awt.event.WindowEvent windowEvent) {
        this.done = true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * Vorbereitung für nächstes Frame
     *
     * @param vergangeneZeit TODO Missing Constructuor Parameter Documentation
     */
    private void action(long vergangeneZeit) {
        backgroundLayer.action(goUp, goRight);

        movieLayer.action((int) vergangeneZeit, gesamtZeit);

        //foregroundLayer.action(goUp, goRight);
        throwLayer.action((int) vergangeneZeit, gesamtZeit);

        for (int i = 0; (i < baelle.length) && (i < anzahlBaelle); i++) {
            baelle[i].action(vergangeneZeit);
        }
    }

    /**
     * Script!!
     */
    private void creditScript() {
        movieLayer.removeAll();
        throwLayer.removeAll();

        //new Color( 60, 60, 255 );
        final Color ueberschriftColor = Color.white;
        final Color ueberschriftColor2 = Color.lightGray;

        //new Color( 60, 60, 255 );//Color.yellow;//new Color( 150, 150, 200 );
        final Color namenColor = Color.white;

        //HO! Bild
        movieLayer.addZeitverzoegertDynamischenEffekt(new VAPSchild(GraphicLibrary.splashscreen,
                                                                    1500));

        //Entwickler
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Entwicklung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 160, 100,
                                                                     6000, 12700, -1));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Entwicklung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 163, 103, 6000,
                                                                     12700, -1));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("und",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 240, 140,
                                                                     6100, 12700, -1));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("und",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 243, 143, 6100,
                                                                     12700, -1));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 130, 180,
                                                                     6200, 12700, -1));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 133, 183, 6200,
                                                                     12700, -1));

        //Thomas Werth
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Projektleitung und Lead-Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 300,
                                                                     7300, 12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Projektleitung und Lead-Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 303, 7300,
                                                                     12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Thomas \"theTom\" Werth",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 330, 7500,
                                                                     12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Thomas \"theTom\" Werth",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 333, 7500,
                                                                     12500, 500));

        //Volker Fischer
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("GUI-Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 420,
                                                                     8700, 12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("GUI-Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 423, 8700,
                                                                     12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Volker \"FoolmooN\" Fischer",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 450, 9000,
                                                                     12500, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Volker \"FoolmooN\" Fischer",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 453, 9000,
                                                                     12500, 500));

        //Co-Entwickler
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Co-Entwickler",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 150, 100,
                                                                     13500, 20200, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Co-Entwickler",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 153, 103, 13500,
                                                                     20200, 500));

        //draghetto
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 180,
                                                                     14500, 20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 183, 14500,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Massimiliano \"Draghetto\" Amato",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 210, 14700,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Massimiliano \"Draghetto\" Amato",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 213, 14700,
                                                                     20000, 500));

        //Jailbird
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung und HO-Friendly Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 280,
                                                                     15500, 20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Programmierung und HO-Friendly Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 283, 15500,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Marco \"Jailbird\" Senn",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 310, 15700,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Marco \"Jailbird\" Senn",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 313, 15700,
                                                                     20000, 500));

        //Thorsten 'Fooczy' Schmidt
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Pluginadmin und Druck-Framework",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 380,
                                                                     16500, 20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Pluginadmin und Druck-Framework",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 383, 16500,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Thorsten \"Fooczy\" Dietz",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 410, 16700,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Thorsten \"Fooczy\" Dietz",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 413, 16700,
                                                                     20000, 500));

        //RagTime
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Bewertungsformeln",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 480,
                                                                     17500, 20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Bewertungsformeln",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 483, 17500,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Ralph \"RAGtime\" Glasstetter",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 510, 17700,
                                                                     20000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Ralph \"RAGtime\" Glasstetter",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 513, 17700,
                                                                     20000, 500));

        //Mitwirkende
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Weitere Mitwirkende",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 150, 100,
                                                                     21000, 26200, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Weitere Mitwirkende",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 153, 103, 21000,
                                                                     26200, 500));

        //Michael Both
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Forenadmin und Fanbetreuung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 180,
                                                                     22000, 26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Forenadmin und Fanbetreuung",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 183, 22000,
                                                                     26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Michael \"qzmann\" Both",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 210, 22200,
                                                                     26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Michael \"qzmann\" Both",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 213, 22200,
                                                                     26000, 500));

        //Nadine Werth
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Lead-Artist",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 270,
                                                                     23000, 26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Lead-Artist",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 273, 23000,
                                                                     26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Nadine Werth",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 300, 23200,
                                                                     26000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Nadine Werth",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 303, 23200,
                                                                     26000, 500));

        //Unterstützung
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Unterstützung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 150, 100,
                                                                     27000, 40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Unterstützung",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 153, 103, 27000,
                                                                     40400, 500));

        //Übersetzung
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("UebersetzungIn"),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 170,
                                                                     28000, 33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("UebersetzungIn"),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 173, 28000,
                                                                     33600, 500));

        //Autor
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Autor"),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 200, 28200,
                                                                     33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Autor"),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 203, 28200,
                                                                     33600, 500));

        //HO!-Site-Server
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("HO!-Site-Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 250,
                                                                     29200, 33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("HO!-Site-Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 253, 29200,
                                                                     33600, 500));

        //Benjamin "Berick" Stössel
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Benjamin \"Berick\" Stössel",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 280, 29400,
                                                                     33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Benjamin \"Berick\" Stössel",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 283, 29400,
                                                                     33600, 500));

        //Webpagedesign
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Webpagedesign",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 330,
                                                                     30400, 33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Webpagedesign",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 333, 30400,
                                                                     33600, 500));

        //Victor "Karyll" Moldoveanu
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Victor \"Karyll\" Moldoveanu",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 360, 30600,
                                                                     33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Victor \"Karyll\" Moldoveanu",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 363, 30600,
                                                                     33600, 500));

        //EPV Assistant
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("EPV Assistant",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 410,
                                                                     31400, 33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("EPV Assistant",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 413, 31400,
                                                                     33600, 500));

        //Andrea "Grigno" Grignaschi
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Andrea \"Grigno\" Grignaschi",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 440, 31600,
                                                                     33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Andrea \"Grigno\" Grignaschi",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 443, 31600,
                                                                     33600, 500));
        /*
        //Roberto "Swan" Pancrazi 
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Roberto \"Swan\" Pancrazi",
													                 new Font("Egal", Font.PLAIN, 30),
													                 namenColor, 150, 360, 31800,
													                 33600, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Roberto \"Swan\" Pancrazi",
														             new Font("Egal", Font.PLAIN, 30),
														             Color.black, 153, 363, 31800,
														             33600, 500));
														             */
        
        //Mirror
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Downloadhosting",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 170,
                                                                     34600, 40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Downloadhosting",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 173, 34600,
                                                                     40400, 500));

        //Dank an alle
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Chiel 'Nevele' Groeneveld",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 200, 34800,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Chiel 'Nevele' Groeneveld",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 203, 34800,
                                                                     40400, 500));

        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Arne 'cycn' Klarenberg",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 240, 35000,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Arne 'cycn' Klarenberg",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 243, 35000,
                                                                     40400, 500));

        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Pamela Kelley 'pkmynk' Buder",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 280, 35200,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Pamela Kelley 'pkmynk' Buder",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 283, 35200,
                                                                     40400, 500));

        //HO! Friendly Server
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("HO! Friendly Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 330,
                                                                     36200, 40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("HO! Friendly Server",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 333, 36200,
                                                                     40400, 500));

        //Jailbird
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Marco 'Jailbird' Senn",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 360, 36400,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Marco 'Jailbird' Senn",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 363, 36400,
                                                                     40400, 500));

        //            //HO! Cup Verwaltung
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "HO! Cup Verwaltung & RatingPrediction", new Font( "Egal", Font.PLAIN, 30 ), ueberschriftColor2, 200, 410 , 36200, 39400, 500 ) );
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "HO! Cup Verwaltung & RatingPrediction", new Font( "Egal", Font.PLAIN, 30 ), Color.black, 203, 413 , 36200, 39400, 500 ) );
        //            //RAGTime
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Ralph 'RAGtime' Glasstetter", new Font( "Egal", Font.PLAIN, 30 ), namenColor, 150, 440 , 36400, 39400, 500 ) );
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Ralph 'RAGtime' Glasstetter", new Font( "Egal", Font.PLAIN, 30 ), Color.black, 153, 443 , 36400, 39400, 500 ) );
        //Foren
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Foren",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     ueberschriftColor2, 200, 450,
                                                                     38200, 40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Foren",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 203, 453, 38200,
                                                                     40400, 500));

        //Dank an alle
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Dank an alle Supporter aus den Foren,",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 480, 38400,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Dank an alle Supporter aus den Foren,",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 483, 38400,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("die Plugin Entwickler und alle anderen Helfer",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 510, 38400,
                                                                     40400, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("die Plugin Entwickler und alle anderen Helfer",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 513, 38400,
                                                                     40400, 500));

        //Kontakt
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Kontakt",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     ueberschriftColor, 150, 100,
                                                                     41400, 47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("Kontakt",
                                                                     new Font("Egal", Font.PLAIN, 40),
                                                                     Color.black, 153, 103, 41400,
                                                                     47000, 500));

        //Homepage
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(MyConnector.getHOSite(),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 220, 42400,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect(MyConnector.getHOSite(),
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 223, 42400,
                                                                     47000, 500));

        //Forum
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("http://forum.hattrickorganizer.net/index.php",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     namenColor, 150, 270, 42600,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("http://forum.hattrickorganizer.net/index.php",
                                                                     new Font("Egal", Font.PLAIN, 30),
                                                                     Color.black, 153, 273, 42600,
                                                                     47000, 500));

        //HSQL
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This product includes Hypersonic SQL.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     namenColor, 60, 350, 43600,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This product includes Hypersonic SQL.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     Color.black, 63, 353, 43600,
                                                                     47000, 500));
        
        //JLayer
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This product uses JLayer.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     namenColor, 60, 370, 43600,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This product uses JLayer.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     Color.black, 63, 373, 43600,
                                                                     47000, 500));

        //Plastic LAF
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Plastic LAF", new Font( "Egal", Font.PLAIN, 15 ), namenColor, 60, 390 , 31500, 35000, 500 ) );
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Plastic LAF", new Font( "Egal", Font.PLAIN, 15 ), Color.black, 63, 393 , 31500, 35000, 500 ) );
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Copyright (c) 2003 JGoodies Karsten Lentzsch. All rights reserved.", new Font( "Egal", Font.PLAIN, 15 ), namenColor, 60, 420 , 31700, 35000, 500 ) );
        //            movieLayer.addZeitverzoegertDynamischenEffekt( new FontEffect( "Copyright (c) 2003 JGoodies Karsten Lentzsch. All rights reserved.", new Font( "Egal", Font.PLAIN, 15 ), Color.black, 63, 423 , 31700, 35000, 500 ) );
        //Hattrick geschwafel            
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This application uses information from the online game service Hattrick.org.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     namenColor, 60, 470, 44600,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This application uses information from the online game service Hattrick.org.",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     Color.black, 63, 473, 44600,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This use has been approved by the developers and copyright owners of Hattrick.org, Extralives AB",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     namenColor, 60, 500, 44800,
                                                                     47000, 500));
        movieLayer.addZeitverzoegertDynamischenEffekt(new FontEffect("This use has been approved by the developers and copyright owners of Hattrick.org, Extralives AB",
                                                                     new Font("Egal", Font.PLAIN, 15),
                                                                     Color.black, 63, 503, 44800,
                                                                     47000, 500));
    }

    /**
     * FrameCounter. Vergangene Zeit ermitteln
     *
     * @return TODO Missing Return Method Documentation
     */
    private long frameCounter() {
        if (startZeit < (System.currentTimeMillis() - 1000)) {
            frameRate = frames;
            frames = 0;
            startZeit = System.currentTimeMillis();

            //m_clOwner.paintAll( m_clOwner.getGraphics () );
            //Partikeleffekt
            throwLayer.addDynamischenEffekt((int) (Math.random() * window.getWidth()),
                                            (int) (Math.random() * window.getHeight()));
            throwLayer.addZeitverzoegertDynamischenEffekt(new PartikelEffect(80,
                                                                             (int) (Math.random() * window
                                                                                                    .getWidth()),
                                                                             (int) (Math.random() * window
                                                                                                    .getHeight()),
                                                                             100, 100, 500, 2, 120,
                                                                             new java.awt.Color(255,
                                                                                                255,
                                                                                                255),
                                                                             500));
        }

        vergangeneZeit = System.currentTimeMillis() - letztesFrame;
        letztesFrame = System.currentTimeMillis();

        return vergangeneZeit;
    }

    /**
     * Neues Bild zeichnen
     *
     * @param g TODO Missing Constructuor Parameter Documentation
     */
    private void render(Graphics g) {
        //Antialiasing
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //g.setColor(Color.gray);
        //g.fillRect(0,0,screenWidth,screenHeight);
        backgroundLayer.render(g);

        movieLayer.render(g);

        //foregroundLayer.render(g);
        throwLayer.render(g);

        for (int i = 0; (i < baelle.length) && (i < anzahlBaelle); i++) {
            baelle[i].render(g);
        }

        /*
           g.setColor(Color.white);
                   g.setFont( defaultFont );
           g.drawString("Framerate:", screenWidth-150, 60);
           g.drawString(String.valueOf(frameRate), screenWidth-70, 60);
                   g.drawString("BGLayer:", screenWidth-150, 80);
           g.drawString(String.valueOf(backgroundLayer.size()), screenWidth-70, 80);
           g.drawString("MovieLayer:", screenWidth-150, 100);
           g.drawString(String.valueOf(movieLayer.size()), screenWidth-70, 100);
                   g.drawString("ThrowLayer:", screenWidth-150, 120);
           g.drawString(String.valueOf(throwLayer.size()), screenWidth-70, 120);
         */
        frames++;
    }

    /**
     * Renderschleife!
     */
    private void renderingLoop() {
    	
    	MP3PlayerWrapper mp3 = new MP3PlayerWrapper();
    	mp3.setMP3File( "gui/sound/credits.mp3" );
    	mp3.start();
        //Erzeugen einer Bufferstrategie (3 Buffer)
        window.createBufferStrategy(2);

        final BufferStrategy myStrategy = window.getBufferStrategy();

        //FrameCounter
        startZeit = System.currentTimeMillis();
        letztesFrame = System.currentTimeMillis();

        //Gesamtzeit des Fensters!
        gesamtZeit = 0;

        //Script starten
        creditScript();

        //Sound - Test (buggy)

        /*
           try
           {
               java.net.URL resource = ClassLoader.getSystemResource( "credits/refrain.wav" );
               java.applet.AudioClip audioClip = java.applet.Applet.newAudioClip( resource );
               audioClip.loop();//Spiel nur kurz an!?
           }
           catch ( Exception e )
           {
               HOLogger.instance().log(getClass(),e);
           }
         */
        while (!done) {
            Graphics g = null;

            try {
                vergangeneZeit = frameCounter();
                gesamtZeit += vergangeneZeit;

                //Aktionen durchführen
                action(vergangeneZeit);

                //Frame zeichnen
                g = myStrategy.getDrawGraphics();
                render(g);

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }

                //Am ende neu starten - Nein!! Am Ende beenden!
                if (gesamtZeit > creditlaenge) {
                    //Credits beenden
                    done = true;

                    /*
                       //Muß neu erstellt werden, da sie sonst zu langsam wird!?
                       window.createBufferStrategy(2);
                       myStrategy = window.getBufferStrategy();
                    
                       //FrameCounter
                       startZeit = System.currentTimeMillis();
                       //Gesamtzeit des Fensters!
                       gesamtZeit = 0;
                    
                       creditScript();
                     */
                }
            } finally {
                if (g != null) {
                    g.dispose();
                }
            }

            if (!myStrategy.contentsLost()) {
                myStrategy.show();
            } else {
                HOLogger.instance().log(getClass(),".");
            }
        }

        //Stopt den Player
    	mp3.close();
        
        //Am Ende unsichtbar machen
        window.setVisible(false);
    }
}
