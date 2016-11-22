package extra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;
import upperClient.ClientRequestHandler;
import upperServer.Message;
import upperServer.MessageBody;
import upperServer.MessageHeader;

public class Requestor {
	
	Marshaller ms=new Marshaller();
	int keyLength=0;
	SecretKey secretKey;
	ClientRequestHandler crh=new ClientRequestHandler("localhost",1515);
	public Requestor() throws UnknownHostException, IOException{
		crh.establishTCP();
		
	}
	public Termination invoke(Invocation inv)throws InterruptedException, IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		Cipher CipherForEncryption=null;
		if(keyLength==0){
			BufferedReader bf= new BufferedReader(new FileReader(new File("key.txt")));
			String key=bf.readLine();
			if(key.equals("128")){
				keyLength=128;
				keyGen.init(128);//key size has to be 128, 192 or 256
				secretKey = keyGen.generateKey();
			}else if(key.equals("64")){
				keyLength=64;
				secretKey = new SecretKeySpec(
				        new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish");
			}else{
				keyLength=256;
			}
				
			bf.close();
		}
		
		
			
			
		if(keyLength==128){
			
			CipherForEncryption = Cipher.getInstance("AES");
		}else if(keyLength==64){
			 CipherForEncryption = Cipher.getInstance("Blowfish/ECB/NoPadding");
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File("crypt.txt")));
		String cipher=new BASE64Encoder().encode(secretKey.getEncoded());
		bw.write(cipher);
		bw.close();
		CipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey);

		Marshaller ms=new Marshaller();
		Termination tm=new Termination();
		byte[] msg1;
		byte[] msg2;
		Message msgFinal=new Message();
		RequestHeader rq=new RequestHeader("",0,true,0,inv.getMethodName());
		RequestBody rb=new RequestBody(inv.getParameters());
		MessageHeader mh=new MessageHeader("MIOP",0,false,0,0);
		MessageBody mb=new MessageBody(rq,rb,null,null);
		Message message= new Message(mh,mb);
		
		msg1=ms.marshall(message);
		int k=msg1.length;
		if(k%8!=0){
			byte[] complement=new byte[8-(k%8)];
			 byte[] c = new byte[msg1.length + complement.length];
			 System.arraycopy(msg1, 0, c, 0, msg1.length);
			 System.arraycopy(complement, 0, c, msg1.length, complement.length);
			 msg1=c;
		}
		byte[] byteCipherText = CipherForEncryption.doFinal(msg1);
		crh.sendTCP(byteCipherText);
		
		msg2=crh.receiveTCP();
		Cipher CipherForDecryption =null;
		if(keyLength==128){
			CipherForDecryption=Cipher.getInstance("AES"); 
		}else if(keyLength==64){
			CipherForDecryption=Cipher.getInstance("Blowfish/ECB/NoPadding");
		}

		CipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] byteDecryptedText = CipherForDecryption.doFinal(msg2);
		
		
		msgFinal=(Message) ms.unmarshall(byteDecryptedText);
		tm.setResult(msgFinal.getBody().getReplyBody().getOperationResult());
		return tm;
		
	}
	
	
}
