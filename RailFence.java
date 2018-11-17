/**
 *
 * @author swa
 */

import java.util.Scanner;

public class RailFence {

    static void display(char matrix[][],int r,int c)
    {
        System.out.println("The rail fence: ");
        for(int i = 0;i<r;i++)
        {
            for(int j = 0;j<c;j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
	
    static String readRailsHorizontally(char matrix[][],int r,int c)
    {
        String ciphertext = "";
        for(int i = 0;i<r;i++)
        {
            for(int j = 0;j<c;j++){
               if(Character.isLetter(matrix[i][j]))
                   ciphertext += matrix[i][j];
            }
            
        }
        return ciphertext;
    }
    
   static String encrypt(String plaintext,int n)
   {
		char rails[][] = new char[n][plaintext.length()];
		int row = 0,col = 0,up=0,down=1;
        for(int i = 0;i<plaintext.length();i++ )
        {
            if(row<=n-1 && down == 1)
            { 
                
                rails[row][col] = plaintext.charAt(i);
                row++;col++;
                
            }
            else
            {
                if(down == 1){
                    down = 0;
                    row--;
                }
                row--;
                rails[row][col] = plaintext.charAt(i);
                col++;
                if(row == 0){
                    down = 1;
                    row++;
                }
            }
            
        }
        
        display(rails,n,plaintext.length());
        String ciphertext = readRailsHorizontally(rails,n,plaintext.length());
        System.out.println("The ciphertext is :"+ciphertext);
		return ciphertext;
   }
    
    static String decrypt(String cipher,int n)
    {
       String message = "";
       char rails[][] = new char[n][cipher.length()];
       int row = 0,col = 0, up = 0, down = 1;
       //Fill up the rail matrix again for decryption with the ciphertext
      
       for (int i=0; i < cipher.length(); i++)
        {
        // check the direction of flow
        if (row == 0)
            down = 1;
        if (row == n-1)
            down = 0;
 
        // place the marker
        rails[row][col++] = '*';
 
        // find the next row using direction flag
        if(down == 1)
            row++;
        else
            row--;
       
    }
 
    // now we can construct the fill the rail matrix
    int index = 0;
    for (int i=0; i<n; i++)
        for (int j=0; j<cipher.length(); j++)
            if (rails[i][j] == '*' && index<cipher.length())
                rails[i][j] = cipher.charAt(index++);
    
        display(rails,n,cipher.length());       
       
       row = 0;col = 0; up = 0; down = 1;
        for(int i = 0;i<cipher.length();i++ )
        {
            if(row<=n-1 && down == 1)
            { 
                
                message += rails[row][col];
                row++;col++;
                
            }
            else
            {
                if(down == 1){
                    down = 0;
                    row--;
                }
                row--;
                message += rails[row][col];
                col++;
                if(row == 0){
                    down = 1;
                    row++;
                }
            }
            
        }
        return message;
        
    }

    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the plaintext :");
        String plaintext = s.nextLine();
        System.out.println("Enter the number of rails :");
        int n = s.nextInt();
        
        String ciphertext = encrypt(plaintext,n);
        String message = decrypt(ciphertext,n);
        System.out.println("The decrypted ciphertext :"+message);

    }  
}

