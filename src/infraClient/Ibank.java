package infraClient;

import java.io.IOException;

import extra.NoMoneyException;
import extra.Requestor;

public interface Ibank {
	public Float getSaldo(int id,Requestor req) throws Throwable;
	
	public void putMoney(int id,Float valor,Requestor req) throws Throwable;
	
	public boolean hasMoney(int id,Float valor,Requestor req) throws Throwable;
	
	public void takeMoney(int id,Float valor,Requestor req) throws Throwable, NoMoneyException;
	
	public void transferMoney(int id,int id2,Float valor,Requestor req) throws Throwable, NoMoneyException,IOException;
}
