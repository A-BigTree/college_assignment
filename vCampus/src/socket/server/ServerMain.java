/**
 * ==================================================
 * Project: vCampus
 * Package: socket.server
 * =====================================================
 * Title: ServerMain.java
 * Created: [2022/8/17 20:52] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/17, created by Shuxin-Wang.
 * 2.
 */

package socket.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[]args){
        try (ServerSocket serverSocket = new ServerSocket(9999);) {
            //处理多个Client端
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerListen(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
