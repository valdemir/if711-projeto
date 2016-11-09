package upperServer;

import java.io.Serializable;

public class MessageHeader implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String magic;
	public int version;
	public boolean byteOrder;
	public int messageType;
	public int messageSize;
	public MessageHeader(String magic, int version, boolean byteOrder,
			int messageType, int messageSize) {
		super();
		this.magic = magic;
		this.version = version;
		this.byteOrder = byteOrder;
		this.messageType = messageType;
		this.messageSize = messageSize;
	}
	public String getMagic() {
		return magic;
	}
	public void setMagic(String magic) {
		this.magic = magic;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isByteOrder() {
		return byteOrder;
	}
	public void setByteOrder(boolean byteOrder) {
		this.byteOrder = byteOrder;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public int getMessageSize() {
		return messageSize;
	}
	public void setMessageSize(int messageSize) {
		this.messageSize = messageSize;
	}

	

	

}
