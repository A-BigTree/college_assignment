# -*- coding:utf-8 -*-
# @author  : Shuxin_Wang
# @email   : 213202122@seu.edu.cn
# @time    : 2022/8/10 
# @function: create catalogue for a md file
# @version : V1.0 
#
import re

import pyperclip

REG_CODE1 = re.compile("^```")
REG_CODE2 = re.compile("```$")


def catalogue_get(line_: str, level: int) -> str:
    l3 = line_[level::].strip()
    res = "- [%s](#%s)\n" % (l3, l3.replace(" ", "-"))
    for i in range(level - 1):
        res = "\t" + res
    return res


def create_catalogue(file_: str, max_level: int = None) -> str:
    try:
        f = open(file_, encoding='utf-8')
    except Exception as e:
        return e.__str__()
    res = ""
    if_code = False
    for line in f:
        l1 = line.strip()
        if REG_CODE1.findall(l1) and REG_CODE2.findall(l1):
            continue
        if if_code:
            if REG_CODE1.findall(l1) or REG_CODE2.findall(l1):
                if_code = False
                continue
            else:
                continue
        else:
            if REG_CODE1.findall(l1):
                if_code = True
                continue
        level = 0
        for l2 in l1:
            if l2 == '#':
                level += 1
            else:
                break
        if level == 0:
            continue
        if max_level is None:
            res += catalogue_get(l1, level)
        elif max_level >= level:
            res += catalogue_get(l1, level)
    pyperclip.copy(res)
    return res


if __name__ == "__main__":
    file_name = str(input("Please input file path:"))
    print(create_catalogue(file_name))
