package upperServer;

import java.io.Serializable;

public class ReplyHeader implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceContext;
	private int RequestID;
	private int ReplyStatus;
	public ReplyHeader(String serviceContext, int requestID, int replyStatus) {
		super();
		this.serviceContext = serviceContext;
		RequestID = requestID;
		ReplyStatus = replyStatus;
	}
	public String getServiceContext() {
		return serviceContext;
	}
	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}
	public int getRequestID() {
		return RequestID;
	}
	public void setRequestID(int requestID) {
		RequestID = requestID;
	}
	public int getReplyStatus() {
		return ReplyStatus;
	}
	public void setReplyStatus(int replyStatus) {
		ReplyStatus = replyStatus;
	}
	

}
