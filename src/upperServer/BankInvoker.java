package upperServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import naming.NamingService;
import infraClient.BankProxy;
import infraClient.ClientProxy;
import extra.Marshaller;
import extra.NoMoneyException;
import extra.Termination;

public class BankInvoker {
 public void invoke(BankProxy cp) throws NoMoneyException, Throwable{
	 ServerRequestHandler srh=new ServerRequestHandler(1515);
	 Message msgFinal=null;
	 Marshaller ms= new Marshaller();
	 Termination ter=new Termination();
	 Message answer;
	 BankImpl bk=new BankImpl();
	 NamingService nm=new NamingService("192.168.0.101",1515);
	 SecretKey secretKey=null;
	 byte [] iv=null;
	 nm.Bind("bank", cp);
	 while(true){
		 byte [] msg=srh.receiveTCP();
		 Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS7PADDING");
		 if(msg!=null){
			 if(secretKey==null){
				 BufferedReader br=new BufferedReader(new FileReader(new File("crypt.txt")));
				 byte [] key = Base64.getDecoder().decode(br.readLine());
				 secretKey = new SecretKeySpec(key, 0, key.length, "AES"); 
				 iv = Base64.getDecoder().decode(br.readLine());
				 br.close();
				  
			 }
			 aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
						new IvParameterSpec(iv));
				byte[] byteDecryptedText = aesCipherForDecryption
						.doFinal(msg);
			 
			 msgFinal=(Message) ms.unmarshall(byteDecryptedText);
			 Float param_p1,param_p2,param_p3;
			 switch(msgFinal.getBody().getRequestHeader().getOperation()){
			 case "takeMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.takeMoney((int)(param_p1/1), param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 
				 srh.sendTCP(encrypt(secretKey,iv,answer));
				 break;
			 case "putMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.putMoney((int)(param_p1/1), param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(encrypt(secretKey,iv,answer));
				 break;
			 case "hasMoney":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 ter.setResult(bk.hasMoney((int)(param_p1/1), param_p2));
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(ter.getResult())));
				 srh.sendTCP(encrypt(secretKey,iv,answer));
				 break;
			 case "transfer":
				 param_p1=(Float) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 param_p3=(Float) msgFinal.getBody().getRequestBody().getParameters().get(2);
				 bk.transferMoney((int)(param_p1/1), (int)(param_p2/1),param_p3);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(encrypt(secretKey,iv,answer));
				 break;
			 case "lookup":
				 ClientProxy cl=(nm.lookup((String) msgFinal.getBody().getRequestBody().getParameters().get(0)));
				 ArrayList<Object> al=new ArrayList<Object>();
				 al.add(cl.getHost());
				 al.add(cl.getPort());
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(al)));
				 srh.sendTCP(encrypt(secretKey,iv,answer));
			 }
		 }
	 }
 }
 public byte[] encrypt(SecretKey sk,byte[] iv,Message msg) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, InterruptedException{
	 Marshaller ms=new Marshaller();
	 Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS7PADDING");
	 aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, sk, 
				new IvParameterSpec(iv));
	 byte[] send=ms.marshall(msg);
	 byte[] byteCipherText = aesCipherForEncryption
				.doFinal(send);
	 return byteCipherText;
 }
}
