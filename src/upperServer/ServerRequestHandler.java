package upperServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {
	private int portNumber;
	private ServerSocket welcomeSocket =null;
	private Socket connectionSocket = null;
	
	private int sentMessageSize;
	private int receivedMessageSize=0;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	BufferedReader in;

	public ServerRequestHandler(int portNumber) throws IOException{
		this.portNumber=portNumber;
		establishTCP();
	}
	
	private void establishTCP() throws IOException {
		// TODO Auto-generated method stub
		welcomeSocket= new ServerSocket(portNumber);
		connectionSocket = welcomeSocket.accept();
		//connectionSocket.setKeepAlive(true);
		//connectionSocket.setTcpNoDelay(true);
		//connectionSocket.setSoTimeout(1500);
		outToClient= new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		//System.out.println();
	}

	public void sendTCP(byte[] msg) throws IOException,InterruptedException{
		sentMessageSize = msg.length;
		System.out.println("message size: "+sentMessageSize);
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		//outToClient.flush();
		
		//connectionSocket.close();
		//welcomeSocket.close();
		//outToClient.close();
		//inFromClient.close();
				
	}
	
	public byte[] receiveTCP() throws IOException,InterruptedException{
		byte[] msg = null;
		
		receivedMessageSize = inFromClient.readInt();
		msg = new byte[receivedMessageSize];
		inFromClient.read(msg,0,receivedMessageSize);
		
		return msg;
	}
	
	
	
	public byte[] receive() throws IOException, InterruptedException {
		
		return this.receiveTCP();
	}
}
