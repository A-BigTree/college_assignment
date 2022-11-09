# OWL2Vec*: embedding of OWL ontologies  

[Machine Learning (2021) 110:1813–1845](https://doi.org/10.1007/s10994-021-05997-6 )

> Jiaoyan Chen · Pan Hu· Ernesto Jimenez‑Ruiz· Ole Magnus Holter·Denvar Antonyrajah· Ian Horrocks
> Received: 31 August 2020 / Revised: 29 January 2021 / Accepted: 12 May 2021 /Published online: 16 June 2021
> © The Author(s) 2021 



# Abstract

知识图谱语义的向量化已被广泛研究并用于自然语言处理和语义网等各个领域的预测和统计分析任务。然而，研究OWL（Web Ontology Language，网络本体语言）向量化的方法方面关注较少，OWL包含比普通知识图更丰富的语义信息，并已广泛应用于生物信息学等领域。在这篇论文中，我们提出了基于随机游走（**Random Walk**）和词向量化（**Word Embedding**）的本体向量化的方法`OWL2Vec*`，该方法通过考虑OWL本体的图结构、词汇信息和逻辑构造对其语义进行编码。我们对三个真实数据集的实证评估表明，`OWL2Vec*`在单个本体的的类成员预测和子类包含预测任务中受益于这三个不同方面。此外，在我们的实验中，`OWL2Vec*`通常显著优于其他最先进的方法。

> Semantic embedding of knowledge graphs has been widely studied and used for prediction and statistical analysis tasks across various domains such as Natural Language Processing and the Semantic Web. However, less attention has been paid to developing robust methods for embedding OWL (Web Ontology Language) ontologies, which contain richer semantic information than plain knowledge graphs, and have been widely adopted in domains such as bioinformatics. In this paper, we propose a random walk and word embedding based ontology embedding method named OWL2Vec\*, which encodes the semantics of an OWL ontology by taking into account its graph structure, lexical information and logical constructors. Our empirical evaluation with three real world datasets suggests that OWL2Vec\*benefits from these three different aspects of an ontology in class membership prediction and class subsumption prediction tasks. Furthermore, OWL2Vec\* often significantly out-performs the state-of-the-art methods in our experiments  



# Keywords

> Ontology · Ontology embedding · Word embedding · Web ontology language · OWL2Vec* · Ontology completion  



# 1 Introduction

近年来，知识图谱（KG）的语义向量化得到了广泛研究（`Wang等人，2017`）。这种向量化的目的是在向量空间中以捕捉图形结构的方式表示KG组件，如实体和关系。已经提出了各种KG嵌入算法，并成功应用于KG细化（例如，链接预测（`Rossi et al.，2020`）和实体对齐`Sun et al.2020`）、推荐系统（`Ristoski et al.，2019`）、零镜头学习（`Chen et al.，2020c；Wang et al.，2018`）、生物信息学中的交互预测（`Smaili et al.2018；Myklebust et al.2019`）等等。然而，这些算法大多集中于为由`RDF`（资源描述框架）三元组组成的多关系图创建向量，比如`⟨England, isPartOf, UK⟩` 和 `⟨UK, hasCapital, London⟩`。它们不涉及OWL本体（或OWL中的本体模式），其中OWL本体不仅包括图形结构，还包括逻辑构造器，如类不相交、存在性和普遍量化（例如，一个国家必须至少有一个城市作为其首都），以及元数据，如类的同义词、定义和注释。OWL本体已广泛应用于许多领域，如生物信息学、语义网和链接数据(`Myklebust et al., 2019; Horrocks, 2008`)。它们能够表达复杂的领域知识和管理大规模的领域词汇表，通常可以提高KG的质量和可用性(`Paulheim & Gangemi, 2015; Chen et al., 2020a`)  。

> In recent years, the semantic embedding of knowledge graphs (KGs) has been widely investigated (Wang et al., 2017). The objective of such embeddings is to represent in a vector space KG components such as entities and relations in a way that captures the structure of the graph. Various kinds of KG embedding algorithms have been proposed and  successfully applied to KG refinement (e.g., link prediction (Rossi et al., 2020) and entity alignment Sun et al. 2020), recommendation systems (Ristoski et al., 2019), zero-shot learning (Chen et al., 2020c; Wang et al., 2018), interaction prediction in bioinformatics (Smaili et al., 2018; Myklebust et al., 2019), and so on. However, most of these algorithms focus on creating embeddings for multi-relational graphs composed of RDF (Resource Description Framework) triples such as ⟨England,1 isPartOf, UK⟩ and ⟨UK, hasCapital, London⟩.2 They do not deal with OWL ontologies (or ontological schemas in OWL) which include not only graph structures, but also logic constructors such as class disjointness, existential and universal quantification (e.g., a country must have at least one city as its capital), and meta data such as the synonyms, definitions and comments of a class. OWL ontologies have been widely used in many domains such as bioinformatics, the Semantic Web and Linked Data (Myklebust et al., 2019; Horrocks, 2008). They are capable of expressing complex domain knowledge and managing large scale domain vocabularies, and can often improve the quality and usability of the KG (Paulheim & Gangemi, 2015; Chen et al., 2020a)  



受KG向量化成功的启发，最近人们对简单本体模式向量化的兴趣越来越大，例如，由层次类、属性域和范围组成 (`Hao et al., 2019; Moon et al., 2017; Alshargi et al., 2018; Guan et al., 2019`)；但是，这些方法依赖于具有大量事实(an ***<u>ABox</u>***)，并且不支持更具表现力的OWL本体，该本体包含一些广泛使用的逻辑构造函数，如上述类不相交性和存在量化。OWL本体的向量化最近开始受到一些关注。 `Kulmanov et al. (2019) and Garg et al. (2019)`提出通过几何学习对逻辑构造函数的语义进行建模，但他们的模型仅分别支持描述逻辑（`DLs`）EL++（与OWL EL\<OWL的一个片段>相近）和`ALC`中的一些逻辑构造函数。此外，这两种方法都**只考虑本体的逻辑和图形结构**，而忽略了广泛存在于元数据中的词汇信息（例如，`rdfs:label`和`rdfs:comment`三元组）`OPA2Vec`(`Smaili et al., 2018`) 通过学习单词向量化模型来考虑本体的词汇信息，该模型编码语料库中项目之间的统计相关性。然而，它将**每个公理视为一个句子**，并没有探索和利用**公理之间**的语义关系。`OWL2Vec`(`Holter et al., 2019`)，这是我们在`OWL2Vec*`之前的非常初步的工作，通过探索类的邻域来捕捉OWL本体的语义。这被证明是非常有效的，但它没有充分利用OWL本体中可用的图形结构、词汇信息或逻辑语义。

------

- **<u>ABox</u>**：
  - **TBox**具有分类(Taxonomy)的能力，分类是系统化建立知识的第一步，通过分类的动作可以将事物的本体集合成共通的概念，这些概念又可组合成更广泛的概念，例如“快速排序”和“选择排序”共通的类别为“排序”，“排序”就是一个上层概念，而“排序”和“递归”又推演出“算法”这个上层概念，在这个分类过程中，每一个类都有其属性和限制，在分类的过程中可以建立类的限制式，这些限制式可以表达出类和类的关系，使用描述逻辑可以针对类的关系限制作描述。 
  - **ABox**是实例断言的集合，用于指明个体的属性或个体之间的关系。由概念断言(如：student(Bob))和关系断言(如：`hasMom(Bob,Nancy)`)组成。简单的讲，**ABox**是将与**TBox**中的类相对应的实例填入，所建立的实例要符合`TBox`中类设计的限制和属性，这些类的实体（individual）称为实例（instance），由这些实例可以将原来只具有概念的架构，组合为可以表现实体知识关系的架构。

![preview](.\image\v2-9a4499ed6bc4e4556214ed81d616a3d9_r.jpg)

> Inspired by the success of KG embeddings, more recently there has been a growing interest in embedding simple ontological schemas consisting, e.g., of hierarchical classes, and property domain and range (Hao et al., 2019; Moon et al., 2017; Alshargi et al., 2018; Guan et al., 2019); however, these methods rely on having a large number of facts (i.e., an ABox), and do not support more expressive OWL ontologies which contain some widely used logic constructors such as the class disjointness and the existential quantification mentioned above. Embeddings for OWL ontologies have started to receive some attention recently. Kulmanov et al. (2019) and Garg et al. (2019) proposed to model the semantics of the logic constructor by geometric learning, but their models only support some of the logic constructors from the description logics (DLs) EL++ (which is closely related to OWL EL – a fragment of OWL) and ALC, respectively. Moreover, both methods consider only the logical and graph structure of an ontology, and ignore its lexical information that widely exists in the meta data (e.g., rdfs:label and rdfs:comment triples). OPA2Vec (Smaili et al., 2018) considers the ontology’s lexical information by learning a word embedding model which encodes statistical correlations between items in a corpus. However, it treats each axiom as a sentence and fails to explore and utilize the semantic relationships between axioms. OWL2Vec* (Holter et al., 2019), which is our very preliminary work before OWL2Vec*, captures the semantics of OWL ontologies by exploring the neighborhoods of classes. This was shown to be quite effective, but it does not fully exploit the graph structure, the lexical information, or the logical semantics available in OWL ontologies.  



在这项工作中，我们扩展了`OWL2Vec`，以提供一个更通用、更健壮的OWL本体向量化框架，我们称之为`OWL2Vec*`。`OWL2Vec*`通过遍历其图形形式来利用OWL（或OWL 2）本体，并生成一个包含三个文档的语料库，这些文档捕捉了本体语义的不同方面：（i）图结构和逻辑构造函数，（ii）词汇信息（例如，实体名称、注释和定义），以及（iii）词汇信息、图形结构和逻辑构造器的组合。最后，`OWL2Vec*`使用单词向量化模型从生成的语料库中创建实体和单词的向量化。请注意，`OWL2Vec*`框架与不同的词向量化方法及其不同的设置兼容，尽管当前的实现采用了`Word2Vec (Mikolov et al., 2013b)`及其`skip-gram`架构。

------

- `Word2Vec`

  `Word2Vec`是语言模型中的一种，它是从大量文本预料中以无监督方式学习语义知识的模型，被广泛地应用于自然语言处理中。

  - `Distributed Representation`最早是`Hinton`于1986年提出的，可以克服`One-Hot Representation`的上述缺点。其基本想法是：通过训练将某种语言中的每一个词 映射成一个固定长度的短向量（当然这里的“短”是相对于`One-Hot Representation`的“长”而言的），所有这些向量构成一个词向量空间，而每一个向量则可视为该空间中的一个点，在这个空间上引入“距离”，就可以根据词之间的距离来判断它们之间的语法、语义上的相似性了。`Word2Vec`中采用的就是这种`Distributed Representation` 的词向量。

  - `CBOW`：方式是在知道词$w_t$的上下文$w_{t−2},w_{t−1},w_{t+1},w_{t+2}$的情况下预测当前词$w_t$

  - `skip-gram`：知道了词$w_t$的情况下,对词wt的上下 文$w_{t−2},w_{t−1},w_{t+1},w_{t+2}$进行预测

> In this work we have extended OWL2Vec\* in order to provide a more general and robust OWL ontology embedding framework which we call OWL2Vec\* . OWL2Vec\*exploits an OWL (or OWL 2) ontology by walking over its graph forms and generates a corpus of three documents that capture different aspects of the semantics of the ontology: (i) the graph structure and the logic constructors, (ii) the lexical information(e.g., entity names, comments and definitions), and (iii) a combination of the lexical information, graph structure and logical constructors. Finally, OWL2Vec\* uses a word  embedding model to create embeddings of both entities and words from the generated corpus. Note that the OWL2Vec\* framework is compatible with different word embedding methods and their different settings, although the current implementation adopts Word2Vec (Mikolov et al., 2013b) and its skip-gram architecture.  



我们在两个案例研究中评估了`OWL2Vec*`——类别隶属度预测和类别包容预测，使用了三个大规模的现实世界本体——一个名为`HeLis`的健康生活方式本体` (Dragoni et al., 2018)`，一个称为`FoodOn`的食物本体 `(Dooley et al., 2018)` 和基因本体`(GO) (G.O. 2008)`。在案例研究中，我们实证分析了（i）对应于图结构、词汇信息和逻辑构造器的语义组合的不同文档和向量化设置，（ii）不同的图结构探索设置（例如，从OWL本体到RDF图的转换方法，以及图行走策略）的影响，（iii）本体蕴涵推理和（iv）词向量化预训练。结果表明，`OWL2Vec*`可以实现比先前研究更好的性能，包括最先进的本体向量化 `(Kulmanov et al., 2019; Garg et al., 2019; Smaili et al., 2018; Holter et al., 2019)`，一些经典的KG向量化，如`DF2Vec (Ristoski and Paulheim 2016)`, `TransE (Bordes et al., 2013)` 和 `DistMult (Yang et al., 2014)`，以及两个基于文本上下文的监督`Transformer (Vaswani et al., 2017)` 。我们还计算了实体之间的欧几里得距离，并可视化了一些示例实体的向量化，以分析不同的向量化方法。

> We have evaluated OWL2Vec* in two case studies — class membership prediction and class subsumption prediction, using three large scale real world ontologies — a healthy lifestyle ontology named HeLis (Dragoni et al., 2018), a food ontology named FoodOn (Dooley et al., 2018) and the Gene Ontology (GO) (G.O. 2008). In the case studies we empirically analyze the impact of (i) different document and embedding settings which correspond to combinations of the semantics of the graph structure, lexical information and logic constructors, (ii) different graph structure exploration settings (e.g., the transformation methods from an OWL ontology to an RDF graph, and the graph walking strategies), (iii) ontology entailment reasoning, and (iv) word embedding pre-training. The results suggest that OWL2Vec* can achieve significantly better performance than the baselines including the state-of-the-art ontology embeddings (Kulmanov et al., 2019; Garg et al., 2019; Smaili et al., 2018; Holter et al., 2019), some classic KG embeddings such as RDF2Vec (Ristoski and Paulheim 2016), TransE (Bordes et al., 2013) and DistMult (Yang et al., 2014), and two supervised Transformer (Vaswani et al., 2017) classifiers based on the textual context. We also calculated the Euclidean distance between entities and visualized the embeddings of some example entities to analyze different embedding methods.  



简单地说，这项研究可以总结如下。

- 这项工作是第一项旨在OWL本体的各种语义实现向量化的工作，包括图结构、词汇信息和逻辑构造函数。已经开发了一个名为`OWL2Vec*`的通用框架，以及处理不同OWL语义的不同策略。`OWL2Vec*`具有以下潜力：（i）在大规模本体上实现统计或机器学习任务，从而帮助其管理和提升其应用；（ii）促进**符号和亚符号系统**集成到新的神经符号解决方案中。
- 该工作在三个真实世界本体的两个重要本体完成案例研究（类成员预测和类包容预测）中对`OWL2Vec*`进行了评估，其中`OWL2Vec*`优于最先进的本体向量化和经典的KG向量化方法。我们还进行了广泛的**消融研究**，以验证所采用的策略以及可视化分析，以便于解释。

> Briefly this study can be summarized as follows.
>
> • This work is among the first that aim at embedding all kinds of semantics of OWL ontologies including the graph structure, the literals and the logical constructors. A general framework named OWL2Vec\* together with different strategies for addressing different OWL semantics has been developed. OWL2Vec* has the potential to(i) enable statistical or machine learning tasks over massive ontologies, thus assisting their curation and boosting their application, (ii) facilitate the integration of symbolic and sub-symbolic systems into new neural-symbolic solutions.
>
> • The work has evaluated OWL2Vec\* in two important ontology completion case studies (class membership prediction and class subsumption prediction) on three real world ontologies, where OWL2Vec\* outperforms both state-of-the art ontology embedding and classic KG embedding methods. We have also conducted extensive ablation studies to verify the adopted strategies as well as visualization analysis to facilitate interpretation.  



本文的其余部分组织如下。下一节介绍了准备工作，包括背景和相关工作。第3节介绍了`OWL2Vec*`的技术细节以及案例研究。第4节介绍了实验和评估结果。最后一节总结并讨论了未来的工作。

> The remainder of the paper is organized as follows. The next section introduces the preliminaries including both background and related work. Section 3 introduces the technical details of OWL2Vec\* as well as the case studies. Section 4 presents the experiments and the evaluation results. The last section concludes and discusses future work.  



# 2 Preliminaries 准备工作



## 2.1 OWL ontologies OWL本体

我们的`OWL2Vec*`向量化目标是OWL本体 `(Bechhofer et al., 2004)`，该本体基于$\mathcal{SROIQ}$描述逻辑（`DL`） `(Baader et al., 2017)`。考虑特征$\sum ＝(\mathcal{N}_C，\mathcal{N}_R，\mathcal{N}_I)$，其中$\mathcal{N}_C，\mathcal{N}_R，\mathcal{N}_I$分别是原子概念、原子角色和个体实例的成对不相交集。复杂的概念和角色可以使用`DL`构造器组合，如并集（例如，C⊓D） ，交集（例如，C⊔D） 即存在（例如，∃r. C）和任意实例（例如，∀r.C）***<u>其中C和D是概念，r是角色</u>***。OWL本体包括$TBox\ \mathcal{T}$和$ABox\  \mathcal{A}$。

- TBox是一组公理(axioms)，如一般概念包含`（GCI）`公理（例如C⊑ D） ，角色包含`（RI）`公理（例如，r⊑ s） 以及反角色公理（例如s≡ r^-^），***<u>其中C和D是概念，r和s是角色，r^-^表示r的相反事物</u>***。

- ABox是一组断言(assertions)，如概念断言（如C(a)）、角色断言（如r(a，b)）和个体等式和不等式断言（如a≡ b和a≢ b） ***<u>其中C是概念，r是角色，a和b是个体</u>***。

  值得注意的是，术语部分(terminological part)也可以分为TBox和RBox，其中RBox建模了角色之间的相互依赖关系，如RI

------

- $\mathcal{SROIQ}$：

  |                         |   Syntax    |                          Semantics                           |
  | :---------------------: | :---------: | :----------------------------------------------------------: |
  |     **Individuals**     |             |                                                              |
  |     Individual name     |     *a*     |                            *a*^I^                            |
  |        **Roles**        |             |                                                              |
  |       Atomic role       |     *R*     |                            *R*^I^                            |
  |      Inverse role       |   *R*^−^    |                 {⟨x, y⟩ \| ⟨y, x⟩ ∈ *R*^I^}                  |
  |     Universal role      |     *U*     |                         ∆^I^ × ∆^I^                          |
  |      **Concepts**       |             |                                                              |
  |     Atomic concept      |     *A*     |                            *A*^I^                            |
  |      Intersection       |  *C* ⊓ *D*  |                        *C*^I^∩ *D*^I^                        |
  |          Union          |  *C* ⊔ *D*  |                        *C*^I^∪ *D*^I^                        |
  |       Complement        |    ¬*C*     |                         ∆^I^\ *C*^I^                         |
  |       Top concept       |      ⊤      |                             ∆^I^                             |
  |     Bottom concept      |      ⊥      |                              ∅                               |
  | Existential restriction |  ∃*R*.*C*   |      {*x* \| some *R*^I^-successor of *x* is in *C*^I^}      |
  |  Universal restriction  |  ∀*R*.*C*   |     {*x* \| all *R*^I^-successors of *x* are in *C*^I^}      |
  |  At-least restriction   | ⩾n *R*.*C*  | {*x* \| at least *n* *R*^I^-successors of *x* are in *C*^I^} |
  |   At-most restriction   | ⩽n *R*.*C*  | {*x* \| at most *n* *R*^I^-successors of *x* are in *C*^I^}  |
  |    Local reflexivity    | ∃*R*.*Self* |                 {*x* \| ⟨*x*, *x*⟩ ∈ *R*^I^}                 |
  |         Nominal         |    {*a*}    |                           {*a*^I^}                           |

> Our OWL2Vec* embedding targets OWL ontologies (Bechhofer et al., 2004), which are based on the SROIQ description logic (DL) (Baader et al., 2017). Consider a signature Σ = (NC, NR, NI), where NC, NR and NI are pairwise disjoint sets of, respectively, atomic concepts, atomic roles and individuals. Complex concepts and roles can be composed using DL constructors such as conjunction (e.g., C ⊓ D), disjunction (e.g., C ⊔ D), existential restriction (e.g., ∃r.C) and universal restrictions (e.g., ∀r.C) where C and D are concepts, and r is a role. An OWL ontology comprises a TBox T and an ABox A. The TBox is a set of axioms such as General Concept Inclusion (GCI) axioms (e.g., C ⊑ D), Role Inclusion (RI) axioms (e.g., r ⊑ s) and Inverse Role axioms (e.g., s ≡ r-), where C and D are concepts, r and s are roles, and r- denotes the inverse of r. The ABox is a set of assertions such as concept assertions (e.g., C(a)), role assertions (e.g., r(a, b)) and individual equality and inequality assertions (e.g., a ≡ b and a ≢ b), where C is a concept, r is a role, a and b are individuals. It is worth noting that the terminological part can also be divided into a TBox and an RBox, where the RBox models the interdependencies between the roles such as the RI  



在OWL中，上述概念、角色和个体分别建模为类、对象属性和实例。为了避免混淆，我们将在本文的其余部分仅使用类、对象属性和实例这三个术语。我们还将使用术语属性，它不仅可以指对象属性，还可以指数据属性和注释属性。同时，为了方便起见，我们还将使用通用术语实体来指代类、属性或实例。请注意，对象属性对两个实例之间的关系进行建模；数据属性建模实例和文字值（例如，数字或文本）之间的关系；注释属性用于表示实体和注释（例如注释或标签）之间的（非逻辑）关系。每个实体由国际化资源标识符（IRI）唯一表示。这些IRI在词汇上可以是“有意义的”（例如，图1a中的vc:AlcoholicBeverages），或者由不携带有用词汇信息的内部ID组成（例如，图1b中的obo:FOODON_00002809）；在任何一种情况下，预期含义也可以通过注释表示（见下文）。为了增强读取能力，我们通常会与实体的基于ID的IRI一起显示来自其可用注释的可读标签。

> In OWL, the aforementioned concept, role and individual are modeled as class, object property and instance, respectively. To avoid confusion, we will only use the terms of class, object property and instance in the remainder of the paper. We will also use the term property which can refer to not only object property, but also data property and annotation property. Meanwhile, for convenience, we will also use a general term entity to refer to a class, a property or an instance. Note that an object property models the relationship between two instances; a data property models the relationship between an instance and a literal value (e.g., number or text); and an annotation property is used to represent a (nonlogical) relationship between an entity and an annotation (e.g., comment or label). Each entity is uniquely represented by an Internationalized Resource Identifier (IRI).4 These IRIs may be lexically ‘meaningful’ (e.g., vc:AlcoholicBeverages in Fig. 1a) or consist of
> internal IDs that do not carry useful lexical information (e.g., obo:FOODON_00002809 in Fig. 1b); in either case the intended meaning may also be indicated via annotations (see below). To enhance the reading, we will typically show together with the ID-based IRI of an entity a readable label from its available annotations.  



在OWL中，`GCI`公理C⊑ D对应于类C和类D之间的包容关系，而概念断言C（a）对应于实例a和类C之间的隶属关系。同时，在OWL中，复杂概念、复杂角色、公理和角色断言可以串行化为（一组）RDF三元组，每个三元组由主语、谓词和对象组成。对于谓词，这些三元组使用了定制的对象属性（例如vc:hasNutrient）和RDF、RDFS和OWL的内置属性（例如RDFS:subClassOf、RDF:type和OWL: someValuesFrom）的组合。例如，在图1中，两个实例vc:FOOD-4001（金发啤酒）和vc:VitaminC_100之间的关系由使用对象属性vc:hasNutrient的三元组表示，而涉及类obo:FOODON_00002809（毛豆）和对象属性obo:RO_0001000（衍生自）的存在限制由使用三个OWL内置属性的三元组表示，即。，owl:Restriction、owl:onProperty和owl:someValuesFrom。OWL断言的RDF三元组的对象可以是文字值；例如，vc:FOOD-4001（金发啤酒）的卡路里量使用定制数据属性vc:amountCalories和xsd:double类型的文字值34.0表示为三元组。

> In OWL, a GCI axiom C ⊑ D corresponds to a subsumption relation between the class C and the class D, while a concept assertion C(a) corresponds to a membership relation between the instance a and the class C. Meanwhile, in OWL, complex concepts, complex roles, axioms and role assertions can be serialised as (sets of) RDF triples, each of which is a tuple composed of a subject, a predicate and an object. For the predicate, these triples use a combination of bespoke object properties (e.g., vc:hasNutrient), and built-in properties by RDF, RDFS and OWL (e.g., rdfs:subClassOf,5 rdf:type and owl:someValuesFrom). In Fig. 1, for example, the relationship between the two instances vc:FOOD-4001 (blonde beer) and vc:VitaminC_100 is represented by a triple using  the object property vc:hasNutrient, while the existential restriction involving the class obo:FOODON_00002809 (edamame) and the object property obo:RO_0001000 (derives from) is represented by triples using three OWL built-in properties, i.e., owl:Restriction, owl:onProperty and owl:someValuesFrom. The object of an RDF triple of an OWL assertion can be a literal value; for example, the calories amount of vc:FOOD-4001 (blonde beer) is represented by a triple using the bespoke data property vc:amountCalories and the literal value 34.0 of type xsd:double.  



除了具有基于形式逻辑的语义的公理和断言之外，本体通常还包含注释公理形式的元数据信息。这些注释也可以用RDF三元组表示，使用注释属性作为谓词；e、 例如，类obo:FOODON_00002809（EDAME）使用rdfs:label来指定名称字符串，使用rdfs:comment来指定描述，并使用obo:IAO-0000115（定义）-一个定制的注释属性来指定自然语言“定义”。

> In addition to axioms and assertions with formal logic-based semantics, an ontology often contains metadata information in the form of annotation axioms. These annotations can also be represented by RDF triples using annotation properties as the predicates; e.g., the class obo:FOODON_00002809 (edamame) is annotated using rdfs:label to specify  a name string, using rdfs:comment to specify a description, and using obo:IAO-0000115 (definition) — a bespoke annotation property to specify a natural language “definition”.  



知识图谱（KG）指结构化知识资源，通常表示为一组RDF三元组`(Hogan et al., 2020)`。许多KG只包含与OWL本体ABox等价的实例和事实。其他一些KG，如DBpedia（Auer等人，2007年），也通过与OWL本体的TBox等效的模式进行了增强。因此，KG通常可以理解为本体。

> Knowledge graph (KG) refers to structured knowledge resources which are often expressed as a set of RDF triples (Hogan et al., 2020). Many KGs only contain instances and facts which are equivalent to an OWL ontology ABox. Some other KGs such as DBpedia (Auer et al., 2007) are also enhanced with an schema which is equivalent to the TBox of an OWL ontology. Thus, a KG can often be understood as an ontology .



## 2.2 Semantic embedding 语义向量化



语义向量化指的是一系列表示学习（或特征学习）技术，将**序列和图形等数据的语义**编码为向量，以便下游机器学习预测和统计分析任务可以利用这些语义`(Bengio et al., 2013)`。词向量化或序列特征学习模型，如前馈神经网络、递归神经网络和变换器，广泛用于语义向量化，并且它们在将上下文（例如，项目共现）序列向量化中表现出良好的性能 `(Mikolov et al., 2013a; Peters et al., 2018; Devlin et al., 2019)`。用于学习顺序项表示的两种经典自动编码架构是skip-gram和连续词包（CBOW）`(Mikolov et al., 2013b, a)`。前者旨在预测项目的环境，而后者旨在根据项目的环境预测项目。`Word2Vec`是一组众所周知的序列特征学习技术，用于从大型语料库中学习单词向量化，最初由谷歌的一个团队开发；它可以被配置为使用skip-gram或CBOW架构`(Mikolov et al., 2013b, a)`。

> Semantic embedding refers to a series of representation learning (or feature learning) techniques that encode the semantics of data such as sequences and graphs into vectors, such that they can be utilized by downstream machine learning prediction and statistical analysis tasks (Bengio et al., 2013). Word embedding or sequence feature learning models such as Feed-Forward Neural Networks, Recurrent Neural Networks and Transformers are widely used for semantic embedding, and they have shown good performance in embedding the context (e.g., item co-occurrence) in sequences (Mikolov et al., 2013a; Peters et al., 2018; Devlin et al., 2019). Two classic auto-encoding architectures for learning representations of sequential items are continuous skip-gram and continuous Bag-of-Words (CBOW) (Mikolov et al., 2013b, a). The former aims at predicting the surroundings of an item, while the latter aims at predicting an item based on its surroundings. Word2Vec is a well known group of sequence feature learning techniques for learning word embeddings from a large corpus, and was initially developed by a team at Google; it can be configured to use either skip-gram or CBOW architectures (Mikolov et al., 2013b, a).  



语义向量化也已扩展到由角色断言组成的KG `(Wang et al., 2017)`。实体和关系（对象属性）在向量空间中表示，同时保留其相对关系（语义），然后将所得向量应用于下游任务，包括链路预测 `(Rossi et al., 2020)`、实体对齐`(Sun et al., 2020)`和错误事实检测和校正 `(Chen et al., 2020a)`。学习KG表示的一个范例是以端到端的方式计算向量化，使用优化算法迭代调整向量以**最小化所有三元组的总体损失**，其中损失通常通过对每个三元组（正样本和负样本）的真/假进行评分来计算。基于该技术的算法包括基于翻译的模型，如`TransE (Bordes et al., 2013) and TransR (Lin et al., 2015)` 以及潜在因素模型，如 `DistMult (Yang et al., 2014)`。

> Semantic embedding has also been extended to KGs composed of role assertions (Wang et al., 2017). The entities and relations (object properties) are represented in a vector space while retaining their relative relationships (semantics), and the resulting vectors are then applied to downstream tasks including link prediction (Rossi et al., 2020), entity alignment (Sun et al., 2020), and erroneous fact detection and correction (Chen et al., 2020a). One paradigm for learning KG representations is computing the embeddings in an end-to-end manner, iteratively adjusting the vectors using an optimization algorithm to minimize the overall loss across all the triples, where the loss is usually calculated by scoring the truth/ falsity of each triple (positive and negative samples). Algorithms based on this technique include translation based models such as TransE (Bordes et al., 2013) and TransR (Lin et al., 2015) and latent factor models such as DistMult (Yang et al., 2014).  



另一种范式是首先明确探索图中实体和关系的邻域，然后使用单词嵌入模型学习嵌入。基于该范式的两个代表性算法是`node2vec` `（Grover&Leskovec，2016）`和深度图核（Deep Graph Kernels ）`（Yanardag和Vishwanathan，2015）`。前者提取随机图行走并创建跳跃图或`CBOW`模型作为训练语料库，而后者使用图核，如`Weisfeiler-Lehman（WL）`子图核作为语料库。然而，这两种嵌入算法最初都是为无向图开发的，因此直接应用于KG时性能可能有限。`RDF2Vec`通过将上述两种算法的思想扩展到有向标记的`RDF`图来解决这一问题，并已被证明可以学习大规模KG的有效向量化，如`DBpedia（Ristoski&Paulheim，2016；Ristosk等人，2019）`。最近的研究探索了使用新词向量化或序列特征学习模型来学习向量化；一个例子是KG向量化和链路预测方法`RW-LMLM`，该方法将随机游动算法与Transformer相结合`（Wang等人，2019）`。

> Another paradigm is to first explicitly explore the neighborhoods of entities and relations in the graph, and then learn the embeddings using a word embedding model. Two representative algorithms based on this paradigm are node2vec (Grover & Leskovec, 2016) and Deep Graph Kernels (Yanardag and Vishwanathan 2015). The former extracts random graph walks and creates skip-gram or CBOW models as the corpus for training, while the latter uses graph kernels such as Weisfeiler-Lehman (WL) sub-graph kernels as the corpus. However, both embedding algorithms were originally developed for undirected graphs, and thus may have limited performance when directly applied to KGs. RDF2Vec addresses this issue by extending the idea of the above two algorithms to directed labeled RDF graphs  and has been shown to learn effective embeddings for large scale KGs such as DBpedia (Ristoski & Paulheim, 2016; Ristoski et al., 2019). Recent studies have explored the usage
> of new word embedding or sequence feature learning models for learning embeddings; one example is the KG embedding and link prediction method named RW-LMLM which combines the random walk algorithm with Transformer (Wang et al., 2019).  



我们的`OWL2Vec*`技术属于词向量范式，但我们关注OWL本体，而不是典型的KG，目标是***<u>不仅保留图结构的语义，还保留词汇信息和逻辑构造器的语义</u>***。请注意，包含层次分类结构的本体图不同于由典型KG的角色（关系）断言组成的多关系图；此外，根据我们对本体嵌入的文字评论和最新调查`（Kulmanov等人，2020年）`，目前没有联合探索本体词汇信息和逻辑构造器的现有KG嵌入方法。

> Our OWL2Vec\* technique belongs to the word embedding paradigm, but we focus on OWL ontologies instead of typical KGs, with the goal of preserving the semantics not only of the graph structure, but also of the lexical information and the logical constructors. Note that the graph of an ontology, which includes hierarchical categorization structure, differs from the multi-relation graph composed of role (relation) assertions of a typical KG; furthermore, according to our literal review on ontology embedding (cf. Sect. 2.3) and the latest survey (Kulmanov et al., 2020), there are currently no existing KG embedding methods that jointly explore the ontology’s lexical information and logical constructors.  



## 2.3 Ontology embedding 本体向量化



机器学习预测和统计分析与本体的使用正受到更广泛的关注，一些OWL本体语义向量化的方法已经在文献中找到。与典型的KG不同，OWL本体不仅包括图形结构，还包括逻辑构造函数，实体通常通过使用`rdfs:label`、`rdfs:comment`和许多其他定制或内置注释属性指定的更丰富的词汇信息来增强。本研究中OWL本体向量化的目标是用向量表示每个OWL命名实体（类、实例或属性），从而将上述信息指示的实体间关系保持在向量空间中，并最大化下游任务的性能，其中输入向量可以理解为学习特征。

> The use of machine learning prediction and statistical analysis with ontologies is receiving wider attention, and some approaches to embedding the semantics of OWL ontologies can already be found in the literature. Unlike typical KGs, OWL ontologies include not only graph structure but also logical constructors, and entities are often augmented with richer
> lexical information specified using rdfs:label, rdfs:comment and many other bespoke or built-in annotation properties. The objective of OWL ontology embedding in this study is to represent each OWL named entity (class, instance or property) by a vector, such that the inter-entity relationships indicated by the above information are kept in the vector space, and the performance of the downstream tasks, where the input vectors can be understood as learned features, is maximized.  



`EL Embedding（Kulmanov等人，2019）`和`Quantum Embedding （Garg等人，2019）`是端到端范式的两种OWL本体嵌入算法。他们通过将逻辑关系转换为几何关系，分别为EL++和$\mathcal{ALC}$的逻辑公理构造特定的得分函数和损失函数。这编码了逻辑构造函数的语义，但忽略了***<u>本体的词汇信息提供的附加语义</u>***。此外，尽管通过考虑类包容和类成员公理来探索图结构，但这种探索是不完整的，因为它只使用`rdfs:subClassOf and rdf:type`边，而忽略了涉及其他关系的边。

> EL Embedding (Kulmanov et al., 2019) and Quantum Embedding (Garg et al., 2019) are two OWL ontology embedding algorithms of the end-to-end paradigm. They construct specific score functions and loss functions for logical axioms from EL++ and ALC, respectively, by transforming logical relations into geometric relations. This encodes the semantics of the logical constructors, but ignores the additional semantics provided by the lexical information of the ontology. Moreover, although the graph structure is explored by considering class subsumption and class membership axioms, the exploration is incomplete as it uses only rdfs:subClassOf and rdf:type edges, and ignores edges involving other relations.



`Onto2Vec（Smaili等人，2018）`和`OPA2Vec (Smaili et al., 2018)`。`Onto2Vec`使用本体的公理作为训练语料库，而`OPA2Vec`使用`rdfs:comment`等提供的词汇信息来补充`Onto2Vec`的语料库。**两者都采用了带有蕴涵推理的本体的演绎闭包**。它们已经用预测`(PPI)`相互作用的基因本体论（即类之间的特定域关系）进行了评估，这与本研究中的类成员预测和类包含预测大不相同。这两种方法都将每个公理视为一个句子，这意味着它们无法探索公理之间的相关性。这使得很难充分探索**图结构和公理之间的逻辑关系**，也可能导致中小型本体的语料库短缺问题。`OWL2Vec*`处理`OPA2Vec`和`Onto2Vec`的上述问题，方法是使用通过遍历`RDF`图生成的语料库来补充它们的公理语料库，`RDF`图是从OWL本体转换而来的，其中考虑了其图结构和逻辑构造器。此外，为了充分利用词法信息，`OWL2Vec*`不仅为当前KG/本体向量化方法中的本体实体创建嵌入，还为词法信息中的单词创建向量化。

> Onto2Vec (Smaili et al., 2018) and OPA2Vec (Smaili et al., 2018) are two ontology embedding algorithms of the word embedding paradigm using a model of either the skipgram architecture or the CBOW architecture. Onto2Vec uses the axioms of an ontology as the corpus for training, while OPA2Vec complements the corpus of Onto2Vec with the lexical information provided by, e.g., rdfs:comment. Both adopt the deductive closure of an ontology with entailment reasoning. They have been evaluated with the Gene Ontology for predicting protein-protein interaction (i.e., a domain-specific relationship between classes), which is quite different from the class membership prediction and the class subsumption prediction in this study. Both methods treat each axiom as a sentence, which means that they cannot explore the correlation between axioms. This makes it hard to fully explore the graph structure and the logical relation between axioms, and may also lead to the problem of corpus shortage for small to medium scale ontologies. OWL2Vec* deals with the above issues of OPA2Vec and Onto2Vec by complementing their axiom corpus with a corpus generated by walking over RDF graphs that are transformed from the OWL ontology with  its graph structure and logical constructors considered. In addition, to fully utilize the lexical information, OWL2Vec* creates embeddings for not only the ontology entities as the current KG/ontology embedding methods but also for the words in the lexical information.



# 3 Methodology 技术方法



图2展示了`OWL2Vec*`的总体框架，主要包括两个核心步骤：（i）从本体中提取语料库，以及（ii）使用语料库进行单词嵌入模型训练。语料库包括**<u>*结构文档、词汇文档和组合文档*</u>**。前两个文档旨在探索本体的图结构、逻辑构造函数和词汇信息，其中可以启用本体蕴涵推理，而第三个文档旨在保留实体（`IRI`）与其词汇标签（单词）之间的相关性。注意，后两个文档是使用第一个文档作为主干构建的，同时考虑了从本体获得的词汇信息。各文件的句子示例见表2。简而言之，给定输入本体O和其中用于向量化的对象集合E，`OWL2Vec*`为E中的每个实体e输出一个向量，表示为e∈ ℝd、 其中d是（可配置的）向量维度。注意，E可以是O中的所有实体，也可以只是特定应用程序所需的一部分。通过`OWL2Vec*`嵌入，我们将其应用于两个下游案例研究——**类成员预测和类包容预测**。对于类成员预测，我们将E设置为所有命名类和实例；对于类包含预测，我们将E设置为所有命名类。

> Figure 2 presents the overall framework of OWL2Vec\*, which mainly consists of two core steps: (i) corpus extraction from the ontology, and (ii) word embedding model training with the corpus. The corpus includes a structure document, a lexical document, and a combined document. The first two documents aim at exploring the ontology’s graph structure, logical constructors and lexical information, where ontology entailment reasoning can be enabled, while the third document aims at preserving the correlation between entities (IRIs) and their lexical labels (words). Note that the latter two documents are constructed using the first document as the backbone while taking into account the lexical information available from the ontology. See Table 2 for sentence examples of each document. Briefly, given an input ontology O and the target entities E of O for embedding, OWL2Vec\* outputs a vector for each entity e in E, denoted as e ∈ ℝd, where d is the (configurable) embedding dimension. Note that E can be all the entities in O or just a part needed for a specific application. With the OWL2Vec* embeddings, we apply them in two downstream case studies — class membership prediction and class subsumption prediction. For class membership prediction we set E to all the named classes and instances; for class subsumption prediction we set E to all the named classes.  

<img src="image\image-20220819182137145.png" alt="image-20220819182137145" style="zoom:80%;" />



## 3.1 From OWL ontology to RDF graph



`OWL2Vec*`结合了两种策略，将原始OWL本体O转化为由RDF三元组组成的图G。第一种策略是根据OWL到RDF图映射进行转换，该映射最初由W3C定义，用于将OWL本体存储和交换为RDF三元组。通过引入一些内置属性或使用公理中的定制属性（例如。，`⟨vc:FOOD-4001（金发啤酒），rdf:type，vc:Beer⟩`, `⟨vc:FOOD-4001，rdfs:label，“Blonde Beer”⟩` 和`⟨vc:FOOD-4001，vc:hasNutrient，vc:VitaminC_1000⟩`). 涉及复杂类表达式的公理需要转换为多个三元组，并且通常依赖于空白节点。例如，图1b中的类obo:FOODON_00002809（EDAME）的存在限制，即来自（obo:RO_0001000（derives from），obo:FOODON_ 03411347（plant））的ObjectSomeValue被转换为四个RDF三元组，即。，`⟨obo:FOODON_00002809, rdfs:subClassOf, _:x⟩, ⟨_:x, owl:someValuesFrom,obo:FOODON_03411347 ⟩, ⟨_:x, rdf:type, owl:Restriction⟩ and ⟨_:x, owl:onProperty, obo:RO_0001000 ⟩`, 其中：`_:x`表示空白节点。在本例中，在obo:FOODON_00002809和obo:FOODON_ 03411347之间插入了一个附加节点_`:x`和一个附加边缘 `rdfs:subClassOf`。

> OWL2Vec\* incorporates two strategies to turn the original OWL ontology O into a graph G that is composed of RDF triples. The first strategy is the transformation according to OWL to RDF Graph Mapping which is originally defined by the W3C6 to store and exchange OWL ontologies as RDF triples. Some simple axioms such as membership and subsumption axioms for atomic entities, data and annotation properties associated to atomic entities, and relational assertions between atomic instances can be directly transformed into RDF triples by introducing some built-in properties or using the bespoke properties in the axioms (e.g., ⟨vc:FOOD-4001 (Blonde Beer), rdf:type, vc:Beer⟩, ⟨vc:FOOD-4001, rdfs:label, “Blonde Beer”⟩ and ⟨vc:FOOD-4001, vc:hasNutrient, vc:VitaminC_1000⟩). Axioms involving complex class expressions need to be transformed into multiple triples and often rely on blank nodes. For example, the existential restriction of the class obo:FOODON_00002809 (edamame) in Fig. 1b, i.e., ObjectSomeValues From(obo:RO_0001000 (derives from), obo:FOODON_03411347 (plant)) is transformed into four RDF triples, i.e., ⟨obo:FOODON_00002809, rdfs:subClassOf, _:x⟩, ⟨_:x, owl:someValuesFrom,obo:FOODON_03411347 ⟩, ⟨_:x, rdf:type, owl:Restriction⟩ and ⟨_:x, owl:onProperty,obo:RO_0001000 ⟩, where _:x denotes a blank node. In this example, one additional node _:x and one additional edge rdfs:subClassOf are inserted between obo:FOODON_00002809 and obo:FOODON_03411347.  



第二种策略基于`Soylu等人（2018年）和Holter等人（2019年）`提出的投影规则，如表1所示，其中每个RDF都是三重的⟨X, r, Y⟩ 在投影（第三列）中，由本体（第一列和第二列）中的一个或多个公理来证明。与第一种策略一样，两个原子实体（表1中的最后一行）之间的简单关系断言或与原子实体关联的简单数据或注释属性直接转换为一个三元组。而与第一种策略不同，这些复杂的逻辑构造函数（表1中的前六行）是近似的。例如，上述obo类的存在限制：FOODON_00002809将用 `⟨obo：FOODON_00002809，obo：RO_0001000，obo：FOODON_03411347⟩`. 这避免了在RDF图中使用空白节点，当学习向量化时，空白节点可能充当实体之间相关性的噪声；但是，在生成的RDF图中不保留精确的逻辑关系。此外，隶属度和包容公理的投影（表1中的第七行和第八行）有两种设置。在第一个设置中，两个涉及的原子实体被转换为一个三元组，谓词为`rdf:type`或`rdfs:subassof`。在第二个设置中，除了上述三元组外，还添加了一个使用`rdf:type-`或`rdfs:subassof-`的逆的三元组。这使得在转换的RDF图上具有包含或隶属关系的两个实体之间能够进行双向漫游，并将影响语料库和向量化。在本文的其余部分中，当我们提到投影规则时，默认情况下，我们参考第一种设置，而第二种设置是使用带逆的投影规则项或使用后缀`("R+")`

> The second strategy is based on projection rules proposed in Soylu et al., (2018), Holter et al., (2019), as shown in Table 1, where every RDF triple ⟨X, r, Y⟩ in the projection (the third column) is justified by one or more axioms in the ontology (the first         and second columns). As in the first strategy, a simple relational assertion between two atomic entities (the final row in Table 1), or a simple data or annotation property associated to an atomic entity, is directly transformed into one single triple. While those complex logical constructors (the first six rows in Table 1), unlike the first strategy, are approximated. For example, the above mentioned existential restriction of the class obo:FOODON_00002809 would be represented with ⟨obo:FOODON_00002809, obo:RO_0001000, obo:FOODON_03411347⟩. This avoids the use of blank nodes in the RDF graph, which may act as noise towards the correlation between entities when the embeddings are learned; but, the exact logical relationships are not kept in the resulting RDF graph. Moreover, the projection of membership and subsumption axioms (the seventh and eighth rows in Table 1) has two settings. In the first setting, the two involved atomic entities are transformed into one triple with the predicate of rdf:type or rdfs:subClassOf. In the second setting, in addition to the above triple, one more triple which uses the inverse of rdf:type or rdfs:subClassOf is added. This enables a bidirectional walk between two entities with a subsumption or membership relationship on the transformed RDF graph, and would impact the corpus and the embeddings. In the remainder of this paper, we by default refer to the first setting when we mention projection rules, and we refer to the second setting by the term of projection rules with inverse or by using the suffix “(+R)”   

<img src="image\image-20220819194415334.png" alt="image-20220819182137145" style="zoom:80%;" />



本体到`RDF`图的两种转换策略都可以结合OWL蕴涵推理器，以在O转换为`RDF`图`G`之前计算`TBox`分类和`ABox`实现。这种推理基于逻辑构造器的公理，并导致某些隐藏知识的显式表示。例如，在图1a中，我们可以推断出隐藏的三元组`⟨vc:FOOD-4001 (blonde beer), rdf:type, vc:AlcoholicBeverages⟩`  从`⟨vc:FOOD-4001, rdf:type, vc:Beer⟩` 和`⟨vc:Beer, rdfs:subClassOf, vc:AlcoholicBeverages⟩`. 当启用推理时，此类推断的隐藏三元组将包含在转换的`RDF`图G中。在我们的实验中，我们使用`HermiT OWL reasoner (Glimm et al., 2014)`，并评估启用或禁用推理的影响。

> Both ontology to RDF graph transformation strategies can incorporate an OWL entailment reasoner to compute the TBox classification and ABox realization before O is transformed into an RDF graph G. Such reasoning grounds the axioms of logical constructors and leads to explicit representation of some hidden knowledge. For example, in Fig. 1a, we can infer a hidden triple ⟨vc:FOOD-4001 (blonde beer), rdf:type, vc:AlcoholicBeverages⟩ from ⟨vc:FOOD-4001, rdf:type, vc:Beer⟩ and ⟨vc:Beer, rdfs:subClassOf, vc:AlcoholicBeverages⟩. When the reasoning is enabled, such inferred hidden triples will be included in the transformed RDF graph G. In our experiments we use the HermiT OWL reasoner (Glimm et al., 2014), and we evaluate the impact of enabling or disabling reasoning (cf. the second paragraph in Sect. 4.3.3 and Table 6).



## 3.2 Structure document 结构文档



结构文档旨在***<u>捕获本体的图结构和逻辑构造器</u>***。对于`RDF`图G，一个选项是使用`RDF`图G计算E中每个目标实体的随机游动。每个游动是实体`IRIs`序列，充当结构文档的句子。表2中的Ex1和Ex2是两个步行示例，都从类 `vc:FOOD-4001 (blonde beer)`开始。为了实现随机游动算法，我们首先将`RDF`图G转换为有向单关系图G′；对于每个`RDF`三元组⟨X、 r，Y⟩ 在G中，将对象X、对象Y和关系r变换为三个顶点，分别从X的顶点到r的顶点和从r的顶点到Y的顶点添加两条边。给定一个起始顶点，我们从其所有连接顶点中公平随机地选择下一个顶点，并将此“步骤”操作迭代特定次数以执行“行走”。

------

- `random walks`：

  - 定义：随机游走，概念接近于布朗运动，是布朗运动的理想数学状态。

  - 核心概念：任何无规则行走者所带的守恒量都各自对应着一个扩散运输定律。

  - 基本思想：

    从一个或一系列顶点开始遍历一张图。在任意一个顶点，遍历者将以概率1-a游走到这个顶点的邻居顶点，以概率a随机跳跃到图中的任何一个顶点，称a为跳转发生概率，每次游走后得出一个概率分布，该概率分布刻画了图中每一个顶点被访问到的概率。用这个概率分布作为下一次游走的输入并反复迭代这一过程。当满足一定前提条件时，这个概率分布会趋于收敛。收敛后，即可以得到一个平稳的概率分布。



> The structure document aims at capturing both the graph structure and the logical constructors of the ontology. With the RDF graph G, one option is computing random walks for each target entity in E with the RDF graph G. Each walk, which is a sequence of entity IRIs, acts as a sentence of the structure document. Ex1 and Ex2 in Table 2 are two walk examples both starting from the class vc:FOOD-4001 (blonde beer). To implement the random walk algorithm, we first transform the RDF graph G into a directed single relation graph G′; for each RDF triple ⟨X, r, Y⟩ in G, the subject X, the object Y and the relation r are transformed into three vertices, two edges are added from the vertex of X to the vertex of r and from the vertex of r to the vertex of Y respectively. Given one starting vertex, we fairly and randomly select the next vertex from all its connected vertices, and iterate this “step” operation for a specific number of times to perform “walking”.  

![屏幕截图 2022-08-19 233054](image\屏幕截图 2022-08-19 233054.png)



`OWL2Vec*`还允许使用`Weisfeiler-Lehman（WL）内核（Shervashidze et al.，2011）`，该内核将子图的结构编码为唯一标识，从而**实现子图在行走中的表示和合并**。对于变换后的单关系图G′中的一个顶点，有一个子图（邻域）从该顶点开始，我们简单地将该子图的`WL`核（恒等式）称为该顶点的`WL`内核。在我们的实现中，我们首先提取原始随机游动。对于每个随机行走，我们将保留起始顶点和从关系中获得的顶点的`IRIs`，但用其`WL`核替换从对象或对象中获得的非起始顶点的`IRIs`。表2中的Ex3是为Ex2的行走启用`WL`子图核的示例。请注意，在计算顶点的`WL`核时，可以设置其子图的大小，即从该顶点到子图中最远顶点的深度。我们生成并采用所有行走，子图大小从0到最大——默认情况下，超参数设置为4。特别是，子图大小为0的`WL`内核支持的随机游动***<u>等价于原始随机游动</u>***。

------

- `Weisfeiler-Lehman（WL）`：	
  - 

> OWL2Vec\* also allows the usage of the Weisfeiler Lehman (WL) kernel (Shervashidze et al., 2011) which encodes the structure of a sub-graph into a unique identity and thus enables the representation and incorporation of the sub-graph in a walk. For one vertex in the transformed single relation graph G′, there is an associated sub-graph (neighbourhood) starting from this vertex, and we simply call this sub-graph’s WL kernel (identity) as  this vertex’s WL kernel. In our implementation, we first extract the original random walks. For each random walk, we then keep the IRIs of the starting vertex and the vertices that are obtained from the relations, but replace the IRIs of the none-starting vertices that are obtained from the subjects or objects with their WL kernels. Ex3 in Table 2 is an example of enabling the WL sub-graph kernel for the walk of Ex2. Note that when calculating a vertex’s WL kernel, the size of its sub-graph, i.e., the depth from this vertex to the farthest vertex in the sub-graph can be set. We generate and adopt all the walks, with the sub-graph size ranging from 0 to a maximum size — a hyper-parameter that is set to 4 by default. Specially, the WL kernel enabled random walk with the sub-graph size of 0 is equivalent to the original random walk.  



为了表达逻辑构造器，`OWL2Vec*`提取本体的所有公理，并补充结构文档的句子。在我们的实现中，每个本体公理都按照OWL Manchester Syntax转换为一个序列，其中保留了原始的内置术语，如“`subassof`”和“`some`”。表2中的示例4是根据存在限制公理的类`obo:FOODON_00002809（edamame）`的曼彻斯特语法( Manchester Syntax)句子的示例。与在投影的RDF图上的随机游走相比，曼彻斯特语法语句指示了buit在术语中的术语之间的逻辑关系，其中投影RDF图针对相同的公理生成了`（obo:FOODON_00002809，obo:RO_0001000，obo:FOODON_03411347）`的语句；与通过`W3C OWL`到`RDF`图映射转换的图上的随机漫游相比，曼彻斯特语法语句更短，并且避免了空白节点。

-------

- `OWL Manchester Syntax`OWL曼彻斯特语法：
  - 。。。

> To capture the logical constructors, OWL2Vec\* extracts all the axioms of the ontology and complements the sentences of the structure document. In our implementation, each ontology axiom is transformed into a sequence following the OWL Manchester Syntax, where the original built-in terms such as “subClassOf” and “some” are kept. Ex4 in Table 2 is an example of such Manchester Syntax sentence according to the axiom of the existential restriction of the class obo:FOODON_00002809 (edamame). In comparison with the random walk over the projected RDF graph, which generates the sentence of (obo:FOODON_00002809, obo:RO_0001000, obo:FOODON_03411347) for the same axiom in Ex4, the Manchester Syntax sentence indicates the logical relationship between the terms by the buit-in terms; while in comparison with the random walk over the graph transformed by W3C OWL to RDF Graph Mapping, the Manchester syntax sentence is shorter and avoids the blank nodes.  



## 3.3 Lexical document 词汇文档



词汇文档包括两种单词句子。第一类是从结构文档中的实体`IRI`语句生成的，而第二类是从本体中的相关词汇注释公理提取的。对于第一类，给定一个实体`IRI`语句，其每个实体都被`rdfs:label`定义的英文标签替换。注意，标签被解析并转换为小写标记，在替换实体IRI之前，没有字母字符的标记被过滤掉。一些实体可能没有`rdfs:label`的英文注释，例如图1a中的类`vc:MilkAndYogurt`和实例`vc:VitaminC_1000`。在这种情况下，我们更喜欢使用IRI的名称部分，并将其解析为单词，假设名称遵循骆驼大小写 `(e.g., vc:MilkAndYogurt is parsed into “milk”, “and” and “yogurt”)`。这类句子的一个例子是表2中的Ex5，它是通过替换Ex1句子的IRIs生成的用他们的话来说。特别是，一些`IRI`既没有英文标签也没有有意义的`IRI`名称，并且当`WL子图`内核被启用时，结构语句中也有内核标识。我们将这些原始`IRIs`和`WL IDs`保存在单词句子中（参见表2中的示例6）

> The lexical document includes two kinds of word sentences. The first kind are generated from the entity IRI sentences in the structure document, while the second are extracted from the relevant lexical annotation axioms in the ontology. For the first kind, given an entity IRI sentence, each of its entities is replaced by its English label defined by rdfs:label. Note that the label is parsed and transformed into lowercase tokens, and those tokens with no letter characters are filtered out, before it replaces the entity IRI. It is possible that some entities have no English annotations by rdfs:label, such as the class vc:MilkAndYogurt and the instance vc:VitaminC_1000 in Fig. 1a. In this case, we prefer to use the name part of the IRI and parse it into words assuming that the name follows the camel case (e.g., vc:MilkAndYogurt is parsed into “milk”, “and” and “yogurt”). One sentence example of this kind is Ex5 in Table 2, which is generated by replacing the IRIs of the Ex1 sentence by their words. Specially, some IRIs have neither English labels or meaningful IRI names, and when the WL sub-graph kernel is enabled, there are also kernel identities in the structure sentence. We keep these original IRIs and identities in the word sentences (cf. Ex6 in Table 2)  



从文本注释中提取第二类单词句子。它们包括两种类型：通过定制注释属性`obo:IAO_0000115 (definition), obo:IAO_0010000 (has axiom label) and oboInOwl:hasSynonym`进行的注释，以及通过内置注释属性`rdfs:comment and rdfs:seeAlso`进行的注记。在我们当前的`OWL2Vec*`实现中，我们考虑了本体的所有注释属性，但`rdfs:label`除外。`rdfs:label`的注释在生成第二类单词句子时被忽略，因为它们已经在第一类单词句子中被考虑（例如，示例5）。更具体地说，对于每个注释公理，`OWL2Vec*`在转换`IRI`语句时用其英文标签或`IRI`名称替换主题实体，并保留从注释值解析的**<u>小写单词</u>**标记。表2中的Ex7是此类单词句子的一个示例，它基于`obo:IAO_0000115 (definition)` 对类`obo:FOODON_00002809 (edamame)`的注释。这将使该模型能够学习“毛豆”与相关背景中的其他词（如“大豆”和“豆荚”）的相关性。

> The second kind of word sentences are extracted from the textual annotations. They include two kinds: annotations by bespoke annotation properties such as obo:IAO_0000115 (definition), obo:IAO_0010000 (has axiom label) and oboInOwl:hasSynonym, and annotations by built-in annotation properties such as rdfs:comment and rdfs:seeAlso. In our current OWL2Vec* implementation, we consider all the annotation properties of an ontology  except for rdfs:label. The annotations by rdfs:label are ignored in generating word sentences of the second kind because they are already considered in the word sentences of the first kind (e.g., Ex5). More specifically, for each annotation axiom, OWL2Vec* replaces the subject entity by its English label or IRI name as in transforming the IRI sentence, and keeps the lowercase word tokens parsed from the annotation value. One example of such word sentence is Ex7 in Table 2 which is based on the annotation by obo:IAO_0000115 (definition) to the class obo:FOODON_00002809 (edamame). It would enable the model to learn the correlation of “edamame” to other words in the relevant background such as “soybean” and “pods”.  



## 3.4 Combined document



`OWL2Vec*`进一步从结构文档和实体注释中提取组合文档，以保持词汇信息中实体`（IRI）`和单词之间的相关性。为此，我们开发了两种策略来处理结构文档中的每个`IRI`语句。一种策略是在`IRI`语句中随机选择一个实体，保留该实体的`IRI`，并用从其标签或`IRI`名称中提取的小写单词标记替换该语句中的其他实体，就像在创建词汇文档时一样。一个例子是表2中的Ex8，其中Ex1的`IRI`语句的`vc:FOOD-4001 (blonde beer)` 的`IRI`被保留，而其他`IRI`被其对应的词替换。另一种策略是遍历`IRI`语句中的所有实体。对于每个实体，它通过保留该实体的`IRI`，并用其小写单词标记替换其他实体，生成一个组合语句，如随机策略中所示。因此，对于一个`IRI`语句，它生成m个组合语句，其中m是`IRI`语句的实体数。表2中的示例9是基于第二种策略的组合语句的示例，而不是示例1中的IRI语句。

> OWL2Vec* further extracts a combined document from the structure document and the entity annotations, so as to preserve the correlation between entities (IRIs) and words in the lexical information. To this end, we developed two strategies to deal with each IRI sentence in the structure document. One strategy is to randomly select an entity in an IRI
> sentence, keep the IRI of this entity, and replace the other entities of this sentence by their lowercase word tokens extracted from their labels or IRI names as in the creation of the lexical document. One example is Ex8 in Table 2, where the IRI of vc:FOOD-4001 (blonde beer) of the IRI sentence of Ex1 is kept while the other IRIs are replaced by their corresponding words. The other strategy is traversing all the entities in a IRI sentence. For each entity, it generates a combined sentence by keeping the IRI of this entity, and replacing the others by their lowercase word tokens as in the random strategy. Thus for one IRI sentence, it generates m combined sentences where m is the number of entities of the IRI sentence. Ex9 in Table 2 is an example of the combined sentences based on the second strategy over the IRI sentence in Ex1.  



合并的文档旨在捕获`IRIs`和单词之间的相关性，例如`vc:FOOD-4001 (blonde beer) and “nutrient” in Ex7`。

- 一方面，这将有助于`IRIs`与单词语义的向量化。这在只有`IRI`向量可用的某些情况下特别有用。例如，一些实体既没有英文标签也没有有意义的`IRI`名称，只有`IRI`向量可以用于这些实体。
- 另一方面，与`IRIs`的关联会将图形结构的一些语义合并到单词的向量化中。同样，在某些上下文中，只分析单词。一个例子是当`OWL2Vec*`用作本体（领域）定制的词向量模型，用于对该特定领域的外部文本进行分类时。同时，这也可能会增加单词之间的相关性 `(e.g., vc:hasNutrient between “beer” and “vitamin” in Ex9)` ，并对单词的向量化产生负面影响。我们在评估中分析了合并文件及其两种策略的影响。

> The combined document aims at capturing the correlation between IRIs and words, such as vc:FOOD-4001 (blonde beer) and “nutrient” in Ex7. On the one hand this would benefit the embeddings of the IRIs with the semantics of words. This is especially useful in some contexts where only IRI vectors are available. For example, some entities have neither English labels or meaningful IRI name, and only IRI vectors can be used for these entities. On the other hand, the association with IRIs would incorporate some semantics of the graph structure into the words’ embeddings. Again there are some contexts where only words are analyzed. One example is when OWL2Vec* is used as an ontology (domain) tailored word embedding model for the classification of external text of this specific domain. Meanwhile, this may also add noise to the correlation between words (e.g., vc:hasNutrient between “beer” and “vitamin” in Ex9) and negatively impact the words’ embeddings. The impact of the combined document and its two strategies is analyzed in our evaluation (cf. Sect. 4.3.1). 



## 3.5 Embeddings



`OWL2Vec*`首先将结构文档、词法文档和组合文档合并为一个文档，然后使用该文档训练具有skip-gram架构的`Word2Vec`模型。当损失趋于稳定时，训练结束。单词的最小计数的超参数被设置为1，使得每个单词或实体`（IRI）`只要在文档中至少出现一次就被编码。特别是，我们可以通过大型通用语料库（如大量维基百科文章）对`Word2Vec`模型进行预训练。这带来了词之间的一些先验相关性，特别是词的同义词之间和词的变体之间的相关性，这使得下游机器学习任务能够识别它们在语料库中的语义平等性或相似性。然而，这种先验相关性也可能是有噪声的，并在特定领域的任务中起到负面作用（参见第4.3.4节中的评估）。请注意，选择`Word2Vec`是因为它是使用最广泛的单词嵌入算法之一。它已经成功地应用于KG嵌入，与随机行走相结合；一个典型的例子是`RDF2Vec (Ristoski and Paulheim 2016; Ristoski et al., 2019).`。通过采用成熟的嵌入技术，在本研究中，我们可以通过开发合适的语料库提取方法，将语义嵌入从KG扩展到表达更广泛语义的本体。`OWL2Vec*`与`Word2Vec`不耦合，因此与其他单词嵌入或序列特征学习方法兼容，如上下文模型BERT，根据最近的一些研究 `(Miaschi and Dell’Orletta 2020)`，该模型已显示出其优越性。我们将选择、评估甚至开发更合适的语言嵌入模型留给我们未来的工作。

> OWL2Vec\* first merges the structure document, the lexical document and the combined document as one document, and then uses this document to train a Word2Vec model with the skip-gram architecture. The training is ended when the loss trends to be stable. The hyper-parameter of the minimum count of words is set to 1 such that each word or entity (IRI) is encoded as long as it appears in the documents at least once. Specially, we can pre-train the Word2Vec model by a large and general corpus such as a dump of Wikipedia articles. This brings some prior correlations between words, especially between a word’s  synonyms and between a word’s variants, which enables the downstream machine learning tasks to identify their semantic equality or similarity w.r.t. the corpus. However, such prior correlations may also be noisy and play a negative role in a domain specific task (cf. the evaluation in Sect. 4.3.4). Note that Word2Vec has been selected because it is one of the most widely used word embedding algorithms. It has already been successfully applied in KG embedding in a combination with random walk; one typical example is RDF2Vec (Ristoski and Paulheim 2016; Ristoski et al., 2019). With the adoption of a mature embedding technique, in this study we can focus on extending semantic embedding from a KG to an ontology which expresses a much wider range of semantics, by developing suitable corpus extraction methods. OWL2Vec* is uncoupled to Word2Vec, and is thus compatible with other word embedding or sequence feature learning methods such as the contextual
> model BERT which has shown its superiority according to some recent studies (Miaschi and Dell’Orletta 2020). We leave the selection, evaluation or even development of more suitable language embedding models to our future work.  



通过训练的单词嵌入模型，`OWL2Vec*`计算每个目标实体e在E中的向量化。其嵌入e是`Viri（e）`和`Vword（e）`的级联，其中`Viri（e）`是e的`IRI`的向量，`Vword`是`e`的所有小写单词标记的向量的汇总。在我们的评估中，我们简单地采用`Vword`的平均算子，这对于不同的数据和任务通常非常有效。由于不同单词向量的预测信息位于不同的维度，平均操作不会导致预测信息的丢失，尤其是当分类器在向量化之后进一步堆叠用于下游应用时（参见第3.6节）。注：还可以考虑一些更复杂的加权策略，例如使用`TF-IDF`（术语频率-逆文档频率）`（Rajaraman和Ullman，2011）`来计算每个单词的重要性`(cf. Arora et al. (2019)`。与从`IRI`语句构造词汇语句的情况一样，如果存在这样的标签，则从其英文标签中提取e的单词标记，否则从其`IRI`名称中提取e。由于级联，e的嵌入大小，即d是原始嵌入大小的两倍。`Viri（e）`和`Vword（e）`也可以独立使用。有关它们的性能比较，请参见第节。4.3.1.

> With the trained word embedding model, OWL2Vec* calculates the embedding of each target entity e in E. Its embedding e is the concatenation of Viri(e) and Vword(e), where Viri(e) is the vector of the IRI of e, and Vword(e) is some summarization of the vectors of all the lowercase word tokens of e. In our evaluation we simply adopt the averaging operator for Vword(e), which usually works quite well for different data and tasks. As predictive information of different words’ embeddings lie in different dimensions, the averaging operation would not lead to a loss of predictive information, especially when a classifier is further stacked after the embeddings for downstream applications (cf. Sect. 3.6). Note some more complicated weighting strategies such as using TF-IDF (term frequency–inverse document frequency) (Rajaraman and Ullman, 2011) to calculate the importance of each token can also be considered (cf. Arora et al. (2019) for more methods). As in the case of constructing lexical sentences from IRI sentences, the word tokens of e are extracted from its English label if such a label exists, or from its IRI name otherwise. Due to the concatenation, the embedding size of e, i.e., d, is twice the original embedding size. Viri(e) and Vword(e) can also be independently used. A comparison of their performance can be found in Sect. 4.3.1.



## 3.6 Case studies 案例分析



我们将`OWL2Vec*`应用于本体完成，该方法首先从已知关系（公理）训练预测模型，然后预测那些似是而非的关系。它包括两个任务：***<u>类成员预测和类包容预测</u>***，其中实体的嵌入可以理解为在没有任何监督的情况下从其邻域、相关公理和词汇信息中自动学习的特征。在本小节的其余部分中，我们首先介绍了成员情况下的预测任务细节，然后介绍了与包含情况下的差异。

> We applied OWL2Vec* in ontology completion which first trains a prediction model from known relations (axioms) and then predicts those plausible relations.8 It includes two tasks: class membership prediction and class subsumption prediction, where the embedding of an entity can be understood as the features automatically learned from its neighbourhood, relevant axioms and lexical information without any supervision. In the remainder of this sub-section we first introduce the prediction task details with the membership case and then present the difference with respect to the subsumption case.  



给定头部实体`e1`和尾部实体`e2`，其中`e1`是实例，`e2`是类，隶属度预测任务旨在训练模型以预测`e1`是`e2`的成员的似然性（即，`e2（e1）`）。输入是`e1`和`e2`的向量的串联，即`x=[e1，e2]`，而输出是[0，1]中的分数y，其中较高的y表示更高的相关度。对于预测模型，可以采用一些（非线性）二元分类器，如随机森林（`RF`）和多层感知（`MLP`）（参见第4.4节中的分类器评估）。

> Given a head entity e1 and a tail entity e2, where e1 is an instance and e2 is a class, the membership prediction task aims at training a model to predict the plausibility that e1 is a member of e2 (i.e., e2(e1)). The input is the concatenation of the embeddings of e1 and e2, i.e., x = [e1, e2], while the output is a score y in [0, 1], where a higher y indicates a more plausible membership relation.  For the prediction model, some (non-linear) binary classifiers such as Random Forest (RF) and Multi-Layer Perception (MLP) can be adopted (cf. the evaluation of classifiers in Sect. 4.4).  



在训练中，正训练样本是那些声明的成员公理。它们直接从本体中提取。而通过破坏每个正样本来构造负样本。即，对于每个正样本（e1，e2），一个负样本（e1, e'2） 其中e′2是本体的一个随机类，而e1即使在蕴涵推理之后也不是e′2中的成员。在预测中，给定一个头部实体（即目标），选择一组候选类（例如，除了顶级类owl:Thing或通过一些启发式规则过滤后的子集之外的所有类），每个候选都由训练分类器用归一化分数进行预测，该分数指示候选的正确程度，然后根据他们的分数对候选人进行排名，其中排名靠前的是实例中最有可能的类别。类包容预测类似于类隶属度预测，不同之处在于e1和e2都是类，目标是预测e1是否被e2包容（即e1⊑ e2），并且头实体e1本身被从候选类中排除。

> In training, positive training samples are those declared membership axioms. They are directly extracted from the ontology. While negative samples are constructed by corrupting each positive sample. Namely, for each positive sample (e1, e2), one negative sample(e1, e'2) is generated, where e′ 2 is a random class of the ontology and e1 is not a member of e′ 2 even after entailment reasoning. In prediction, given a head entity (i.e., the target), a candidate set of classes are selected (e.g., all the classes except for the top class owl:Thing, or a subset after filtering via some heuristic rules), each candidate is predicted with a normalized score by the trained classifier, which indicates the degree of the candidate to be correct, and the candidates are then ranked according to their scores where the top is the most likely class of the instance. Class subsumption prediction is similar to class membership prediction, except that e1 and e2 are both classes, the goal is to predict whether e1 is subsumed by e2 (i.e., e1 ⊑ e2), and the head entity e1 itself is excluded from the candidate classes.  



# 4 Evaluation



