package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class EventStatusRec implements Serializable {
	private String name;
	private String description;
	private String mode;
	private String eventStatus;
	private String frequency;
	private String recStatus;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	

}
