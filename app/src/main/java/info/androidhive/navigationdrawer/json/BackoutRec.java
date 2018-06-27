package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class BackoutRec implements Serializable{
	private String interfaceName;
	private String interfaceType;
	private String interfaceSubType;
	private String internalID;
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getInterfaceSubType() {
		return interfaceSubType;
	}
	public void setInterfaceSubType(String interfaceSubType) {
		this.interfaceSubType = interfaceSubType;
	}
	public String getInternalID() {
		return internalID;
	}
	public void setInternalID(String internalID) {
		this.internalID = internalID;
	}

	public BackoutRec(String interfaceName, String interfaceType, String interfaceSubType, String internalID) {
		this.interfaceName = interfaceName;
		this.interfaceType = interfaceType;
		this.interfaceSubType = interfaceSubType;
		this.internalID = internalID;
	}

	public BackoutRec() {
	}
}
