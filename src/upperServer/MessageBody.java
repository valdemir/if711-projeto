package upperServer;

import java.io.Serializable;

import extra.RequestBody;
import extra.RequestHeader;

public class MessageBody implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ReplyBody rb;
	ReplyHeader rh;
	RequestBody o1;
	RequestHeader o2;
	public MessageBody(RequestHeader object2, RequestBody object, ReplyHeader replyHeader,
			ReplyBody replyBody) {
		this.o1=object;
		this.o2=object2;
		this.rb=replyBody;
		this.rh=replyHeader;
	}
	public ReplyBody getReplyBody() {
		return rb;
	}
	public void setReplyBody(ReplyBody rb) {
		this.rb = rb;
	}
	public ReplyHeader getReplyHeader() {
		return rh;
	}
	public void setReplyHeader(ReplyHeader rh) {
		this.rh = rh;
	}
	public RequestBody getRequestBody() {
		return o1;
	}
	public void setRequestBody(RequestBody o1) {
		this.o1 = o1;
	}
	public RequestHeader getRequestHeader() {
		return o2;
	}
	public void setRequestHeader(RequestHeader o2) {
		this.o2 = o2;
	}

	

}
