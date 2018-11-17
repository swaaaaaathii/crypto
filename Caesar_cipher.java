import java.util.Scanner;

/**
 *
 * @author swa
 */
public class Caesar_cipher {

    /**
     * @param args the command line arguments
     */
    public static String encrypt(String message, int key){
        String encrypted="";
        for(int i=0; i<message.length(); i++){
            int asciivalue = (int)(message.charAt(i)+key);
            if(asciivalue>122)
                encrypted += (char)(96 + asciivalue - 122);
            else
                encrypted += (char)(int)(asciivalue);
        }
        return encrypted;
    }
    
    public static String decrypt(String message, int key){
        String decrypted="";
        for(int i=0; i<message.length(); i++){
            int asciivalue = (int)(message.charAt(i)-key);
            if(asciivalue<97)
                decrypted += (char)(123-(97-asciivalue));
            else
            decrypted += (char)(int)(message.charAt(i)-key);
        }
        return decrypted;
    }
    
    public static void main(String[] args) {
        System.out.println("Enter your message: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.next();
        System.out.println("Enter the key: ");
        int key = sc.nextInt();
        String encrypted_message = encrypt(message,key);
        System.out.println("Encrypted message: " + encrypted_message);
        String decrypted_message = decrypt(encrypted_message,key);
        if(decrypted_message.equals(message))
            System.out.println("Decryption successful! Message is: "+decrypted_message);
        else
           System.out.println("Decryption unsuccessful. Check your key.");
    }
    
}
