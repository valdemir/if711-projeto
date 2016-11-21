package upperServer;

import extra.NoMoneyException;
import extra.notEnoughMoney;

public class BankImpl implements Bank{

		Float[] contas=new Float[10000];
		public BankImpl(){
			for(int i=0;i<10000;i++){
				contas[i]=(float)0;
			}
		}
		
		public Float getSaldo(int id){
			try{
				return this.contas[id];
			}catch(Exception e){
				return (float) 0;
			}
			
		}

		public void putMoney(int id, Float valor) throws Throwable {
			this.contas[id]+=valor;
			
			
		}

		public boolean hasMoney(int id, Float valor) {
			return this.contas[id]>=valor;
		}

		public void takeMoney(int i, Float param_p2) throws Throwable, NoMoneyException {
			this.contas[i]-=param_p2;
			
			
		}

		public void transferMoney(int from,int to,Float valor) throws Throwable {
			if(hasMoney(from,valor)){
				takeMoney(from,valor);
				putMoney(to,valor);
			}else{
				throw new notEnoughMoney();
			}
			
		}

		

		
	}



