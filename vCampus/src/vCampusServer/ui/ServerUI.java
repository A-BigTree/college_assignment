/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusServer.ui
 * =====================================================
 * Title: ServerUI.java
 * Created: [2022/8/14 18:27] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/14, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

public class ServerUI {
    private JPanel main;
    private JButton button2;
    private JLabel label1;
    private JLabel label2;
    private JScrollPane jSP;
    private JTextArea textArea1;

    public ServerUI() {
    }

    public void setTable1(String text){
        label1.setText(text);
    }

    public void setLabel2(String text){
        label2.setText(text);
    }

    public JPanel getMain() {
        return main;
    }

    public void setButton2(ServerSocket serverSocket){
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverSocket !=null){
                    try{
                        serverSocket.close();
                        setTable1("服务器已关闭 ！");
                    }catch (Exception e1){
                        setTable1("关闭服务器失败！");
                    }
                }else{
                    setTable1("服务器未开启！");
                }
            }
        });
    }

    public void setTextArea1(String text){
        textArea1.append(text);
    }
}
