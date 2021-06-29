package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cred {

	public static String userCurrent;
	public static String passCurrent;

	public static boolean check(String username, String password) {

		File file = new File("info.txt");

		Scanner scan;
		try {
			scan = new Scanner(file);
			String user = scan.next();
			String pass = scan.next();

			scan.close();

			if (user.equals(username) && pass.equals(password)) {
				userCurrent = user;
				passCurrent = pass;
				return true;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static void save(String username, String password) {
		File file = new File("info.txt");

		try {
			file.createNewFile();
			FileWriter write = new FileWriter(file, false);
			write.write(username + " " + password);
			userCurrent = username;
			passCurrent = password;

			write.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
