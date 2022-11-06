#include "graymappingdialog.h"
#include "ui_graymappingdialog.h"

GrayMappingDialog::GrayMappingDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::GrayMappingDialog)
{
    ui->setupUi(this);
}

GrayMappingDialog::~GrayMappingDialog()
{
    delete ui;
}


int GrayMappingDialog::getMaxGray(){
    return ui->textEdit->toPlainText().toInt();
}

int GrayMappingDialog::getWindowIndex(){
    return ui->textEdit_2->toPlainText().toInt();
}

int GrayMappingDialog::getWindowWidth(){
    return ui->textEdit_3->toPlainText().toInt();
}

void GrayMappingDialog::initParams(ParameterSetting *param){
    ui->textEdit->setText(QString("%1").arg(param->GRAY_SCALE));
    ui->textEdit_2->setText(QString("%1").arg(param->GRAY_WINDOW.x));
    ui->textEdit_3->setText(QString("%1").arg(param->GRAY_WINDOW.y));
}
