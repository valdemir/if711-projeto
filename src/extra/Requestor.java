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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import Decoder.BASE64Encoder;
import upperClient.ClientRequestHandler;
import upperServer.Message;
import upperServer.MessageBody;
import upperServer.MessageHeader;

public class Requestor {
	
	Marshaller ms=new Marshaller();
	int aes_keyLength=0;
	byte[] iv;
	SecretKey secretKey;
	ClientRequestHandler crh=new ClientRequestHandler("localhost",1515);
	public Requestor() throws UnknownHostException, IOException{
		crh.establishTCP();
		
	}
	public Termination invoke(Invocation inv)throws InterruptedException, IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		Cipher aesCipherForEncryption;
		if(aes_keyLength==0){
			BufferedReader bf= new BufferedReader(new FileReader(new File("key.txt")));
			String key=bf.readLine();
			if(key.equals("128")){
				aes_keyLength=128;
			}else if(key.equals("1024")){
				aes_keyLength=1024;
			}else{
				aes_keyLength=2048;
			}
				
			bf.close();
			keyGen.init(128);//has to be 128, 192 or 256
			secretKey = keyGen.generateKey();
		}
		
		
			
			iv = new byte[128 / 8];
		if(aes_keyLength==128){
			// Save the IV bytes or send it in plaintext with the encrypted data so you can decrypt the data later
			SecureRandom prng = new SecureRandom();
			
			prng.nextBytes(iv);
			
			aesCipherForEncryption = Cipher.getInstance("AES");
		}else{
			 aesCipherForEncryption = Cipher.getInstance("RSA");
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File("crypt.txt")));
		String cipher=new BASE64Encoder().encode(secretKey.getEncoded());
		bw.write(cipher);
		bw.close();
		aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey);

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
		byte[] byteCipherText = aesCipherForEncryption
				.doFinal(msg1);
		
		
		
		crh.sendTCP(byteCipherText);
		msg2=crh.receiveTCP();
		
		Cipher aesCipherForDecryption = Cipher.getInstance("AES"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!				

		aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] byteDecryptedText = aesCipherForDecryption
				.doFinal(msg2);
		
		
		msgFinal=(Message) ms.unmarshall(byteDecryptedText);
		tm.setResult(msgFinal.getBody().getReplyBody().getOperationResult());
		return tm;
		
	}
	
	
}
