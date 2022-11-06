#ifndef GRAYMAPPINGDIALOG_H
#define GRAYMAPPINGDIALOG_H

#include <QDialog>

#include "parametersetting.h"

namespace Ui {
class GrayMappingDialog;
}

class GrayMappingDialog : public QDialog
{
    Q_OBJECT

public:
    explicit GrayMappingDialog(QWidget *parent = nullptr);
    ~GrayMappingDialog();

    int getMaxGray();
    int getWindowIndex();
    int getWindowWidth();

    void initParams(ParameterSetting *param);

private:
    Ui::GrayMappingDialog *ui;
};

#endif // GRAYMAPPINGDIALOG_H
