package upperServer;

import java.io.Serializable;
import java.util.ArrayList;

public class ReplyBody implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Object> result;
	public ReplyBody(ArrayList<Object> result) {
		this.result=result;
	}

	public ArrayList<Object> getParameters() {
		return this.result;
	}
	public void addParameter(Object r){
		result.add(r);
	}
	public void setParameters(ArrayList<Object> r){
		this.result=r;
	}

	public ArrayList<Object> getOperationResult() {
		// TODO Auto-generated method stub
		return this.result;
	}
	
}
