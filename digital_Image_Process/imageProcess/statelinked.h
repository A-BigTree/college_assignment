#ifndef STATELINKED_H
#define STATELINKED_H

#include "parametersetting.h"

#define MAX_STATE 5

class StateLinked
{
private:
    ParameterSetting *params = new ParameterSetting[MAX_STATE];
    int sP = 0;
    int eP = 0;
    int iP = 0;

public:
    StateLinked();
};

#endif // STATELINKED_H
