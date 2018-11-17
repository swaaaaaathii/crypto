import java.math.BigInteger;
import java.util.Scanner;

public class DES {
	
	static int pc1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
    static int pc2[] = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
	static int shifts[] = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
	static int ip[] = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};
	static int e[] = {32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
	static int s[][][] = {{{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}}
						,{{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}}
						,{{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}}
						,{{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},{3,15,0,6,10,1,13,8 ,9,4,5,11,12,7,2,14}}
						,{{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}}
						,{{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}}
						,{{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}}
						,{{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}}};
	
	static int ip_inverse[] = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
	static int p[] = {16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};
	private static String keys[] = new String[16];
   
	private static String bintoHex(String arg) {
        return new BigInteger(arg, 2).toString(16);
    }
    
    private static String hexToBin(String s) {
        String bin = new BigInteger(s, 16).toString(2);
        String zeroes = "";
        if(bin.length()!=64){
            zeroes = addzeroes(bin.length(),64);
        }
        return zeroes+bin;
    }
    
    private static String addzeroes(int len, int req_len) {
    	int loop = req_len-len;
    	String zeroes="";
        for(int i=0; i<loop; i++){
            zeroes += "0";
        }
        return zeroes;
    }
    
    private static void keygeneration(String key) {
    	String permuted_key = "";
    	String c[] = new String[17];
    	String d[] = new String[17];
    	for(int i=0; i<56; i++) {
    		permuted_key += key.charAt(pc1[i]-1);
    	}
    	c[0] = permuted_key.substring(0,28);
    	d[0] = permuted_key.substring(28);
    	for(int i=1; i<=16; i++) {
    		c[i] = c[i-1].substring(shifts[i-1]) + c[i-1].substring(0,shifts[i-1]);
    		d[i] = d[i-1].substring(shifts[i-1]) + d[i-1].substring(0,shifts[i-1]);
    		keys[i-1] = "";
    		for(int j=0; j<48; j++) 
    			keys[i-1] += (c[i]+d[i]).charAt(pc2[j]-1);
    		System.out.println("Subkey"+i+": "+keys[i-1]);
    	}
    }
    
    private static String sbox(String str, int i) {
    	String s_row = ""+str.charAt(0) + str.charAt(5);
    	String s_col = str.substring(1, 5);
    	int row = Integer.parseInt(s_row,2);
    	int col = Integer.parseInt(s_col,2);
    	int result = s[i][row][col];
    	String bin_result = Integer.toBinaryString(result);
    	return addzeroes(bin_result.length(),4) + bin_result;
    }
    
    private static String f(String r, String k) {
    	String eR = "";
    	for(int i=0; i<48; i++) {
    		eR += r.charAt(e[i]-1);
    	}
    	String exor_result = exor(k,eR,48);
    	String b[] = new String[8];
    	int x=0, y=6;
    	for(int i=0; i<8; i++) {
    		b[i] = exor_result.substring(x, y);
    		x=y;
    		y+=6;
    	}
    	String s = "";
    	for(int i=0; i<8; i++) {
    		s += sbox(b[i],i);
    	}
    	String f = "";
    	for(int i=0; i<32; i++) {
    		f += s.charAt(p[i]-1);
    	}
    	return f;
    }
    
    private static String exor(String s1, String s2, int len) {
    	BigInteger lval = new BigInteger(s1,2);
		BigInteger rval = new BigInteger(s2,2);
		String result = lval.xor(rval).toString(2);
		result=addzeroes(result.length(),len)+result;
		return result;
    }
    
    private static String encode64(String message, int code) {
    	String permuted_message = "";
    	for(int i=0; i<64; i++) {
    		permuted_message += message.charAt(ip[i]-1);
    	}
    	String l[] = new String[17];
    	String r[] = new String[17];
    	l[0] = permuted_message.substring(0,32);
    	r[0] = permuted_message.substring(32);
    	for(int i=1; i<=16; i++) {
    		l[i] = r[i-1];
    		if(code==1)
    			r[i] = exor(l[i-1],f(r[i-1],keys[i-1]),32);
    		else if(code==0)
    			r[i] = exor(l[i-1],f(r[i-1],keys[15-(i-1)]),32);
    	}
    	String rl16 = r[16] + l[16];
    	String bin_string = "";
    	for(int i=0; i<64; i++) {
    		bin_string += rl16.charAt(ip_inverse[i]-1);
    	}
    	String hexStr = bintoHex(bin_string);
    	return hexStr;
    }
    
    public static String encryption(String hex_message, String hex_key) {
    	float len = (float)hex_message.length()/16;
        int size = (int)Math.ceil(len);
    	String bin_key = hexToBin(hex_key);
        System.out.println("\nBinary key: "+bin_key);
        keygeneration(bin_key);
        int x=0, y;
        if(hex_message.length()<16) y=hex_message.length();
        else y=16;
        String encrypted = "";
        for(int i=0; i<size; i++) {
        	String bin_message = hexToBin(hex_message.substring(x, y));
        	System.out.println("Binary message " + (i+1) + ": "+bin_message);
        	encrypted += encode64(bin_message,1);
        	x=y;
        	if(i==size-2)
        		y=hex_message.length();
        	else
        		y += 16;
        }
        return encrypted;
    }
    
    public static String decryption(String hex_message, String hex_key) {
    	float len = (float)hex_message.length()/16;
        int size = (int)Math.ceil(len);
    	String bin_key = hexToBin(hex_key);
        System.out.println("\nBinary key: "+bin_key);
        keygeneration(bin_key);
        int x=0, y;
        if(hex_message.length()<16) y=hex_message.length();
        else y=16;
    	String decrypted = "";
    	for(int i=0; i<size; i++) {
    		String bin_message = hexToBin(hex_message.substring(x, y));
    		System.out.println("Binary decryption message " + (i+1) + ": "+bin_message);
    		decrypted += encode64(bin_message,0);
    		x=y;
        	if(i==size-2)
        		y=hex_message.length();
        	else
        		y += 16;
    	}
    	decrypted = addzeroes(decrypted.length(),16) + decrypted;
    	return decrypted;
    }
    
    public static void main(String[] args) {
        System.out.println("Enter the hexadecimal plaintext: ");
        Scanner in = new Scanner(System.in);    
        String hex_message = in.next();
        System.out.println("1. Single DES	2. Double DES	3. Triple DES	4. Exit");
        System.out.print("Enter mode of encryption: ");
        int choice = in.nextInt();
        String encrypted = new String();
        String decrypted = new String();
        switch(choice) {
        case 1:
        	System.out.println("Enter the hexadecimal key: ");
            String hex_key = in.next();
            encrypted = encryption(hex_message,hex_key);
            System.out.println("Encrypted string: "+encrypted);
            decrypted = decryption(encrypted,hex_key);
            System.out.println("Decrypted string: "+decrypted);
            break;
        case 2:
        	System.out.println("Enter the two keys: ");
        	String hex_key1 = in.next();
        	String hex_key2 = in.next();
        	System.out.println("\nFirst encryption: ");
        	String temp = encryption(hex_message,hex_key1);
        	System.out.println("Encrypted string: "+temp);
        	System.out.println("\nSecond encryption: ");
        	encrypted = encryption(temp,hex_key2);
        	System.out.println("Encrypted string: "+encrypted);
        	System.out.println("\n\nFirst decryption: ");
        	temp = decryption(encrypted,hex_key2);
        	System.out.println("Decrypted string: "+temp);
        	System.out.println("\nSecond decryption: ");
        	decrypted = decryption(temp,hex_key1);
        	System.out.println("Decrypted string: "+decrypted);
        	break;
        case 3:
        	System.out.println("Enter the two keys: ");
        	String hex_key_1 = in.next();
        	String hex_key_2 = in.next();
        	System.out.println("\nEncryption: ");
        	encrypted = encryption(decryption(encryption(hex_message,hex_key_1),hex_key_2),hex_key_1);
        	System.out.println("Encrypted string: "+encrypted);
        	System.out.println("\nDecryption: ");
        	decrypted = decryption(encryption(decryption(encrypted,hex_key_1),hex_key_2),hex_key_1);
        	System.out.println("Decrypted string: "+decrypted);
        	break;
        case 4:
        	System.exit(0);
        	default:
        		System.out.println("Error in input");
        }
        in.close();
    }
}
