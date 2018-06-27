package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class InterfaceRec implements Serializable {
	private String uid;
	private String name;
	private String status;
	private String intfType;
	private String intfSubType;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIntfType() {
		return intfType;
	}
	public void setIntfType(String intfType) {
		this.intfType = intfType;
	}
	public String getIntfSubType() {
		return intfSubType;
	}
	public void setIntfSubType(String intfSubType) {
		this.intfSubType = intfSubType;
	}

	public InterfaceRec(String uid, String name, String status, String intfType, String intfSubType) {
		this.uid = uid;
		this.name = name;
		this.status = status;
		this.intfType = intfType;
		this.intfSubType = intfSubType;
	}

	public InterfaceRec() {
	}
}
