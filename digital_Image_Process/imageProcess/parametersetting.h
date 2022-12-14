#ifndef PARAMETERSETTING_H
#define PARAMETERSETTING_H

#include<imageinfo.h>
#include<math.h>
using namespace std;

//表示数据对
struct Index{
    int x;
    int y;
};

class ParameterSetting
{
public:
    //命令
    int code;
    //直方图
    bool IS_HI;
    //图片缩放
    Index WIDTH_AND_HEIGHT;  //缩放图片宽高
    //图片旋转
    Index CENTER_POINT;  //旋转中心点
    double ROTATION_ANGLE; //旋转角度
    bool RIGHT_OR_LEFT;  //左旋或右旋
    //灰度窗设置
    bool IS_G_W;
    int GRAY_SCALE; //最大灰度范围
    Index GRAY_WINDOW; //灰度窗参数
    unsigned short *PIXEL; //灰度值
    int PIXEL_SUM; //灰度值数量
    //图像增强
    bool IS_LA;
    //灰度反转
    bool IS_G_R;
    //图像左右翻转
    bool IS_H_R;

    ParameterSetting();

    ~ParameterSetting(){
        delete[]PIXEL;
    }

    //初始化参数
    void initParameters(ImageInfo* image);
    //设置命令
    void setCode(int code){
        this->code = code;
    }
    //设置图片缩放参数
    void setScale(int width, int height);
    //按比例图片缩放
    void setScale(double precentage);
    //设置图片旋转参数
    void setRotation(int indexX, int indexY, double angle, bool isRight);
    //设置灰度窗
    void setGrayMapping(int maxGray, int windowIndex, int windowWidth);
};

#endif // PARAMETERSETTING_H
