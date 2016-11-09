package infraClient;


import java.io.IOException;
import java.util.ArrayList;

import extra.Invocation;
import extra.NoMoneyException;
import extra.Requestor;
import extra.Termination;

public class BankProxy extends ClientProxy implements Ibank{

	
	private static final long serialVersionUID = 8427265848381431450L;
	String host;
	int port;
	int id;
	
	public BankProxy(String host,int port, int id){
		super(host,port,id);
	}
	public Float getSaldo(int id) throws Throwable {
		Invocation inv=new Invocation();
		Termination ter= new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local{};
		String methodName=null;
		Requestor requestor= new Requestor();
		
		
		methodName=Local.class.getEnclosingMethod().getName();
		parameters.add(id);
		inv.setObjectId(this.getObjectID());
		inv.setIPAdress(this.getHost());
		inv.setPortNumber(this.getPort());
		inv.setOperationName(methodName);
		inv.setParameters(parameters);
		
		ter=requestor.invoke(inv);
		Float j=(Float) ter.getResult().get(0);
		
		return j;
		
	}

	public void putMoney(int id, int valor) throws Throwable {
		Invocation inv=new Invocation();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local{};
		String methodName=null;
		Requestor requestor= new Requestor();
		
		
		methodName=Local.class.getEnclosingMethod().getName();
		parameters.add(id);
		parameters.add(valor);
		inv.setObjectId(this.getObjectID());
		inv.setIPAdress(this.getHost());
		inv.setPortNumber(this.getPort());
		inv.setOperationName(methodName);
		inv.setParameters(parameters);
		
		requestor.invoke(inv);
		
		
	}

	public boolean hasMoney(int id, int valor) throws Throwable {
		Invocation inv=new Invocation();
		Termination ter= new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local{};
		String methodName=null;
		Requestor requestor= new Requestor();
		
		
		methodName=Local.class.getEnclosingMethod().getName();
		parameters.add(id);
		parameters.add(valor);
		inv.setObjectId(this.getObjectID());
		inv.setIPAdress(this.getHost());
		inv.setPortNumber(this.getPort());
		inv.setOperationName(methodName);
		inv.setParameters(parameters);
		
		ter=requestor.invoke(inv);
		return (boolean) ter.getResult().get(0);
	}

	public void takeMoney(int id, int valor) throws Throwable, NoMoneyException {
		Invocation inv=new Invocation();
		ArrayList<Object> parameters = new ArrayList<Object>();
		String methodName=null;
		Requestor requestor= new Requestor();
		
		
		methodName="takeMoney";
		parameters.add(id);
		parameters.add(valor);
		inv.setObjectId(this.getObjectID());
		inv.setIPAdress(this.getHost());
		inv.setPortNumber(this.getPort());
		inv.setOperationName(methodName);
		inv.setParameters(parameters);
		
		requestor.invoke(inv);
		
	}

	public void transferMoney(int id,int id2,int valor) throws Throwable, NoMoneyException,
			IOException {
		Invocation inv=new Invocation();
		ArrayList<Object> parameters = new ArrayList<Object>();
		String methodName=null;
		Requestor requestor= new Requestor();
		
		
		methodName="transferMoney";
		parameters.add(id);
		parameters.add(id2);
		parameters.add(valor);
		inv.setObjectId(this.getObjectID());
		inv.setIPAdress(this.getHost());
		inv.setPortNumber(this.getPort());
		inv.setOperationName(methodName);
		inv.setParameters(parameters);
		
		requestor.invoke(inv);
		
	}

	

}
