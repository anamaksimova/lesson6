package j2hw6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final int PORT=8089;


    public static void main(String[] args)  {

        Socket socket = null;
        Scanner sc = new Scanner(System.in);
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started");

            socket = server.accept();
            System.out.println("Client is connected");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Thread thread1= new Thread(() -> {
                while (true) {
                    try {
                        out.writeUTF(sc.nextLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.start();

            Thread thread2= new Thread(() -> {
                while (true) {
                    try {
                        String  str = in.readUTF();

                        if (str.equals("/end")) {
                            System.out.println("Client is disconnected");
                            break;
                        }
                        System.out.println("Client: " + str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread2.start();


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        }
    }
