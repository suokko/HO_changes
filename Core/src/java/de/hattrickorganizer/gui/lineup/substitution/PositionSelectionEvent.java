package de.hattrickorganizer.gui.lineup.substitution;

public class PositionSelectionEvent {

	private Integer position;
	private Change change;

	public enum Change {
		SELECTED, DESELECTED;
	}

	public PositionSelectionEvent(Integer position, Change change) {
		this.position = position;
		this.change = change;
	}

	public Integer getPosition() {
		return this.position;
	}

	public Change getChange() {
		return this.change;
	}

}