package upperServer;

import java.util.ArrayList;

import naming.NamingService;
import infraClient.BankProxy;
import infraClient.ClientProxy;
import extra.Marshaller;
import extra.NoMoneyException;
import extra.Termination;

public class BankInvoker {
 public void invoke(BankProxy cp) throws NoMoneyException, Throwable{
	 ServerRequestHandler srh=new ServerRequestHandler(cp.getPort());
	 Message msgFinal=null;
	 Marshaller ms= new Marshaller();
	 Termination ter=new Termination();
	 Message answer;
	 BankImpl bk=new BankImpl();
	 NamingService nm=new NamingService("192.168.56.1",5432);
	 nm.Bind("bank", cp);
	 while(true){
		 byte [] msg=srh.receive();
		 if(msg!=null){
			 msgFinal=(Message) ms.unmarshall(msg);
			 Float param_p1,param_p2,param_p3;
			 switch(msgFinal.getBody().getRequestHeader().getOperation()){
			 case "takeMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.takeMoney((int)(param_p1/1), param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(ms.marshall(answer));
				 break;
			 case "putMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.putMoney((int)(param_p1/1), param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(ms.marshall(answer));
				 break;
			 case "hasMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 ter.setResult(bk.hasMoney((int)(param_p1/1), param_p2));
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(ter.getResult())));
				 srh.sendTCP(ms.marshall(answer));
				 break;
			 case "transfer":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 param_p3=(Float) msgFinal.getBody().getRequestBody().getParameters().get(2);
				 bk.transferMoney((int)(param_p1/1), (int)(param_p2/1),param_p3);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(ms.marshall(answer));
				 break;
			 case "lookup":
				 ClientProxy cl=(nm.lookup((String) msgFinal.getBody().getRequestBody().getParameters().get(0)));
				 ArrayList<Object> al=new ArrayList<Object>();
				 al.add(cl.getHost());
				 al.add(cl.getPort());
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(al)));
				 srh.sendTCP(ms.marshall(answer));
			 }
		 }
	 }
 }
}
