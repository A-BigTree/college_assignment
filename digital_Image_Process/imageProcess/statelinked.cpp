#include "statelinked.h"

StateLinked::StateLinked()
{

}

//加入一个操作
void StateLinked::addParam(ParameterSetting* param){
    params[eP].code = param->code;
    //复制布尔值
    params[eP].IS_HI = param->IS_HI;
    params[eP].IS_G_W = param->IS_G_W;
    params[eP].IS_LA = param->IS_LA;
    params[eP].IS_G_R = param->IS_G_R;
    params[eP].IS_H_R = param->IS_H_R;
    //复制图片缩放
    params[eP].WIDTH_AND_HEIGHT = param->WIDTH_AND_HEIGHT;
    //复制图像旋转
    params[eP].RIGHT_OR_LEFT = param->RIGHT_OR_LEFT;
    params[eP].ROTATION_ANGLE = param->ROTATION_ANGLE;
    params[eP].CENTER_POINT = param->CENTER_POINT;
    //复制灰度窗
    params[eP].WIDTH_AND_HEIGHT = param->WIDTH_AND_HEIGHT;
    params[eP].GRAY_SCALE = param->GRAY_SCALE;
    params[eP].PIXEL_SUM = param->PIXEL_SUM;

    if(params[eP].PIXEL != NULL)
        delete[]params[eP].PIXEL;
    params[eP].PIXEL = new unsigned short[params[eP].PIXEL_SUM];
    copy(param->PIXEL, param->PIXEL + param->PIXEL_SUM, params[eP].PIXEL);
    //更新指针
    eP = (eP + 1) % MAX_STATE;
    if(eP == sP){
        sP = (sP + 1) % MAX_STATE;
    }
    iP = (eP + 4) % MAX_STATE;
}


//是否可以撤回
bool StateLinked::canUndo(){
    return iP != sP;
}


//是否可以前进
bool StateLinked::canGoto(){
    return iP != (eP + 4) % MAX_STATE;
}


//获取一个撤回操作
ParameterSetting* StateLinked::undoAction(){
    if(!canUndo()){
        return NULL;
    }
    iP = (iP + 4 ) % MAX_STATE;
    return new ParameterSetting(params[iP]);
}


//获取一个前进操作
ParameterSetting* StateLinked::gotoAction(){
    if(!canGoto()){
        return NULL;
    }
    iP = (iP + 1) % MAX_STATE;
    return new ParameterSetting(params[iP]);
}

//获取参数队列
ParameterSetting* StateLinked::getParams(){
    return params;
}


//获取头指针
int StateLinked::getHeader(){
    return sP;
}


//获取尾指针
int StateLinked::getEnd(){
    return eP;
}


//获取活动指针
int StateLinked::getIndex(){
    return iP;
}
