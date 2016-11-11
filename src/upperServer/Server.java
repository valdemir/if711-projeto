package upperServer;



import infraClient.BankProxy;
import extra.NoMoneyException;


public class Server {

	public static void main(String[] args) throws NoMoneyException, Throwable {
		BankInvoker invoker=new BankInvoker();
		BankProxy bank=new BankProxy("192.168.0.101",1515,100);
		
		
		
		invoker.invoke(bank);
		
		
		
	}

}
