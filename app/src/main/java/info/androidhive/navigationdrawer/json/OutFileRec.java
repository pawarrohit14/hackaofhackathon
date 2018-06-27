package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class OutFileRec implements Serializable {
	private String internalID;
	private String mop;
	private String paymentsInFile;
	private String office;
	private String scheduledTime;
	private String outGroupID;
	private String status;
	public String getInternalID() {
		return internalID;
	}
	public void setInternalID(String internalID) {
		this.internalID = internalID;
	}
	public String getMop() {
		return mop;
	}
	public void setMop(String mop) {
		this.mop = mop;
	}
	public String getPaymentsInFile() {
		return paymentsInFile;
	}
	public void setPaymentsInFile(String paymentsInFile) {
		this.paymentsInFile = paymentsInFile;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getOutGroupID() {
		return outGroupID;
	}
	public void setOutGroupID(String outGroupID) {
		this.outGroupID = outGroupID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
