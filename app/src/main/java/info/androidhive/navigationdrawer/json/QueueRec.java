package info.androidhive.navigationdrawer.json;

import java.io.Serializable;

public class QueueRec implements Serializable{

	private String queueName;
	private long count;
	private String office;
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
}
