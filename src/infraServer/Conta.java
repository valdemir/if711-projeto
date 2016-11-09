package infraServer;

import java.io.Serializable;

public class Conta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5957607315974756955L;
	Float dinheiro;
	public Conta(){
		this.dinheiro=(float) 0.0;
	}
	public Conta(Float v){
		this.dinheiro=v;
	}
	public void take(Float param_p2){
		dinheiro-=param_p2;
	}
	public void put(Float valor){
		dinheiro+=valor;
	}
	public boolean hasMoney(Float valor){
		return dinheiro>=valor;
	}
	public Float getMoney() {
		return dinheiro;
	}
}
