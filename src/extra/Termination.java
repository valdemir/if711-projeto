package extra;

import java.util.ArrayList;



public class Termination {
	ArrayList<Object> result;
	public Termination(ArrayList<Object>result){
		this.result=result;
	}
	public Termination(){
		
	}

	public ArrayList<Object> getResult() {
		return this.result;
	}
	
	public void setResult(ArrayList<Object> r) {
		this.result=r;
		
	}
	public void setResult(Object takeMoney) {
		result.add(takeMoney);
		
	}
	
}
