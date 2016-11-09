package extra;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestBody implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Object> parameters;
	
	public ArrayList<Object> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}

	public RequestBody(ArrayList<Object> parameters) {
		this.parameters=parameters;
	}

}
