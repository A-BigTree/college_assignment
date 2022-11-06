#ifndef IMAGEPROCESS_H
#define IMAGEPROCESS_H
#include<iostream>
#include<math.h>

#include "imageinfo.h"

using namespace std;

class ImageProcess{
public:

    ImageProcess();
    //转为灰度图
    ImageInfo* toGray(ImageInfo *image);
    //调整大小
    ImageInfo* adjustSize(ImageInfo *image, int width, int height);
    //直方图均衡化
    ImageInfo* imageAverage(ImageInfo *image, bool isGray = true);
    //图片缩放
    ImageInfo* imageZoom(ImageInfo *image, int width, int height);
    //得到相对距离
    int relativeDis(int index, int newIndex){
        return newIndex - index;
    }
    //旋转坐标变换
    int getRotationDis(int x, int y, double angle, bool isX = true){
        if(isX){
            return floor(x * cos(angle) - y * sin(angle));
        }else{
            return floor(x * sin(angle) + y * cos(angle));
        }
    }
    //图片旋转
    ImageInfo* imageRotation(ImageInfo *image, int x, int y, double angle, bool isRight);
    //读取二进制文件
    ImageInfo* readBinaryImage(unsigned short *pixels, int width, int height, unsigned short min, unsigned short max);
    //灰度窗调整
    ImageInfo* grayMapping(unsigned short *pixels, int width, int height, int right, int left);
    //拉普拉斯锐化
    unsigned short* laplaceSharpening(unsigned short *pixels, int width, int height);

};

#endif // IMAGEPROCESS_H
