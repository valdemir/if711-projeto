package extra;
import java.security.SecureRandom;



public class SecureRandomGen {

	/**
	 * @param args
	 */
	public double generate(int bit) {
		double i=0;
		try {
	        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	        byte[] bytes = new byte[bit];
	        secureRandom.nextBytes(bytes); 
	        i=secureRandom.nextDouble();
	        
	    } catch (Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}

}
