/**
 * ==================================================
 * Project: vCampus
 * Package: socket.server
 * =====================================================
 * Title: ServerListen.java
 * Created: [2022/8/13 18:42] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package socket.server;

import socket.vo.Message;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

public class ServerListen implements Runnable, Serializable {
    private Socket socket;
    // 来自客户端socket对象

    public ServerListen(Socket socket) {
        this.socket = socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /*
     *多线程运行接口
     */
    @Override
    public void run() {
        try {
            /* ObjectInputStream反序列化流，将之前使用ObjectOutputStream
              序列化的原始数据恢复为对象，以流的方式读取对象。*/
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object ob = ois.readObject();
            Message message = (Message) ob;
            //System.out.println(message.getData());

            ServerProcess sp = new ServerProcess(socket, message);
            Thread thread = new Thread(sp);
            thread.start();
            try{
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
