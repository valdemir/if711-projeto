package upperClient;

import infraClient.BankProxy;
import naming.NamingService;
import extra.NoMoneyException;
import extra.Requestor;


public class Client {

	public static void main(String[] args) throws NoMoneyException, Throwable{
		NamingService namingservice=new NamingService("192.168.0.101",1515); 
		Requestor req=new Requestor();
		BankProxy bp=namingservice.lookupClient("bank",req);
		bp.takeMoney(3,50,req);
		
			
		
		
		
			
			

	}

}
