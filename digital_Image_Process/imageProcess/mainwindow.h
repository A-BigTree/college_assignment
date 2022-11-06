#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QFileDialog>
#include <QMessageBox>
#include <QPixmap>
#include <QLabel>
#include <QFrame>
#include<QFile>
#include<QDebug>

#include "imageinfo.h"
#include "imageprocess.h"
#include "parametersetting.h"
#include "zoommanagedialog.h"
#include "graymappingdialog.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
    //指定label展示图片
    void showImage(QLabel *label, QImage *image);
    //显示结果图片（未完成
    void showResultImages();

private slots:
    //读取图片
    void on_actionImport_triggered();

    //功能运行
    void on_pushButton_clicked();

    void on_actionParameters_triggered();

    void on_actionread_binary_triggered();

private:
    Ui::MainWindow *ui;
    //原始图片
    ImageInfo *originImage = NULL;
    //灰度图片
    ImageInfo *grayImage = NULL;
    //结果图片列表
    ImageInfo **resultImages = NULL;
    //结果图片数量
    int resultNum;
    //图像处理
    ImageProcess *process;
    //参数设置
    ParameterSetting *params;

};
#endif // MAINWINDOW_H
