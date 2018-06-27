package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class TaskRec implements Serializable {
	private String office;
	private String description;
	private String name;
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
