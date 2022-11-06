#include "parametersetting.h"

ParameterSetting::ParameterSetting(){
    //初始化图像缩放
    WIDTH_AND_HEIGHT.x = 50;
    WIDTH_AND_HEIGHT.y = 50;

    //初始化图片旋转
    CENTER_POINT.x = 50;
    CENTER_POINT.y = 50;
    ROTATION_ANGLE = 0.;
    RIGHT_OR_LEFT = true;

    //初始化灰度窗
    GRAY_SCALE = 4096;
    GRAY_WINDOW.x = 0;
    GRAY_WINDOW.y = GRAY_SCALE;
}


void ParameterSetting::initParameters(ImageInfo* image){
    //初始化图片缩放
    WIDTH_AND_HEIGHT.x = image->getImage()->width() / 2;
    WIDTH_AND_HEIGHT.y = image->getImage()->height() / 2;

    //初始化图片旋转
    CENTER_POINT.x = image->getImage()->width() / 2;
    CENTER_POINT.y = image->getImage()->height() / 2;
    ROTATION_ANGLE =90;
    RIGHT_OR_LEFT = true;
}

void ParameterSetting::setScale(int width, int height){
    WIDTH_AND_HEIGHT.x = width;
    WIDTH_AND_HEIGHT.y = height;
}

void ParameterSetting::setScale(double precentage){
    WIDTH_AND_HEIGHT.x = WIDTH_AND_HEIGHT.x * (precentage / 100);
    WIDTH_AND_HEIGHT.y = WIDTH_AND_HEIGHT.y * (precentage / 100);
}

void ParameterSetting::setRotation(int indexX, int indexY, double angle, bool isRight){
    CENTER_POINT.x = indexX;
    CENTER_POINT.y = indexY;
    ROTATION_ANGLE = angle;
    RIGHT_OR_LEFT = isRight;
}

void ParameterSetting::setGrayMapping(int maxGray, int windowIndex, int windowWidth){
    GRAY_SCALE = maxGray;
    GRAY_WINDOW.x = windowIndex;
    GRAY_WINDOW.y = windowWidth;
}

