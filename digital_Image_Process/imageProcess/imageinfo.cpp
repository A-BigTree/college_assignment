#include "imageinfo.h"

ImageInfo::ImageInfo(){

}

ImageInfo::~ImageInfo(){
    delete image;
}

ImageInfo::ImageInfo(QString fileName, QString remark, QImage* image){
    this->fileName = fileName;
    this->remark = remark;
    this->image = image;
}


ImageInfo::ImageInfo(QString filePath){
    QStringList list = filePath.split(QRegExp("[\\/]"));
    int index = list.length();

    this->fileName = list[index - 1];

    this->remark = "原始图片读取";
    if(this->readImage(filePath)){
        this->remark.append("成功！");
    }else{
        this->remark.append("失败!");
    }
}

bool ImageInfo::readImage(QString filePath){
    this->image = new QImage();
    if(!(image->load(filePath))){
        return false;
    }
    return true;
}

bool ImageInfo::saveImage(QString filePath){
    return this->image->save(filePath);
}

bool ImageInfo::saveImage(){
    return this->image->save(this->fileName);
}

void ImageInfo::setFileName(QString fileName){
    this->fileName = fileName;
}

void ImageInfo::setRemark(QString reamrk){
    this->remark = reamrk;
}

void ImageInfo::setImage(QImage* image){
    this->image = image;
}

QString ImageInfo::getFileName(){
    return this->fileName;
}

QString ImageInfo::getRemark(){
    return this->remark;
}

QImage* ImageInfo::getImage(){
    return this->image;
}
