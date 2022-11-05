/**
 * ==================================================
 * Project: vCampus
 * Package: socket.client
 * =====================================================
 * Title: ClientListen.java
 * Created: [2022/8/13 18:38] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package socket.client;

import socket.vo.Message;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListen implements Runnable{

    private Socket socket;
    //客户端的socket对象
    public Object data;
    // 服务端传输回的数据
    ClientListen(Socket socket){
        this.socket = socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Socket getSocket() {
        return socket;
    }

    public Object getData() {
        return data;
    }

    @Override
    public void run(){
        try{
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message)ois.readObject();
            setData(message.getData());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

