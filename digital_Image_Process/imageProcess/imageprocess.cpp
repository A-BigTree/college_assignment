#include "imageprocess.h"

ImageProcess::ImageProcess(){

}

ImageInfo* ImageProcess::toGray(ImageInfo *image){
    int height = image->getImage()->height();
    int width = image->getImage()->width();
    QImage *ret = new QImage(width, height, QImage::Format_Indexed8);
    ret->setColorCount(256);
    for(int i = 0; i < 256; i++)
    {
        ret->setColor(i, qRgb(i, i, i));
    }
    switch(image->getImage()->format())
    {
    case QImage::Format_Indexed8:
        for(int i = 0; i < height; i ++)
        {
            const uchar *pSrc = (uchar *)image->getImage()->constScanLine(i);
            uchar *pDest = (uchar *)ret->scanLine(i);
            memcpy(pDest, pSrc, width);
        }
        break;
    case QImage::Format_RGB32:
    case QImage::Format_ARGB32:
    case QImage::Format_ARGB32_Premultiplied:
        for(int i = 0; i < height; i ++)
        {
            const QRgb *pSrc = (QRgb *)image->getImage()->constScanLine(i);
            uchar *pDest = (uchar *)ret->scanLine(i);

            for( int j = 0; j < width; j ++)
            {
                 pDest[j] = qGray(pSrc[j]);
            }
        }
        break;
    default:
        break;
    }
    return new ImageInfo(image->getFileName().prepend("Gray_"), "灰度图像转换成功！", ret);
}

ImageInfo* ImageProcess::adjustSize(ImageInfo *image, int width, int height){
    //忽略比例并进行平滑处理
    QImage newImage = image->getImage()->scaled(width, height);
    QString fileName = image->getFileName().prepend(QString("adjust_%1_%2").arg(width).arg(height));
    QString remark = QString("%1X%2 大小图片调整为 %3X%4.").arg(image->getImage()->width()).arg(image->getImage()->height()).arg(width).arg(height);

    return new ImageInfo(fileName, remark, new QImage(newImage));
}

ImageInfo* ImageProcess::imageAverage(ImageInfo *image, bool isGray){
    QImage *ImageAverage= new QImage(image->getImage()->width(),image->getImage()->height(),QImage::Format_ARGB32);
    int i,j;
    int width,height;
    width=image->getImage()->width();
    height=image->getImage()->height();
    QRgb rgb;
    int r[256],g[256],b[256];//原图各个灰度数量的统计
    int rtmp,gtmp,btmp,rj,gj,bj;
    float rPro[256],gPro[256],bPro[256];//原图各个灰度级的概率
    float rTemp[256],gTemp[256],bTemp[256];//均衡化后各个灰度级的概率
    int rJun[256],gJun[256],bJun[256];//均衡化后对应像素的值
    memset(r,0,sizeof(r));
    memset(g,0,sizeof(g));
    memset(b,0,sizeof(b));

    //获取原图各个灰度的数量
    for(i=0;i<width;i++)
    {
        for(j=0;j<height;j++)
        {
            rgb=image->getImage()->pixel(i,j);
            r[qRed(rgb)]++;
            g[qGreen(rgb)]++;
            b[qBlue(rgb)]++;
        }
    }

    //获取原图各个灰度级的概率
    for(i=0;i<256;i++)
    {
        rPro[i]=(r[i]*1.0)/(width*height);
        gPro[i]=(g[i]*1.0)/(width*height);
        bPro[i]=(b[i]*1.0)/(width*height);
    }

    //均衡化后各个灰度级的概率，同时获取均衡化后对应像素的值
    for(i=0;i<256;i++)
    {
        if(i==0)
        {
            rTemp[0]=rPro[0];
            gTemp[0]=gPro[0];
            bTemp[0]=bPro[0];
        }
        else
        {
            rTemp[i]=rTemp[i-1]+rPro[i];
            gTemp[i]=gTemp[i-1]+gPro[i];
            bTemp[i]=bTemp[i-1]+bPro[i];
        }
        rJun[i]=(int)(255*rTemp[i]+0.5);
        gJun[i]=(int)(255*gTemp[i]+0.5);
        bJun[i]=(int)(255*bTemp[i]+0.5);
    }

    for(i=0;i<width;i++)
    {
        for(j=0;j<height;j++)
        {
            rgb=image->getImage()->pixel(i,j);
            rtmp=qRed(rgb);
            gtmp=qGreen(rgb);
            btmp=qBlue(rgb);
            rj=rJun[rtmp];
            gj=gJun[gtmp];
            bj=bJun[btmp];
            ImageAverage->setPixel(i,j,qRgb(rj,gj,bj));
        }
    }
    if(isGray){
        ImageInfo *temp = toGray(new ImageInfo("", "", ImageAverage));
        delete ImageAverage;
        return new ImageInfo(image->getFileName().prepend("average_gray_"), "直方图均衡化成功(已转为灰度图)!", temp->getImage());
    }

    return new ImageInfo(image->getFileName().prepend("average_"), "直方图均衡化成功!", ImageAverage);

}

ImageInfo* ImageProcess::imageZoom(ImageInfo *image, int width, int height){

    QImage newImage(width, height, QImage::Format_Indexed8);
    newImage.setColorCount(256);
    for(int i = 0; i < 256; i++){
        newImage.setColor(i, qRgb(i, i, i));
    }

    //偏移量
    double offsetX = (double) (image->getImage()->width() / width);
    double offsetY = (double) (image->getImage()->height() / height);
    //灰度图缩放
    if(image->getImage()->format() == QImage::Format_Indexed8){
        //像素遍历计算
        uchar *temp = new uchar[width];
        for(int h = 0; h < height; h++){
            double picY = (h + 0.5) * offsetY - 0.5;
            //double picY = h * offsetY;
            int j = floor(picY);
            double v = picY - j;
            const uchar *srcI = (uchar *)image->getImage()->constScanLine(j);
            const uchar *srcI1 = (uchar *)image->getImage()->constScanLine(j + 1);
            for(int w = 0; w < width; w++){
                //中心对齐
                double picX = (w + 0.5) * offsetX - 0.5;
                //double picX = w * offsetX;
                int i = floor(picX);
                double u = picX - i;

                temp[w] = (uchar) ((1 - u) * (1 - v) * srcI[i] + (1 - u) * v *srcI[i + 1] +
                        u * (1 - v) * srcI1[i] + u * v * srcI1[i + 1]);
            }
            uchar* ret = (uchar *)newImage.scanLine(h);
            memcpy(ret, temp, width);
        }
        delete[]temp;
    }

    QString fileName = image->getFileName().prepend(QString("adjust_%1_%2_").arg(width).arg(height));
    QString remark = QString("%1X%2 大小图片调整为 %3X%4.").arg(image->getImage()->width()).arg(image->getImage()->height()).arg(width).arg(height);
    return new ImageInfo(fileName, remark, new QImage(newImage));
}

ImageInfo* ImageProcess::imageRotation(ImageInfo *image, int x, int y, double angle, bool isRight){

    int r_h = image->getImage()->height();
    int r_w = image->getImage()->width();

    if(!isRight){
        angle = -1 * angle;
    }
    //角度->弧度
    double Angle=((double)angle/180) * 3.1415926;
    Angle = round(Angle*100)/100;
    //找到旋转边界
    int maxX = -9999999, minX = 9999999, maxY = -9999999, minY = 9999999;

    int x1 = relativeDis(x, 0);
    maxX = max(maxX, x1);
    minX = min(minX, x1);

    int y1 = relativeDis(y, 0);
    maxY = max(maxY, y1);
    minY = min(minY, y1);

    int x2 = relativeDis(x, r_w);
    maxX = max(maxX, x2);
    minX = min(minX, x2);

    int y2 = relativeDis(y, r_h);
    maxY=max(maxY,y2);
    minY=min(minY,y2);

    int x1t,x2t,x3t,x4t,y1t,y2t,y3t,y4t;
    x1t=getRotationDis(x1,y1,Angle);
    maxX=max(maxX,x1t);
    minX=min(minX,x1t);

    x2t=getRotationDis(x1,y2,Angle);
    maxX=max(maxX,x2t);
    minX=min(minX,x2t);

    x3t=getRotationDis(x2,y1,Angle);
    maxX=max(maxX,x3t);
    minX=min(minX,x3t);

    x4t=getRotationDis(x2,y2,Angle);
    maxX=max(maxX,x4t);
    minX=min(minX,x4t);

    y1t=getRotationDis(x1,y1,Angle, false);
    maxY=max(maxY,y1t);
    minY=min(minY,y1t);

    y2t=getRotationDis(x1,y2,Angle, false);
    maxY=max(maxY,y2t);
    minY=min(minY,y2t);

    y3t=getRotationDis(x2,y1,Angle,false);
    maxY=max(maxY,y3t);
    minY=min(minY,y3t);

    y4t=getRotationDis(x2,y2,Angle, false);
    maxY=max(maxY,y4t);
    minY=min(minY,y4t);

    int mod_height = maxY - minY;
    int mod_width = maxX - minX;

    //映射计算
    QImage *mod_img = new QImage(mod_width, mod_height, QImage::Format_Indexed8);
    mod_img->setColorCount(256);
    for(int i = 0; i < 256; i++){
        mod_img->setColor(i, qRgb(i, i, i));
    }
    for (int i=0;i < r_h;i++){
        const uchar* line = (uchar *)image->getImage()->constScanLine(i);
        for (int j=0; j<r_w; j++){
            int x_ = j - x;
            int y_ = i - y;
            int trans_x = getRotationDis(x_,y_,Angle) - minX;
            int trans_y = getRotationDis(x_,y_,Angle, false) - minY;
            if(trans_x < mod_width && trans_y < mod_height){
                mod_img->setPixel(trans_x,trans_y, line[j]);
            }
        }
    }

    QString temp = QString("绕点(%1, %2)旋转%3度").arg(x).arg(y).arg(angle);


    return new ImageInfo(image->getFileName().prepend(temp + "_"), temp + "成功！", mod_img);
}

ImageInfo* ImageProcess::readBinaryImage(unsigned short *pixels, int width, int height, unsigned short min, unsigned short max){
    QImage* image = new QImage(width, height, QImage::Format_Indexed8);
    image->setColorCount(256);
    for(int i = 0; i < 256; i++){
        image->setColor(i, qRgb(i, i, i));
    }

    double k = (double)255 / (max - min);

    uchar* temp = new uchar[width];
    for(int j = 0; j < height; j++){
        for(int i = 0; i < width; i++){
            temp[i] = (uchar) round(k * (pixels[i + j * width] - min));
        }
        uchar* scan = image->scanLine(j);
        copy(temp, temp + width, scan);
    }
    delete[]temp;

    return new ImageInfo("binary_image", "二进制文件读取成功！", image);
}

ImageInfo* ImageProcess::grayMapping(unsigned short *pixels, int width, int height, int windowIndex, int windowWidth){

    int halfWidth = windowWidth / 2;
    int left = windowIndex - halfWidth;
    int right = windowIndex + halfWidth;

    double k = (double)255 / (right - left);

    QImage* image = new QImage(width, height, QImage::Format_Indexed8);
    image->setColorCount(256);
    for(int i = 0; i < 256; i++){
        image->setColor(i, qRgb(i, i, i));
    }

    uchar* temp = new uchar[width];
    for(int j = 0; j < height; j++){
        for(int i = 0; i < width; i++){
            unsigned short p = pixels[i + j * width];
            if(p < left){
                temp[i] = 0;
            }else if( p > right){
                temp[i] = 255;
            }else{
                temp[i] = (uchar)round(k * (p - left));
            }
        }
        uchar* scan = image->scanLine(j);
        copy(temp, temp + width, scan);
    }
    delete[]temp;

    return new ImageInfo("gary_mapping_image", QString("灰度窗[%1, %2]调整成功！").arg(windowIndex).arg(windowWidth), image);

}

unsigned short* ImageProcess::laplaceSharpening(unsigned short *pixels, int width, int height){
    unsigned short *res = new unsigned short[width * height];
    fill(res, res + width * height, 0);
    //滤波器
    int kernel[3][3] = {
        {-1, -1, -1},
        {-1, 8, -1},
        {-1, -1, -1}
    };
    //滤波器尺寸
    const int kernelSize = 3;
    const int kernelRange = kernelSize / 2;
    //计算过程
    for(int j = 0; j < height; j++){
        for(int i = 0; i < width; i++){
            int temp = 0;
            //滤波器滤波
            for(int x = -1 * kernelRange; x <= kernelRange; x++){
                for(int y = -1 * kernelRange; y <= kernelRange; y++){
                    int m = j + x, n = i + y;
                    if(m >= 0 && n >= 0 && m < height && n < width){
                        temp += kernel[x + kernelRange][y + kernelRange] * pixels[n + m * width];
                    }
                }
            }
            if(pixels[i + j * width] + temp < 0){
                res[i + j * width] = 0;
            }else{
                res[i + j * width] = pixels[i + j * width] + temp;
            }

        }
    }
    return res;
}


ImageInfo* ImageProcess::grayInversion(ImageInfo *image){
    QImage* imageR = new QImage(image->getImage()->width(), image->getImage()->height(), QImage::Format_Indexed8);
    imageR->setColorCount(256);
    for(int i = 0; i < 256; i++){
        imageR->setColor(i, qRgb(i, i, i));
    }
    uchar* temp = new uchar[image->getImage()->width()];
    for(int j = 0; j < image->getImage()->height(); j++){
        const uchar* tempI = image->getImage()->constScanLine(j);
        for(int i = 0; i < image->getImage()->width(); i++){
            temp[i] = 255 - tempI[i];
        }
        uchar* scan = imageR->scanLine(j);
        copy(temp, temp + image->getImage()->width(), scan);
    }
    delete[]temp;

    return new ImageInfo("gray_inversion_image", "灰度反转成功！", imageR);
}

ImageInfo* ImageProcess::hReverse(ImageInfo *image){
    QImage* imageR = new QImage(image->getImage()->width(), image->getImage()->height(), QImage::Format_Indexed8);
    imageR->setColorCount(256);
    for(int i = 0; i < 256; i++){
        imageR->setColor(i, qRgb(i, i, i));
    }
    uchar* temp = new uchar[image->getImage()->width()];
    for(int j = 0; j < image->getImage()->height(); j++){
        const uchar* tempI = image->getImage()->constScanLine(j);
        for(int i = 0; i < image->getImage()->width(); i++){
            temp[i] = tempI[image->getImage()->width() - 1 - i];
        }
        uchar* scan = imageR->scanLine(j);
        copy(temp, temp + image->getImage()->width(), scan);
    }
    delete[]temp;

    return new ImageInfo("h_reverse_image", "图像左右翻转成功！", imageR);
}
