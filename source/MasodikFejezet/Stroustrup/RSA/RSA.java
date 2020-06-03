import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator; 
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
    

public class RSA {
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger privateKey;
   private BigInteger publicKey;
   private BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   RSA(int N) {
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      modulus    = p.multiply(q);                                  
      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      privateKey = publicKey.modInverse(phi);
   }


   BigInteger encrypt(byte[] bytes) {
       BigInteger swap = new BigInteger(bytes);
      return swap.modPow(publicKey, modulus);
   }

   BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus);
   }

   public String toString(BigInteger decrypt) {
	   
	   byte[] array = decrypt.toByteArray();
	   if (array[0] == 0) {
	       byte[] tmp = new byte[array.length - 1];
	       System.arraycopy(array, 1, tmp, 0, tmp.length);
	       array = tmp;
	   }
	   String str = new String(array, StandardCharsets.UTF_8);
	   return str;
   }
 
   public static void main(String[] args) {
	   
    int N = Integer.parseInt(args[0]);
    RSA key = new RSA(N);
    System.out.println("key = " + key);

    // create random message, encrypt and decrypt
    // BigInteger message = new BigInteger(N-1, random);

    //// create message by converting string to integer
    String s = "The story of Shakespeare's Hamlet was derived from the legend of Amleth, preserved by 13th-century chronicler Saxo Grammaticus in his Gesta Danorum, as subsequently retold by the 16th-century scholar François de Belleforest. Shakespeare may also have drawn on an earlier Elizabethan play known today as the Ur-Hamlet, though some scholars believe Shakespeare wrote the Ur-Hamlet, later revising it to create the version of Hamlet we now have. He almost certainly wrote his version of the title role for his fellow actor, Richard Burbage, the leading tragedian of Shakespeare's time. In the 400 years since its inception, the role has been performed by numerous highly acclaimed actors in each successive century.";
    byte[] bytes = s.getBytes();
    //BigInteger message = new BigInteger(bytes);
    //BigInteger result = new BigInteger("0");


    List<BigInteger> result = new ArrayList<BigInteger>();
    
    //list.add(new BigInteger("23456"));


    byte[] atmenet = new byte[1];
    for(int i = 0; i < bytes.length; i++)
    {
        atmenet[0] = bytes[i];
        result.add(key.encrypt(atmenet));
        //result = key.encrypt(atmenet);
    }


    //BigInteger encrypt = key.encrypt(message);
    //BigInteger decrypt = key.decrypt(result);
    System.out.println("message   = " + s);
    System.out.println("encrypted = " + result);
    //System.out.println("decrypted = " + key.toString(decrypt));
   }
}
