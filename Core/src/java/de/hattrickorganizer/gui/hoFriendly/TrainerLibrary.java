// %47947707:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.Vector;

import de.hattrickorganizer.gui.model.ImageSequenzItem;
import de.hattrickorganizer.gui.model.TrainerSequenz;
import de.hattrickorganizer.tools.Helper;


/**
 * TrainerLibrary jetzt komplett static!
 *
 * @author Volker Fischer
 * @version 0.4b 27.10.02
 */
public class TrainerLibrary {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Component m_clComponent = new de.hattrickorganizer.gui.templates.ImagePanel();
    private static MediaTracker m_clTracker;

    /** TODO Missing Parameter Documentation */
    public static final AlphaComposite DEFAULTALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                                                 1.0f);

    //public static final Color          MINTRANSPARENTCOLOR  =   new Color( 90, 15, 85 );

    /** TODO Missing Parameter Documentation */
    public static final Color MINTRANSPARENTCOLOR = new Color(140, 15, 85);

    /** TODO Missing Parameter Documentation */
    public static final Color MAXTRANSPARENTCOLOR = new Color(255, 100, 160);

    /** TODO Missing Parameter Documentation */
    public static boolean INITIALISIERT;

    //-----------Grafiken-Anfang----------------------------------------------------	
    //--Anfang Trainer--

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Langeweile;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Gespannt;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Erfreut;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Erfreut2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Traurig;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Veraergert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Frustiert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Frustiert2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Erleichtert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Erleichtert2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer1_Stolz;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Langeweile;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Gespannt;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Erfreut;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Erfreut2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Traurig;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Veraergert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Frustiert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Frustiert2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Erleichtert;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Erleichtert2;

    /** TODO Missing Parameter Documentation */
    public static transient TrainerSequenz Trainer2_Stolz;

    //--Anfang Trainer--        
    //Grafiken
    //Trainer 1

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gelangw_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_gesp_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erfr_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erfr_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erfr_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erfr_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erleich_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erleich_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erleich_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_erleich_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_trau_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_veraer_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_veraer_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_veraer_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_frust_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_frust_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer1_stolz_1;

    //Trainer 2

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gelangw_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_gesp_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erfr_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erfr_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erfr_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erfr_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erleich_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erleich_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erleich_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_erleich_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_4;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_5;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_6;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_trau_7;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_veraer_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_veraer_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_veraer_3;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_frust_1;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_frust_2;

    /** TODO Missing Parameter Documentation */
    public static transient Image Trainer2_stolz_1;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Karte für den Trainer in der Variante
     *
     * @param heimtrainer TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getKarte(boolean heimtrainer, int variante) {
        //Heim
        if (heimtrainer) {
            //Verschiedene Varianten
            if (variante < 40) {
                return Trainer1_Frustiert;
            } else if (variante < 60) {
                return Trainer1_Frustiert2;
            } else {
                return Trainer1_Veraergert;
            }
        }
        //Gast
        else {
            //Verschiedene Varianten
            if (variante < 40) {
                return Trainer2_Frustiert;
            } else if (variante < 60) {
                return Trainer2_Frustiert2;
            } else {
                return Trainer2_Veraergert;
            }
        }
    }

    /**
     * Spielende für den Trainer mit dem Ergebnis in der variante
     *
     * @param heimtrainer TODO Missing Constructuor Parameter Documentation
     * @param heimsieg TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getSpielende(boolean heimtrainer, boolean heimsieg, int variante) {
        //Heim
        if (heimtrainer) {
            //HeimSieg
            if (heimsieg) {
                return Trainer1_Erfreut2;
            }
            //Verloren
            else {
                return Trainer1_Traurig;
            }
        }
        //Gast
        else {
            //Gastsieg
            if (!heimsieg) {
                return Trainer2_Erfreut2;
            }
            //Verloren
            else {
                return Trainer2_Traurig;
            }
        }
    }

    /**
     * Torabschluss
     *
     * @param heimtrainer für den Trainer
     * @param heim für welche Mannschaft
     * @param tor ein Treffer
     * @param variante Variante
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getTorschussAbschluss(boolean heimtrainer, boolean heim,
                                                       boolean tor, int variante) {
        //Heim
        if (heimtrainer) {
            //Für Heim
            if (heim) {
                //Tor
                if (tor) {
                    //Verschiedene Varianten
                    if (variante < 70) {
                        return Trainer1_Erfreut;
                    } else {
                        return Trainer1_Erfreut2;
                    }
                }
                //Kein Tor
                else {
                    //Verschiedene Varianten
                    if (variante < 50) {
                        return Trainer1_Frustiert;
                    } else if (variante < 75) {
                        return Trainer1_Frustiert2;
                    } else if (variante < 95) {
                        return Trainer1_Veraergert;
                    } else {
                        return Trainer1_Traurig;
                    }
                }
            }
            //Für Gast
            else {
                //Tor
                if (tor) {
                    //Verschiedene Varianten
                    if (variante < 40) {
                        return Trainer1_Frustiert;
                    } else if (variante < 70) {
                        return Trainer1_Frustiert2;
                    } else if (variante < 90) {
                        return Trainer1_Veraergert;
                    } else {
                        return Trainer1_Traurig;
                    }
                }
                //Kein Tor
                else {
                    //Verschiedene Varianten
                    if (variante < 50) {
                        return Trainer1_Erleichtert;
                    } else if (variante < 90) {
                        return Trainer1_Erleichtert2;
                    } else {
                        return Trainer1_Stolz;
                    }
                }
            }
        }
        //Gast
        else {
            //Für Gast
            if (!heim) {
                //Tor
                if (tor) {
                    //Verschiedene Varianten
                    if (variante < 70) {
                        return Trainer2_Erfreut;
                    } else {
                        return Trainer2_Erfreut2;
                    }
                }
                //Kein Tor
                else {
                    //Verschiedene Varianten
                    if (variante < 50) {
                        return Trainer2_Frustiert;
                    } else if (variante < 75) {
                        return Trainer2_Frustiert2;
                    } else if (variante < 95) {
                        return Trainer2_Veraergert;
                    } else {
                        return Trainer2_Traurig;
                    }
                }
            }
            //Für Heim
            else {
                //Tor
                if (tor) {
                    //Verschiedene Varianten
                    if (variante < 40) {
                        return Trainer2_Frustiert;
                    } else if (variante < 70) {
                        return Trainer2_Frustiert2;
                    } else if (variante < 90) {
                        return Trainer2_Veraergert;
                    } else {
                        return Trainer2_Traurig;
                    }
                }
                //Kein Tor
                else {
                    //Verschiedene Varianten
                    if (variante < 50) {
                        return Trainer2_Erleichtert;
                    } else if (variante < 90) {
                        return Trainer2_Erleichtert2;
                    } else {
                        return Trainer2_Stolz;
                    }
                }
            }
        }
    }

    /**
     * Torspannung für den Trainer in der Variante
     *
     * @param heimtrainer TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getTorschussAnfang(boolean heimtrainer, int variante) {
        //Heim
        if (heimtrainer) {
            return Trainer1_Gespannt;
        }
        //Gast
        else {
            return Trainer2_Gespannt;
        }
    }

    /**
     * Verletzung für den Trainer in der Variante
     *
     * @param heimtrainer TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getVerletztung(boolean heimtrainer, int variante) {
        //Heim
        if (heimtrainer) {
            return Trainer1_Frustiert;
        }
        //Gast
        else {
            return Trainer2_Frustiert;
        }
    }

    //-----------Grafiken-Ende------------------------------------------------------        
    //-----------Zugriffsmethoden---------------------------------------------------

    /**
     * Warte Sequenz für den Trainer in der Variante
     *
     * @param heimtrainer TODO Missing Constructuor Parameter Documentation
     * @param variante TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TrainerSequenz getWarten(boolean heimtrainer, int variante) {
        //Heim
        if (heimtrainer) {
            return Trainer1_Langeweile;
        }
        //Gast
        else {
            return Trainer2_Langeweile;
        }
    }

    /**
     * lädt alle Grafiken, und erstellt die ImageSequenzItem-Vectoren:<br>
     */
    public static void load() {
        //Nur laden, wenn noch nicht geschehen
        if (!INITIALISIERT) {
            m_clTracker = new MediaTracker(m_clComponent);
            loadTrainer();

            INITIALISIERT = true;
        }
    }

    //Lädt Grafiken mit dem Mediatracker
    private static Image cache(Image image) {
        try {
            m_clTracker.addImage(image, 0);

            m_clTracker.waitForAll();

            if (m_clTracker.isErrorAny()) {
                throw new Exception();
            }

            return image;
        } catch (Throwable e) {
            //vapEngine.Verwaltung.instance ().writeLog ("Fehler beim Laden: "+e.getMessage());
        }

        return null;
    }

    //-----------Zugriffsmethoden---------------------------------------------------        
    //--HilfsFunktionen-------------------------------------------------------------
    /*private static Image drehen(Image sourceImage, double grad) {
        final java.awt.image.BufferedImage bImage = new java.awt.image.BufferedImage(sourceImage
                                                                                     .getWidth(null),
                                                                                     sourceImage
                                                                                     .getHeight(null),
                                                                                     java.awt.image.BufferedImage.TYPE_4BYTE_ABGR);
        bImage.getGraphics().drawImage(sourceImage, 0, 0, null);

        final java.awt.image.BufferedImageOp bImageOp = new java.awt.image.AffineTransformOp(java.awt.geom.AffineTransform
                                                                                             .getRotateInstance(grad,
                                                                                                                sourceImage
                                                                                                                .getWidth(null) / 2,
                                                                                                                sourceImage
                                                                                                                .getHeight(null) / 2),
                                                                                             java.awt.image.AffineTransformOp.TYPE_BILINEAR);
        return bImageOp.filter(bImage, null);
    }*/

    //Lädt Grafiken auch im jar-File
    private static Image loadImage(String datei) {
        try {
            final java.net.URL resource = m_clComponent.getClass().getClassLoader().getResource(datei);
            final Image image = m_clComponent.getToolkit().createImage(resource);

            if (image == null) {
                //vapEngine.Verwaltung.instance ().writeLog ("Null-Image: "+datei);
            }

            m_clTracker.addImage(image, 0);
            m_clTracker.waitForAll();

            if (m_clTracker.isErrorAny()) {
                throw new Exception();
            }

            return image;
        } catch (Throwable e) {
            //vapEngine.Verwaltung.instance ().writeLog ( "Fehler beim Laden: "+datei+" "+e );
        }

        return null;
    }

    /**
     * lädt alle weiteren Grafiken
     */
    private static void loadTrainer() {
        loadTrainer1_Langweile();
        loadTrainer1_Gespannt();
        loadTrainer1_Erfreut();
        loadTrainer1_Erfreut2();
        loadTrainer1_Traurig();
        loadTrainer1_Veraergert();
        loadTrainer1_Frustriert();
        loadTrainer1_Frustriert2();
        loadTrainer1_Erleichtert();
        loadTrainer1_Erleichtert2();
        loadTrainer1_Stolz();

        loadTrainer2_Langweile();
        loadTrainer2_Gespannt();
        loadTrainer2_Erfreut();
        loadTrainer2_Erfreut2();
        loadTrainer2_Traurig();
        loadTrainer2_Veraergert();
        loadTrainer2_Frustriert();
        loadTrainer2_Frustriert2();
        loadTrainer2_Erleichtert();
        loadTrainer2_Erleichtert2();
        loadTrainer2_Stolz();
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Erfreut() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_erfr_1 = cache(de.hattrickorganizer.tools.Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erfr_1.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_1, 3000, 0, 30));

        Trainer1_Erfreut = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Erfreut2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_erfr_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erfr_2.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_erfr_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erfr_3.png"),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getRed(),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getGreen(),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getBlue(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getRed(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getGreen(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getBlue()));
        Trainer1_erfr_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erfr_4.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_2, 500, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_4, 800, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erfr_2, 1500, 0, 30));

        Trainer1_Erfreut2 = new TrainerSequenz(sequenzTemp, 2);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Erleichtert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_erleich_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erleich_1.png"),
                                                              MINTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MINTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MINTRANSPARENTCOLOR
                                                              .getBlue(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getBlue()));
        Trainer1_erleich_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erleich_2.png"),
                                                              MINTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MINTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MINTRANSPARENTCOLOR
                                                              .getBlue(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_erleich_1, 1200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erleich_2, 1000, 0, 30));

        Trainer1_Erleichtert = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Erleichtert2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_erleich_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erleich_3.png"),
                                                              MINTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MINTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MINTRANSPARENTCOLOR
                                                              .getBlue(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getBlue()));
        Trainer1_erleich_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/erleich_4.png"),
                                                              MINTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MINTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MINTRANSPARENTCOLOR
                                                              .getBlue(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getRed(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getGreen(),
                                                              MAXTRANSPARENTCOLOR
                                                              .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_erleich_3, 1200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_erleich_4, 1000, 0, 30));

        Trainer1_Erleichtert2 = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Frustriert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_frust_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/frust_1.png"),
                                                            MINTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MINTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MINTRANSPARENTCOLOR
                                                            .getBlue(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_frust_1, 3000, 0, 30));

        Trainer1_Frustiert = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Frustriert2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        //Trainer1_frust1 schon in loadTrainer1_Frustiert geladen
        Trainer1_frust_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/frust_2.png"),
                                                            MINTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MINTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MINTRANSPARENTCOLOR
                                                            .getBlue(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_frust_2, 1000, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_frust_1, 2000, 0, 30));

        Trainer1_Frustiert2 = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Gespannt() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_gesp_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_1.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_2.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_3.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_4.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_5.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_6.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_gesp_7 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gesp_7.png"),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getRed(),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getGreen(),
	                                                       MINTRANSPARENTCOLOR
	                                                       .getBlue(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getRed(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getGreen(),
	                                                       MAXTRANSPARENTCOLOR
	                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gesp_7, 2000, 0, 30));

        Trainer1_Gespannt = new TrainerSequenz(sequenzTemp, TrainerSequenz.DURCHLAEUFE_ENDLOS);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Langweile() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_gelangw_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_1.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_2.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_3.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_4.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_5.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_6.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer1_gelangw_7 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/gelangw_7.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));

        //0,5sec Beim Start
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_1, 500, 0, 30));

        //3,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_1, 3500, 0, 30));

        //2sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_3, 2000, 0, 30));

        //2sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_2, 2000, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_5, 300, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_6, 2500, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_7, 2500, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_6, 2500, 0, 30));

        //1,2sec
        sequenzTemp.add(new ImageSequenzItem(Trainer1_gelangw_2, 1200, 0, 30));

        Trainer1_Langeweile = new TrainerSequenz(sequenzTemp, TrainerSequenz.DURCHLAEUFE_ENDLOS);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Stolz() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_stolz_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/stolz_1.png"),
                                                            MINTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MINTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MINTRANSPARENTCOLOR
                                                            .getBlue(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getRed(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getGreen(),
                                                            MAXTRANSPARENTCOLOR
                                                            .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_stolz_1, 3000, 0, 30));

        Trainer1_Stolz = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Traurig() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_trau_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_1.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_trau_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_2.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_trau_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_3.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_trau_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_4.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_trau_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_5.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));
        Trainer1_trau_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/trau_6.png"),
                                                           MINTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MINTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MINTRANSPARENTCOLOR
                                                           .getBlue(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getRed(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getGreen(),
                                                           MAXTRANSPARENTCOLOR
                                                           .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_1, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_2, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_trau_6, 300, 0, 30));

        Trainer1_Traurig = new TrainerSequenz(sequenzTemp, 3);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer1_Veraergert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer1_veraer_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/veraer_1.png"),
		                                                     MINTRANSPARENTCOLOR
		                                                     .getRed(),
		                                                     MINTRANSPARENTCOLOR
		                                                     .getGreen(),
		                                                     MINTRANSPARENTCOLOR
		                                                     .getBlue(),
		                                                     MAXTRANSPARENTCOLOR
		                                                     .getRed(),
		                                                     MAXTRANSPARENTCOLOR
		                                                     .getGreen(),
		                                                     MAXTRANSPARENTCOLOR
		                                                     .getBlue()));
        Trainer1_veraer_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/veraer_2.png"),
	                                                         MINTRANSPARENTCOLOR
	                                                         .getRed(),
	                                                         MINTRANSPARENTCOLOR
	                                                         .getGreen(),
	                                                         MINTRANSPARENTCOLOR
	                                                         .getBlue(),
	                                                         MAXTRANSPARENTCOLOR
	                                                         .getRed(),
	                                                         MAXTRANSPARENTCOLOR
	                                                         .getGreen(),
	                                                         MAXTRANSPARENTCOLOR
	                                                         .getBlue()));
        Trainer1_veraer_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/horst/veraer_3.png"),
                                                             MINTRANSPARENTCOLOR
                                                             .getRed(),
                                                             MINTRANSPARENTCOLOR
                                                             .getGreen(),
                                                             MINTRANSPARENTCOLOR
                                                             .getBlue(),
                                                             MAXTRANSPARENTCOLOR
                                                             .getRed(),
                                                             MAXTRANSPARENTCOLOR
                                                             .getGreen(),
                                                             MAXTRANSPARENTCOLOR
                                                             .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_1, 500, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer1_veraer_1, 500, 0, 30));

        Trainer1_Veraergert = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Erfreut() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_erfr_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erfr_1.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_1, 3000, 0, 30));

        Trainer2_Erfreut = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Erfreut2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_erfr_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erfr_2.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_erfr_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erfr_3.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_erfr_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erfr_4.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_2, 500, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_4, 800, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erfr_2, 1500, 0, 30));

        Trainer2_Erfreut2 = new TrainerSequenz(sequenzTemp, 2);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Erleichtert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_erleich_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erleich_1.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_erleich_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erleich_2.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_erleich_1, 1200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erleich_2, 1000, 0, 30));

        Trainer2_Erleichtert = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Erleichtert2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_erleich_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erleich_3.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_erleich_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/erleich_4.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_erleich_3, 1200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_erleich_4, 1000, 0, 30));

        Trainer2_Erleichtert2 = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Frustriert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_frust_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/frust_1.png"),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getBlue(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_frust_1, 3000, 0, 30));

        Trainer2_Frustiert = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Frustriert2() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        //Trainer2_frust1 schon in loadTrainer1_Frustiert geladen
        Trainer2_frust_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/frust_2.png"),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getBlue(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_frust_2, 1000, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_frust_1, 2000, 0, 30));

        Trainer2_Frustiert2 = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Gespannt() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_gesp_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_1.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_2.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_3.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_4.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_5.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_6.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_gesp_7 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gesp_7.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_1, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_2, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_3, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_6, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_7, 2000, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_5, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_4, 150, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gesp_5, 150, 0, 30));

        Trainer2_Gespannt = new TrainerSequenz(sequenzTemp, TrainerSequenz.DURCHLAEUFE_ENDLOS);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Langweile() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_gelangw_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_1.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_2.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_3.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_4.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_5.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_6.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));
        Trainer2_gelangw_7 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/gelangw_7.png"),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MINTRANSPARENTCOLOR
                                                                                          .getBlue(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getRed(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getGreen(),
                                                                                          MAXTRANSPARENTCOLOR
                                                                                          .getBlue()));

        //0,5sec Beim Start
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_2, 500, 0, 30));

        //1,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_2, 1500, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_5, 300, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_6, 2500, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_7, 2500, 0, 30));

        //2,5sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_6, 2500, 0, 30));

        //1,2sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_2, 1200, 0, 30));

        //4sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_1, 4000, 0, 30));

        //2sec
        sequenzTemp.add(new ImageSequenzItem(Trainer2_gelangw_3, 2000, 0, 30));

        Trainer2_Langeweile = new TrainerSequenz(sequenzTemp, TrainerSequenz.DURCHLAEUFE_ENDLOS);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Stolz() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_stolz_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/stolz_1.png"),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MINTRANSPARENTCOLOR
                                                                                        .getBlue(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getRed(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getGreen(),
                                                                                        MAXTRANSPARENTCOLOR
                                                                                        .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_stolz_1, 3000, 0, 30));

        Trainer2_Stolz = new TrainerSequenz(sequenzTemp, 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Traurig() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_trau_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_1.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_trau_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_2.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_trau_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_3.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_trau_4 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_4.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_trau_5 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_5.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));
        Trainer2_trau_6 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/trau_6.png"),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MINTRANSPARENTCOLOR
                                                                                       .getBlue(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getRed(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getGreen(),
                                                                                       MAXTRANSPARENTCOLOR
                                                                                       .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_1, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_2, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_3, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_4, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_5, 300, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_trau_6, 300, 0, 30));

        Trainer2_Traurig = new TrainerSequenz(sequenzTemp, 3);
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadTrainer2_Veraergert() {
        final Vector<ImageSequenzItem> sequenzTemp = new Vector<ImageSequenzItem>();

        Trainer2_veraer_1 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/veraer_1.png"),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getBlue(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getBlue()));
        Trainer2_veraer_2 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/veraer_2.png"),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getBlue(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getBlue()));
        Trainer2_veraer_3 = cache(Helper.makeColorTransparent(loadImage("gui/bilder/trainer/otto/veraer_3.png"),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MINTRANSPARENTCOLOR
                                                                                         .getBlue(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getRed(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getGreen(),
                                                                                         MAXTRANSPARENTCOLOR
                                                                                         .getBlue()));

        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_1, 500, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_3, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_2, 200, 0, 30));
        sequenzTemp.add(new ImageSequenzItem(Trainer2_veraer_1, 500, 0, 30));

        Trainer2_Veraergert = new TrainerSequenz(sequenzTemp, 1);
    }
}
