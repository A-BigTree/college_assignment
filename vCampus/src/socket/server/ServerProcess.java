/**
 * ==================================================
 * Project: vCampus
 * Package: socket.server
 * =====================================================
 * Title: ServerProcess.java
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
import vCampusServer.ui.ServerMain;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerProcess implements Runnable {
    private Socket socket;
    //从客户端接受到的Socket对象
    private Message message;
    // 从客户端接受到的Message对象

    public ServerProcess() {

    }

    public ServerProcess(Socket socket, Message message) {
        this.socket = socket;
        this.message = message;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public Message getMessage() {
        return message;
    }

    /*
     *多线程运行接口
     */
    @Override
    public void run() {
        //创建发送到客户端的socket对象
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("服务端接受到命令：" + message.getData().toString() + message.getType());

            //To do something


            //向客户端发送message
            Message ans = new Message(message.getType(), true, new String("您好，这里是服务端，操作" + message.getType()));
            oos.reset();
            oos.writeObject(ans);
            oos.flush();
            ServerMain.ui.setTextArea1("IP:" + socket.getInetAddress().toString() + "；\t指令ID：" + message.getType() + "；\t运行结果：" + ans.get_State() + "；\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
