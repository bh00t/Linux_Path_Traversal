package playmentApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		Directory currentDir = new Directory("/");
		while(true)
		{
			input = br.readLine();
			currentDir = Operations.command(input,currentDir);
		}
	}
}
