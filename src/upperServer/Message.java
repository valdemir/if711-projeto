package upperServer;

import java.io.Serializable;

public class Message implements Serializable{
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1138639749409031480L;
	/**
	 * 
	 */
	
	MessageHeader mh;
	MessageBody mb;
	public Message(MessageHeader messageHeader, MessageBody messageBody) {
		this.mh=messageHeader;
		this.mb=messageBody;
	}

	

	public Message() {
		// TODO Auto-generated constructor stub
	}



	public MessageBody getBody() {
		// TODO Auto-generated method stub
		return mb;
	}
	public MessageHeader getHeader(){
		return mh;
	}
	

}
