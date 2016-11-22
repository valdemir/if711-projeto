package upperClient;

import java.io.IOException;
import infraClient.BankProxy;
import naming.NamingService;
import extra.Requestor;


public class Client {

	public static void main(String[] args) {
			
		try {
			Requestor req;
			req = new Requestor();
			NamingService namingservice=new NamingService("192.168.0.101",1515); 
			
			BankProxy bp=namingservice.lookupClient("bank",req);
			bp.takeMoney(3,(float) 50,req);
			bp.putMoney(3, (float)500, req);
			Float print=bp.getSaldo(3,req);
			System.out.println(print);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("program finished");
		}
		
		
		
		
			
			

	}

}
