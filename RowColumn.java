import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author swa
 */
public class RowColumn {
    
    public static char e_matrix[][];
    public static char d_matrix[][];
	public static String key_order = "";
    
    public static String sortString(String inputString)
    {
        // convert input string to char array
        char tempArray[] = inputString.toCharArray();
         
        // sort tempArray
        Arrays.sort(tempArray);
		String key = new String(tempArray);
		String temp = "";
		
		for(int i=0; i<key.length(); i++){
			temp += String.valueOf(i);
		}
		
		for(int i=0; i<key.length(); i++){
			key_order += temp.charAt(inputString.indexOf(key.charAt(i)));
		}
         
        // return new sorted string
        return key;
    }
    
    public static String encrypt(String message, String key, int rows, int cols){
        int x=0;
        String sorted_key = sortString(key);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(i==0)
                    e_matrix[i][j] = key.charAt(j);
                else{
                    e_matrix[i][j] = message.charAt(x);
                    x++;
                }
                System.out.print(e_matrix[i][j] + " ");
            }
            System.out.println();
        }
        String encrypted = "";
       
        for(int y=0; y<key.length(); y++){
            char val = sorted_key.charAt(y);
            int col=0;
            for(int i=0; i<cols; i++){
                if(e_matrix[0][i] == val)
                    col = i;
            }
            for(int j=1; j<rows; j++){
                encrypted += e_matrix[j][col];
            }
        }
        return encrypted;
    }
    
    public static String decrypt(String message, String key, int rows, int cols){
        String decrypted = "";
        int x=0;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                d_matrix[i][j] = message.charAt(x);
                x++;
                System.out.print(d_matrix[i][j] + " ");
            }
            System.out.println();
        }
        
        for(int i=0; i<cols; i++){
            for(int y=0; y<key.length();y++){
                decrypted += d_matrix[Integer.parseInt(String.valueOf(key_order.charAt(y)))][i];
            }
        }
        return decrypted;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plaintext: ");
        String message = sc.nextLine();
        System.out.println("Enter the key(digits): ");
        String key = sc.next();
        int cols = key.length();
        if(message.length()%cols!=0){
            while(message.length()%cols!=0)
                message += " ";
        }
        int rows = message.length()/cols;
        e_matrix = new char[rows+1][cols];
        String encrypted = encrypt(message,key,rows+1,cols);
        System.out.println("The encrypted string is : " + encrypted);
        d_matrix = new char[cols][rows];
        String decrypted = decrypt(encrypted, key, cols, rows);
        System.out.println("The decrypted string is: " + decrypted);
    }
    
}
