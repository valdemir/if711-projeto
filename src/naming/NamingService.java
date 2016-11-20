package naming;

import infraClient.BankProxy;
import infraClient.ClientProxy;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import extra.Invocation;
import extra.Marshaller;
import extra.Requestor;
import extra.Termination;

public class NamingService {
	public HashMap<String, ClientProxy> names;
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
	public BankProxy lookupClient(String str,Requestor crh) throws InterruptedException, IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		ArrayList<Object> ar=new ArrayList<Object>();
		ar.add("bank");
		Invocation inv=new Invocation();
		BankProxy cl=new BankProxy("192.168.0.101", 1515,1000);
		inv.SetClientProxy(cl);
		inv.setOperationName("lookup");
		inv.setParameters(ar);
		Termination i=crh.invoke(inv);
		ArrayList<Object> gg=i.getResult();
		cl.setHost((String) gg.get(0));
		cl.setPort((int) gg.get(1));
		return cl;
	}
	public ClientProxy lookup(String str){
		return names.get(str);
	}
}
