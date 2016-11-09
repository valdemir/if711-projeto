package upperServer;



import naming.NamingService;
import infraClient.BankProxy;


import extra.NoMoneyException;


public class Server {

	public static void main(String[] args) throws NoMoneyException, Throwable {
		BankInvoker invoker=new BankInvoker();
		BankProxy bank=new BankProxy("192.168.56.1",5432,100);
		
		
		
		invoker.invoke(bank);
		
		
		
	}

}
