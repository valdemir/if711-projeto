package upperClient;

import infraClient.BankProxy;
import naming.NamingService;
import extra.NoMoneyException;


public class Client {

	public static void main(String[] args) throws NoMoneyException, Throwable{
		NamingService namingservice=new NamingService("192.168.0.101",1515); 
		
		BankProxy bp=namingservice.lookupClient("bank");
		bp.takeMoney(3,50);
		
			
		
		
		
			
			

	}

}
