#ifndef IMAGEINFO_H
#define IMAGEINFO_H
#include<QString>
#include<QImage>
#include<QRegExp>
#include<QStringList>


class ImageInfo{

private:
    //文件名称
    QString fileName;
    //图片备注信息
    QString remark;
    //图片对象
    QImage* image;


public:
    ImageInfo();

    ~ImageInfo();

    ImageInfo(QString fileName, QString remark, QImage* image);

    ImageInfo(QString filePath);
    //读取图片
    bool readImage(QString filePath);
    //保存图片
    bool saveImage(QString filePath);
    //默认图片保存
    bool saveImage();
    /*------------Bean相关操作----------*/
    void setFileName(QString fileName);

    void setRemark(QString reamrk);

    void setImage(QImage* image);

    QString getFileName();

    QString getRemark();

    QImage* getImage();
};

#endif // IMAGEINFO_H
