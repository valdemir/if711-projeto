package infraClient;

import java.io.IOException;

import extra.NoMoneyException;

public interface Ibank {
	public Float getSaldo(int id) throws Throwable;
	
	public void putMoney(int id,int valor) throws Throwable;
	
	public boolean hasMoney(int id,int valor) throws Throwable;
	
	public void takeMoney(int id,int valor) throws Throwable, NoMoneyException;
	
	public void transferMoney(int id,int id2,int valor) throws Throwable, NoMoneyException,IOException;
}
