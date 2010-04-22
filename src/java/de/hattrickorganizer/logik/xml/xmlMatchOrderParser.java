// %3261485518:de.hattrickorganizer.logik.xml%
/*
 * xmlMatchOrderParser.java
 *
 * Created on 14. Juni 2004, 18:18
 */
package de.hattrickorganizer.logik.xml;

import java.io.File;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import plugins.ISpielerPosition;
import de.hattrickorganizer.model.MyHashtable;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * Parser for the matchorders.
 *
 * @author TheTom
 */
public class xmlMatchOrderParser {
	final static String[] PLAYERPOSITIONS = { "Keeper", "RightBack", "InsideBack1", "InsideBack2",
			"LeftBack", "RightWinger", "InsideMid1", "InsideMid2", "LeftWinger", "Forward1", "Forward2" };
    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new instance of xmlMatchOrderParser
     */
    public xmlMatchOrderParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------
    /**
     * Parse match orders from a file name.
     */
    public final Hashtable<String, String> parseMatchOrder(String dateiname) {
        Document doc = XMLManager.instance().parseFile(dateiname);
        return parseDetails(doc);
    }

    /**
     * Parse match orders from a file.
     */
    public final Hashtable<String, String> parseMatchOrder(File datei) {
        Document doc = XMLManager.instance().parseFile(datei);
        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //parse public
    ////////////////////////////////////////////////////////////////////////////////
    public final Hashtable<String, String> parseMatchOrderFromString(String inputStream) {
        Document doc = null;
        doc = XMLManager.instance().parseString(inputStream);
        return parseDetails(doc);
    }

    /**
     * Create a player from the given XML.
     */
    protected final void addPlayer(Element ele, Hashtable<String, String> hash) throws Exception {
        Element tmp = null;
        int roleID = -1;
        String behavior = "-1";
        String spielerID = "-1";
        String name = "";

        tmp = (Element) ele.getElementsByTagName("PlayerID").item(0);
        spielerID = XMLManager.instance().getFirstChildNodeValue(tmp);

        if (spielerID.trim().equals("")) {
            spielerID = "-1";
        }

        tmp = (Element) ele.getElementsByTagName("RoleID").item(0);
        if (tmp != null) {
        	roleID = Integer.parseInt(XMLManager.instance().getFirstChildNodeValue(tmp));
        }

        tmp = (Element) ele.getElementsByTagName("PlayerName").item(0);
        name = XMLManager.instance().getFirstChildNodeValue(tmp);

        // individual orders only for the 10 players in the lineup (starting11 - keeper)
        if ((roleID > 1) && (roleID < 12)) {
            tmp = (Element) ele.getElementsByTagName("Behaviour").item(0);
            behavior = XMLManager.instance().getFirstChildNodeValue(tmp);
        }

        switch (roleID) {
            case 1:
                hash.put("KeeperID", spielerID);
                hash.put("KeeperName", name);
                hash.put("KeeperOrder", "0");
                break;

            case 2:
                hash.put("RightBackID", spielerID);
                hash.put("RightBackName", name);
                hash.put("RightBackOrder", behavior);
                break;

            case 3:
            	if (!hash.containsKey("InsideBack1ID")) {
            		hash.put("InsideBack1ID", spielerID);
            		hash.put("InsideBack1Name", name);
            		hash.put("InsideBack1Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
                break;

            case 4:
            	if (!hash.containsKey("InsideBack2ID")) {
            		hash.put("InsideBack2ID", spielerID);
            		hash.put("InsideBack2Name", name);
            		hash.put("InsideBack2Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
            	break;

            case 5:
                hash.put("LeftBackID", spielerID);
                hash.put("LeftBackName", name);
                hash.put("LeftBackOrder", behavior);
                break;

            case 6:
                hash.put("RightWingerID", spielerID);
                hash.put("RightWingerName", name);
                hash.put("RightWingerOrder", behavior);
                break;

            case 7:
            	if (!hash.containsKey("InsideMid1ID")) {
            		hash.put("InsideMid1ID", spielerID);
            		hash.put("InsideMid1Name", name);
            		hash.put("InsideMid1Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
                break;

            case 8:
            	if (!hash.containsKey("InsideMid2ID")) {
            		hash.put("InsideMid2ID", spielerID);
            		hash.put("InsideMid2Name", name);
            		hash.put("InsideMid2Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
                break;

            case 9:
                hash.put("LeftWingerID", spielerID);
                hash.put("LeftWingerName", name);
                hash.put("LeftWingerOrder", behavior);
                break;

            case 10:
            	if (!hash.containsKey("Forward1ID")) {
            		hash.put("Forward1ID", spielerID);
            		hash.put("Forward1Name", name);
            		hash.put("Forward1Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
                break;

            case 11:
            	if (!hash.containsKey("Forward2ID")) {
            		hash.put("Forward2ID", spielerID);
            		hash.put("Forward2Name", name);
            		hash.put("Forward2Order", behavior);
            	} else {
            		addAdditionalPlayer(hash, roleID, spielerID, name, behavior);
            	}
                break;

            case 12:
                hash.put("SubstKeeperID", spielerID);
                hash.put("SubstKeeperName", name);
                break;

            case 13:
                hash.put("SubstBackID", spielerID);
                hash.put("SubstBackName", name);
                break;

            case 14:
                hash.put("SubstInsideMidID", spielerID);
                hash.put("SubstInsideMidName", name);
                break;

            case 15:
                hash.put("SubstWingerID", spielerID);
                hash.put("SubstWingerName", name);
                break;

            case 16:
                hash.put("SubstForwardID", spielerID);
                hash.put("SubstForwardName", name);
                break;

            case 17:
                hash.put("KickerID", spielerID);
                hash.put("KickerName", name);
                break;

            case 18:
                hash.put("CaptainID", spielerID);
                hash.put("CaptainName", name);
                break;
        }
    }
    
    private static void addAdditionalPlayer(Hashtable<String, String> hash, int roleID, String spielerID, String name, String behavior) {
    	String key = "Additional1";
    	if (!hash.containsKey(key+"ID")) {
    		hash.put(key + "ID", spielerID);
    		hash.put(key + "Role", String.valueOf(roleID));
    		hash.put(key + "Name", name);
    		hash.put(key + "Behaviour", behavior);
    		return;
    	}
    	key = "Additional2";
    	if (!hash.containsKey(key+"ID")) {
    		hash.put(key + "ID", spielerID);
    		hash.put(key + "Role", String.valueOf(roleID));
    		hash.put(key + "Name", name);
    		hash.put(key + "Behaviour", behavior);
    		return;
    	}
    	key = "Additional3";
    	if (!hash.containsKey(key+"ID")) {
    		hash.put(key + "ID", spielerID);
    		hash.put(key + "Role", String.valueOf(roleID));
    		hash.put(key + "Name", name);
    		hash.put(key + "Behaviour", behavior);
    		return;
    	}
    	key = "Additional4";
    	if (!hash.containsKey(key+"ID")) {
    		hash.put(key + "ID", spielerID);
    		hash.put(key + "Role", String.valueOf(roleID));
    		hash.put(key + "Name", name);
    		hash.put(key + "Behaviour", behavior);
    		return;
    	}
    	// max. 4 additional/repositioned players in the new lineup?
    }

    /////////////////////////////////////////////////////////////////////////////////
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * erstellt das MAtchlineup Objekt
     */
    protected final Hashtable<String, String> parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        final MyHashtable hash = new MyHashtable();
        NodeList list = null;

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            ele = (Element) root.getElementsByTagName("Version").item(0);

            //Nach Format ab Version 1.2 parsen
            //Daten füllen
            //Fetchdate
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("MatchID").item(0);
            hash.put("MatchID", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("MatchData").item(0);

            if (!XMLManager.instance().getAttributeValue(root, "Available").trim().equalsIgnoreCase("true")) {
                return hash;
            }

            ele = (Element) root.getElementsByTagName("Attitude").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("Attitude", (XMLManager.instance().getFirstChildNodeValue(ele)));
            } else {
                hash.put("Attitude", "0");
            }

            ele = (Element) root.getElementsByTagName("HomeTeamID").item(0);
            hash.put("HomeTeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("HomeTeamName").item(0);
            hash.put("HomeTeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("AwayTeamID").item(0);
            hash.put("AwayTeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("AwayTeamName").item(0);
            hash.put("AwayTeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("MatchDate").item(0);
            hash.put("MatchDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaID").item(0);
            hash.put("ArenaID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            hash.put("ArenaName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TacticType").item(0);
            hash.put("TacticType", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Lineup").item(0);

            list = root.getElementsByTagName("Player");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                addPlayer((Element) list.item(i), hash);
            }
            fillEmptySpotsWithAdditionalPlayers(hash);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchOrderParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
    
    private static void fillEmptySpotsWithAdditionalPlayers(Hashtable<String, String> hash) {
    	try {
			int a = 1;
			String add = hash.get("Additional" + a + "ID");
			if (add == null) {
				return;
			} else {
				String pos = getNextFreeSlot(hash);
				if (pos != null) {
					hash.put(pos + "ID", add);
					hash.put(pos + "Name", hash.get("Additional" + a + "Name"));
					//hash.put(pos + "Order", hash.get("Additional" + a + "Behaviour"));
					try {
						int role = Integer.parseInt(hash.get("Additional" + a + "Role"));
						if (role == 3 || role == 4) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_INNENV));
						} else if (role == 7 || role == 8) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_MITTELFELD));
						} else if (role == 10 || role == 11) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_STUERMER));
						}
					} catch (Exception e) {
						HOLogger.instance().debug(xmlMatchOrderParser.class, "Err(add "+a+"): " + e);
						hash.put(pos + "Order", "0");
					}
				}
			}
    		a = 2;
			add = hash.get("Additional" + a + "ID");
			if (add == null) {
				return;
			} else {
				String pos = getNextFreeSlot(hash);
				if (pos != null) {
					hash.put(pos + "ID", add);
					hash.put(pos + "Name", hash.get("Additional" + a + "Name"));
					//hash.put(pos + "Order", hash.get("Additional" + a + "Behaviour"));
					try {
						int role = Integer.parseInt(hash.get("Additional" + a + "Role"));
						if (role == 3 || role == 4) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_INNENV));
						} else if (role == 7 || role == 8) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_MITTELFELD));
						} else if (role == 10 || role == 11) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_STUERMER));
						}
					} catch (Exception e) {
						HOLogger.instance().debug(xmlMatchOrderParser.class, "Err(add "+a+"): " + e);
						hash.put(pos + "Order", "0");
					}
				}
			}
			a = 3;
			add = hash.get("Additional" + a + "ID");
			if (add == null) {
				return;
			} else {
				String pos = getNextFreeSlot(hash);
				if (pos != null) {
					hash.put(pos + "ID", add);
					hash.put(pos + "Name", hash.get("Additional" + a + "Name"));
					//hash.put(pos + "Order", hash.get("Additional" + a + "Behaviour"));
					try {
						int role = Integer.parseInt(hash.get("Additional" + a + "Role"));
						if (role == 3 || role == 4) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_INNENV));
						} else if (role == 7 || role == 8) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_MITTELFELD));
						} else if (role == 10 || role == 11) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_STUERMER));
						}
					} catch (Exception e) {
						HOLogger.instance().debug(xmlMatchOrderParser.class, "Err(add "+a+"): " + e);
						hash.put(pos + "Order", "0");
					}
				}
			}
			a = 4;
			add = hash.get("Additional" + a + "ID");
			if (add == null) {
				return;
			} else {
				String pos = getNextFreeSlot(hash);
				if (pos != null) {
					hash.put(pos + "ID", add);
					hash.put(pos + "Name", hash.get("Additional" + a + "Name"));
					//hash.put(pos + "Order", hash.get("Additional" + a + "Behaviour"));
					try {
						int role = Integer.parseInt(hash.get("Additional" + a + "Role"));
						if (role == 3 || role == 4) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_INNENV));
						} else if (role == 7 || role == 8) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_MITTELFELD));
						} else if (role == 10 || role == 11) {
							hash.put(pos + "Order", String.valueOf(ISpielerPosition.ZUS_STUERMER));
						}
					} catch (Exception e) {
						HOLogger.instance().debug(xmlMatchOrderParser.class, "Err(add "+a+"): " + e);
						hash.put(pos + "Order", "0");
					}
				}
			}
    	} catch (Exception e) {
    		HOLogger.instance().debug(xmlMatchOrderParser.class, e);
    	}
    }
    
    private static String getNextFreeSlot(Hashtable<String, String> hash) {
    	for (String pos : PLAYERPOSITIONS) {
    		if (!hash.containsKey(pos + "ID")) {
    			return pos;
    		}
    	}
    	return null;
    }

    /**
     * erstellt das Matchlineup Objekt
     */
    protected final Hashtable<?, ?> parseVersion1_1(Document doc) {
        Element ele = null;
        Element root = null;
        final MyHashtable hash = new MyHashtable();

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            //Fetchdate
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("MatchID").item(0);
            hash.put("MatchID", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("MatchData").item(0);

            if (!XMLManager.instance().getAttributeValue(root, "Available").trim().equalsIgnoreCase("true")) {
                return hash;
            }

            ele = (Element) root.getElementsByTagName("Attitude").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("Attitude", (XMLManager.instance().getFirstChildNodeValue(ele)));
            } else {
                hash.put("Attitude", "0");
            }

            ele = (Element) root.getElementsByTagName("HomeTeamID").item(0);
            hash.put("HomeTeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("HomeTeamName").item(0);
            hash.put("HomeTeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("AwayTeamID").item(0);
            hash.put("AwayTeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("AwayTeamName").item(0);
            hash.put("AwayTeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("MatchDate").item(0);
            hash.put("MatchDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaID").item(0);
            hash.put("ArenaID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            hash.put("ArenaName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TacticType").item(0);
            hash.put("TacticType", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("LineUp").item(0);
            ele = (Element) root.getElementsByTagName("KeeperID").item(0);
            hash.put("KeeperID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("KeeperName").item(0);
            hash.put("KeeperName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("RightBackID").item(0);
            hash.put("RightBackID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("RightBackName").item(0);
            hash.put("RightBackName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("RightBackOrder").item(0);
            hash.put("RightBackOrder", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack1ID").item(0);
            hash.put("InsideBack1ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack1Name").item(0);
            hash.put("InsideBack1Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack1Order").item(0);
            hash.put("InsideBack1Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack2ID").item(0);
            hash.put("InsideBack2ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack2Name").item(0);
            hash.put("InsideBack2Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideBack2Order").item(0);
            hash.put("InsideBack2Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftBackID").item(0);
            hash.put("LeftBackID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftBackName").item(0);
            hash.put("LeftBackName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftBackOrder").item(0);
            hash.put("LeftBackOrder", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("RightWingerID").item(0);
            hash.put("RightWingerID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("RightWingerName").item(0);
            hash.put("RightWingerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("RightWingerOrder").item(0);
            hash.put("RightWingerOrder", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid1ID").item(0);
            hash.put("InsideMid1ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid1Name").item(0);
            hash.put("InsideMid1Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid1Order").item(0);
            hash.put("InsideMid1Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid2ID").item(0);
            hash.put("InsideMid2ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid2Name").item(0);
            hash.put("InsideMid2Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("InsideMid2Order").item(0);
            hash.put("InsideMid2Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftWingerID").item(0);
            hash.put("LeftWingerID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftWingerName").item(0);
            hash.put("LeftWingerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LeftWingerOrder").item(0);
            hash.put("LeftWingerOrder", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward1ID").item(0);
            hash.put("Forward1ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward1Name").item(0);
            hash.put("Forward1Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward1Order").item(0);
            hash.put("Forward1Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward2ID").item(0);
            hash.put("Forward2ID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward2Name").item(0);
            hash.put("Forward2Name", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Forward2Order").item(0);
            hash.put("Forward2Order", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstKeeperID").item(0);
            hash.put("SubstKeeperID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstKeeperName").item(0);
            hash.put("SubstKeeperName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstBackID").item(0);
            hash.put("SubstBackID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstBackName").item(0);
            hash.put("SubstBackName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstInsideMidID").item(0);
            hash.put("SubstInsideMidID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstInsideMidName").item(0);
            hash.put("SubstInsideMidName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstWingerID").item(0);
            hash.put("SubstWingerID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstWingerName").item(0);
            hash.put("SubstWingerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstForwardID").item(0);
            hash.put("SubstForwardID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("SubstForwardName").item(0);
            hash.put("SubstForwardName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("KickerID").item(0);
            hash.put("KickerID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("KickerName").item(0);
            hash.put("KickerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CaptainID").item(0);
            hash.put("CaptainID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("CaptainName").item(0);
            hash.put("CaptainName", (XMLManager.instance().getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchOrderParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }
        return hash;
    }
    
}
