package extra;

import java.io.Serializable;

public class RequestHeader implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String context;
	int requestId;
	boolean responseExpected;
	int objectKey;
	String operation;
	public RequestHeader(String context, int requestId,
			boolean responseExpected, int objectKey, String operation) {
		super();
		this.context = context;
		this.requestId = requestId;
		this.responseExpected = responseExpected;
		this.objectKey = objectKey;
		this.operation = operation;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public boolean isResponseExpected() {
		return responseExpected;
	}
	public void setResponseExpected(boolean responseExpected) {
		this.responseExpected = responseExpected;
	}
	public int getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(int objectKey) {
		this.objectKey = objectKey;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	

}
