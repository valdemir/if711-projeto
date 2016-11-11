package upperClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.URL;

import extra.Invocation;

public class ClientRequestHandler {
	private String host;
	private int port;
	private int sentMessageSize;
	private int receivedMessageSize;
	
	private Socket clientSocket =null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer =null;
	
	private DatagramSocket clientUDPSocket = null;
	private DatagramPacket receivedDatagramPacket = null;
	private DatagramPacket sentDatagramPacket = null;
	private byte[] receivedData = new byte[1024];
//	private byte[] sentData = new byte[1024];
	private InetAddress serverIPAddress = null;
	
	
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
		try{
			clientSocket= new Socket(this.host, this.port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			
		}catch(Exception e){
			
		}
		
		//daqui pra frente, ele segue o modelo tcp
		
		sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg,0,sentMessageSize);
		outToServer.flush();
	}
	
	public byte [] receiveTCP() throws IOException{
		byte[] msg = null;
		System.out.println("reading message...");
		receivedMessageSize = inFromServer.readInt();
		msg=new byte [receivedMessageSize];
		inFromServer.read(msg,0,receivedMessageSize);
		System.out.println("message read");
		//clientSocket.close();
		//outToServer.close();
		//inFromServer.close();
		
		return msg;
	}
	
	public void sendUDP(byte [] msg) throws InterruptedException, IOException{
		clientUDPSocket = new DatagramSocket();
		
		serverIPAddress = InetAddress.getByName(this.host);
		sentDatagramPacket = new DatagramPacket(msg, msg.length, serverIPAddress, this.port);
		clientUDPSocket.send(sentDatagramPacket);
	}
	
	public byte [] receiveUDP() throws IOException{
		byte[] msg = null;
		
		receivedDatagramPacket = new DatagramPacket(receivedData, receivedData.length);
		clientUDPSocket.receive(receivedDatagramPacket);
		String msgAux = new String(receivedDatagramPacket.getData());
		msg = msgAux.getBytes();
		
		clientUDPSocket.close();
		return msg;
	}
	public void sendHTTP(byte[] message) throws IOException{
		URL url = new URL("http://localhost:"+this.port);
		con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setUseCaches(false);
		String test = "<name>"+message+"</name>";
		byte[] bytes = test.getBytes();
		con.setRequestProperty("Content-length", String.valueOf(bytes.length));
		con.setRequestProperty("Content-type", "text/html");
		out = con.getOutputStream();
		out.write(bytes);
		out.flush();
	}
	public String receiveHTTP() throws IOException{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String temp="";
		while((temp = in.readLine()) != null);
		System.out.println(temp);
		out.close();
		in.close();
		con.disconnect();
		return temp;
		
	}

	public byte[] receive() throws IOException {
		// TODO Auto-generated method stub
		return receiveTCP();
	}
}
