package mysocketserver;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class MySocketServer {

    public static void main(String[] args) throws Exception {
        PrintStream out = null;
        BufferedReader in = null;

        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            Socket clientSocket = serverSocket.accept();
            out = new PrintStream(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while (true) {
                inputLine = in.readLine();
                if (inputLine == null || inputLine.isEmpty()) {
                    break;
                }
                System.out.println(inputLine);
            }
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Length: 23");
            out.println("Content-Type: text/html; charset=utf-8\n\n");
            out.println("<b>This is my content</b>");

            try {
                clientSocket.close();
                serverSocket.close();
            } catch (Exception ex) {
                System.out.print(Arrays.toString(ex.getStackTrace()));
            }

        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
