package com.via.ui;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServer {


    public static void startAppiumServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
            Thread.sleep(10000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Appium Server started");
    }

    public static void stopAppiumServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Appium Server stopped");
    }


    public static boolean isServerRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        }
        return isServerRunning;
    }
}