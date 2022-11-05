/**
 * ==================================================
 * Project: vCampus
 * Package: socket.client
 * =====================================================
 * Title: ClientMain.java
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
import java.net.Socket;

/**
 * <p>ClientMain</p>
 *
 * 客户端主程序接口, 通过调用`request()`函数向服务端发送命令.
 *
 */
public class ClientMain {

    private static volatile Socket socket;
    // 客户端Socket对象

    public static void main(String[]args){
        Object res = request(1,true, Boolean.TRUE);
        System.out.println("客户端接受到数据:" + (String)res);
        Object res2 = request(2, true, 250.4);
        System.out.println("客户端接受到数据:" + (String)res2);
        disconnect();
    }

    public static Socket getSocket() {
        return socket;
    }

    private static void connect() {
        try {
            socket = new Socket("127.0.0.1", 9999);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *向服务端发送指定命令消息.
     *
     * @param type: 命令ID
     * @param state: 消息状态
     * @param data: 传输数据对象
     * @return 返回服务端返回结果类型
     */
    public static Object request(int type, boolean state, Object data){
        connect();
        ClientSend cs = new ClientSend(socket,new Message(type,state,data));
        Thread thread = new Thread(cs);
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        return cs.getData();
    }

    /**
     *关闭socket.
     */
    public static void disconnect(){
        try{
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

