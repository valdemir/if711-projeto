package naming;

import infraClient.BankProxy;
import infraClient.ClientProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import extra.Marshaller;
import extra.RequestBody;
import extra.RequestHeader;
import upperClient.ClientRequestHandler;
import upperServer.Message;
import upperServer.MessageBody;
import upperServer.MessageHeader;

public class NamingService {
	public HashMap<String, ClientProxy> names;
	ClientRequestHandler crh= new ClientRequestHandler("192.168.56.1", 5432);
	Marshaller ms=new Marshaller();
	public String host;
	public int port;
	
	public NamingService(String host,int port){
		names=new HashMap<String,ClientProxy>();
		this.host=host;
		this.port=port;
	}
	
	public void Bind(String str,BankProxy bank){
		names.put(str, bank);
	}
	public ClientProxy lookupClient(String str) throws InterruptedException, IOException, ClassNotFoundException{
		ArrayList<Object> ar=new ArrayList<Object>();
		ar.add("bank");
		Message msg=new Message(new MessageHeader(null, 0, false, 0, 0),new MessageBody(new RequestHeader(null, 0, true, 0, "lookup"),new RequestBody(null),null, null));
		crh.sendTCP(ms.marshall(msg));
		
		Message m=(Message)ms.unmarshall(crh.receiveTCP());
		ClientProxy cl=new ClientProxy();
		ArrayList<Object> gg= m.getBody().getReplyBody().getParameters();
		cl.setHost((String) gg.get(0));
		cl.setPort((int) gg.get(1));
		return cl;
	}
	public ClientProxy lookup(String str){
		return names.get(str);
	}
}
