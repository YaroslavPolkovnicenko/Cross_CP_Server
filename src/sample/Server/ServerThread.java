package sample.Server;

import sample.Employees.Employee;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class ServerThread implements Runnable {

    ServerController controller;

    public ServerThread(ArrayList<Employee> list, ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

        try {

            ServerSocket serverSocket = new ServerSocket(8800);
            Thread thread;
            ParseThread serverThread;

            Socket socket = serverSocket.accept();

            serverThread = new ParseThread(socket, controller);
            thread = new Thread(serverThread);

            thread.start();
            thread.join();

            for(Employee s : serverThread.getList()) {
                System.out.println("Результат: " + s);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}