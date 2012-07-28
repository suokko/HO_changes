package ho.module.lineup;

import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.Weather;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class AufstellungsAssistentPanelNew extends ImagePanel implements
		IAufstellungsAssistentPanel {

	private static final long serialVersionUID = -6853036429678216392L;
	private WeatherChooser weatherChooser;

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
	public Weather getWeather() {
		return weatherChooser.getWeather();
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

		this.weatherChooser = new WeatherChooser();
		gbc.gridx = 1;
		gbc.insets = new Insets(4, 2, 2, 4);
		add(this.weatherChooser, gbc);

		// dummy component to consume all extra space
		gbc.gridx++;
		gbc.gridy++;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(new JPanel(), gbc);
	}

	private void addListeners() {
		this.weatherChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("####- " + weatherChooser.getWeather());
				HOMainFrame.instance().getAufstellungsPanel().update();
			}
		});
	}

	/**
	 * Component to show/choose the weather via some toggle buttons.
	 * 
	 */
	private class WeatherChooser extends JPanel {

		private static final long serialVersionUID = -3666581300985404900L;
		private final List<ActionListener> actionListeners = new ArrayList<ActionListener>();
		private JToggleButton sunnyBtn;
		private JToggleButton partiallyCloudyBtn;
		private JToggleButton overcastBtn;
		private JToggleButton rainyBtn;
		private ButtonGroup buttonGroup;

		public WeatherChooser() {
			initComponents();
			setWeather(Weather.PARTIALLY_CLOUDY);
		}

		public void addActionListener(ActionListener listener) {
			if (!this.actionListeners.contains(listener)) {
				this.actionListeners.add(listener);
			}
		}

		public void removeActionListener(ActionListener listener) {
			this.actionListeners.remove(listener);
		}

		public void setWeather(Weather weather) {
			switch (weather) {
			case SUNNY:
				this.buttonGroup.setSelected(this.sunnyBtn.getModel(), true);
				break;
			case PARTIALLY_CLOUDY:
				this.buttonGroup.setSelected(this.partiallyCloudyBtn.getModel(), true);
				break;
			case OVERCAST:
				this.buttonGroup.setSelected(this.overcastBtn.getModel(), true);
				break;
			case RAINY:
				this.buttonGroup.setSelected(this.rainyBtn.getModel(), true);
				break;
			default:
				this.buttonGroup.clearSelection();
				break;
			}
		}

		public Weather getWeather() {
			ButtonModel btnModel = this.buttonGroup.getSelection();
			if (btnModel != null) {
				if (btnModel == this.sunnyBtn.getModel()) {
					return Weather.SUNNY;
				}
				if (btnModel == this.partiallyCloudyBtn.getModel()) {
					return Weather.PARTIALLY_CLOUDY;
				}
				if (btnModel == this.overcastBtn.getModel()) {
					return Weather.OVERCAST;
				}
				if (btnModel == this.rainyBtn.getModel()) {
					return Weather.RAINY;
				}
			}

			return null;
		}

		private void initComponents() {
			setOpaque(false);

			Dimension btnSize = new Dimension(28, 28);
			this.buttonGroup = new ButtonGroup();
			this.sunnyBtn = new JToggleButton();
			this.sunnyBtn.setPreferredSize(btnSize);
			this.sunnyBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.SUNNY.getId()]));
			add(this.sunnyBtn);
			this.buttonGroup.add(this.sunnyBtn);

			this.partiallyCloudyBtn = new JToggleButton();
			this.partiallyCloudyBtn.setPreferredSize(btnSize);
			this.partiallyCloudyBtn.setIcon(ThemeManager
					.getIcon(HOIconName.WEATHER[Weather.PARTIALLY_CLOUDY.getId()]));
			add(this.partiallyCloudyBtn);
			this.buttonGroup.add(this.partiallyCloudyBtn);

			this.overcastBtn = new JToggleButton();
			this.overcastBtn.setPreferredSize(btnSize);
			this.overcastBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.OVERCAST
					.getId()]));
			add(this.overcastBtn);
			this.buttonGroup.add(this.overcastBtn);

			this.rainyBtn = new JToggleButton();
			this.rainyBtn.setPreferredSize(btnSize);
			this.rainyBtn.setIcon(ThemeManager.getIcon(HOIconName.WEATHER[Weather.RAINY.getId()]));
			add(this.rainyBtn);
			this.buttonGroup.add(this.rainyBtn);

			ActionListener al = new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					for (int i = actionListeners.size() - 1; i >= 0; i--) {
						actionListeners.get(i).actionPerformed(e);
					}
				}
			};
			this.sunnyBtn.addActionListener(al);
			this.partiallyCloudyBtn.addActionListener(al);
			this.overcastBtn.addActionListener(al);
			this.rainyBtn.addActionListener(al);
		}
	}

}
