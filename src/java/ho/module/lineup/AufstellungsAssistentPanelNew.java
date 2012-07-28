package ho.module.lineup;

import ho.core.datatype.CBItem;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.comp.renderer.WeatherListCellRenderer;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.Weather;
import ho.core.util.Helper;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class AufstellungsAssistentPanelNew extends ImagePanel implements
		IAufstellungsAssistentPanel {

	private static final long serialVersionUID = -6853036429678216392L;
	private JComboBox weatherComboBox;

	public AufstellungsAssistentPanelNew() {
		initComponents();
		addListeners();
	}

	@Override
	public boolean isExcludeLastMatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConsiderForm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIgnoreSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGroupFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIdealPositionZuerst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotGroup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isIgnoreInjured() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getWeather() {
		return ((CBItem) this.weatherComboBox.getSelectedItem()).getId();
	}

	@Override
	public void addToAssistant(PlayerPositionPanel positionPanel) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<Integer, Boolean> getPositionStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		JLabel weatherLabel = new JLabel("Wetter");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(4, 4, 2, 2);
		add(weatherLabel, gbc);

		this.weatherComboBox = new JComboBox(Helper.WETTER);
		this.weatherComboBox.setRenderer(new WeatherListCellRenderer());
		gbc.gridx = 1;
		gbc.insets = new Insets(4, 2, 2, 4);
//		add(this.weatherComboBox, gbc);
		add(new WeatherChooser(), gbc);

		// dummy component to consume all extra space
		gbc.gridx++;
		gbc.gridy++;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(new JPanel(), gbc);
	}

	private void addListeners() {
		this.weatherComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HOMainFrame.instance().getAufstellungsPanel().update();

			}
		});
	}
	
	private class WeatherChooser extends JPanel {

		private static final long serialVersionUID = -3666581300985404900L;
		private JToggleButton sunnyBtn;
		private JToggleButton partlyCloudyBtn;
		private JToggleButton cloudyBtn;
		private JToggleButton rainBtn;
		
		public WeatherChooser() {
			initComponents();
		}
		
		private void initComponents() {
			setOpaque(false);
			
			Dimension btnSize = new Dimension(28, 28);
			ButtonGroup btnGrp = new ButtonGroup();
			this.sunnyBtn = new JToggleButton();
			this.sunnyBtn.setPreferredSize(btnSize);
			this.sunnyBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.SUNNY.getId()]));
			add(this.sunnyBtn);
			btnGrp.add(this.sunnyBtn);
			
			this.partlyCloudyBtn = new JToggleButton();
			this.partlyCloudyBtn.setPreferredSize(btnSize);
			this.partlyCloudyBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.PARTIALLY_CLOUDY.getId()]));
			add(this.partlyCloudyBtn);
			btnGrp.add(this.partlyCloudyBtn);
			
			this.cloudyBtn = new JToggleButton();
			this.cloudyBtn.setPreferredSize(btnSize);
			this.cloudyBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.OVERCAST.getId()]));
			add(this.cloudyBtn);
			btnGrp.add(this.cloudyBtn);
			
			this.rainBtn = new JToggleButton();
			this.rainBtn.setPreferredSize(btnSize);
			this.rainBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.RAINY.getId()]));
			add(this.rainBtn);
			btnGrp.add(this.rainBtn);
		}
	}

}
