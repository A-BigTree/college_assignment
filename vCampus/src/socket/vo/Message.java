/**
 * ==================================================
 * Project: vCampus
 * Package: socket.vo
 * =====================================================
 * Title: Message.java
 * Created: [2022/8/13 18:39] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package socket.vo;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private int type;
    private boolean state;
    private Object data;

    public Message(int type,boolean state,Object data){
        this.type=type;
        this.state=state;
        this.data=data;
    }

    public int getType() {
        return type;
    }

    public boolean get_State(){
        return state;
    }

    public Object getData() {
        return data;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "...";
    }
}

