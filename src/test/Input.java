package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Input {

	public static void main(String[] args) {

		PrintStream out = System.out;
		Scanner sc;
		PrintStream file;

		try {
			//input file
			sc = new Scanner(new File("C://temp//bookstore.txt"));
			//output file
			file = new PrintStream("C://temp//saida.txt");

			while (sc.hasNextLine()) {
				file.println(sc.nextLine());
			}
			
			sc.close();
			file.close();
		} catch (IOException e) {
			out.println("Error trying to read the file " + e);
		}
	}

}
