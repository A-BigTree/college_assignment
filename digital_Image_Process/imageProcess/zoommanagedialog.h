#ifndef ZOOMMANAGEDIALOG_H
#define ZOOMMANAGEDIALOG_H

#include <QDialog>
#include<parametersetting.h>

namespace Ui {
class zoomManageDialog;
}

class zoomManageDialog : public QDialog
{
    Q_OBJECT

public:
    explicit zoomManageDialog(QWidget *parent = nullptr);
    ~zoomManageDialog();

    int getWidth();
    int getHeight();

    int getIndexX();
    int getIndexY();
    double getAngle();
    bool isLeft();

    void initDialog(ParameterSetting *params);

private:
    Ui::zoomManageDialog *ui;
};

#endif // ZOOMMANAGEDIALOG_H
