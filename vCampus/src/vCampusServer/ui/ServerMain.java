/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusServer.ui
 * =====================================================
 * Title: ServerMain.java
 * Created: [2022/8/16 14:07] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/16, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.ui;

import socket.server.ServerListen;
import javax.swing.JFrame;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static int sum = 0;
    private static int port = 9999;
    public static final ServerUI ui = new ServerUI();

    public static void main(String[] args) {
        JFrame frame = new JFrame("服务器终端");
        frame.setContentPane(ui.getMain());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        ServerMain.startServer();
    }

    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            ui.setTable1("成功启动服务器：端口：" + port);
            ui.setLabel2("访问次数：" + 0);
            ui.setButton2(serverSocket);
            ui.setTextArea1("访问具体信息：\n");
            //处理多个Client端
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                sum++;
                ui.setLabel2("访问次数：" + sum);
                new Thread(new ServerListen(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPort(int port) {
        ServerMain.port = port;
    }
}
