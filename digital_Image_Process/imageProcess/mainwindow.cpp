#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow){
    ui->setupUi(this);
    process = new ImageProcess();
    params = new ParameterSetting();
    state = new StateLinked();
    ui->actionundo->setEnabled(false);
    ui->actiongoto->setEnabled(false);
}

MainWindow::~MainWindow(){
    delete ui;
    delete originImage;
    delete grayImage;
    delete[]resultImages;
    delete process;
    delete state;
}

void MainWindow::showImage(QLabel *label, QImage *image){
    QPixmap pic = QPixmap::fromImage(*image);
    label->setPixmap(pic);
    label->resize(image->size());

}


void MainWindow::showResultImages(){
    //清空horizontalLayout布局内的所有元素
    QLayoutItem *child;
     while ((child = ui->resultLayout->takeAt(0)) != 0)
     {
            //setParent为NULL，防止删除之后界面不消失
            if(child->widget())
            {
                child->widget()->setParent(NULL);
            }

            delete child;
     }


    for(int i = 0;i<resultNum;i++){
        QLabel *textLabel = new QLabel;
        textLabel->setAlignment(Qt::AlignCenter);
        textLabel->setText(resultImages[i]->getRemark());
        ui->resultLayout->addWidget(textLabel, i * 2, 0);

        QLabel *imageLabel = new QLabel;
        imageLabel->setAlignment(Qt::AlignCenter);
        imageLabel->setFrameShape(QFrame::Box);
        showImage(imageLabel, resultImages[i]->getImage());
        ui->resultLayout->addWidget(imageLabel, i * 2 + 1, 0);
    }


}


void MainWindow::on_actionImport_triggered(){
    QString path = QFileDialog::getOpenFileName(this, "打开文件", "C:\\Users", "*.bmp *.jpg *.png");

    if(!path.isEmpty()){
        if(originImage != NULL){
            delete originImage;
        }
        if(grayImage != NULL){
            delete grayImage;
        }

        //显示原图
        originImage = new ImageInfo(path);
        //初始化参数
        params->initParameters(originImage);
        //显示原图
        ui->remarkLabel->setText(originImage->getRemark());
        showImage(ui->originImage, originImage->getImage());
        //显示灰度图
        grayImage = process->toGray(originImage);
        ui->remarkLabel->setText(
                    ui->remarkLabel->text().append("\n").append(grayImage->getRemark())
                    );
        showImage(ui->grayImage, grayImage->getImage());
    }else{
        QMessageBox::information(this, tr("错误"), tr("打开图像失败"));
    }
    return;
}


void MainWindow::on_pushButton_clicked(){
    runAction(ui->comboBox->currentIndex());
    params->setCode(ui->comboBox->currentIndex());
    if(ui->comboBox->currentIndex() == 6)
        params->IS_G_R = !params->IS_G_R;
    if(ui->comboBox->currentIndex() == 7)
        params->IS_H_R = !params->IS_H_R;
    state->addParam(params);
    ui->actionundo->setEnabled(state->canUndo());
    ui->actiongoto->setEnabled(state->canGoto());
}


void MainWindow::on_actionParameters_triggered()
{
    int ret;
    int index = ui->comboBox->currentIndex();

    if(index == 2||index == 3){
        zoomManageDialog zd;
        zd.setWindowTitle("缩放旋转参数设置");
        zd.initDialog(params);
        ret=zd.exec();

        if(ret == QDialog::Accepted){
            params->setScale(zd.getWidth(), zd.getHeight());
            params->setRotation(zd.getIndexX(), zd.getIndexY(), zd.getAngle(), !zd.isLeft());
        }
    }else if(index == 4 || index == 5){
        GrayMappingDialog gm;
        gm.setWindowTitle("灰度窗映射设置");
        gm.initParams(params);
        ret = gm.exec();

        if(ret == QDialog::Accepted){
            params->setGrayMapping(gm.getMaxGray(), gm.getWindowIndex(), gm.getWindowWidth());
        }
    }

}


void MainWindow::on_actionread_binary_triggered(){
    QString path = QFileDialog::getOpenFileName(this, "打开文件", "C:\\Users", "*");

    if(!path.isEmpty()){
        if(originImage != NULL){
            delete originImage;
        }
        if(grayImage != NULL){
            delete grayImage;
        }

        //读取二进制文件
        QFile file(path);
        file.open(QIODevice::ReadOnly);

        QByteArray res = file.readAll();
        char *data = res.data();

        int width = *(unsigned long*)data;
        data += sizeof(unsigned long);
        int height = *(unsigned long*)data;
        data += sizeof(unsigned long);

        unsigned short max = 0, min = 9999;

        params->PIXEL_SUM = width * height;
        unsigned short* pixels = new unsigned short[width * height];
        for(int i = 0; i < width * height; i++){
            pixels[i] = *(unsigned short*)data;
            data += sizeof(unsigned short);
            if(pixels[i] > max){
                max = pixels[i];
            }
            if(pixels[i] < min){
                min = pixels[i];
            }
        }
        params->PIXEL = pixels;

        originImage = process->readBinaryImage(params->PIXEL, width, height, min, max);
        //初始化参数
        params->initParameters(originImage);
        //显示原图
        ui->remarkLabel->setText(originImage->getRemark());
        showImage(ui->originImage, originImage->getImage());
        //显示灰度图
        grayImage = process->toGray(originImage);
        ui->remarkLabel->setText(
                    ui->remarkLabel->text().append("\n").append(grayImage->getRemark())
                    );
        showImage(ui->grayImage, grayImage->getImage());

    }else{
        QMessageBox::information(this, tr("错误"), tr("打开图像失败"));
    }
}


void MainWindow::on_actionSave_triggered(){
    int i;
    for(i = 0; i < resultNum; i++){
        QString curPath = QDir::currentPath();
        QString dlgTitle = "保存结果图片";
        QString filter = "灰度图(*.bmp);;矢量图(*.png);;标准图(*.jpg);;所有文件(*.*)";
        QString aFileName = QFileDialog::getSaveFileName(this, dlgTitle, curPath.append("/untitled"), filter);
        //qDebug()<<aFileName;
        if(aFileName.isEmpty()){
            QMessageBox::information(this, tr("错误"), tr("保存图像失败"));
        }else{
            resultImages[i]->saveImage(aFileName);
        }
    }
    ui->remarkLabel->setText(QString("结果图像保存成功！共%1张.").arg(i));
}


void MainWindow::runAction(int state){
    if(originImage == NULL){
        return;
    }
    //初始化
    if(resultImages!=NULL){
        delete []resultImages;
    }
    if(imageR == NULL){
        imageR = originImage;
    }
    resultNum = 0;

    int widthO, heightO;

    switch (state) {

    //直方图均衡化
    case 0:
        resultImages = new ImageInfo*[2];
        resultNum = 2;
        resultImages[1] = process->imageAverage(originImage);
        resultImages[0] = process->imageAverage(imageR, false);
        imageR = process->imageAverage(imageR, false);
        ui->remarkLabel->setText("直方图均衡化成功！");
        showResultImages();
        params->IS_HI = true;
        break;

    //傅里叶变换
    case 1:
        break;

    //图片缩放
    case 2:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->imageZoom(imageR, params->WIDTH_AND_HEIGHT.x, params->WIDTH_AND_HEIGHT.y);
        imageR = process->imageZoom(imageR, params->WIDTH_AND_HEIGHT.x, params->WIDTH_AND_HEIGHT.y);
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        showResultImages();
        break;

    //图片旋转
    case 3:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->imageRotation(imageR, params->CENTER_POINT.x, params->CENTER_POINT.y,
                                                 params->ROTATION_ANGLE, params->RIGHT_OR_LEFT);
        imageR = process->imageRotation(imageR, params->CENTER_POINT.x, params->CENTER_POINT.y,
                                        params->ROTATION_ANGLE, params->RIGHT_OR_LEFT);
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        showResultImages();
        break;

    //灰度窗调整
    case 4:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->grayMapping(params->PIXEL, originImage->getImage()->width(),
                                               originImage->getImage()->height(), params->GRAY_WINDOW.x,
                                               params->GRAY_WINDOW.y);
        imageR = process->grayMapping(params->PIXEL, originImage->getImage()->width(),
                                      originImage->getImage()->height(), params->GRAY_WINDOW.x,
                                      params->GRAY_WINDOW.y);
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        widthO = originImage->getImage()->width();
        heightO = originImage->getImage()->height();
        if(originImage != NULL){
            delete originImage;
        }
        if(grayImage != NULL){
            delete grayImage;
        }
        originImage = process->grayMapping(params->PIXEL, widthO, heightO, params->GRAY_WINDOW.x, params->GRAY_WINDOW.y);
        grayImage = process->toGray(originImage);
        showImage(ui->originImage, originImage->getImage());
        showImage(ui->grayImage, grayImage->getImage());
        showResultImages();
        params->IS_G_W = true;
        break;

    //图像增强
    case 5:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->grayMapping(process->laplaceSharpening(params->PIXEL,originImage->getImage()->width(),originImage->getImage()->height()),
                                               originImage->getImage()->width(),
                                               originImage->getImage()->height(),params->GRAY_WINDOW.x,
                                               params->GRAY_WINDOW.y);
        imageR = process->grayMapping(process->laplaceSharpening(params->PIXEL,originImage->getImage()->width(),originImage->getImage()->height()),
                                      originImage->getImage()->width(),
                                      originImage->getImage()->height(),params->GRAY_WINDOW.x,
                                      params->GRAY_WINDOW.y);
        resultImages[0]->setRemark(resultImages[0]->getRemark().prepend("图像增强成功！"));
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        widthO = originImage->getImage()->width();
        heightO = originImage->getImage()->height();
        if(originImage != NULL){
            delete originImage;
        }
        if(grayImage != NULL){
            delete grayImage;
        }
        originImage = process->grayMapping(params->PIXEL, widthO, heightO, params->GRAY_WINDOW.x, params->GRAY_WINDOW.y);
        grayImage = process->toGray(originImage);
        showImage(ui->originImage, originImage->getImage());
        showImage(ui->grayImage, grayImage->getImage());
        showResultImages();
        params->IS_LA = true;
        break;
    //灰度反转
    case 6:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->grayInversion(imageR);
        imageR = process->grayInversion(imageR);
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        showResultImages();
        break;
    //左右翻转
    case 7:
        resultImages = new ImageInfo*[1];
        resultNum = 1;
        resultImages[0] = process->hReverse(imageR);
        imageR = process->hReverse(imageR);
        ui->remarkLabel->setText(resultImages[0]->getRemark());
        showResultImages();
        break;

    default:
        break;
    }
}


void MainWindow::wheelEvent(QWheelEvent *event){
    if(resultNum == 0){
        return;
    }
    QPoint numDegrees = event->angleDelta();
    double step = 0;
    if(!numDegrees.isNull()){
        step = (double) numDegrees.y() / 1200;
    }
    event->accept();

    int cWidth = resultImages[0]->getImage()->width();
    int cHeight = resultImages[0]->getImage()->height();

    cWidth += (int) cWidth * step;
    cHeight += (int) cHeight * step;

    QImage temp = imageR->getImage()->scaled(cWidth, cHeight, Qt::IgnoreAspectRatio, Qt::SmoothTransformation);
    delete resultImages[0];
    resultImages[0] = new ImageInfo("", "", new QImage(temp));
    showResultImages();
}

void MainWindow::on_actionundo_triggered()
{
    qDebug() << "Undo";
    qDebug()<<state->getIndex();
    params = state->undoAction();
    qDebug() << state->getIndex();
    runAction(params->code);
    ui->actionundo->setEnabled(state->canUndo());
    ui->actiongoto->setEnabled(state->canGoto());
}


void MainWindow::on_actiongoto_triggered()
{
    qDebug()<<"Goto";
    qDebug()<<state->getIndex();
    params = state->gotoAction();
    qDebug()<<state->getIndex();
    runAction(params->code);
    ui->actionundo->setEnabled(state->canUndo());
    ui->actiongoto->setEnabled(state->canGoto());
}

