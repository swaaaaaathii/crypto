import java.util.Scanner;

/**
 *
 * @author swa
 */
public class PlayFair {
    public static char pfmatrix[][] = new char[5][5];
    
    //To display matrix
    public static void display(char array[][]){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    //Initialising the playfair matrix
    public static void initmatrix(String key){
        int keyval = 0;
        String alphabets = "abcdefghijklmnopqrstuvwXyz";
	//Eliminate alphabets present in key
        for(int i=0; i<key.length();i++)
            alphabets = alphabets.replace(String.valueOf(key.charAt(i)),"");
  	//Fill in matrix with key and remaining alphabets
        key+=alphabets;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                pfmatrix[i][j] = key.charAt(keyval);
                keyval++;
            }
        }
    }
    
    //Find the character to replace with
    public static String find(char c1, char c2, int opt){
        int row1=-1,row2=-1,col1=-1,col2=-1,flag1=0,flag2=0;
        String code="";
        for(int i=0; i<5; i++){
            for(int j=0; j<5;j++){
                if(pfmatrix[i][j] == c1) {
                	row1 = i;
                	col1 = j;
                	flag1 = 1;
                }
                else if(pfmatrix[i][j] == c2) {
                	row2 = i;
                	col2 = j;
                	flag2 = 1; 
                }
                if(flag1==1 && flag2==1) break;
            }
        }
        if(row1==row2) { 
        	if(col1+opt == 5)
        		code += String.valueOf(pfmatrix[row1][0]) + String.valueOf(pfmatrix[row2][col2+opt]);
        	else if(col2+opt == 5)
				code += String.valueOf(pfmatrix[row1][col1+opt]) + String.valueOf(pfmatrix[row2][0]);
        	else if(col1+opt == -1)
        		code += String.valueOf(pfmatrix[row1][4]) + String.valueOf(pfmatrix[row2][col2+opt]);
        	else if(col2+opt == -1)
        		code += String.valueOf(pfmatrix[row1][col1+opt]) + String.valueOf(pfmatrix[row2][4]);
        	else
        		code += String.valueOf(pfmatrix[row1][col1+opt]) + String.valueOf(pfmatrix[row2][col2+opt]);
        }
        else if(col1==col2) {
        	if(row1+opt == 5)
        		code += String.valueOf(pfmatrix[0][col1]) + String.valueOf(pfmatrix[row2+opt][col2]);
        	else if(row2+opt == 5)
        		code += String.valueOf(pfmatrix[row1+opt][col1]) + String.valueOf(pfmatrix[0][col2]);
        	else if(row1+opt == -1)
        		code += String.valueOf(pfmatrix[4][col1]) + String.valueOf(pfmatrix[row2+opt][col2]);
        	else if(row2+opt == -1)
        		code += String.valueOf(pfmatrix[row1+opt][col1]) + String.valueOf(pfmatrix[4][col2]);
        	else
        		code += String.valueOf(pfmatrix[row1+opt][col1]) + String.valueOf(pfmatrix[row2+opt][col2]);
        }
        else {
        	code += String.valueOf(pfmatrix[row1][col2]) + String.valueOf(pfmatrix[row2][col1]);
        }
        return code;
    }
    
    //Split the plaintext into pairs
    public static String pair(String message) {
    	String pairing = "";
		for(int i=0; i<message.length();i++){
			pairing += message.charAt(i);
			if(i+1<message.length() && message.charAt(i)==message.charAt(i+1))
				pairing += 'X';
		}
		if(pairing.length()%2!=0)
			pairing += 'X';
        System.out.println("After pairing: " + pairing);
        return pairing;
    }
    
    //Encryption
    public static String encryptpf(String message, String key) {
       String encrypt="";
       initmatrix(key);
	   display(pfmatrix);
       String pairing = pair(message);
       for(int j=0; j<=pairing.length()-2; j+=2){
           encrypt += find(pairing.charAt(j),pairing.charAt(j+1),1);
       }
       return encrypt;
    }

    //Decryption
    public static String decryptpf(String encrypted_message, String key) {
        String decrypt="";
        for(int j=0; j<=encrypted_message.length()-2; j+=2){
            decrypt += find(encrypted_message.charAt(j),encrypted_message.charAt(j+1),-1);
        }
        decrypt = decrypt.replace("X","");
        return decrypt;
    }
    
    public static void main(String args[]){
        System.out.println("Enter your message: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();
		//For multiple words
        String words[] = message.split(" ");
        String key,tempkey;
        System.out.println("Enter your key: ");
		tempkey = sc.next();
		key = ""+tempkey.charAt(0);
		//Removing repeating letters
        for(int i=1; i<tempkey.length();i++){
       	    if(!key.contains(""+tempkey.charAt(i)))
                key += tempkey.charAt(i);
        }
		System.out.println("Key: "+key);
        String encrypted_message = "";
        for(int i=0; i<words.length; i++) {
        	encrypted_message += encryptpf(words[i],key) + " ";
        }
        
        System.out.println("Encrypted message: " + encrypted_message);
        String encrypted_words[] = encrypted_message.split(" ");
        String decrypted_message = "";
        for(int i=0; i<encrypted_words.length; i++) {
        	decrypted_message += decryptpf(encrypted_words[i],key) + " ";
        }
        if(decrypted_message.equals(message+" "))
            System.out.println("Decryption successful! Message is: "+decrypted_message);
        else
           System.out.println("Decryption unsuccessful. Check your key.");
    }
}
