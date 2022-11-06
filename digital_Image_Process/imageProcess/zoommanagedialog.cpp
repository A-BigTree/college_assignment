#include "zoommanagedialog.h"
#include "ui_zoommanagedialog.h"

zoomManageDialog::zoomManageDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::zoomManageDialog)
{
    ui->setupUi(this);
}

zoomManageDialog::~zoomManageDialog()
{
    delete ui;
}

int zoomManageDialog::getWidth(){
    return ui->wTextEdit->toPlainText().toInt();
}

int zoomManageDialog::getHeight(){
    return ui->hTextEdit->toPlainText().toInt();

}

int zoomManageDialog::getIndexX(){
    return ui->xTtextEdit->toPlainText().toInt();

}

int zoomManageDialog::getIndexY(){
    return ui->yTextEdit_2->toPlainText().toInt();
}

double zoomManageDialog::getAngle(){
    return ui->aTextEdit_3->toPlainText().toDouble();
}

bool zoomManageDialog::isLeft(){
    return ui->checkBox->isChecked();
}

void zoomManageDialog::initDialog(ParameterSetting *params){
    ui->wTextEdit->setText(QString("%1").arg(params->WIDTH_AND_HEIGHT.x));
    ui->hTextEdit->setText(QString("%1").arg(params->WIDTH_AND_HEIGHT.y));
    ui->xTtextEdit->setText(QString("%1").arg(params->CENTER_POINT.x));
    ui->yTextEdit_2->setText(QString("%1").arg(params->CENTER_POINT.y));
    ui->aTextEdit_3->setText(QString("%1").arg(params->ROTATION_ANGLE));
    if(!params->RIGHT_OR_LEFT){
        ui->checkBox->setCheckState(Qt::Checked);
    }
}
