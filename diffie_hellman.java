import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class diffie_hellman {
	
	private static BigInteger p,g,xa,ya,xb,yb,K;
	public static BigInteger two = BigInteger.valueOf(2);
	
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
	
	private static ArrayList<BigInteger> primefactors(BigInteger n){
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
		int flag=0;
		//Check if 2 is a factor and divide repeatedly
		while(n.mod(two).intValue()==0) {
			n = n.divide(two);
			flag=1;
		}
		if(flag==1) factors.add(two);
		//n is now an odd number
		long num = n.longValue();
		long sqrt = (long)Math.sqrt(num);
		BigInteger number;
		for(long i=3; i<sqrt; i+=2) {
			number = BigInteger.valueOf(i);
			if(n.mod(number).intValue()==0) {
				n = n.divide(number);
				factors.add(number);
			}
		}
		if(n.intValue()!=1)	factors.add(n);
		return factors;
	}
	
	public static BigInteger primitiveroot(BigInteger p) {
		BigInteger g;
		BigInteger phi_n = p.subtract(BigInteger.ONE);
		System.out.println("Euler totient: "+phi_n);
		int flag = 1;
		ArrayList<BigInteger> factors = primefactors(phi_n);
		while(true) {
			//generate a random number between 2 and phi
			SecureRandom rand = new SecureRandom();
			g = new BigInteger(12,rand);
			if(g.compareTo(two)<0||g.compareTo(phi_n)>0)
				g = two;
			for(int i=0; i<factors.size(); i++) {
				BigInteger power = phi_n.divide(factors.get(i));
				//if g^((p-1)/factor)mod p is 1, then g is not a primitive root
				if(g.modPow(power,p).intValue()==1) {
					flag = 0;
					break;
				}
			}
			if(flag==1) break;
		}
		return g;
	}
	 
	public static void main(String[] args) {
		SecureRandom rand = new SecureRandom();
		while(true) {
			p = new BigInteger(24,rand);
			if(isPrime(p))	break;
		}
		System.out.println("p: "+p);
		g = primitiveroot(p);
		System.out.println("g: "+g);
		while(true) {
			xa = new BigInteger(24,rand);
			if(xa.compareTo(p)<0) break;
		}
		while(true) {
			xb = new BigInteger(24,rand);
			if(xb.compareTo(p)<0) break;
		}
		ya = g.modPow(xa,p);
		yb = g.modPow(xb, p);
		System.out.println("Secret key of A(xA): "+xa);
		System.out.println("Public key of A(yA): "+ya);
		System.out.println("Secret key of B(xB): "+xb);
		System.out.println("Public key of B(yB): "+yb);
		K = yb.modPow(xa,p);
		System.out.println("A's shared secret key: "+K);
		K = ya.modPow(xb, p);
		System.out.println("B's shared secret key: "+K);
	}

}
