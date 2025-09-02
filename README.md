# JxPlatform3 (JxPlatform v3)

JxPlatform3 is a tool platform that provides three types of easy-to-use models of Java source code. 
It facilitates the development and maintenance of various kinds of software tools. 

See [API reference](https://katsuhisamaruyama.github.io/jxplatform3/) when you write code with JxPlatform3.

### Source Code Model 

JxPlatform3 builds a Java source code model consisting of the following elements: 

* JavaProject - Provides information on project resources such as Java source files, packages, and classes 
* JavaFile - Provides information on a Java source file 
* JavaPackage - Provides information on a package 
* JavaClass - Provides information on a class, an interface, or an enum
* JavaMethod - Provides information on a method, a constructor, or an initializer within a class
* JavaField - Provides information on a field or an enum constant within a class
* JavaLocalVar - Provides information on a local or a parameter variable within a method

### CFG (Control Flow Graph) 

JxPlatform3 provides a CFG for each method/field existing in Java source code. 

* CFG - Provides information on a CFG of a method declaration or a field declaration (corresponding to JavaMethod or JavaField)
* CCFG - Provides information on a class control flow graph (CCFG) of a class declaration (corresponding to JavaClass)
* BasicBlock - Provides information on a basic block of a CFG 
* CallGraph - Provides information on a call graph

Each CFG consists of nodes and edges between two nodes. 

* CFGNode - Represents a node of a CFG, which is a parent of all CFG nodes 
* CFGEntry - Represents the entry of a CCF 
* CFGMethodEntry- Represents the entry of a CFG for a method declaration or a constructor declaration
* CFGFieldEntry - Represents the entry of a CFG for a field declaration or an enum-constant declaration
* CCFGEntry - Represents the entry of a CCFG 
* CFGExit - Represents the exit of a CFG 
* CFGMerge - Represents a node where multiple control flows merge
* CFGStatement - Represents a statement or an expression, which has defined and/or used variables 
* CFGMethodCall - Represents a method call
* CFGParameter - Represents a parameter for the method call
* CFGReceiver - Represents a receiver for the method call
* CFGException - Represents an exception 
* ControlFlow - Represents control flow between CFG nodes 

Each CFGStatement node holds a define-set and a use-set of references to fields, local variables, and method calls. 
The define-set contains fields and local variables defined in an expression corresponding to the node. 
The use-set contains fields and local variables used in the expression and method calls performed. 

* JReference - Represents a reference to a called method or an accessed variable 
* JMethodReference - Represents a reference to a called method or a called constructor 
* JVariableReference - Represents a reference to an accessed variable 
* JFieldReference - Represents a reference to an accessed field 
* JUncoveredFieldReference - Represents a reference to an accessed field in a called method 
* JLocalVarReference - Represents a reference to an accessed local variable 

### PDG (Program Dependence Graph) 

JxPlatform3 provides a PDG from a CFG for each method/field existing in Java source code. 

* PDG - Provides information on a PDG of a method or a field 
* ClDG - Provides information on a class dependence graph (ClDG) of a class 
* DependencyGraph - Stores information on a dependency graph consisting of ClDGs.
* SDG - Provides information about a system dependence graph (SDG) the whole source code

Each PDG or ClDG consists of nodes and edges between two nodes. 

* PDGNode - Represents a node of a PDG, which is a parent of all PDG nodes. 
The PDG node and its corresponding CFG node have the same identifier 
* PDGEntry -  Represents the entry of a PDG 
* ClDGEntry - Represents the entry of a ClDG 
* PDGStatement - Represents a statement or an expression, which has defined and/or used variables 
* DependencyGraphEdge - Represents a dependence edge appearing in PDGs, ClDGs, and SDGs 
* Dependence - Represents a dependence edge appearing in PDGs 
* CD - Represents a control dependence between PDG nodes 
* DD - Represents a data dependence between PDG nodes 
* InterPDGEdge - Represents a dependence edge connecting nodes in different PDGs 
* InterPDGCD - Represents a control dependence edge connecting nodes in different PDGs 
* InterPDGDD - Represents a data dependence edge connecting nodes in different PDGs 

### Program slice 

JxPlatform3 provides a slice and its corresponding source code. 
Each slice consists PDG nodes that may affect the value of a variable of interest (called a slice criterion). 

* Slice - Stores information on a program slice 
* SliceCriterion - Represents a slicing criterion 

A slice is constructed based on flow-sensitive analysis. 
It traverses only the PDG nodes that reach a node given as a slice criterion. 

## Requirement

* JDK 17 
* [Eclipse](https://www.eclipse.org/) 2022-09 (4.25.0) and later 

## License 

[Eclipse Public License 2.0 (EPL-2.0)](<https://opensource.org/license/epl-2-0>) 

## Installation

You can download the latest jar file (`jxplatform-3.x.x.jar`) 
from the [Releases](https://github.com/katsuhisamaruyama/jxplatform3/releases/latest) page.

Alternatively, you can build a jar file with the Gradle on your own environment. 

```
git clone https://github.com/katsuhisamaruyama/jxplatform3/
cd jxplatform3/org.jtool.jxplatform
./gradlew jar shadowJar
```
The jar file (`jxplatform-3-SNAPSHOT.jar`) exists in the 'build/libs' directory. 
Please deploy it in the directory for library files (e.g., 'lib' or 'libs'), 
and specify the directory as the build path and the runtime classpath under your environment.
When using the Eclipse, see the "Build Path" settings of a project.

A easier way is to adopt Gradle or Maven. 
You can import a jar file from the maven repository.

For Gradle,
1. Please add the maven central repository in your `build.gradle` file.

```
repositories {
    mavenCentral()
    maven { url 'https://repo.gradle.org/gradle/libs-releases' }
}
```

2. You add the dependency as follows:

```
dependencies {
    ...
    implementation 'io.github.katsuhisamaruyama:org.jtool.jxplatform:3.x.x'
}
```

Please replace `3.x.x` with the latest version number (e.g., `3.0.28`).

If you write code using JxPlatform3 in Eclipse, the command execution of `./gradlew eclipse` helps your configuration.


For Maven,
1. Please add the maven repository in your `pom.xml` file.

```
<repositories>
    <repository>
      <id>central</id>
      <url>https://repo1.maven.org/maven2/</url>
      <releases>
        <enabled>true</enabled>
      </releases>
    </repository>
    <repository>
      <id>gradle</id>
      <url>https://repo.gradle.org/gradle/libs-releases/</url>
      <releases>
        <enabled>true</enabled>
      </releases>
    </repository>
  </repositories>
```

2. You add the dependency as follows:

```
<dependency>
    <groupId>io.github.katsuhisamaruyama</groupId>
    <artifactId>org.jtool.jxplatform</artifactId>
    <version>3.x.x</version>
</dependency>
```

Please replace `3.x.x` with the latest version number (e.g., `3.0.28`).

## Usage

### Creating a builder

Using JxPlatform3, you first create a model builder instance (as a facade) that builds source code models, CFGs, and PDGs.
The following two model builders are prepared.

* ModelBuilderBatch - Batch processing builder 
* IncrementalModelBuilder - Incrementally processing builder 

The both builder provides simple APIs described as follows:

```java
List<JavaProject> build(String name, String target)
JavaProject build(String name, String target, String classpath)
JavaProject build(String name, String target, String classpath, String srcpath, String binpath)
JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath)

  name - an arbitrary project name
  target - the path of the root directory for a target project
  classpath - the path(s) of the directory that contains class and/or jar files
  srcpath - the path(s) where the source files are located
  binpath - the path(s) where the binary files are located
```

### Building a source code model under batch-processing applications

You can use a convenient API provided by the ModelBuilderBatch class, without specifying classpath etc. 
This API automatically collects needed information from configuration files of Ant, Gradle, and Maven projects.

The code snippet building a source code model is describe below. 

```java
ModelBuilder builder = new ModelBuilderBatch();
builder.analyzeBytecode(true);    // whether analyzing bytecode files or not 
builder.useCache(true);           // whether using a cache of already analyzed data
builder.setConsoleVisible(true);  // whether displaying messages on console

List<JavaProject> jprojects = builder.build(name, target);
  // If a project has a single module,
  // the resulting list has only one object corresponding to the target project 
  // If a project has multiple modules,
  // objects stored in the list correspond to the underlying modules 

for (JavaProject jproject : jprojects) {
    List<JavaFile> files = jproject.getFiles();
    List<JavaClass> classes = jproject.getClasses();
    
    for (JavaClass jclass : classes) {
        JavaPackage jpackage = jclass.getPackage();
        List<JavaMethod> methods = jclass.getMethods();
        
        for (JavaMethod jmethod : methods) {
            List<JavaLocalVar> locals = jmethod.getLocalVariables();
            List<JavaLocalVar> params = jmethod.getParameters();
        }
        
        List<JavaField> fields = jclass.getFields();
    }
}

builder.unbuild();
```

### Building a source code model under incrementally-processing applications

The code snippet incrementally building a source code model is describe below. 

```java
ModelBuilder builder = new ModelBuilderBatch();
List<JavaProject> jprojects = builder.build(name, target);
IncrementalModelBuilder ibuilder = new IncrementalModelBuilder(builder.getModelBuilderImpl(), projects);

// Monitors just one project
JavaProject jproject = jprojects.get(0);
String path = jproject.getPath();

// Notifies the addition of a Java file
ibuilder.addFile(jproject, path + File.separator + "Added.java");

// Re-builds the source code model from the added file and its related ones
ibuilder.incrementalBuild();

List<JavaClass> classes = jproject.getClasses();

// Notifies the deletion and update of Java files
ibuilder.removeFile(jproject, path + File.separator + "Deleled.java");
ibuilder.updateFile(jproject, path + File.separator + "Updated.java");

// Re-builds the source code model from the deleted and updated files and their related ones
ibuilder.incrementalBuild();

List<JavaClass> classes = jproject.getClasses();

builder.unbuild();
```

### Creating CFGs

The following code snippet builds CCFGs for all classes and CFGs for all methods and fields within a project.

```java
ModelBuilder builder = new ModelBuilderBatch();
List<JavaProject> jprojects = builder.build(name, target);
List<JavaClass> classes = jprojects.stream()
                                   .flatMap(p -> p.getClasses().stream())
                                   .collect(Collectors.toList());

for (JavaClass jclass : classes) {
    CCFG ccfg = builder.getCCFG(jclass);
    for (CFG cfg : ccfg.getCFGs()) {
        cfg.print();
    }
}

builder.unbuild();
```

A CFG can be created from an object of JavaMethod or JavaField as described below.

```java
boolean force;  // whether forcibly creating a CFG or allowing to reuse it 

CFG cfg = builder.getCFG(jmethod, force);
CFG cfg = builder.getCFG(jmethod);  // force:false

CFG cfg = builder.getCFG(jfield, force);
CFG cfg = builder.getCFG(jfield);  // force:false
```

A call graph can be created within a project as described below.

```java
CallGraph callGraph = builder.getCallGraph(jproject);
```

### Creating PDGs

The following code builds ClDGs for all classes and PDGs for all methods and fields within a project.

```java
ModelBuilder builder = new ModelBuilderBatch();
List<JavaProject> jprojects = builder.build(name, target);
List<JavaClass> classes = jprojects.stream()
                                   .flatMap(p -> p.getClasses().stream())
                                   .collect(Collectors.toList());

for (JavaClass jclass : classes) {
    ClDG cldg = builder.getClDG(jclass);
        for (PDG pdg : cldg.getPDGs()) {
        pdg.print();
    }
}

builder.unbuild();
```

There are several ways to create PDGs, ClDGs, and SDGs. 

```java
CFG cfg;        // a CFG from which a PDG is created 
CCFG ccfg;      // a CCFG from which a ClDG is created 
boolean force;  // whether forcibly creating a PDG or allowing to reuse it 
boolean whole;  // whether a dependency graph will be created by using the whole information 
                //   related to calls to methods and accesses to fields of outside classes

PDG pdg = builder.getPDG(jproject, cfg, force, whole);
PDG pdg = builder.getPDG(jproject, cfg); // force:false, whole:true

PDG pdg = builder.getPDG(jmethod, force, whole);
PDG pdg = builder.getPDG(jmethod);  // force:false, whole:true
 
PDG pdg = builder.getPDG(jfield, force, whole);
PDG pdg = builder.getPDG(jfield);  // force:false, whole:true

ClDG cldg = builder.getClDG(jproject, ccfg, force, whole);
ClDG cldg = builder.getClDG(jproject, ccfg); // force:false, whole:true

ClDG cldg = builder.getClDG(jclass, force, whole);
ClDG cldg = builder.getClDG(jclass);  // force:false, whole:true

SDG sdg = builder.getSDG(jproject, force);
SDG sdg = builder.getSDG(jproject); // force:false
```

The `DependencyGraph` class is used to obtain sub-graphs of the SDG, consisting of ClDGs created from specific classes. 

```java
DependencyGraph graph = builder.getDependencyGraph(jclass, force, whole);
DependencyGraph graph = builder.getDependencyGraph(jclass); // force:false, whole:true

DependencyGraph graph = builder.getDependencyGraph(classes, force, whole);  // classes:Set<JavaClass>
DependencyGraph graph = builder.getDependencyGraph(classes);  // force:false, whole:true
```

### Extracting program slices

A program slice can be created from an object of PDG as described below.

```java
DependencyGraph graph;   // a dependency graph for a class to be sliced
PDGNode node;            // a node given as a slice criterion
JVariableReference var;  // a variable of interest given as a slice criterion

SliceCriterion criterion = new SliceCriterion(graph, node, var);
Slice slice = new Slice(criterion);
slice.print();
```

To find the slice criterion, convenient static methods are also provided.

```java
DependencyGraph graph;  // a dependency graph consisting of ClDGs, 
                        // which is used when extracting a slice
JavaFile jfile;         // a file including a class to be sliced
JavaClass jclass;       // a class to be sliced
JavaMethod jmethod;     // a method to be sliced
JavaField jfield;       // a field to be sliced
int lineNumber:         // the line number corresponding to a variable of interest
int columnNumber:       // column number corresponding to the variable on the line

SliceCriterion criterion = SliceCriterion.find(graph, jfile, lineNumber, columnNumber);
SliceCriterion criterion = SliceCriterion.find(graph, jclass, lineNumber, columnNumber);
SliceCriterioncriterion = SliceCriterion.find(graph, jmethod, lineNumber, columnNumber);
SliceCriterioncriterion = SliceCriterion.find(graph, jfield, lineNumber, columnNumber);
```

The following code snippet generates source code from a program slice.

```java
String code = slice.getCode(jclass);
String code = slice.getCode(jmethod);
String code = slice.getCode(jfield);
```

## History

* [JxPlatform](https://github.com/katsuhisamaruyama/jxplatform) Apr 8, 2017 
* [JxPlatfrom2](https://github.com/katsuhisamaruyama/jxplatform2) Nov 21, 2019 

## Author

[Katsuhisa Maruyama](https://www.fse.cs.ritsumei.ac.jp/~maru/index.html)
@Ritsumeikan Univ.
