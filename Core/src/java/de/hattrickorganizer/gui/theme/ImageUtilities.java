package de.hattrickorganizer.gui.theme;

import gui.HOColorName;
import gui.HOIconName;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import plugins.ISpielerPosition;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.ColorChangeFilter;
import de.hattrickorganizer.tools.FuzzyTransparentFilter;
import de.hattrickorganizer.tools.TransparentFilter;

public class ImageUtilities {

    /** Hashtable mit Veränderungspfeilgrafiken nach Integer als Key */
    private static Hashtable<Integer,ImageIcon> m_clPfeilCache = new Hashtable<Integer,ImageIcon>();
    private static Hashtable<Integer,ImageIcon> m_clPfeilWideCache = new Hashtable<Integer,ImageIcon>();
    private static Hashtable<Integer,ImageIcon> m_clPfeilLightCache = new Hashtable<Integer,ImageIcon>();
    private static Hashtable<Integer,ImageIcon> m_clPfeilWideLightCache = new Hashtable<Integer,ImageIcon>();
    /** Cache für Transparent gemachte Bilder */
    public static HashMap<Image,Image> m_clTransparentsCache = new HashMap<Image,Image>();
    public static ImageIcon MINILEER = new ImageIcon(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
	/**
	 * Tauscht eine Farbe im Image durch eine andere
	 *
	 */
	public static Image changeColor(Image im, Color original, Color change) {
	    final ImageProducer ip = new FilteredImageSource(im.getSource(),
	    		new ColorChangeFilter(original, change));
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}

	/**
	 * paint a cross over an image
	 */
	public static Image getImageDurchgestrichen(Image image,Color helleFarbe, Color dunkleFarbe) {
	    try {
	        final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	
	        final java.awt.Graphics2D g2d = (java.awt.Graphics2D) bufferedImage.getGraphics();
	
	        g2d.drawImage(image, 0, 0, null);
	
	        //Kreuz zeichnen
	        g2d.setColor(helleFarbe);
	        g2d.drawLine(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight());
	        g2d.drawLine(bufferedImage.getWidth() - 1, 0, 0, bufferedImage.getHeight());
	        g2d.setColor(dunkleFarbe);
	        g2d.drawLine(1, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
	        g2d.drawLine(bufferedImage.getWidth(), 0, 1, bufferedImage.getHeight());
	        return bufferedImage;
	    } catch (Exception e) {
	        return image;
	    }
	}

	/**
	 * Macht eine Farbe in dem Bild transparent
	 *
	 */
	public static Image makeColorTransparent(Image im, int minred, int mingreen,
	                                                  int minblue, int maxred, int maxgreen,
	                                                  int maxblue) {
	    final ImageProducer ip = new FilteredImageSource(im.getSource(),
	    		new FuzzyTransparentFilter(minred, mingreen, minblue, maxred, maxgreen, maxblue));
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}

	/**
	 * Kopiert das zweite Image auf das erste
	 *
	 */
	public static Image merge(Image background, Image foreground) {
	    final BufferedImage image = new BufferedImage(
	    		background.getWidth(null), background.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    image.getGraphics().drawImage(background, 0, 0, null);
	    image.getGraphics().drawImage(foreground, 0, 0, null);
	
	    return image;
	}

	/**
	 * Macht eine Farbe in dem Bild transparent
	 *
	 */
	public static Image makeColorTransparent(Image im, Color color) {
	    Image image = null;
	
	    //Cache durchsuchen
	    image = (Image) m_clTransparentsCache.get(im);
	
	    //Nicht im Cache -> laden
	    if (image == null) {
	        final ImageProducer ip = new FilteredImageSource(im.getSource(), new TransparentFilter(color));
	        image = Toolkit.getDefaultToolkit().createImage(ip);
	
	        //Bild in den Cache hinzufügen
	        m_clTransparentsCache.put(im, image);
	    }
	
	    return image;
	}

	public static ImageIcon getImageIcon4Veraenderung(int wert, boolean aktuell) {
	        ImageIcon icon = null;
	        final Integer keywert = Integer.valueOf(wert);
	        int xPosText = 3;
	
	        // Nicht im Cache
	        if ((!m_clPfeilCache.containsKey(keywert) && aktuell)
	            || (!m_clPfeilLightCache.containsKey(keywert) && !aktuell)) {
	            final BufferedImage image = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
	
	            //Pfeil zeichnen
	            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();
	
	            //g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
	            if (wert == 0) {
	                //                g2d.setColor ( Color.darkGray );
	                //                g2d.drawLine ( 3, 8, 9, 8 );
	            } else if (wert > 0) {
	                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
	                final int[] ypoints = {6, 0, 0, 6, 6, 13, 13, 6, 6};
	
	                //Polygon füllen
	                if (!aktuell) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	                }
	
	                int farbwert = Math.min(240, 90 + (50 * wert));
	                g2d.setColor(new Color(0, farbwert, 0));
	                g2d.fillPolygon(xpoints, ypoints, xpoints.length);
	
	                //Polygonrahmen
	                farbwert = Math.min(255, 105 + (50 * wert));
	                g2d.setColor(new Color(40, farbwert, 40));
	                g2d.drawPolygon(xpoints, ypoints, xpoints.length);
	
	                //Wert eintragen
	                if (!aktuell) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	                }
	
	                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));
	
	                //Für 1 und 2 Weisse Schrift oben
	                if (wert < 3) {
	                    g2d.setColor(Color.black);
	                    g2d.drawString(wert + "", xPosText, 11);
	                    g2d.setColor(Color.white);
	                    g2d.drawString(wert + "", xPosText + 1, 11);
	                }
	                //Sonst Schwarze Schrift oben (nur bei Positiven Veränderungen)
	                else {
	                    //Position bei grossen Zahlen weiter nach vorne
	                    if (wert > 9) {
	                        xPosText = 0;
	                    }
	
	                    g2d.setColor(Color.white);
	                    g2d.drawString(wert + "", xPosText, 11);
	                    g2d.setColor(Color.black);
	                    g2d.drawString(wert + "", xPosText + 1, 11);
	                }
	            } else if (wert < 0) {
	                final int[] xpoints = {0, 6, 7, 13, 10, 10, 3, 3, 0};
	                final int[] ypoints = {7, 13, 13, 7, 7, 0, 0, 7, 7};
	
	                //Polygon füllen
	                if (!aktuell) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	                }
	
	                int farbwert = Math.min(240, 90 - (50 * wert));
	                g2d.setColor(new Color(farbwert, 0, 0));
	                g2d.fillPolygon(xpoints, ypoints, xpoints.length);
	
	                //Polygonrahmen
	                farbwert = Math.min(255, 105 - (50 * wert));
	                g2d.setColor(new Color(farbwert, 40, 40));
	                g2d.drawPolygon(xpoints, ypoints, xpoints.length);
	
	                //Wert eintragen
	                if (!aktuell) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	                }
	
	                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));
	
	                //Position bei grossen Zahlen weiter nach vorne
	                if (wert < -9) {
	                    xPosText = 0;
	                }
	
	                g2d.setColor(Color.black);
	                g2d.drawString(Math.abs(wert) + "", xPosText, 11);
	                g2d.setColor(Color.white);
	                g2d.drawString(Math.abs(wert) + "", xPosText + 1, 11);
	            }
	
	            //Icon erstellen und in den Cache packen
	            icon = new ImageIcon(image);
	//            String desc = "(";
	//            if (wert>0) {
	//            	desc = desc + "+";
	//            }
	//            desc = desc + wert + ")";
	//            icon.setIconDescription(desc);
	
	            if (aktuell) {
	                m_clPfeilCache.put(keywert, icon);
	            } else {
	                m_clPfeilLightCache.put(keywert, icon);
	            }
	
	            //HOLogger.instance().log(Helper.class, "Create Pfeil: " + wert );
	        }
	        //Im Cache
	        else {
	            if (aktuell) {
	                icon = m_clPfeilCache.get(keywert);
	            } else {
	                icon = m_clPfeilLightCache.get(keywert);
	            }
	
	            //HOLogger.instance().log(Helper.class, "Use Pfeilcache: " + wert );
	        }
	
	        return icon;
	    }

	/**
	 * Creates a wide image for use where value can be greater than 99
	 * @param wert the Value
	 * @param aktuell
	 * @return an icon representation of the value
	 */
	public static ImageIcon getWideImageIcon4Veraenderung(int value, boolean current) {
        ImageIcon icon = null;
        final Integer keywert = Integer.valueOf(value);
        int xPosText = 8;

        // Not in cache
        if ((!m_clPfeilWideCache.containsKey(keywert) && current)
            || (!m_clPfeilWideCache.containsKey(keywert) && !current)) {
            final BufferedImage image = new BufferedImage(24, 14, BufferedImage.TYPE_INT_ARGB);
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();
            if (value != 0)
            {
               if (value > 0) {
	                final int[] xpoints = {5, 11, 12, 18, 15, 15, 8, 8, 5};
	                final int[] ypoints = {6, 0, 0, 6, 6, 13, 13, 6, 6};
	                //Fill polygon
	                if (!current) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	                }
	                int farbwert = Math.min(240, 90 + (50 * value));
	                g2d.setColor(new Color(0, farbwert, 0));
	                g2d.fillPolygon(xpoints, ypoints, xpoints.length);
	
	                //Polygon Frame
	                farbwert = Math.min(255, 105 + (50 * value));
	                g2d.setColor(new Color(40, farbwert, 40));
	                g2d.drawPolygon(xpoints, ypoints, xpoints.length);
	
	                //Enter value
	                if (!current) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	                }
	                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));
	
	                //For 1 and 2, use white at top
	                if (value < 3) {
	                    g2d.setColor(Color.black);
	                    g2d.drawString(value + "", xPosText, 11);
	                    g2d.setColor(Color.white);
	                    g2d.drawString(value + "", xPosText + 1, 11);
	                }
	                // Black writing (only for positive)
	                else {
	                    // Reposition by value length
	                	if (value > 9)
	                		xPosText -= ((Integer.toString(value).length() - 1) * 3);
	                	
	                    g2d.setColor(Color.white);
	                    g2d.drawString(value + "", xPosText, 11);
	                    g2d.setColor(Color.black);
	                    g2d.drawString(value + "", xPosText + 1, 11);
	                }
	            } else {
	                final int[] xpoints = {5, 11, 12, 18, 15, 15, 8, 8, 5};
	                final int[] ypoints = {7, 13, 13, 7, 7, 0, 0, 7, 7};
	
	                //Fill Polygon
	                if (!current) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	                }
	
	                int farbwert = Math.min(240, 90 - (50 * value));
	                g2d.setColor(new Color(farbwert, 0, 0));
	                g2d.fillPolygon(xpoints, ypoints, xpoints.length);
	
	                //Polygon Frame
	                farbwert = Math.min(255, 105 - (50 * value));
	                g2d.setColor(new Color(farbwert, 40, 40));
	                g2d.drawPolygon(xpoints, ypoints, xpoints.length);
	
	                //Enter value
	                if (!current) {
	                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	                }
	
	                g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 10));
	                // No need to worry about space for - as absolute value is used.
	                if (Math.abs(value) > 9)
	            		xPosText -= ((Integer.toString(Math.abs(value)).length() - 1) * 3);
	                g2d.setColor(Color.black);
	                g2d.drawString(Math.abs(value) + "", xPosText, 11);
	                g2d.setColor(Color.white);
	                g2d.drawString(Math.abs(value) + "", xPosText + 1, 11);
	            }
            }
            //Make the Icon and cache it
            icon = new ImageIcon(image);
            if (current) {
            	m_clPfeilWideCache.put(keywert, icon);
            } else {
                m_clPfeilWideLightCache.put(keywert, icon);
            }
        }
        //In Cache
        else {
            if (current) {
                icon = m_clPfeilWideCache.get(keywert);
            } else {
                icon = m_clPfeilWideLightCache.get(keywert);
            }
        }
        return icon;
    }
	
	public static ImageIcon NOIMAGEICON = new ImageIcon(new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB));

	/**
	 * Return ImageIcon for Position
	 *
	 */
	public static ImageIcon getImage4Position(SpielerPosition position, int trickotnummer) {
	    if (position == null) {
	        return ImageUtilities.getImage4Position(0, (byte) 0, trickotnummer);
	    }
	
	    return ImageUtilities.getImage4Position(position.getId(), position.getTaktik(), trickotnummer);
	}

	/**
	 * Return ImageIcon for Position
	 *
	 */
	public static ImageIcon getImage4Position(int posid, byte taktik, int trickotnummer) {
		Color trickotfarbe = null;
		Image trickotImage = null;
		ImageIcon komplettIcon = null;
		StringBuilder key = new StringBuilder(20);
		// Im Cache nachsehen
		key.append("trickot_").append(posid).append("_").append(taktik).append("_").append(trickotnummer);
		komplettIcon = ThemeManager.getIcon(key.toString());
		
		if (komplettIcon == null) {
			switch (posid) {
				case ISpielerPosition.keeper: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_KEEPER);
					break;
				}
	
				case ISpielerPosition.rightCentralDefender:
				case ISpielerPosition.leftCentralDefender:
				case ISpielerPosition.middleCentralDefender: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_CENTRALDEFENCE);
					break;
				}
	
				case ISpielerPosition.leftBack:
				case ISpielerPosition.rightBack: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_WINGBACK);
					break;
				}
	
				case ISpielerPosition.rightInnerMidfield:
				case ISpielerPosition.leftInnerMidfield:
				case ISpielerPosition.centralInnerMidfield: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_MIDFIELD);
					break;
				}
	
				case ISpielerPosition.leftWinger:
				case ISpielerPosition.rightWinger: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_WING);
					break;
				}
	
				case ISpielerPosition.rightForward:
				case ISpielerPosition.leftForward:
				case ISpielerPosition.centralForward: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_FORWARD);
					break;
				}
	
				case ISpielerPosition.substKeeper: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_SUBKEEPER);
					break;
				}
	
				case ISpielerPosition.substDefender: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_SUBDEFENCE);
					break;
				}
	
				case ISpielerPosition.substInnerMidfield: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_SUBMIDFIELD);
					break;
				}
	
				case ISpielerPosition.substWinger: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_SUBWING);
					break;
				}
	
				case ISpielerPosition.substForward: {
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT_SUBFORWARD);
					break;
				}
	
				default:
					trickotfarbe = ThemeManager.getColor(HOColorName.SHIRT);
			}
	
			// Bild laden, transparenz hinzu, trikofarbe wechseln
			trickotImage = changeColor(changeColor(makeColorTransparent(ThemeManager.getIcon(HOIconName.TRICKOT).getImage(),
					Color.WHITE), Color.BLACK, trickotfarbe), new Color(100, 100, 100), trickotfarbe.brighter());
			komplettIcon = new ImageIcon(trickotImage);
	
		// return new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
		// Trickotnummer
			if ((trickotnummer > 0) && (trickotnummer < 100)) {
				BufferedImage image = new BufferedImage(24, 14, BufferedImage.TYPE_INT_ARGB);
	
				// 5;
				int xPosText = 18;
	
				// Helper.makeColorTransparent( image, Color.white );
				final java.awt.Graphics2D g2d = (java.awt.Graphics2D) image.getGraphics();
	
				// Wert eintragen
				// g2d.setComposite ( AlphaComposite.getInstance(
				// AlphaComposite.SRC_OVER, 1.0f ) );
				g2d.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 10));
	
				// Position bei grossen Zahlen weiter nach vorne
				if (trickotnummer > 9) {
					xPosText = 12;
				}
	
				g2d.setColor(Color.black);
				g2d.drawString(trickotnummer + "", xPosText, 13);
	
				// Zusammenführen
				image = (BufferedImage) merge(komplettIcon.getImage(), image);
	
				// Icon erstellen und in den Cache packen
				komplettIcon = new ImageIcon(image);
				
			}
			// In den Cache hinzufügen
			ThemeManager.instance().put(key.toString(), komplettIcon);
		} // komplettIcon == null 
	
		return komplettIcon;
	}

	public static ImageIcon getFlagIcon(int country) {
	    return ThemeManager.instance().classicSchema.loadImageIcon("flags/"+ country + "flag.png");
	}


}
