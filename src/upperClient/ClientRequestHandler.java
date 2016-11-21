package upperClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.UnknownHostException;

import extra.Invocation;

public class ClientRequestHandler {
	private String host;
	private int port;
	private int sentMessageSize;
	private int receivedMessageSize;
	
	private Socket clientSocket =null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer =null;
	
	HttpURLConnection con;
	OutputStream out;
	
	public ClientRequestHandler(String host, int port){
		this.host=host;
		this.port=port;
	}
	
	public ClientRequestHandler(Invocation req) {
		this.host=req.getHost();
		this.port=req.getPort();
	}

	public void sendTCP(byte [] msg) throws InterruptedException, IOException{
		System.out.println(this.host+" "+this.port);
			
		sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg ,0,sentMessageSize);
		//outToServer.flush();
	}
	
	public byte [] receiveTCP() throws IOException{
		byte[] msg = null;
		System.out.println("reading message...");
		receivedMessageSize = 0;
		receivedMessageSize = inFromServer.readInt();
		if(receivedMessageSize!=0){
		msg=new byte [receivedMessageSize];
		inFromServer.read(msg,0,receivedMessageSize);
		}
		System.out.println("message read");
		//clientSocket.close();
		//outToServer.close();
		//inFromServer.close();
		
		return msg;
	}
	
	

	public byte[] receive() throws IOException {
		// TODO Auto-generated method stub
		return receiveTCP();
	}

	public void establishTCP() throws UnknownHostException, IOException {
		clientSocket= new Socket(this.host, this.port);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new DataInputStream(clientSocket.getInputStream());
		clientSocket.setKeepAlive(true);
		clientSocket.setTcpNoDelay(true);
		clientSocket.setSoTimeout(1500);
		
	}
}
