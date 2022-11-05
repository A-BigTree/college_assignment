# Wikidata data model



- **author  : Shuxin_Wang**
- **email   : 213202122@seu.edu.cn**
- **time    : 2022/6/6**



------

***目录***

- [前言](#前言)
- [官方API使用](#官方API使用)
  - [`action` = `wbsearchentities`](#`action`-=-`wbsearchentities`)
  	- [参数设置](#参数设置)
  	- [查询过程](#查询过程)
  	- [json数据解析](#json数据解析)
  - [`action`=`wbgetentities`](#`action`=`wbgetentities`)
  	- [参数设置](#参数设置)
  	- [查询过程](#查询过程)
  	- [json数据解析](#json数据解析)
- [维基百科数据模型（Wikidata datamodel）](#维基百科数据模型（Wikidata-datamodel）)
- [ch1.基本数据模型（Datamodel）](#ch1.基本数据模型（Datamodel）)
- [---> ch2.label(实体名称)](#--->-ch2.label(实体名称))
- [---> ch3.aliases(别名)](#--->-ch3.aliases(别名))
- [---> ch4.descriptions(描述)](#--->-ch4.descriptions(描述))
- [---> ch5.claims(属性)⭐](#--->-ch5.claims(属性)⭐)
  - [--> ch5.1 datatype(数据类型)](#-->-ch5.1-datatype(数据类型))
  - [--> ch5.2 valuetype(值类型)](#-->-ch5.2-valuetype(值类型))
  	- [wikibase-entityid(维基数据实体id值)](#wikibase-entityid(维基数据实体id值))
  	- [globecoordinate(地理位置)](#globecoordinate(地理位置))
  	- [time(时间)](#time(时间))
  	- [string(字符串)](#string(字符串))
  	- [monolingualtext(未翻译文本)](#monolingualtext(未翻译文本))
  	- [quantity(十进制数字)](#quantity(十进制数字))
  - [--> ch 5.3 qualifiers-order(限定词顺序)](#-->-ch-5.3-qualifiers-order(限定词顺序))
  - [--> ch 5.4 qualifiers(限定词)](#-->-ch-5.4-qualifiers(限定词))
  - [--> ch 5.5 references(引用)](#-->-ch-5.5-references(引用))
- [---> ch6.sitelinks(外部链接)](#--->-ch6.sitelinks(外部链接))
- [Example](#Example)
  - [部分`claims`](#部分`claims`)
  - [`sitelinks`](#`sitelinks`)



# 前言



- 维基百科是☞多种语言、内容自由个网络百科全书项目。而维基数据是一个自由的协作式的多语言辅助数据库，用于收集结构化的数据，旨在支援维基百科、维基共享资源以及其他维基媒体运动中的项目。
- 维基数据可以通过多种方式访问：使用内建工具、外部工具或程序接口。
	- Wikidata Query和Reasonator是搜索和检查维基数据项的一些流行工具。tools一页有大量有趣的项目可供探索。
	- 客户端维基可以使用Lua Scribunto接口访问其页面的数据。您可以使用**Wikidata API**独立检索所有数据。
- 我们这次利用python调用[官方API](https://www.wikidata.org/w/api.php)来实现数据的获取。



# 官方API使用

- **注意：因为某些原因wiki国内好像访问不稳定，必要请在魔法😀下使用**



Wiki官方提供了很多不同功能的接口，具体见其[官方文档](https://www.wikidata.org/w/api.php)。

- `url`=`https://www.wikidata.org/w/api.php`

- 这里我们使用两个`action`,`action`=`wbsearchentities`和`action`=`wbgetentities`
- `action`=`wbsearchentities`:对输入**文本**进行搜索得到**实体id**；
- `action`=`wbgetentities`：对输入**实体id**搜索得到该**实体的详细信息**；



## `action` = `wbsearchentities`



- 接口API文档[MediaWiki API help - Wikidata](https://www.wikidata.org/w/api.php?action=help&modules=wbsearchentities)



### 参数设置

- `action`:`'wbsearchentities'`
- `format`:`'json'`

- `search`:(**带查询文本**)必须⭐
- `language`:(**查询语言**)必须⭐
- `type`:(**查询实体类型，默认：`'item'`**)
- `limit`:(**结果返回最大数量，默认：`7`**)
- `strictlanguage`:(**是否禁用语言回退**)非必须
- `continue`:(**继续搜索的偏移量，默认：`0`**)
- `props`:(**返回每个实体的这些属性,默认：`'url'`，好像只有url😓**)



### 查询过程

以文本`"SEU"`为例

```python
import requests

url = "https://www.wikidata.org/w/api.php"

params = {
    'action': 'wbsearchentities',
    'format': 'json',
    'search': "SEU",   # 搜索文本
    'language': 'en',  # 查询语言（英文）
    'type': 'item',    
    'limit': 10        # 返回最大数目
}

# 访问
get = requests.get(url=url, params=params)
# 转为json数据
re_json = get.json()
print(re_json)
```



### json数据解析

**该json数据结构较简单，分析如下：**

- `searchinfo`:
  - `search`:**"SEU"**
- `search`:
  - `0..*`(**结果队列**)
    - `id`(实体id):**(目标值)**
    - `titile`(实体id，同`id`)：**(目标值)**
    - `pageid`(网页id):**(目标值)**
    - `display`:
      - `label`(实体名称)：
        - `language`(语言):**(目标值)**
        - `value`:**(目标值)**
      - `(description)`(实体描述)
        - `language`(语言)：**(目标值)**
        - `value`:**(目标值)**
    - `repository`(存储库)：**(目标值)**
    - `url`(实体链接)：**(目标值)**
    - `concepturi`(实体概念链接):**(目标值)**
    - `label`(实体名称):**(目标值)**
    - `description`(实体描述):**(目标值)**
    - `match`(该结果的匹配实体出处，即为什么返回该实体)：
      - `type`(查询部分,label? alias(别名)?,description?):**(目标值)**
      - `language`:`en`
      - `text`:**"SEU"**
    - `aliases`(别名，简称):
      - `0..*`(**列表**)
        - **(目标值)**
- `search-continue`(返回结果数目):**(目标值)**
- `success`(是否查询成功):`1`



通过解析我们可以得到一些查询结果的实体`id`，比如`Q3551770`，但是我们只能简单知道该实体的名称、描述，如果我们想知道**该实体的更多详细信息，比如属性关系等**，我们就需要利用下面介绍的`action`=`wbgetentities`



## `action`=`wbgetentities`



- 接口API文档[MediaWiki API help - Wikidata](https://www.wikidata.org/w/api.php?action=help&modules=wbgetentities)



### 参数设置

- `action`:`'wbgetentities'`
- `format`:`'json'`
- `ids`:(**带查询实体id，可接受多个用`'|'`隔开**)必须⭐
- `language`:(**查询语言**)非必须



**还有更多的参数可以调整设置，不过不是必须的，这里就不再介绍，详情见官方文档**



### 查询过程

以实体id `Q3551770`为例

```python
import requests

url = "https://www.wikidata.org/w/api.php"

params = {
    'ids': 'Q3551770',   # 实体id,可多个，比如'Q123|Q456'
    'action': 'wbgetentities',
    'format': 'json',
    'language': 'en',
}

# 访问
get = requests.get(url=url, params=params)
# 转为json数据
re_json = get.json()
print(re_json)
```



### json数据解析



**哦哦哦哦哦！重头戏来喽！**通过输出的json格式数据我们发现这个json数据”又臭又长“，看了让人头大。

其实这里的每个实体的数据包含了该实体的全部信息，**符合维基百科数据模型**，所以**想要真正清晰的解析该json数据，就要明白维基百科数据模型层次结构**。下面才开始今天真正是主题。

**前方高能！**



# 维基百科数据模型（Wikidata datamodel）

- **维基百科数据模型介绍页**[Help:Wikidata datamodel - Wikidata](https://www.wikidata.org/wiki/Help:Wikidata_datamodel)
- 使用[官方API](https://www.wikidata.org/w/api.php)**（action=wbgetentities）**查询wikidata返回实体json文件时，json数据的基本结构**（对应键值）**



# ch1.基本数据模型（Datamodel）

- `entities`:
  - `Qxx`:
    - `id`:`Qxx`
    - `titile`:`Qxx`
    - `type`:**item**
    - `pageid`:**（目标值）**
    - `lastrevid`:**（目标值）**
    - `labels` ---> **ch 2**
    - `(aliases)` ---> **ch 3**
    - `(descriptions)` ---> **ch 4**
    - `(claims)` ---> **ch 5**
    - `(sitelinks)` ---> **ch 6**

  - `Qxxx`:
    - ........

- `success`:`1`



# ---> ch2.label(实体名称)



**(`xx`为语言标记，之后不再赘述)** **具体见 `https://www.wikidata.org/w/api.php?action=help&modules=wbgetentities`的`Parameters`中 `language`介绍**。

- `labels`:
  - `xx`:
    - `language`:`xx`
    - `value`:**（目标值）**



# ---> ch3.aliases(别名)



- `aliases`:
  - `xx`
    - `0..`**(列表)**
      - `language`:`xx`
      - `value`:**（目标值）**



# ---> ch4.descriptions(描述)



- `descriptions`:
  - `xx`
    - `language`:`xx`
    - ``value`:**（目标值）**



# ---> ch5.claims(属性)⭐



**（`Pxx`为属性编号）**

- `claims`:
  - `Pxx`:
    - `0..*`**(列表)**
      - `mainsnak`
        - `snaktype`:`value`
        - `property`:`Pxx`
        - `hash`:**（目标值）**
        - `datatype`: --> **ch 5.1**
        - `datavalue`:  --> **ch 5.2**
      - `type`:`statement`
      - `(qualifiers-order)`: --> **ch 5.3**
      - `(qualifiers)`: --> **ch 5.4**
      - `(references)`: --> **ch 5.5**



## --> ch5.1 datatype(数据类型)



- wikidata目前确定的数据类型**共17种**，每种数据类型**对应一种值类型（valuetype）**（[List of all data types available - Wikidata](https://www.wikidata.org/wiki/Special:ListDatatypes)，[Help:数据类型 - Wikidata](https://www.wikidata.org/wiki/Help:Data_type/zh)），列表如下：



| Datetype                                                     |     json键值      |     Valuetype     |
| :----------------------------------------------------------- | :---------------: | :---------------: |
| Commons media**(参考维基共享资源上的文件,图片等)**           |   commonsMedia    |      string       |
| Globe coordinate**(地理位置，经纬度)**                       | globe-coordinate  |  globecoordinate  |
| Item**(内部链接到另一个项)**                                 |   wikibase-item   | wikibase-entityid |
| Property**(指向属性的内部链接)**                             | wikibase-property | wikibase-entityid |
| String**(不需要翻译成不同语言或数字格式的字符链，数字和符号)** |      string       |      string       |
| Monolingual text**(未翻译为其他语言的字符串，当地语言编写的位置实体名称，某种标识符，化学式或拉丁学名)** |  monolingualtext  |  monolingualtext  |
| External identifier**(表示外部系统中使用的标识符的字符串)**  |    external-id    |      string       |
| Quantity**(代表一个十进制数字，以及有关该数字的不确定性区间的信息，以及一个测量单位)** |     quantity      |     quantity      |
| Time**(以格里高利或朱利安日历存储日期)**                     |       time        |       time        |
| URL**(用于标识某种外部资源，可能是某种外部网站的链接)**      |        url        |      string       |
| Mathematical expression**(显示为数学公式的格式化字符串)**    |       math        |      string       |
| Geographic shape**(参考维基共享资源上的地图数据文件命名空间)** |     geo-shape     |      string       |
| Musical Notation**(描述遵循 LilyPond 语法的音乐的字符串)**   | musical-notation  |      string       |
| Tabular data**(参考维基共享资源上的表格数据文件命名字符串)** |   tabular-data    |      string       |
| Lexeme**(对于引用另一个 Lexeme 的 Lexemes 的陈述)**          |  wikibase-lexeme  | wikibase-entityid |
| Form**(对于 Lexemes 上的陈述，这些陈述引用了其他 Lexeme 上的 Forms 以表明它们的关系)** |   wikibase-form   | wikibase-entityid |
| Sense**(对于在其他 Lexemes 上引用 Senses 的 Lexemes 的陈述)** |  wikibase-sense   | wikibase-entityid |



## --> ch5.2 valuetype(值类型)



- **确定datatype（数据类型）后，可以知道valuetype（值类型），从而确定`value`的结构层次**；
- **由ch5.1可知，17种datatype（数据类型）**对应**共6种valuetype（值类型）**；



### wikibase-entityid(维基数据实体id值)

- `datavalue`:
  - `type`:`wikibase-entityid`
  - `value`:
    - `entity-type`(实体类型):`item`
    - `numeric-id`(实体id):**（目标值）**
    - `(id)`（类型+id，如Q5，P31）:**（目标值）**



### globecoordinate(地理位置)

- `datavalue`:
  - `type`:`globecoordinate`
  - `value`:
    - `latitude`(纬度)：**（目标值）**
    - `longitude`(经度)：**（目标值）**
    - `precision`(精度)：**（目标值）**
    - `globe`(观测星球实体链接)：**（目标值）**



### time(时间)

- `datavalue`:
  - `type`:`time`
  - `value`:
    - `time`(时间)：**（目标值）**
    - `precision`(精度):**（目标值）**
    - `before`(给定时间后可能有多少个单位的显式整数值):**（目标值）**
    - `after`(给定时间前可能有多少个单位的显式整数值)：**（目标值）**
    - `timezone`(时区信息作为与 UTC 的偏移量（以分钟为单位）):**（目标值）**
    - `calendarmodel`(作为 URI 给出的显式值):**（目标值）**



### string(字符串)

- `datavalue`:

  - `type`:`string`

  - `value`:**（目标值）**

    

### monolingualtext(未翻译文本)

- `datavalue`:
  - `type`:`monolingualtext`
  - `value`:
    - `text`(文本信息):**（目标值）**
    - `language`(语言):**（目标值）**



### quantity(十进制数字)

- `datavalue`:
  - `type`:quantity
  - `value`:
    - `amount`(主值):**（目标值）**
    - `lowerBound`(下界误差):**（目标值）**
    - `upperBound`(上界误差):**（目标值）**
    - `unit`(默认为“1”的字符串的隐含部分):`"1"`



## --> ch 5.3 qualifiers-order(限定词顺序)

- `qualifiers-order`:
  - `0..*`**（列表）**
    - `Pxx`
    - ......



## --> ch 5.4 qualifiers(限定词)

- `qualifiers`：
  - `Pxx`：
    - `0..*`(**列表**)
      - `snaktype`:`value`
      - `property`:`Pxx`
      - `hash`:**（目标值）**
      - `datatype`: --> **ch 5.1**
      - `datavalue`:  --> **ch 5.2**



## --> ch 5.5 references(引用)

- `references`:
  - `0..*`(**列表**)
    - `hash`:**（目标值）**
    - `snaks-order`:
      - `0..*`(**列表**)
        - `Pxx`
        - .......
    - `snaks`:
      - `Pxx`:
        - `0..*`(**列表**)
          - `snaktype`:`value`
          - `property`:`Pxx`
          - `hash`:**（目标值）**
          - `datatype`: --> **ch 5.1**
          - `datavalue`:  --> **ch 5.2**



# ---> ch6.sitelinks(外部链接)

- `sitelinks`:
  - `xxwiki`:
    - `site`:`xxwiki`
    - `(badges)`:
      - `0..*`(**列表**)
        - `id`**（目标值）**
    - `title`:**（目标值）**
  - `xxwikivoyage`:
    - `site`:`xxwikivoyage`
    - `(badges)`:
      - `0..*`(**列表**)
        - `id`**（目标值）**
    - `title`:**（目标值）**
  - `commonswiki`:
    - `site`:`commonswiki`
    - `(badges)`:
      - `0..*`(**列表**)
        - `id`**（目标值）**
    - `title`:**（目标值）**



# Example

- (https://www.wikidata.org/w/api.php?action=wbgetentities&ids=Q3551770&languages=en)**结果如下**

```json
{
    "entities": {
            "Q3551770": {
                "pageid": 3380864,
                "ns": 0,
                "title": "Q3551770",
                "lastrevid": 1653372026,
                "modified": "2022-06-03T07:33:09Z",
                "type": "item",
                "id": "Q3551770",
                "labels": {
                    "en": {
                        "language": "en",
                        "value": "Southeast University"
                    }
                },
                "descriptions": {
                    "en": {
                        "language": "en",
                        "value": "university in Nanjing, China"
                    }
                },
                "aliases": {
                    "en": [
                        {
                            "language": "en",
                            "value": "SEU"
                        }
                    ]
                },
                "claims": {            	
                    ....
                },
                "sitelinks":{            
                    ...            
                },
	"success": 1
}            
```

## 部分`claims`

```json
"claims":
{
    "P159": [
        {
            "mainsnak": {
                "snaktype": "value",
                "property": "P159",
                "hash": "5491d0bd1b2b30b9e4747254d508f277795a3c7f",
                "datavalue": {
                    "value": {
                        "entity-type": "item",
                        "numeric-id": 16666,
                        "id": "Q16666"
                    },
                    "type": "wikibase-entityid"
                },
                "datatype": "wikibase-item"
            },
            "type": "statement",
            "qualifiers": {
                "P625": [
                    {
                        "snaktype": "value",
                        "property": "P625",
                        "hash": "2d91cc4e510689c095172344a17b0a0bee41fa4b",
                        "datavalue": {
                            "value": {
                                "latitude": 32.057777777778,
                                "longitude": 118.78888888889,
                                "altitude": null,
                                "precision": 0.0001,
                                "globe": "http://www.wikidata.org/entity/Q2"
                            },
                            "type": "globecoordinate"
                        },
                        "datatype": "globe-coordinate"
                    }
                ]
            },
            "qualifiers-order": [
                "P625"
            ],
            "id": "Q3551770$b269c8db-4c49-d2c9-628e-393a5c0d9b65",
            "rank": "normal"
        }
    ],
    "P856": [
        {
            "mainsnak": {
                "snaktype": "value",
                "property": "P856",
                "hash": "0d857dd8262d0b6729ea3b911c534d0075fbb77d",
                "datavalue": {
                    "value": "http://www.seu.edu.cn/",
                    "type": "string"
                },
                "datatype": "url"
            },
            "type": "statement",
            "id": "Q3551770$8ed363d2-400b-f684-a881-bd02902fe19d",
            "rank": "normal"
        }
    ],
    "P910": [
        {
            "mainsnak": {
                "snaktype": "value",
                "property": "P910",
                "hash": "1f3b85ac00b0aa7408defdb9e93dbf9278752b73",
                "datavalue": {
                    "value": {
                        "entity-type": "item",
                        "numeric-id": 8781146,
                        "id": "Q8781146"
                    },
                    "type": "wikibase-entityid"
                },
                "datatype": "wikibase-item"
            },
            "type": "statement",
            "id": "Q3551770$6C7F1667-F717-4374-A48D-4A0838D77B41",
            "rank": "normal"
        }
    ]
}
```



## `sitelinks`

```json
"sitelinks": 
{
    "cswiki": {
        "site": "cswiki",
        "title": "Jihov\u00fdchodn\u00ed univerzita",
        "badges": []
    },
    "dewiki": {
        "site": "dewiki",
        "title": "Universit\u00e4t S\u00fcdostchinas",
        "badges": []
    },
    "enwiki": {
        "site": "enwiki",
        "title": "Southeast University",
        "badges": []
    },
    "frwiki": {
        "site": "frwiki",
        "title": "Universit\u00e9 du Sud-Est",
        "badges": []
    },
    "ganwiki": {
        "site": "ganwiki",
        "title": "\u4e1c\u5357\u5927\u5b66",
        "badges": []
    },
    "idwiki": {
        "site": "idwiki",
        "title": "Universitas Tenggara",
        "badges": []
    }
}
```

