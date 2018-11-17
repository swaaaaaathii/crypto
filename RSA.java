import java.util.*;
import java.math.*;
import java.security.SecureRandom;

public class RSA {

    public static int message;
    
    public static BigInteger p_big,q_big,phi_big,e_val,d_val,n_val;
    public static BigInteger encryptText,decryptText;
    
    public static BigInteger encrypt(int text){
    	//C = m^e mod n.
        BigInteger encryption=(BigDecimal.valueOf(text).toBigInteger()).modPow(e_val, n_val);
        return encryption;
    }
    
    public static BigInteger decrypt(BigInteger text)
    {
    	// m = C^d mod n
        BigInteger decryption=(text).modPow(d_val, n_val);
        return decryption;
    }
    
    public static boolean isPrime(BigInteger b)
    {
        int iterations=-1;
        if ((b.intValue()==0)||(b.intValue()==1))
            return false;
        /** base case - 2 is prime **/
        if (b.intValue()==2)
            return true;
        /** an even number other than 2 is composite **/
        if (b.mod(BigInteger.valueOf(2)).intValue()==0)
            return false;
        BigInteger s;
        s=b.subtract(BigInteger.ONE);
        while (s.mod(BigInteger.valueOf(2)).intValue()==0)
        {
            s=s.divide(BigInteger.valueOf(2));
            iterations++;
        }
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i <=iterations; i++)
        {
            BigInteger r = new BigInteger(14,rand);            
            BigInteger a = (r.mod(b.subtract(BigInteger.ONE))).add(BigInteger.ONE);
            BigInteger temp = s;
            BigInteger mod = a.modPow(temp, b);
            while (temp.intValue() != b.intValue()-1 && mod.intValue() != 1 && mod.intValue() != b.intValue()-1)
            {
                mod = mulMod(mod, mod, b);
                temp=temp.multiply(BigInteger.valueOf(2));
            }
            if (mod.intValue() != b.intValue()-1 && (temp.mod(BigInteger.valueOf(2))).intValue() == 0)
            {
                return false;
            }
        }
        return true;
    }
    
    public static BigInteger mulMod(BigInteger a, BigInteger b, BigInteger mod) 
    {
        return a.multiply(b).mod(mod);
    }
    
    public static void keyGeneration()
            
    {
    	BigInteger gcd;
    	//n=pxq
        n_val=p_big.multiply(q_big);
        //phi(n)=(p-1)x(q-1)
        phi_big=(p_big.subtract(BigInteger.ONE)).multiply(q_big.subtract(BigInteger.ONE));
        SecureRandom rand=new SecureRandom();
        //1<e<phi(n) and gcd(e,phi(n))=1
        while(true)
        {
           e_val=new BigInteger(n_val.bitLength(),rand);
           gcd = e_val.gcd(phi_big);
           if(gcd.intValue()==1)
              break;
        }
        System.out.println("E:"+e_val);
        //1<d<phi(n) and ed=1(mod phi(n))
        d_val=e_val.modInverse(phi_big);
        System.out.println("D:"+d_val);
    }
    
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter the message(digits): ");
       message=sc.nextInt();
       SecureRandom random = new SecureRandom();
       while(true) {
	        p_big = new BigInteger(15,random);
	        if(isPrime(p_big)) break;
       }
       System.out.println("P:"+p_big);
       while(true) {
            q_big = new BigInteger(15,random);
            if(isPrime(q_big)) break;   
       }
       System.out.println("Q:"+q_big);
       keyGeneration();
       encryptText=encrypt(message);
       System.out.println("After encryption: "+encryptText);
       decryptText=decrypt(encryptText);
       System.out.println("After decryption: "+decryptText); 
    }
}
