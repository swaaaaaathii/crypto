import java.util.Scanner;
import java.math.BigInteger;

public class Hill_Cipher {

	static char key_array[][], inverted_key_array[],  message_array[][], encrypted_message_array[][];
	static int key_array_int[][], message_array_int[][], inverted_key_array_int[][], encrypted_message_array_int[][];
	static int no_of_columns;
	
	//For displaying matrices
	public static void display(char arr[][], int n, int m) {
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				System.out.print(arr[i][j] + " ");				
			}
			System.out.println();
		}
	}
	
	public static void display(int arr[][], int n, int m) {
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				System.out.print(arr[i][j] + " ");				
			}
			System.out.println();
		}
	}
	
	//Converting to ascii values
	public static void convert_to_int(char arr[][],int int_array[][], int n, int m) {
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				int_array[i][j] = arr[i][j] - 97;
			}
		}
		display(int_array,n,m);
	}
	
	//Splitting key into key matrix
	public static void split_key(int n, String key) {
		int x=0, diff;
		key_array =  new char[n][n];
		String alphabet = "abcdefghijklmnopqrstuvwxyz"; 
		if((diff = n*n - key.length())>0) {
			key += alphabet.substring(0, diff);
		}
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				key_array[i][j] = key.charAt(x);
				x++;
			}
		}
		display(key_array,n,n);
		key_array_int = new int[n][n];
	}
	
	//Splitting plaintext into array
	public static void split_message(int n, String message, char array[][]) {
		int x=-1;
		for(int i=0; i<no_of_columns; i++) {
			for(int j=0; j<n; j++) {
				x++;
				if(x>=message.length() || message.charAt(x)==' ') 
					array[j][i] = 'x';
				else 
					array[j][i] = message.charAt(x);
				 
			}
		}
		display(array,n, no_of_columns);
	}
	
	//Encryption and decryption
	public static String encrypt_or_decrypt(int a[][], int b[][]) {
		int n = a.length;
		int m = b[0].length;
		int product[][] = new int[a.length][m];
		String message="";
		for(int i=0;i<n;i++){    
			for(int j=0;j<m;j++){    
				product[i][j]=0;      
				for(int k=0;k<a[0].length;k++){      
					product[i][j]+=a[i][k]*b[k][j];      
				}
				product[i][j] = product[i][j]%26;
			}     
		}
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				message += String.valueOf((char)(product[j][i]+97));
			}
		}
		System.out.println("After multiplication and modulo 26: ");
		display(product,n,no_of_columns);
		return message;
	}
	
	//For finding determinant of 2x2 matrix
	public static int det2x2(int a, int b, int c, int d) {
		return (a*d) - (b*c);
	}
	
	//To find all the values in an array mod 26
	public static void mod26(int array[][], int n) {
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(array[i][j]>=0)
					array[i][j] = array[i][j]%26;
				else
					while(true) {
						array[i][j] += 26;
						if(array[i][j]>0) break;
					}
			}
		}
	}
	
	//Finding inverse
	public static int[][] invert(int array[][], int n){
		int adj[][] = new int[n][n];
		BigInteger inverse;
		int determinant=-1;
		//Finding determinant and multiplicative inverse
		switch(n) {
			case 3:
				determinant = array[0][0]*((array[1][1] * array[2][2]) - (array[1][2]*array[2][1])) - array[0][1]*((array[1][0] * array[2][2]) - (array[1][2] * array[2][0])) + array[0][2]*((array[1][0] * array[2][1]) - (array[1][1] * array[2][0]));
				break;
			case 2:
				determinant = det2x2(array[0][0], array[0][1], array[1][0], array[1][1]);
				break;
			default:
				System.out.println("Error");
		}
		
		if(determinant<0) {
			determinant=26-((-determinant)%26);
		}
		else
			determinant = determinant%26;
		
		if(determinant==0) {
			System.out.println("Determinant is zero. Inverse does not exist");
			System.exit(0);
		}
		System.out.println("Determinant: "+determinant);

		try {
			inverse = BigInteger.valueOf(determinant);
			inverse = inverse.modInverse(BigInteger.valueOf(26));
			System.out.println("Inverse: "+inverse);
			
			//Finding adjugate matrix
			if(n==3) {
				adj[0][0] = det2x2(array[1][1], array[1][2], array[2][1], array[2][2]);
				adj[0][1] = -(det2x2(array[0][1], array[0][2], array[2][1], array[2][2]));
				adj[0][2] = det2x2(array[0][1], array[0][2], array[1][1], array[1][2]);
				adj[1][0] = -(det2x2(array[1][0], array[1][2], array[2][0], array[2][2]));
				adj[1][1] = det2x2(array[0][0], array[0][2], array[2][0], array[2][2]);
				adj[1][2] = -(det2x2(array[0][0], array[0][2], array[1][0], array[1][2]));
				adj[2][0] = det2x2(array[1][0], array[1][1], array[2][0], array[2][1]);
				adj[2][1] = -(det2x2(array[0][0], array[0][1], array[2][0], array[2][1]));
				adj[2][2] = det2x2(array[0][0], array[0][1], array[1][0], array[1][1]);
			}
			
			else if(n==2) {
				adj[0][0] = array[1][1];
				adj[0][1] = -array[0][1];
				adj[1][0] = -array[1][0];
				adj[1][1] = array[0][0];
			}
			
			mod26(adj,n);
			
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					adj[i][j]*=inverse.intValue();
					adj[i][j] = adj[i][j]%26;
				}
			}
		}
		catch(ArithmeticException e) {
			System.out.println("Inverse does not exist. Cannot decrypt.");
			System.exit(0);
		}
		
		return adj;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String message;
		String key;
		int n;
		//Input values
		System.out.println("Enter the message: ");
		message = sc.nextLine();
		while(true) {
			System.out.println("Enter the order of the key: ");
			n = sc.nextInt();
			if(n==2 || n==3) 
				break;
			else
				System.out.println("Program works only for key orders 2 and 3.");	
		}
		System.out.println("Enter the key: ");
		key = sc.next();
		sc.close();
		
		//Start encryption
		System.out.println("The key matrix: ");
		split_key(n,key);
		System.out.println("The key matrix after converting to int: ");
		convert_to_int(key_array, key_array_int, n, n);
		float lenbyn = (float)message.length()/n;
		System.out.println("The message column matrix: ");
		no_of_columns = (int)Math.ceil(lenbyn);
		message_array = new char[n][no_of_columns];
		split_message(n,message,message_array);
		message_array_int = new int[n][no_of_columns];
		System.out.println("The message column matrix after converting to int: ");
		convert_to_int(message_array, message_array_int, n, no_of_columns);
		//C = KPmod26
		String encrypted_message = encrypt_or_decrypt(key_array_int, message_array_int);
		System.out.println("The encrypted message is: "+encrypted_message);
		
		//Start decryption
		inverted_key_array_int = new int[n][n];
		inverted_key_array_int = invert(key_array_int,n);
		System.out.println("Inverted Key Matrix: ");
		display(inverted_key_array_int,n,n);
		System.out.println("The encrypted message column matrix: ");
		encrypted_message_array = new char[n][no_of_columns];
		split_message(n,encrypted_message,encrypted_message_array);
		encrypted_message_array_int = new int[n][no_of_columns];
		System.out.println("The encrypted message column matrix after converting to int: ");
		convert_to_int(encrypted_message_array, encrypted_message_array_int, n, no_of_columns);
		//M = K^-1Cmod26
		String decrypted_message = encrypt_or_decrypt(inverted_key_array_int, encrypted_message_array_int);
		decrypted_message = decrypted_message.replace("x", " ");
		System.out.println("The decrypted message is: "+decrypted_message);
	}
}
