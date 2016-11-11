package extra;

import infraClient.ClientProxy;

import java.io.Serializable;
import java.util.ArrayList;

public class Invocation implements Serializable{
	/**
	 * 
	 */
	ClientProxy cp;
	private static final long serialVersionUID = 1L;
	public int objectID=0;
	public String host="";
	public int port=0;
	public String methodName="";
	public ArrayList<Object> parameters;
	public String protocol="";
	
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Invocation(){
		parameters=new ArrayList<Object>();
		this.protocol="TCP";
		
	}
	public void setObjectId(int objectID) {
		this.objectID=objectID;
		
	}

	public void setIPAdress(String host) {
		this.host=host;
		
	}

	public void setPortNumber(int port) {
		// TODO Auto-generated method stub
		this.port=port;
	}

	public void setOperationName(String methodName) {
		// TODO Auto-generated method stub
		this.methodName=methodName;
	}

	public void setParameters(ArrayList<Object> parameters) {
		// TODO Auto-generated method stub
		this.parameters=parameters;
	}
	public int getObjectID() {
		return objectID;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getMethodName() {
		return methodName;
	}
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	public void AddParameter(Object a){
		parameters.add(a);
	}
	public ClientProxy getClientProxy() {
		return cp;
	}
	public void SetClientProxy(ClientProxy cp){
		this.cp=cp;
	}
}
