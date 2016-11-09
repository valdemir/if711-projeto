package extra;

import java.io.Serializable;

public class AbsoluteObjectReference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultString;
	private boolean resultBoolean;
	private int resultInt;
	private Object result;
	public AbsoluteObjectReference(Object result){
		this.result=result;
	}
	public AbsoluteObjectReference(){
		
	}
	public void addResult(Object r){
		this.result=r;
	}
	public Object getResult() {
		return this.result;
	}
	public int getInt(){
		return this.resultInt;
	}
	public void setInt(int j){
		this.resultInt=j;
	}
	public String getString(){
		return this.resultString;
	}
	public void setString(String j){
		this.resultString=j;
	}
	public boolean getBoolean(){
		return this.resultBoolean;
	}
	public void setBoolean(boolean j){
		this.resultBoolean=j;
	}
}
