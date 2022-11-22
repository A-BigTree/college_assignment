#ifndef STATELINKED_H
#define STATELINKED_H

#include "parametersetting.h"

//撤销最大步数
#define MAX_STATE 5

class StateLinked
{
private:
    //参数循环队列
    ParameterSetting *params = new ParameterSetting[MAX_STATE];
    //头指针
    int sP = 0;
    //尾指针
    int eP = 0;
    //撤销前进运动指针
    int iP = 0;

public:
    StateLinked();
    ~StateLinked(){
        delete params;
    }

    //加入一个操作
    void addParam(ParameterSetting* param);
    //是否可以撤回
    bool canUndo();
    //是否可以前进
    bool canGoto();
    //获取一个撤回操作
    ParameterSetting* undoAction();
    //获取一个前进操作
    ParameterSetting* gotoAction();
    //获取参数队列
    ParameterSetting* getParams();
    //获取头指针
    int getHeader();
    //获取尾指针
    int getEnd();
    //获取活动指针
    int getIndex();
};

#endif // STATELINKED_H
