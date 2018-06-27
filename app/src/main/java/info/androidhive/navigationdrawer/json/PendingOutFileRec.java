package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class PendingOutFileRec implements Serializable {
	private String bulkingProfile;
	private String profileName;
	private String mop;
	private String office;
	private String systemSending;
	private String txnCount;
	private String totalTxnAmt;
	public String getBulkingProfile() {
		return bulkingProfile;
	}
	public void setBulkingProfile(String bulkingProfile) {
		this.bulkingProfile = bulkingProfile;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getMop() {
		return mop;
	}
	public void setMop(String mop) {
		this.mop = mop;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getSystemSending() {
		return systemSending;
	}
	public void setSystemSending(String systemSending) {
		this.systemSending = systemSending;
	}
	public String getTxnCount() {
		return txnCount;
	}
	public void setTxnCount(String txnCount) {
		this.txnCount = txnCount;
	}
	public String getTotalTxnAmt() {
		return totalTxnAmt;
	}
	public void setTotalTxnAmt(String totalTxnAmt) {
		this.totalTxnAmt = totalTxnAmt;
	}

}
