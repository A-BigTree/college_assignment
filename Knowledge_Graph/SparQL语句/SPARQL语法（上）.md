# SPARQL语法（上）

**@ author： Shuxin-Wang**

**@ time： 2022.08.02**

- ***<u>W3C</u>***[原文链接](https://www.w3.org/TR/sparql11-query/)

------

***目录***

- [SPARQL语法（上）](#SPARQL语法（上）)
- [一、引言](#一、引言)
	- [1.1 文档大纲](#1.1-文档大纲)
	- [1.2 文档约定](#1.2-文档约定)
		- [1.2.1 命名空间(Namespaces)](#1.2.1-命名空间(Namespaces))
		- [1.2.2 数据描述](#1.2.2-数据描述)
		- [1.2.3 结果说明](#1.2.3-结果说明)
		- [1.2.4 术语](#1.2.4-术语)
- [二、 简单SPARQL查询](#二、-简单SPARQL查询)
	- [2.1 编写简单查询](#2.1-编写简单查询)
	- [2.2 多重匹配](#2.2-多重匹配)
	- [2.3 匹配RDF文本(RDF literal)](#2.3-匹配RDF文本(RDF-literal))
		- [2.3.1 将文本与语言标记匹配](#2.3.1-将文本与语言标记匹配)
		- [2.3.2 将文本与数字类型匹配](#2.3.2-将文本与数字类型匹配)
		- [2.3.4 将文本与任意数据类型匹配](#2.3.4-将文本与任意数据类型匹配)
	- [2.4 查询结果中的空白节点标签](#2.4-查询结果中的空白节点标签)
	- [2.5 用表达式创建值](#2.5-用表达式创建值)
	- [2.6 构建RDF图](#2.6-构建RDF图)
- [三、RDF术语约束 (RDF Term Constraints)](#三、RDF术语约束-(RDF-Term-Constraints))
	- [3.1 筛选字符串值](#3.1-筛选字符串值)
	- [3.2 限制数值](#3.2-限制数值)
- [四、SPARQL语法(SPARQL Syntax)](#四、SPARQL语法(SPARQL-Syntax))
	- [4.1 RDF项语法](#4.1-RDF项语法)
		- [4.1.1 IRI的语法](#4.1.1-IRI的语法)
			- [4.1.1.1 前缀名称](#4.1.1.1-前缀名称)
			- [4.1.1.2 相对IRIs](#4.1.1.2-相对IRIs)
		- [4.1.2 文本数值(Literals)语法](#4.1.2-文本数值(Literals)语法)
		- [4.1.3 查询变量(Query Variables)的语法](#4.1.3-查询变量(Query-Variables)的语法)
		- [4.1.4 空白节点(Blank Nodes)的语法](#4.1.4-空白节点(Blank-Nodes)的语法)
	- [4.2 三重模式(Triple Patten)的语法](#4.2-三重模式(Triple-Patten)的语法)
		- [4.2.1 谓语宾语列表](#4.2.1-谓语宾语列表)
		- [4.2.2 宾语列表](#4.2.2-宾语列表)
		- [4.2.3 RDF集合](#4.2.3-RDF集合)
		- [4.2.4 rdf: 类型(type)](#4.2.4-rdf:-类型(type))



# 一、引言

RDF 是一种有向的、带标签的图形数据格式，用于表示 Web 中的信息。RDF通常用于表示个人信息，社交网络，有关数字工件的元数据，以及提供一种对不同信息源的集成方式。此规范定义了 RDF 的 SPARQL 查询语言的语法和语义。

>RDF is a directed, labeled graph data format for representing information in the Web. RDF is often used to represent, among other things, personal information, social networks, metadata about digital artifacts, as well as to provide a means of integration over disparate sources of information. This specification defines the syntax and semantics of the SPARQL query language for RDF.



## 1.1 文档大纲

略



## 1.2 文档约定



### 1.2.1 命名空间(Namespaces)

在本文档中，除非另有说明，否则示例假定使用以下命名空间前缀绑定：

> In this document, examples assume the following namespace prefix bindings unless otherwise stated:

| Prefix(前缀) |                   IRI/URI                   |
| :----------: | :-----------------------------------------: |
|    `rdf:`    | http://www.w3.org/1999/02/22-rdf-syntax-ns# |
|    `rdfs`    |    http://www.w3.org/2000/01/rdf-schema#    |
|    `xsd`     |      http://www.w3.org/2001/XMLSchema#      |
|     `fn`     |   http://www.w3.org/2005/xpath-functions#   |
|    `sfn`     |        http://www.w3.org/ns/sparql#         |



### 1.2.2 数据描述

本文档使用 [Turtle](http://www.w3.org/TR/turtle/) [[TURTLE](https://www.w3.org/TR/sparql11-query/#TURTLE)] 数据格式显式显示每个三元组。Turtle 允许使用前缀缩写 IRI：

> This document uses the [Turtle](http://www.w3.org/TR/turtle/) [[TURTLE](https://www.w3.org/TR/sparql11-query/#TURTLE)] data format to show each triple explicitly. Turtle allows IRIs to be abbreviated with prefixes:

```turtle
@prefix dc:   <http://purl.org/dc/elements/1.1/> .
@prefix :     <http://example.org/book/> .
:book1  dc:title  "SPARQL Tutorial" .
```



### 1.2.3 结果说明

结果集以表格形式说明。

> Result sets are illustrated in tabular form.



### 1.2.4 术语

- [IRI](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-URI-reference)（对应于概念和抽象语法术语”`RDF URI reference`")
- [字面值（literal）](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-literal)
- [词汇形式（lexical form）](http://www.w3.org/TR/rdf-concepts/#dfn-lexical-form)
- [纯文本（plain literal）](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-plain-literal)
- [语言标签（language tag）](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-language-identifier)
- [键入的文字（typed literal）](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-typed-literal)
- [数据类型 IRI](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-datatype-URI)（对应于概念和抽象语法术语”`datatype URI`")
- [空白节点（blank node）](http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/#dfn-blank-node)



# 二、 简单SPARQL查询

大多数形式的SPARQL查询都包含一组称为*基本图形*模式的三重模式。三重模式类似于RDF三元组，除了每个主语，谓词和宾语都可以是一个变量。当来自该子图的***RDF项***可以替换为变量并且结果是RDF图等效于子图时，基本图形模式与RDF数据的子图*匹配*。

> Most forms of SPARQL query contain a set of triple patterns called a *basic graph pattern*. Triple patterns are like RDF triples except that each of the subject, predicate and object may be a variable. A basic graph pattern *matches* a subgraph of the RDF data when [RDF terms](https://www.w3.org/TR/sparql11-query/#defn_RDFTerm) from that subgraph may be substituted for the variables and the result is RDF graph equivalent to the subgraph.



## 2.1 编写简单查询

下面的示例显示了一个 `SPARQL` 查询，用于从给定的数据图中查找书籍的标题。查询由两部分组成：子句标识要显示在查询结果中的变量，子句提供与数据图匹配的基本图形模式。此示例中的基本图形模式由单个三重模式组成，对象位置有单个变量 (`?title`)

Data:

```turtle
<http://example.org/book/book1> <http://purl.org/dc/elements/1.1/title> "SPARQL Tutorial" .
```

Query:

```SPARQL
SELECT ?title
WHERE
{
  <http://example.org/book/book1> <http://purl.org/dc/elements/1.1/title> ?title .
}
```

Query Result:

|       title       |
| :---------------: |
| "SPARQL Tutorial" |



## 2.2 多重匹配

查询的结果是一个***求解序列***，对应于查询的图形模式与数据匹配的方式。一个查询可能有零个、一个或多个解决方案。

Data:

```turtle
@prefix foaf:  <http://xmlns.com/foaf/0.1/> .

_:a  foaf:name   "Johnny Lee Outlaw" .
_:a  foaf:mbox   <mailto:jlow@example.com> .
_:b  foaf:name   "Peter Goodguy" .
_:b  foaf:mbox   <mailto:peter@example.org> .
_:c  foaf:mbox   <mailto:carol@example.org> .
```

Query:

```SPARQL
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>
SELECT ?name ?mbox
WHERE
  { ?x foaf:name ?name .
    ?x foaf:mbox ?mbox }
```

Query Result:

|        name         |            mbox             |
| :-----------------: | :-------------------------: |
| "Johnny Lee Outlaw" | \<mailto:jlow@example.com>  |
|   "Peter Goodguy"   | \<mailto:peter@example.org> |

每个解决方案都提供了一种方法，其中所选变量可以绑定到 RDF 项，以便查询模式与数据匹配。结果集给出了所有可能的解决方案。在上面的示例中，以下两个数据子集提供了两个匹配项。

```turtle
_:a foaf:name  "Johnny Lee Outlaw" .
_:a foaf:box   <mailto:jlow@example.com> .
```

```turtle
_:b foaf:name  "Peter Goodguy" .
_:b foaf:box   <mailto:peter@example.org> .
```

这是一个[基本的图形模式匹配](https://www.w3.org/TR/sparql11-query/#BGPsparql);查询模式中使用的所有变量都必须绑定到每个解决方案中。



## 2.3 匹配RDF文本(RDF literal)

下面的数据包含三个 RDF 文本：

```turtle
@prefix dt:   <http://example.org/datatype#> .
@prefix ns:   <http://example.org/ns#> .
@prefix :     <http://example.org/ns#> .
@prefix xsd:  <http://www.w3.org/2001/XMLSchema#> .

:x   ns:p     "cat"@en .
:y   ns:p     "42"^^xsd:integer .
:z   ns:p     "abc"^^dt:specialDatatype .
```

请注意，在Turtle中，`"cat"@en`是一个RDF文字，具有词汇形式“cat”和语言标签“en”; `"42"^^xsd:integer`是数据类型为`http://www.w3.org/2001/XMLSchema#integer` 的类型化文本 ;`"abc"^^dt:specialDatatype`是数据类型为`http://example.org/datatype#specialDatatype`的类型化文本。

> Note that, in Turtle, `"cat"@en` is an RDF literal with a lexical form "cat" and a language tag "en"; `"42"^^xsd:integer` is a typed literal with the datatype `http://www.w3.org/2001/XMLSchema#integer`; and `"abc"^^dt:specialDatatype` is a typed literal with the datatype `http://example.org/datatype#specialDatatype`.
>
> This RDF data is the data graph for the query examples in sections 2.3.1–2.3.3.



### 2.3.1 将文本与语言标记匹配

以下查询没有解决方案，因为 RDF 文本`"cat"`与`"cat"@en`不同：

```turtle
SELECT ?v WHERE { ?v ?p "cat" }
```

|  v   |
| :--: |

但是下面的查询将找到一个解决方案，其中变量`v`被绑定到`:x`，因为语言标记被指定并且与给定数据匹配：

```turtle
SELECT ?v WHERE { ?v ?p "cat"@en }
```

|             v             |
| :-----------------------: |
| <http://example.org/ns#x> |



### 2.3.2 将文本与数字类型匹配

SPARQL 查询中的整数指示数据类型为`xsd:integer` 的 RDF 类型文本。例如：`42`是`"42"^^<http://www.w3.org/2001/XMLSchema#integer>` 的缩写形式。

以下查询中的模式具有将变量`v`绑定到`:y`的解决方案。

```turtle
SELECT ?v WHERE { ?v ?p 42 }
```



|             v             |
| :-----------------------: |
| <http://example.org/ns#y> |

***第 4.1.2节*** 定义了`xsd:float`和`xsd:double` 的 SPARQL 缩写形式。



### 2.3.4 将文本与任意数据类型匹配

以下查询具有将变量`v`绑定到`:z`的解决方案。查询处理器不必对数据类型空间中的值有任何了解。由于词法形式和数据类型 IRI 都匹配，因此文本匹配。

```turtle
SELECT ?v 
WHERE 
{ 
    ?v ?p "abc"^^<http://example.org/datatype#specialDatatype> 
}
```



|             v             |
| :-----------------------: |
| <http://example.org/ns#z> |



## 2.4 查询结果中的空白节点标签

查询结果可以包含空白节点。本文档中的示例结果集中的空白节点以“_：”的形式编写，后跟空白节点标签。

空白节点标签的作用域为结果集（请参阅“[SPARQL 查询结果 XML 格式](http://www.w3.org/TR/rdf-sparql-XMLres/)”和“[SPARQL 1.1 查询结果 JSON 格式](http://www.w3.org/TR/sparql11-results-json/)”），或者，对于查询表单，`CONSTRUCT`则为结果图。在结果集中使用相同的标签表示相同的空白节点。

Data:

```turtle
@prefix foaf:  <http://xmlns.com/foaf/0.1/> .

_:a  foaf:name   "Alice" .
_:b  foaf:name   "Bob" .
```

Query:

```SPARQL
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>
SELECT ?x ?name
WHERE  { ?x foaf:name ?name }
```

Query Result:

|  x   |  name   |
| :--: | :-----: |
| _:c  | "Alice" |
| _:d  |  "Bob"  |



## 2.5 用表达式创建值

SPARQL 1.1 允许从复杂表达式创建值。下面的查询显示了如何使用 [`CONCAT`](https://www.w3.org/TR/sparql11-query/#func-concat) 函数来连接 `foaf:` 数据中的名字和姓氏，然后使用 [`SELECT` 子句中的表达式](https://www.w3.org/TR/sparql11-query/#selectExpressions)赋值；或者使用 [`BIND`](https://www.w3.org/TR/sparql11-query/#bind) 表单赋值。

> SPARQL 1.1 allows to create values from complex expressions. The queries below show how to the [CONCAT](https://www.w3.org/TR/sparql11-query/#func-concat) function can be used to concatenate first names and last names from foaf data, then assign the value using an [expression in the `SELECT` clause](https://www.w3.org/TR/sparql11-query/#selectExpressions) and also assign the value by using the [BIND](https://www.w3.org/TR/sparql11-query/#bind) form.

Data:

```turtle
@prefix foaf:  <http://xmlns.com/foaf/0.1/> .
          
_:a  foaf:givenName   "John" .
_:a  foaf:surname  "Doe" .
```

Query:

```SPARQL
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>

SELECT ( CONCAT(?G, " ", ?S) AS ?name )
WHERE  
{ 
    ?P foaf:givenName ?G ; 
       foaf:surname ?S
}
```

Query:

```SPARQL
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>

SELECT ?name
WHERE  
{ 
   ?P foaf:givenName ?G ; 
      foaf:surname ?S
   BIND(CONCAT(?G, " ", ?S) AS ?name)
}
```

Query Result:

|    name    |
| :--------: |
| "John Doe" |



## 2.6 构建RDF图

SPARQL有几个[查询表单](https://www.w3.org/TR/sparql11-query/#QueryForms)。`SELECT`查询表单返回变量绑定。`CONSTRUCT`查询表单返回 RDF 图。该图基于模板构建，该模板用于根据查询的图形模式匹配的结果生成RDF三元组。

> SPARQL has several [query forms](https://www.w3.org/TR/sparql11-query/#QueryForms). The `SELECT` query form returns variable bindings. The `CONSTRUCT` query form returns an RDF graph. The graph is built based on a template which is used to generate RDF triples based on the results of matching the graph pattern of the query.

Data:

```turtle
@prefix org:    <http://example.com/ns#> .

_:a  org:employeeName   "Alice" .
_:a  org:employeeId     12345 .

_:b  org:employeeName   "Bob" .
_:b  org:employeeId     67890 .
```

Query:

```SPARQL
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>
PREFIX org:    <http://example.com/ns#>

CONSTRUCT { ?x foaf:name ?name }
WHERE  { ?x org:employeeName ?name }
```

Results:

```SPARQL
@prefix foaf: <http://xmlns.com/foaf/0.1/> .
      
_:x foaf:name "Alice" .
_:y foaf:name "Bob" .
```



# 三、RDF术语约束 (RDF Term Constraints)

图模式匹配生成一个解序列，其中每个解都有一组与 RDF 项的变量绑定。SPARQL `FILTER`将解决方案限制为`TRUE`过滤器表达式计算结果 。

本节提供对SPARQL `FILTER`的非正式介绍;它们的语义在“[表达式和测试值](https://www.w3.org/TR/sparql11-query/#expressions)”一节中定义，其中有一个[全面的函数库](https://www.w3.org/TR/sparql11-query/#SparqlOps)。本节中的示例共享一个输入图：

> Graph pattern matching produces a solution sequence, where each solution has a set of bindings of variables to RDF terms. SPARQL `FILTER`s restrict solutions to those for which the filter expression evaluates to `TRUE`.
>
> This section provides an informal introduction to SPARQL `FILTER`s; their semantics are defined in section '[Expressions and Testing Values](https://www.w3.org/TR/sparql11-query/#expressions)' where there is a [comprehensive function library](https://www.w3.org/TR/sparql11-query/#SparqlOps). The examples in this section share one input graph:

Data:

```turtle
@prefix dc:   <http://purl.org/dc/elements/1.1/> .
@prefix :     <http://example.org/book/> .
@prefix ns:   <http://example.org/ns#> .

:book1  dc:title  "SPARQL Tutorial" .
:book1  ns:price  42 .
:book2  dc:title  "The Semantic Web" .
:book2  ns:price  23 .
```



## 3.1 筛选字符串值

SPARQL `FILTER`函数比如`regex`可以测试RDF文本。`regex`仅匹配[字符串文本](https://www.w3.org/TR/sparql11-query/#func-string)。`regex`可以使用 [str](https://www.w3.org/TR/sparql11-query/#func-str) 函数来匹配其他文本的词法形式。

> SPARQL `FILTER` functions like `regex` can test RDF literals. `regex` matches only [string literals](https://www.w3.org/TR/sparql11-query/#func-string). `regex` can be used to match the lexical forms of other literals by using the [str](https://www.w3.org/TR/sparql11-query/#func-str) function.

Query:

```SPARQL
PREFIX  dc:  <http://purl.org/dc/elements/1.1/>
SELECT  ?title
WHERE   { ?x dc:title ?title
          FILTER regex(?title, "^SPARQL") 
        }
```

Query Result:

|       title       |
| :---------------: |
| "SPARQL Tutorial" |

正则表达式匹配可以使用 “`i`” 标志变得不区分大小写。

Query:

```SPARQL
PREFIX  dc:  <http://purl.org/dc/elements/1.1/>
SELECT  ?title
WHERE   { ?x dc:title ?title
          FILTER regex(?title, "web", "i" ) 
        }
```

Query Result:

|       title        |
| :----------------: |
| "The Semantic Web" |

正则表达式语言[由 XQuery 1.0 和 XPath 2.0 函数和运算符定义](http://www.w3.org/TR/xpath-functions/#regex-syntax)，并且基于 [XML 架构正则表达式](http://www.w3.org/TR/xmlschema-2/#regexs)。

> The regular expression language is [defined by XQuery 1.0 and XPath 2.0 Functions and Operators](http://www.w3.org/TR/xpath-functions/#regex-syntax) and is based on [XML Schema Regular Expressions](http://www.w3.org/TR/xmlschema-2/#regexs).



## 3.2 限制数值

Query:

```SPARQL
PREFIX  dc:  <http://purl.org/dc/elements/1.1/>
PREFIX  ns:  <http://example.org/ns#>

SELECT  ?title ?price
WHERE   { ?x ns:price ?price .
          FILTER (?price < 30.5)
          ?x dc:title ?title . }
```

Query Result:

|       title        | price |
| :----------------: | :---: |
| "The Semantic Web" |  23   |



# 四、SPARQL语法(SPARQL Syntax)

本节介绍 SPARQL 用于 [RDF 项](https://www.w3.org/TR/sparql11-query/#sparqlBasicTerms)和[三重模式的](https://www.w3.org/TR/sparql11-query/#sparqlTriplePatterns)语法。完整的语法在第[19节中](https://www.w3.org/TR/sparql11-query/#grammar)给出。



## 4.1 RDF项语法



### 4.1.1 IRI的语法

[iri](https://www.w3.org/TR/sparql11-query/#riri) 生产指定一组 IRI [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)];IRI 是 URI 的泛化 [[RFC3986](https://www.w3.org/TR/sparql11-query/#rfc3986)]，与 URI 和 URL 完全兼容。[前缀名称](https://www.w3.org/TR/sparql11-query/#rPrefixedName)生产指定前缀名称。下面介绍了从前缀名称到 IRI 的映射。IRI参考（相对或绝对IRI）由[IRIREF](https://www.w3.org/TR/sparql11-query/#rIRIREF)生产指定，其中“<”和“>”分隔符不构成IRI参考的一部分。相对 IRI 与 [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)] 中 IRI 引用的 2.2 ABNF 和 IRI 中的`irelative-ref`引用相匹配，并解析为 IRI，如下所述。

RDF概念和抽象语法中定义的RDF术语集包括RDF URI引用，而SPARQL术语包括IRI。包含 “`<`”、“`>`”、“`"`”、“（双引号）、空格、”`{`“、”`}`“、”`|`“、”`\`“、”`^`“和"```"的 RDF URI 引用不是 IRI。未定义针对由此类 RDF URI 引用组成的 RDF 语句的 SPARQL 查询的行为。

> The [iri](https://www.w3.org/TR/sparql11-query/#riri) production designates the set of IRIs [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)]; IRIs are a generalization of URIs [[RFC3986](https://www.w3.org/TR/sparql11-query/#rfc3986)] and are fully compatible with URIs and URLs. The [PrefixedName](https://www.w3.org/TR/sparql11-query/#rPrefixedName) production designates a prefixed name. The mapping from a prefixed name to an IRI is described below. IRI references (relative or absolute IRIs) are designated by the [IRIREF](https://www.w3.org/TR/sparql11-query/#rIRIREF) production, where the '<' and '>' delimiters do not form part of the IRI reference. Relative IRIs match the `irelative-ref` reference in section 2.2 ABNF for IRI References and IRIs in [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)] and are resolved to IRIs as described below.
>
> The set of RDF terms defined in RDF Concepts and Abstract Syntax includes RDF URI references while SPARQL terms include IRIs. RDF URI references containing "`<`", "`>`", '`"`' (double quote), space, "`{`", "`}`", "`|`", "`\`", "`^`", and "```" are not IRIs. The behavior of a SPARQL query against RDF statements composed of such RDF URI references is not defined.



#### 4.1.1.1 前缀名称

关键字`PREFIX`将前缀标签与 IRI 相关联。前缀名称是前缀标签和本地部分，由冒号 “`:`” 分隔。通过将与前缀和本地部分关联的 IRI 连接起来，前缀名称将映射到 IRI。前缀标签或本地部件可能为空。请注意，[SPARQL 本地名称](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL)允许前导数字，而 [XML 本地名称](http://www.w3.org/TR/2006/REC-xml-names11-20060816/#NT-LocalPart)不允许。[SPARQL 本地名称](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL)还允许通过反斜杠字符转义（例如`ns:id\=123` ）在 IRI 中使用非字母数字字符。[SPARQL本地名称](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL)比[CURIE](http://www.w3.org/TR/curie/)具有更多的语法限制。

> The `PREFIX` keyword associates a prefix label with an IRI. A prefixed name is a prefix label and a local part, separated by a colon "`:`". A prefixed name is mapped to an IRI by concatenating the IRI associated with the prefix and the local part. The prefix label or the local part may be empty. Note that [SPARQL local names](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL) allow leading digits while [XML local names](http://www.w3.org/TR/2006/REC-xml-names11-20060816/#NT-LocalPart) do not. [SPARQL local names](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL) also allow the non-alphanumeric characters allowed in IRIs via backslash character escapes (e.g. `ns:id\=123`). [SPARQL local names](https://www.w3.org/TR/sparql11-query/#rPN_LOCAL) have more syntactic restrictions than [CURIE](http://www.w3.org/TR/curie/)s.



#### 4.1.1.2 相对IRIs

根据[统一资源标识符 （URI）：通用语法](http://www.ietf.org/rfc/rfc3986.txt) [[RFC3986](https://www.w3.org/TR/sparql11-query/#rfc3986)] 仅使用第 5.2 节中的基本算法，将相对 IRI 与基本 IRI 结合使用。既不执行基于语法的规范化，也不执行基于方案的规范化（如 RFC3986 的 6.2.2 和 6.2.3 节中所述）。根据[国际化资源标识符 （IRI）](http://www.ietf.org/rfc/rfc3987.txt) [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)] 的第 6.5 节，IRI 引用中允许的字符的处理方式与 URI 引用中未保留字符的处理方式相同。

关键字`BASE`定义了用于根据 RFC3986 第 5.1.1 节“嵌入在内容中的基本 URI”解析相对 IRI 的基本 IRI。第 5.1.2 节 “来自封装实体的基本 URI” 定义了基本 IRI 如何来自封装文档，例如带有 xml：base 指令的 SOAP 信封或带有 Content-Location 标头的 mime 多部分文档。在 5.1.3 中标识的“检索 URI”（基本“来自检索 URI 的 URI”）是从中检索特定 SPARQL 查询的 URL。如果上述各项均未指定基本 URI，则使用默认基本 URI（第 5.1.4 节 “默认基本 URI”）。

> Relative IRIs are combined with base IRIs as per [Uniform Resource Identifier (URI): Generic Syntax](http://www.ietf.org/rfc/rfc3986.txt) [[RFC3986](https://www.w3.org/TR/sparql11-query/#rfc3986)] using only the basic algorithm in section 5.2. Neither Syntax-Based Normalization nor Scheme-Based Normalization (described in sections 6.2.2 and 6.2.3 of RFC3986) are performed. Characters additionally allowed in IRI references are treated in the same way that unreserved characters are treated in URI references, per section 6.5 of [Internationalized Resource Identifiers (IRIs)](http://www.ietf.org/rfc/rfc3987.txt) [[RFC3987](https://www.w3.org/TR/sparql11-query/#rfc3987)].
>
> The `BASE` keyword defines the Base IRI used to resolve relative IRIs per RFC3986 section 5.1.1, "Base URI Embedded in Content". Section 5.1.2, "Base URI from the Encapsulating Entity" defines how the Base IRI may come from an encapsulating document, such as a SOAP envelope with an xml:base directive or a mime multipart document with a Content-Location header. The "Retrieval URI" identified in 5.1.3, Base "URI from the Retrieval URI", is the URL from which a particular SPARQL query was retrieved. If none of the above specifies the Base URI, the default Base URI (section 5.1.4, "Default Base URI") is used.

```SPARQL
<http://example.org/book/book1>
```

```SPARQL
BASE <http://example.org/book/>
<book1>
```

```SPARQL
PREFIX book: <http://example.org/book/>
book:book1
```



### 4.1.2 文本数值(Literals)语法

文本的一般语法是一个字符串（括在双引号`"..."`或单引号`'...'`中），带有可选的语言标记（由`@`引入）或可选数据类型 IRI 或前缀名称（由`^^`引入）。

为方便起见，整数可以直接写入（没有引号和显式数据类型IRI），并被解释为数据类型的类型化文本`xsd:integer`;数字中有 '.' 但没有指数的十进制数被解释为`xsd:decimal` ;和带有指数的数字被解释为`xsd:double` 。`xsd:boolean`类型的值也可以写为`true`或 ` false`。

为了便于编写本身包含引号或长且包含换行符的文字值，SPARQL提供了一个额外的引用结构，其中文字被括在三个单引号或双引号中。

SPARQL 中的文字语法示例如下：

- `"chat"`
- `'chat'@fr`带有语言标签“fr”
- `"xyz"^^<http://example.org/ns/userDatatype>`
- `"abc"^^appNS:appDataType`
- ```The librarian said, "Perhaps you would enjoy 'War and Peace'."```
- `1`， = `"1"^^xsd:integer`
- `1.3`，= `"1.3"^^xsd:decimal`
- `1.300`，= `"1.300"^^xsd:decimal`
- `1.0e6`，= `"1.0e6"^^xsd:double`
- `true`，= `"true"^^xsd:boolean`
- `false`，= `"false"^^xsd:boolean`

> The general syntax for literals is a string (enclosed in either double quotes, `"..."`, or single quotes, `'...'`), with either an optional language tag (introduced by `@`) or an optional datatype IRI or prefixed name (introduced by `^^`).
>
> As a convenience, integers can be written directly (without quotation marks and an explicit datatype IRI) and are interpreted as typed literals of datatype `xsd:integer`; decimal numbers for which there is '.' in the number but no exponent are interpreted as `xsd:decimal`; and numbers with exponents are interpreted as `xsd:double`. Values of type `xsd:boolean` can also be written as `true` or `false`.
>
> To facilitate writing literal values which themselves contain quotation marks or which are long and contain newline characters, SPARQL provides an additional quoting construct in which literals are enclosed in three single- or double-quotation marks.
>
> Examples of literal syntax in SPARQL include:
>
> - `"chat"`
> - `'chat'@fr` with language tag "fr"
> - `"xyz"^^<http://example.org/ns/userDatatype>`
> - `"abc"^^appNS:appDataType`
> - ```The librarian said, "Perhaps you would enjoy 'War and Peace'."```
> - `1`, which is the same as `"1"^^xsd:integer`
> - `1.3`, which is the same as `"1.3"^^xsd:decimal`
> - `1.300`, which is the same as `"1.300"^^xsd:decimal`
> - `1.0e6`, which is the same as `"1.0e6"^^xsd:double`
> - `true`, which is the same as `"true"^^xsd:boolean`
> - `false`, which is the same as `"false"^^xsd:boolean`



### 4.1.3 查询变量(Query Variables)的语法

查询变量由使用“？”或“\$”进行标记;“？”或“\$”不是变量名称的一部分。在查询中，`?abc`和`$abc`标识相同的变量。



### 4.1.4 空白节点(Blank Nodes)的语法

图形模式中的[空白节点](http://www.w3.org/TR/rdf-concepts/#section-blank-nodes)充当变量，而不是对所查询数据中特定空白节点的引用。

空白节点由标签形式（如 “`_:abc`”）或缩写形式 “`[]`” 指示。在查询语法中仅在一个位置使用的空白节点可以用`[]`表示。将使用唯一的空白节点来形成三重模式。对于带有标签 “`_:abc`” 的空白节点，空白节点标签将写为 “`abc`”。同一查询中的两种不同的基本图形模式***<u>不能</u>使用相同***的空白节点标签。

该结构`[:p :v]`可以以三重模式使用。它创建一个空白节点标签，该标签用作所有包含的谓词-对象对的主题。创建的空白节点还可以在主体和对象位置的进一步三重模式中使用。

>[Blank nodes](http://www.w3.org/TR/rdf-concepts/#section-blank-nodes) in graph patterns act as variables, not as references to specific blank nodes in the data being queried.
>
>Blank nodes are indicated by either the label form, such as "`_:abc`", or the abbreviated form "`[]`". A blank node that is used in only one place in the query syntax can be indicated with `[]`. A unique blank node will be used to form the triple pattern. Blank node labels are written as "`_:abc`" for a blank node with label "`abc`". The same blank node label cannot be used in two different basic graph patterns in the same query.
>
>The `[:p :v]` construct can be used in triple patterns. It creates a blank node label which is used as the subject of all contained predicate-object pairs. The created blank node can also be used in further triple patterns in the subject and object positions.

以下两种形式：

```turtle
# 第一种
[:p "v"].
# 第二种
[] :p "v".
```

分配一个唯一的空白节点标签（此处为“`b57`”），相当于：

```turtle
_:b57 :p "v".
```

这个分配的空白节点标签可以用作进一步三重模式的主语或宾语：

```turtle
# 作为主语
[ :p "v" ] :q "w".

# 作为宾语
:x :q [ :p "v"].
```

缩写的空白节点语法可以与常见主语和公共谓词的其他缩写结合使用。

```turtle
 [ foaf:name  ?name ;
    foaf:mbox  <mailto:alice@example.org> ]
```



## 4.2 三重模式(Triple Patten)的语法

三重模式写成主语，谓词和宾语;有一些简短的方法来编写一些常见的三重模式结构。

以下示例表示相同的查询：

```SPARQL
PREFIX  dc: <http://purl.org/dc/elements/1.1/>

SELECT  ?title
WHERE   
{ 
    <http://example.org/book/book1> dc:title ?title 
}  
```

```SPARQL
PREFIX  dc: <http://purl.org/dc/elements/1.1/>
PREFIX  : <http://example.org/book/>

SELECT  $title
WHERE   
{ 
    :book1  dc:title  $title 
}
```

```SPARQL
BASE    <http://example.org/book/>
PREFIX  dc: <http://purl.org/dc/elements/1.1/>

SELECT  $title
WHERE   
{ 
    <book1>  dc:title  ?title 
}
```



### 4.2.1 谓语宾语列表

可以编写具有共同主语的三重模式，以便主语只写一次，并且通过使用“`;`”表示法用于多个三重模式。

```SPARQL
 ?x  foaf:name  ?name ;
     foaf:mbox  ?mbox .
```



### 4.2.2 宾语列表

如果三重模式同时共享主语和谓语，则对象可以用 “`,`” 分隔。

```SPARQL
 ?x foaf:nick  "Alice" , "Alice_" .
```



### 4.2.3 RDF集合

[RDF集合](http://www.w3.org/TR/2004/REC-rdf-mt-20040210/#collections)可以使用语法“（element1 element2 ...）”以三重模式编写。形式“`()`”是 IRI ` http://www.w3.org/1999/02/22-rdf-syntax-ns#nil`的替代方法。当与集合元素一起使用时，例如`(1 ?x 3 4)` ，将为集合分配具有空白节点的三重模式。集合顶部的空白节点可以用作其他三重模式中的主语或宾语。由集合语法分配的空白节点不会出现在查询中的其他位置。

> [RDF collections](http://www.w3.org/TR/2004/REC-rdf-mt-20040210/#collections) can be written in triple patterns using the syntax "(element1 element2 ...)". The form "`()`" is an alternative for the IRI `http://www.w3.org/1999/02/22-rdf-syntax-ns#nil`. When used with collection elements, such as `(1 ?x 3 4)`, triple patterns with blank nodes are allocated for the collection. The blank node at the head of the collection can be used as a subject or object in other triple patterns. The blank nodes allocated by the collection syntax do not occur elsewhere in the query.

```SPARQL
(1 ?x 3 4) :p "w" .
```



> is syntactic sugar for (noting that `b0`, `b1`, `b2` and `b3` do not occur anywhere else in the query):

```turtle
_:b0  rdf:first  1 ;
	  rdf:rest   _:b1 .
_:b1  rdf:first  ?x ;
	  rdf:rest   _:b2 .
_:b2  rdf:first  3 ;
	  rdf:rest   _:b3 .
_:b3  rdf:first  4 ;
	  rdf:rest   rdf:nil .
_:b0  :p         "w" .
```



> RDF collections can be nested and can involve other syntactic forms:

```turtle
(1 [:p :q] ( 2 ) ) .
```



> is syntactic sugar for:

```turtle
_:b0  rdf:first  1 ;
	  rdf:rest   _:b1 .
_:b1  rdf:first  _:b2 .
_:b2  :p         :q .
_:b1  rdf:rest   _:b3 .
_:b3  rdf:first  _:b4 .
_:b4  rdf:first  2 ;
	  rdf:rest   rdf:nil .
_:b3  rdf:rest   rdf:nil .
```



### 4.2.4 rdf: 类型(type)

关键字 “`a`” 可以用作三重模式中的谓词，并且是 IRI http://www.w3.org/1999/02/22-rdf-syntax-ns#type的替代方法。此关键字区分大小写。

```turtle
?x  a  :Class1 .
[ a :appClass ] :p "v" .
```

与下面表示相同：

```turtle
?x    rdf:type  :Class1 .
_:b0  rdf:type  :appClass .
_:b0  :p        "v" .
```