package upperServer;

import infraClient.BankProxy;
import infraClient.ClientProxy;

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
import javax.crypto.spec.SecretKeySpec;

import naming.NamingService;
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
	 NamingService nm=new NamingService("localhost",1515);
	 SecretKey secretKey=null;
	 byte [] iv=null;
	 nm.Bind("bank", cp);
	 String mode="AES";
	 while(true){
		 byte [] msg=srh.receiveTCP();
		 
		 
		 if(msg!=null){
			 if(secretKey==null){
				 BufferedReader br=new BufferedReader(new FileReader(new File("crypt.txt")));
				 byte [] key = Base64.getDecoder().decode(br.readLine());
				 System.out.println("Key length: "+key.length);
				 if(key.length==16){
					 mode="AES";
					 secretKey = new SecretKeySpec(key, 0, key.length, "AES");
				 }else if(key.length==8){
					 mode="Blowfish/ECB/NoPadding";
					 secretKey= new SecretKeySpec(
						        new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish");
				 }else{
					 
				 }
				 br.close();
				  
			 }
			 Cipher CipherForDecryption = Cipher.getInstance(mode);
			 CipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey);
				byte[] byteDecryptedText = CipherForDecryption
						.doFinal(msg);
			 
			 msgFinal=(Message) ms.unmarshall(byteDecryptedText);
			 Float param_p2,param_p3;
			 int p1,p2;
			 switch(msgFinal.getBody().getRequestHeader().getOperation()){
			 case "takeMoney":
				 p1= (int) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.takeMoney(p1, param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 System.out.println("take money");
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 case "putMoney":
				 p1=(int) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 bk.putMoney(p1, param_p2);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 case "hasMoney":
				 p1=(int) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 param_p2=(Float) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 ter.setResult(bk.hasMoney(p1, param_p2));
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(ter.getResult())));
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 case "transfer":
				 p1=(int) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 p2=(int) msgFinal.getBody().getRequestBody().getParameters().get(1);
				 param_p3=(Float) msgFinal.getBody().getRequestBody().getParameters().get(2);
				 bk.transferMoney(p1, p2,param_p3);
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(null)));
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 case "lookup":
				 ClientProxy cl=(nm.lookup((String) msgFinal.getBody().getRequestBody().getParameters().get(0)));
				 ArrayList<Object> al=new ArrayList<Object>();
				 al.add(cl.getHost());
				 al.add(cl.getPort());
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(al)));
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 case "getSaldo":
				 p1=(int) msgFinal.getBody().getRequestBody().getParameters().get(0);
				 System.out.println(p1);
				 if(bk.getSaldo(p1)!=null){
					 ter.setResult(bk.getSaldo(p1));
				 }else{
					 ter.setResult(0);
				 }
				 answer=new Message(new MessageHeader("TCP", 0, false, 0, 0),new MessageBody(null,null,
						 new ReplyHeader("",0,0),new ReplyBody(ter.getResult())));
				 srh.sendTCP(encrypt(secretKey,iv,answer,mode));
				 break;
			 }
		 }
	 }
 }
 public byte[] encrypt(SecretKey sk,byte[] iv,Message msg,String mode) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, InterruptedException{
	 Marshaller ms=new Marshaller();
	 Cipher aesCipherForEncryption = Cipher.getInstance(mode);
	 aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, sk);
	 byte[] send=ms.marshall(msg);
	 int k=send.length;
	 if(k%8!=0){
		 byte[] complement=new byte[8-(k%8)];
		 byte[] c = new byte[send.length + complement.length];
		 System.arraycopy(send, 0, c, 0, send.length);
		 System.arraycopy(complement, 0, c, send.length, complement.length);
		 send=c;
	 }
	 byte[] byteCipherText = aesCipherForEncryption
				.doFinal(send);
	 return byteCipherText;
 }
}
