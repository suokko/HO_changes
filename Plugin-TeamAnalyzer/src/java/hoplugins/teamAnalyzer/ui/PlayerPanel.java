// %2588915486:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.manager.PlayerDataManager;
import hoplugins.teamAnalyzer.report.TacticReport;
import hoplugins.teamAnalyzer.util.NameUtil;
import hoplugins.teamAnalyzer.vo.PlayerInfo;
import hoplugins.teamAnalyzer.vo.SpotLineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class PlayerPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1838357704496299083L;
    protected JLabel appearanceField = new JLabel("", SwingConstants.RIGHT);
    protected JLabel nameField = new JLabel("", SwingConstants.LEFT);
    protected JLabel positionField = createLabel("", Color.BLACK, 0);
    protected JLabel positionImage = new JLabel();
    protected JLabel specialEventImage = new JLabel();
    protected JPanel ratingPanel = new JPanel();
    protected TacticPanel tacticPanel = new TacticPanel();
    private JPanel mainPanel;
    private PlayerInfoPanel infoPanel = new PlayerInfoPanel();

    protected boolean containsPlayer = false;
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PlayerPanel object.
     */
    public PlayerPanel() {
        setLayout(new BorderLayout());

        Font nFont = new Font(nameField.getFont().getFontName(), Font.BOLD,
                              nameField.getFont().getSize());

        nameField.setFont(nFont);

        JPanel details = new JPanel();

        details.setBorder(BorderFactory.createEtchedBorder());
        details.setBackground(getBackGround());
        details.setLayout(new BorderLayout());

        JPanel images = new JPanel();

        images.setBackground(getBackGround());
        images.setLayout(new BorderLayout());
        images.add(positionImage, BorderLayout.WEST);
        images.add(specialEventImage, BorderLayout.EAST);
        details.add(images, BorderLayout.WEST);
        details.add(nameField, BorderLayout.CENTER);
        details.add(appearanceField, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();

        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.setBackground(getBackGround());
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(details, BorderLayout.NORTH);
        centerPanel.add(ratingPanel, BorderLayout.WEST);

        if (!(this instanceof UserTeamPlayerPanel)) {
            centerPanel.add(infoPanel, BorderLayout.SOUTH);
        }

        mainPanel = Commons.getModel().getGUI().createImagePanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.setPreferredSize(getDefaultSize());
        mainPanel.add(positionField, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(tacticPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Color getBackGround() {
        return Color.WHITE;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Dimension getDefaultSize() {
        int height = 60;

        if (!(this instanceof UserTeamPlayerPanel)) {
            if (SystemManager.getConfig().isShowPlayerInfo()) {
                height = height + 50;
            }
        }

        if (SystemManager.getConfig().isTacticDetail()) {
            height = height + 50;
        }
       
        // return new Dimension(180, height); - Blaghaid
        return new Dimension(150, height);
    }

    public boolean getContainsPlayer () {
    	return containsPlayer;
    }
    
    /**
     * Reload and refresh data for a certain spot/player.
     */
    public void reload(SpotLineup lineup, int week, int season) {
        tacticPanel.setVisible(SystemManager.getConfig().isTacticDetail());
        mainPanel.setPreferredSize(getDefaultSize());

        if (lineup != null) {
        	containsPlayer = true;
        	
            switch (lineup.getStatus()) {
                case PlayerDataManager.INJURED:
                    nameField.setForeground(Color.RED);
                    break;

                case PlayerDataManager.SUSPENDED:
                    nameField.setForeground(Color.RED);
                    break;

                case PlayerDataManager.SOLD:
                    nameField.setForeground(Color.BLUE);
                    break;

                default:
                    nameField.setForeground(Color.BLACK);
                    break;
            }

            nameField.setText(NameUtil.getPlayerDesc(lineup.getName()));

            appearanceField.setText("" + lineup.getAppearance());

            if (SystemManager.getConfig().isShowPlayerInfo()) {
                PlayerInfo pi = PlayerDataManager.getPlayerInfo(lineup.getPlayerId(), week, season);

                if (pi.getAge() != 0) {
                    infoPanel.setValue(pi);
                } else {
                    infoPanel.clearData();
                }

                infoPanel.setVisible(true);
            }

            int posCode = Commons.getModel().getHelper().getPosition(lineup.getPosition());
            positionImage.setIcon(Commons.getModel().getHelper().getImage4Position(posCode, (byte) 0));
            int specialEvent = PlayerDataManager.getLatestPlayerInfo(lineup.getPlayerId()).getSpecialEvent();

            if (lineup.getPlayerId() == 0) {
                specialEventImage.setIcon(null);
            } else {
                specialEventImage.setIcon(Commons.getModel().getHelper().getImageIcon4Spezialitaet(specialEvent));
            }

            positionField.setText(Commons.getModel().getHelper().getNameForPosition((byte) lineup
                                                                                    .getPosition()));
            updateRatingPanel(lineup.getRating());
            tacticPanel.reload(lineup.getTactics());
        } else {
        	containsPlayer = false;
        	
            nameField.setText(" ");
            appearanceField.setText(" ");
            positionField.setText(" ");
            infoPanel.setVisible(false);
            infoPanel.clearData();
            updateRatingPanel(0);
            positionImage.setIcon(Commons.getModel().getHelper().getImage4Position(0, (byte) 0));
            specialEventImage.setIcon(null);
            tacticPanel.reload(new ArrayList<TacticReport>());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rating TODO Missing Method Parameter Documentation
     */
    protected void updateRatingPanel(double rating) {
        ratingPanel.removeAll();
        ratingPanel.setLayout(new BorderLayout());

        JPanel starPanel = Commons.getModel().getGUI().createStarPanel((int) Math.round(rating * 2),
                                                                       true);

        starPanel.setBackground(getBackGround());
        ratingPanel.add(starPanel, BorderLayout.WEST);
        ratingPanel.setOpaque(true);
        ratingPanel.setBackground(getBackGround());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param farbe TODO Missing Method Parameter Documentation
     * @param Bordertype TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private JLabel createLabel(String text, Color farbe, int Bordertype) {
        JLabel bla = new JLabel(text);

        bla.setHorizontalAlignment(0);
        bla.setForeground(farbe);
        bla.setBorder(BorderFactory.createEtchedBorder(Bordertype));

        return bla;
    }
}
