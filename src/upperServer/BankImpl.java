package upperServer;

import infraServer.Conta;

import java.util.ArrayList;

import extra.NoMoneyException;
import extra.notEnoughMoney;

public class BankImpl implements Bank{

		ArrayList<Conta> contas=new ArrayList<Conta>(10000);
		public BankImpl(){
			for(int i=0;i<10000;i++){
				contas.add(new Conta());
			}
		}
		
		public Float getSaldo(int id) throws Throwable {
			return this.contas.get(id).getMoney();
			
		}

		public void putMoney(int id, Float valor) throws Throwable {
			this.contas.get(id).put(valor);
			
			
		}

		public boolean hasMoney(int id, Float valor) {
			return this.contas.get(id).hasMoney(valor);
		}

		public void takeMoney(int i, Float param_p2) throws Throwable, NoMoneyException {
			this.contas.get(i).take(param_p2);
			
			
		}

		public void transferMoney(int from,int to,Float valor) throws notEnoughMoney {
			if(this.contas.get(from).hasMoney(valor)){
				this.contas.get(from).take(valor);
				this.contas.get(to).put(valor);
			}else{
				throw new notEnoughMoney();
			}
			
		}

		

		
	}



