/**
 * ==================================================
 * Project: vCampus
 * Package: socket.client
 * =====================================================
 * Title: ClientSend.java
 * Created: [2022/8/13 18:39] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package socket.client;

import socket.vo.Message;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSend implements Runnable{

    private Socket socket;
    // 客户端发送的socket对象
    private Message message;
    // 客户端->服务端的Message对象

    public ClientSend(Socket socket, Message message){
        this.socket = socket;
        this.message = message;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setData(Object data){
        message.setData(data);
    }

    public Socket getSocket() {
        return socket;
    }

    public Message getMessage() {
        return message;
    }

    public Object getData(){
        return message.getData();
    }

    @Override
    public void run(){
        //To do something
        try{
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if(message.get_State()){
                oos.writeObject(message);
                oos.flush();

                ClientListen cl = new ClientListen(socket);
                Thread thread = new Thread(cl);
                thread.start();
                try{
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                setData(cl.getData());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

