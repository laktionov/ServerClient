package main;

/**
 * Created by Сергей on 26.11.2016.
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(PORT);
            System.out.println("Started " + ss);
            try {
                System.out.println("Waiting for a client");
                String str = "";
                String adress = "";
                String line = "";
                while (true) {
                    Socket s = ss.accept();
                    System.out.println("Connected: " + s);
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        str = in.readLine();
                        System.out.println(str);
                        try {
                            out.print("HTTP/1.1 200 OK \r\n\r\n");
                            adress = str.split(" ")[1].substring(1);
                            out.println(adress);
                            Scanner scanner = new Scanner(new FileReader(adress));
                            while (scanner.hasNextLine()) {
                                line = scanner.nextLine();
                                System.out.println(line);
                                out.println(line);
                            }
                            scanner.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } finally {
                        s.close();
                    }
                }
            } finally {
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not listen to port " + PORT);
        }
    }

}
