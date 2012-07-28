package ho.module.lineup;

import java.util.Map;

public interface IAufstellungsAssistentPanel {

	boolean isExcludeLastMatch();

	boolean isConsiderForm();

	boolean isIgnoreSuspended();

	String getGroup();

	boolean isGroupFilter();

	boolean isIdealPositionZuerst();

	boolean isNotGroup();

	int getOrder();

	boolean isIgnoreInjured();

	int getWeather();

	void addToAssistant(PlayerPositionPanel positionPanel);

	/**
	 * Returns a Map of statuses from the position selection.
	 * The keys are integers containing roleIDs for the positions.
	 * The values are booleans for whether the role should be included or not.
	 * The map does not contain all positions, only those being sent through filtering.
	 * 
	 * @return The Map
	 */
	 Map<Integer, Boolean> getPositionStatuses();

}