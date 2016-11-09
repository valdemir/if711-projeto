package naming;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NamingRequestHandler {
	private int portNumber;
	private ServerSocket welcomeSocket =null;
	private Socket connectionSocket = null;
	
	private int sentMessageSize;
	private int receivedMessageSize;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	
	BufferedReader in;
	
	public NamingRequestHandler(int portNumber){
		this.portNumber=portNumber;
	}
	
	public void sendTCP(byte[] msg) throws IOException,InterruptedException{
		sentMessageSize = msg.length;
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		outToClient.flush();
		
		
	}
	
	public byte[] receiveTCP() throws IOException,InterruptedException{
		byte[] msg = null;
		
		welcomeSocket= new ServerSocket(portNumber);
		connectionSocket = welcomeSocket.accept();
		
		//daqui pra frente, ele segue o tcp
		outToClient= new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		receivedMessageSize = inFromClient.readInt();
		msg = new byte[receivedMessageSize];
		inFromClient.read(msg,0,receivedMessageSize);
		
		return msg;
	}
	public void closeConnection() throws IOException{
			connectionSocket.close();
			welcomeSocket.close();
			outToClient.close();
			inFromClient.close();
		
		
	}
	
}
