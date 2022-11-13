# -*- coding:utf-8 -*-
import matplotlib.pyplot as plt
import pylab as mpl
import pandas as pd
import numpy as np
import csv
import re


# 读取csv文件
def dataReadFromCsv(filename, dicName='', isDic=False):
    dataList = []
    try:
        with open(filename, 'r', encoding='utf-8') as f:
            # 全部读取
            if not isDic:
                reader = csv.reader(f)
                # 跳过标题
                header = next(reader)
                for data in reader:
                    dataList.append(data)
                return dataList, header
            # 按照标题查询
            else:
                reader = csv.DictReader(f)
                for data in reader:
                    try:
                        if data[dicName] == 'nan':
                            dataList.append(0.)
                        else:
                            dataList.append(data[dicName])
                    except:
                        print('列名不存在！！')
                        return
                return dataList
    except:
        print('读取csv文件失败！！！')
        return


# 画图1
def dataToImage(dataX, dataY, title='', x_title='', y_title=''):
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.plot(dataX, dataY)
    # 设置标签
    plt.title(title)
    plt.xlabel(x_title)
    plt.ylabel(y_title)
    plt.yticks(range(int(min(dataY)), int(max(dataY)), int((int(min(dataY)) - int(max(dataY))) / 10)))
    plt.show()


# 食物类别索引
def foodKindIndex():
    # 分类数据
    dataKind = dataReadFromCsv('data\\food.csv', dicName='分类', isDic=True)
    kindIndex = []
    temp = 0
    start = 0
    tempStr = dataKind[0]
    while temp < len(dataKind):
        temp += 1
        if temp == len(dataKind):
            kindIndex.append((start, temp, temp - start, tempStr))
            break
        if tempStr != dataKind[temp]:
            # 四元组(start,end,num,foodName)
            kindIndex.append((start, temp, temp - start, tempStr))
            tempStr = dataKind[temp]
            start = temp
    del dataKind
    return kindIndex


# 一张图内绘制多条曲线
def datasToImage(dataY, kindList):
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.figure(figsize=(15, 10), dpi=500)
    for i in range(len(kindList)):
        plt.plot([i for i in range(kindList[i][2])], dataY[kindList[i][0]:kindList[i][1]], label=kindList[i][3])
        plt.yticks(range(int(min(dataY)), int(max(dataY)), int((int(min(dataY)) - int(max(dataY))) / 10)))
    # 自动检测要在图例中显示的元素，并且显示
    plt.legend()
    plt.show()


# 不同食物种类的数目柱状图
def dataToImage2(data):
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.figure(figsize=(15, 10), dpi=200)
    name_list = [tu[3] for tu in data]
    num_list = [tu2[2] for tu2 in data]
    plt.bar(range(len(num_list)), num_list, tick_label=name_list)
    for x, y in enumerate(num_list):
        plt.text(x, y, '%s' % y, ha='center', va='bottom')
    plt.title('食物种类数量')
    plt.xlabel('食物种类')
    plt.ylabel('数量')
    plt.show()


# 不同食物种类的平均热量柱状图
def dataToImage3(data, dataC):
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.figure(figsize=(15, 10), dpi=200)
    name_list = [tu[3] for tu in data]
    num_list = [round(sum(dataC[tu2[0]:tu2[1]]) / tu2[2], 2) for tu2 in data]
    plt.bar(range(len(num_list)), num_list, tick_label=name_list)
    for x, y in enumerate(num_list):
        plt.text(x, y, f'{y}', ha='center', va='bottom')
    plt.title('不同食物种类平均热量')
    plt.xlabel('食物种类')
    plt.ylabel('平均热量(千卡/100g)')
    plt.show()


# 不同食物种类最高与最低热量
def dataToImage4(data, dataC, dataA):
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.figure(figsize=(20, 10), dpi=200)
    max_list = []
    min_list = []
    for tu2 in data:
        temp = dataC[tu2[0]:tu2[1]]
        max_index = temp.index(max(temp))
        min_index = temp.index(min(temp))
        max_list.append(max_index + tu2[0])
        min_list.append(min_index + tu2[0])
    name_list = [tu[3] for tu in data]
    num_list = [dataC[i] for i in max_list]
    num_list1 = [dataC[i] for i in min_list]
    num_list2 = [round(sum(dataC[tu2[0]:tu2[1]]) / tu2[2], 2) for tu2 in data]
    plt.bar(np.array(range(len(num_list))) - 0.3, num_list, label='最高', tick_label=name_list, color='indianred',
            width=0.3)
    plt.bar(np.array(range(len(num_list))), num_list2, label='平均', color='green', width=0.3)
    plt.bar(np.array(range(len(num_list))) + 0.3, num_list1, label='最高', color='steelblue', width=0.3)
    for x, y in enumerate(num_list):
        plt.text(x - 0.3, y, f'{dataA[max_list[x]][0]}:{y}', ha='center', va='bottom')
    for x, y in enumerate(num_list2):
        plt.text(x, y, f'平均:{y}', ha='center', va='bottom')
    for x, y in enumerate(num_list1):
        plt.text(x + 0.3, y, f'{dataA[min_list[x]][0]}:{y}', ha='center', va='bottom')
    plt.title('不同食物种类最高与最低热量')
    plt.xlabel('食物种类')
    plt.ylabel('热量(千卡/100g)')
    plt.legend()
    plt.show()


# 搜索特定食物
def searchFood(foodName, dataA):
    reIndex = []
    for i in range(len(dataA)):
        # 查找名称
        if re.search(foodName, dataA[i][0]) is not None:
            reIndex.append(i)
        # 查找别名
        elif re.search(foodName, dataA[i][1]) is not None:
            reIndex.append(i)
    # 返回查找结果索引
    return reIndex


# 特定食物生成热量柱状图
def specialDataToImage(dataIndex, dataC, dataA, header):
    print(f'查找到{len(dataIndex)}个结果。。。')
    if len(dataIndex) == 0:
        print('数据索引为空！！')
        return
    elif len(dataIndex) == 1:
        print('只查找到一项，信息如下：')
        print(f'名称：{dataA[dataIndex[0]][0]}\t别名：{dataA[dataIndex[0]][1]}\t特征：{dataA[dataIndex[0]][2]}')
        print('详细信息：')
        print([f'{header[i]}:{dataA[dataIndex[0]][i]}'for i in range(3, len(header))])
        return
    elif len(dataIndex) >= 25:
        print('查找结果大于25个，结果如下，只给出前10个结果热量柱状图：')
        print([f'{dataA[i][0]}({dataA[i][2]})' for i in dataIndex])
        dataIndex = dataIndex[0:25]
    # 指定默认字体
    mpl.rcParams['font.sans-serif'] = ['FangSong']
    # 解决保存图像是负号'-'显示为方块的问题
    mpl.rcParams['axes.unicode_minus'] = False
    plt.figure(figsize=(25, 10),dpi=100)
    name_list = []
    for i in dataIndex:
        name = dataA[i][0]
        if dataA[i][2] != 'Empty':
            name += f'\n({dataA[i][2]})'
        name_list.append(name)
    num_list = [dataC[i] for i in dataIndex]
    plt.bar(range(len(num_list)), num_list, tick_label=name_list)
    for x, y in enumerate(num_list):
        plt.text(x, y, '%s' % y, ha='center', va='bottom')
    plt.title('食物热量比较')
    plt.xlabel('食物名称')
    plt.ylabel('热量(千卡/100g)')
    plt.show()


# 主函数
if __name__ == '__main__':
    # data1 = pd.read_csv('data\\food.csv')
    data2, headers = dataReadFromCsv('data\\food.csv')
    # 数据概览
    # print(data1.info())
    # 热量数据
    dataCalorie = dataReadFromCsv('data\\food.csv', dicName='热量(千卡)', isDic=True)
    dataCalorie = [float(i) for i in dataCalorie]
    kindI = foodKindIndex()
    # 不同食物种类的数目柱状图
    # dataToImage2(kindI)
    # 不同食物种类的平均热量柱状图
    # dataToImage3(kindI, dataCalorie)
    # 不同食物种类最高与最低热量柱状图
    # dataToImage4(kindI, dataCalorie, data2)
    # 查找食物
    while 1:
        foodN = input('输入要查找食物的名称(退出请输入0)：')
        if foodN == '0':
            break
        reList = searchFood(foodN, data2)
        specialDataToImage(reList, dataCalorie, data2, headers)
