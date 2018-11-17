import java.util.Scanner;

public class Vigenere {
    public static char alphabets[][] = new char[26][26];
	//To create the vigenere matrix
    public static void create_matrix(){
        for(int i=0; i<26; i++){
            for(int j=0; j<26; j++){
                int val = j+i+97;
                if(val>122)
                    val = 96 + val - 122;
                alphabets[i][j] = (char)(val);
                System.out.print(alphabets[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static String encrypt(String message, String key){
        String encryption = "";
        for(int i=0; i<message.length(); i++){
            if(message.charAt(i) != ' '){
                int val2 = (int)message.charAt(i) - 97;
                int val1 = (int)key.charAt(i) - 97;
                encryption += String.valueOf(alphabets[val1][val2]);
            }
            else{
                encryption += " ";
            }
        }
        return encryption;
    }
    
    public static String decrypt(String message, String key) {
        String decryption = "";
        int j;
        for(int i=0; i<message.length(); i++){
            if(message.charAt(i) != ' '){
                int val1 = (int)key.charAt(i) - 97;
                for(j=0; j<26; j++){
                    if(alphabets[val1][j] == message.charAt(i))
                        break;
                }
                decryption += String.valueOf((char)(j+97));
            }
            else
                decryption += " ";
        }
        return decryption;
    }
    
    public static void main(String[] args) {
       Scanner sc =  new Scanner(System.in);
       create_matrix();
       System.out.println("Enter the message: ");
       String message = sc.nextLine();
       System.out.println("Enter the key phrase: ");
       String key = sc.nextLine();
       int counter = 0; 
       int key_length = key.length();
       int message_length = message.length();
	   //Making key length equal to message length
       if(key_length<message_length){
            while(key.length() != message.length()){
                key += key.charAt(counter);
                counter++;
                if(counter == key_length)
                    counter = 0; 
            }
       }
       System.out.println("Changed key: "+key);
       String encrypted_message = encrypt(message,key);
       System.out.println("The encrypted message is: "+encrypted_message);
       String decrypted_message = decrypt(encrypted_message,key);
       System.out.println("The decrypted message is: "+decrypted_message);
    }
}
