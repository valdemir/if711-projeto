package extra;

import java.io.IOException;
import java.net.UnknownHostException;

import upperClient.ClientRequestHandler;
import upperServer.Message;
import upperServer.MessageBody;
import upperServer.MessageHeader;

public class Requestor {
	
	Marshaller ms=new Marshaller();
	ClientRequestHandler crh=new ClientRequestHandler("192.168.0.101",1515);
	public Requestor() throws UnknownHostException, IOException{
		crh.establishTCP();
		
	}
	public Termination invoke(Invocation inv)throws InterruptedException, IOException, ClassNotFoundException{
		Marshaller ms=new Marshaller();
		Termination tm=new Termination();
		byte[] msg1;
		byte[] msg2;
		Message msgFinal=new Message();
		RequestHeader rq=new RequestHeader("",0,true,0,inv.getMethodName());
		RequestBody rb=new RequestBody(inv.getParameters());
		MessageHeader mh=new MessageHeader("MIOP",0,false,0,0);
		MessageBody mb=new MessageBody(rq,rb,null,null);
		Message message= new Message(mh,mb);
		
		msg1=ms.marshall(message);
		
		crh.sendTCP(msg1);
		msg2=crh.receiveTCP();
		msgFinal=(Message) ms.unmarshall(msg2);
		tm.setResult(msgFinal.getBody().getReplyBody().getOperationResult());
		return tm;
		
	}
	
	
}
