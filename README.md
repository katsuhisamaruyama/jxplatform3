# JxPlatform3 (JxPlatform v3)

JxPlatform3 is a tool platform that provides three types of easy-to-use models of Java source code. 
It facilitates the development and maintenance of various kinds of software tools. 

See [API reference](https://katsuhisamaruyama.github.io/jxplatform3/) when you write code with JxPlatform3.

### Source Code Model 

JxPlatform3 builds a Java source code model consisting of the following elements: 

* JavaProject - Provides information on project resources such as Java source files, packages, and classes 
* JavaFile - Provides information on a Java source file 
* JavaPackage - Provides information on a package 
* JavaClass - Provides information on a class, an interface, an enum, or an enum constant 
* JavaMethod - Provides information on a method, a constructor, or an initializer within a class
* JavaVariable - Provides information on a variable
* JavaField - Provides information on a field within a class
* JavaLocalVar - Provides information on a local variable within a method or a parameter in a method call

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

* JDK 11 
* [Eclipse](https://www.eclipse.org/) 2022-09 (4.25.0) and later 


## License 

[Eclipse Public License 1.0 (EPL-1.0)](<https://opensource.org/licenses/eclipse-1.0.php>) 

## Installation

You can download the latest jar file (`jxplatform-3.x.x.jar`) 
from the [Releases](https://github.com/katsuhisamaruyama/jxplatform3/releases/latest) page.

Alternatively, you can build jar files with the Gradle on your own environment. 

```
git clone https://github.com/katsuhisamaruyama/jxplatform3/
cd jxplatform3/org.jtool.jxplatform
./gradlew jar shadowJar
```
The jar file (`jxplatform-3-SNAPSHOT.jar`) exists in the 'build/libs' directory. 

### As a Library

You can deploy `jxplatform-X.jar` in the directory for library files (e.g., `libs`), 
and specify the directory as the build path and the runtime classpath under your environment.
When using the Eclipse, see the "Build Path" settings of a project.

### As an Eclipse plug-in

The plug-in implementation has not been tested yet.
Please look forward to a release of the tested version.

## Usage

JxPlatform3 provides two types of model builders as follows:

* ModelBuilderBatch - Batch processing builder 
* IncrementalModelBuilder - Incrementally processing builder 

### Building a source code model under batch-processing applications

The code snippet building a source code model is describe below. 

```
// import org.jtool.jxplatform.builder.ModelBuilderBatch;
// import org.jtool.srcmodel.JavaProject;
// import org.jtool.srcmodel.JavaFile;
// import org.jtool.srcmodel.JavaClass;
// import org.jtool.srcmodel.JavaMethod;
// import org.jtool.srcmodel.JavaField;

String name;  // an arbitrary project name
String path;  // the path of the root directory
              // that contains Java source files in a target project

ModelBuilderBatch builder = new ModelBuilderBatch();
builder.analyzeBytecode(true);
builder.useCache(true);
builder.setConsoleVisible(true);

List<JavaProject> jprojects = builder.build(name, path);
// If a project has a single module,
// the resulting list has only one object corresponding to the target project 
// If a project has multiple modules,
// objects stored in the list correspond to the underlying modules 

List<JavaFile> files = jprojects.stream()
                                .flatMap(p -> p.getFiles().stream())
                                .collect(Collectors.toList());
List<JavaClass> classes = jprojects.stream()
                                   .flatMap(p -> p.getClasses().stream())
                                   .collect(Collectors.toList());
List<JavaMethod> methods = classes.stream()
                                  .flatMap(p -> p.getMethods().stream())
                                  .collect(Collectors.toList());
List<JavaField> fields = classes.stream()
                                .flatMap(p -> p.getFields().stream())
                                .collect(Collectors.toList());

builder.unbuild();
```

### Building a source code model under incrementally-processing applications

The code snippet incrementally building a source code model is describe below. 

```
// import org.jtool.jxplatform.builder.IncrementalModelBuilder;
// import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
// import org.jtool.jxplatform.project.ModelBuilderImpl;

String name;       // an arbitrary project name
String path;       // the path of the root directory for a target project 
String classpath;  // the path of the directory that contains class and/or jar files

ModelBuilderImpl builderImpl = new ModelBuilderBatchImpl();
builderImpl.analyzeBytecode(true);
builderImpl.useCache(true);
builderImpl.setConsoleVisible(true);

IncrementalModelBuilder builder = new IncrementalModelBuilder(builderImpl);
JavaProject jproject = builder.build(name, path, classpath);
// One builder monitors just one project

// Notifies the addition of a Java file
builder.addFile(path + File.separator + "Added.java");  

// Re-builds the source code model from the added file and its related ones
builder.incrementalBuild();

// Notifies the deletion and update of Java files
builder.removeFile(path + File.separator + "Deleled.java");
builder.updateFile(path + File.separator + "Updated.java");

// Re-builds the source code model from the deleted and updated files and their related ones
builder.incrementalBuild();

builder.unbuild();
```

### Creating CFGs

The following code snippet builds CCFGs for all classes and CFGs for all methods and fields within a project.

```
// import org.jtool.cfg.CCFG;
// import org.jtool.cfg.CFG;

ModelBuilderBatch builder = new ModelBuilderBatch();
List<JavaProject> jprojects = builder.build(name, path);
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

```
JavaMethod jmethod;
JavaField jfield;
boolean force;       // whether the analyzer forces to create a CFG or allows to reuse it 
CFG cfg;
cfg = builder.getCFG(jmethod, force);
cfg = builder.getCFG(jmethod);  // force:false
cfg = builder.getCFG(jfield, force);
cfg = builder.getCFG(jfield);  // force:false
```

A call graph can be created within a project as described below.

```
JavaProject jproject;
CallGraph callGraph = build.getCallGraph(jproject);
```

### Creating PDGs

The following code builds ClDGs for all classes and PDGs for all methods and fields within a project.

```
// import org.jtool.pdg.ClDG;
// import org.jtool.pdg.PDG;

ModelBuilderBatch builder = new ModelBuilderBatch();
List<JavaProject> jprojects = builder.build(name, path);
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

```
ModelBuilder builder;
boolean force;       // whether the analyzer forces to create a CFG or allows to reuse it 
boolean whole;       // whether a dependency graph will be created with the whole information 
                     // related to calls to methods and accesses to fields of outside classes
JavaProject jproject;
JavaMethod jmethod;
JavaField jfield;

CFG cfg;
PDG pdg;
pdg = builder.getPDG(jproject, cfg, force, whole);
pdg = builder.getPDG(jproject, cfg); // force:false, whole:true

pdg = builder.getPDG(jmethod, force, whole);
pdg = builder.getPDG(jmethod);  // force:false, whole:true
 
pdg = builder.getPDG(jfield, force, whole);
pdg = builder.getPDG(jfield);  // force:false, whole:true

CCFG ccfg;
ClDG cldg;
cldg = builder.getClDG(jproject, ccfg, force, whole);
cldg = builder.getClDG(jproject, ccfg); // force:false, whole:true

cldg = builder.getClDG(jclass, force, whole);
cldg = builder.getClDG(jclass);  // force:false, whole:true

SDG sdg;
sdg = builder.getSDG(jproject, force);
sdg = builder.getSDG(jproject); // force:false
```

The `DependencyGraph` class is used to obtain sub-graphs of the SDG, consisting of ClDGs created from specific classes. 

```
DependencyGraph graph;
graph = builder.getDependencyGraph(jclass, force, whole);
graph = builder.getDependencyGraph(jclass); // force: false, whole: true

graph = builder.getDependencyGraph(classes, force, whole);  // classes: Set<JavaClass>
graph = builder.getDependencyGraph(classes);  // force:false, whole:true
```

### Extracting program slices

A program slice can be created from an object of PDG as described below.

```
// import org.jtool.eclipse.pdg.PDGNode;
// import org.jtool.eclipse.cfg.JVariableReference;
// import org.jtool.eclipse.slice.Slice;
// import org.jtool.eclipse.slice.SliceCriterion;

JavaClass jclass;        // a class to be sliced
PDGNode node;            // a node given as a slice criterion
JVariableReference var;  // a variable of interest given as a slice criterion

DependencyGraph graph = builder.getDependencyGraph(jclass);
SliceCriterion criterion = new SliceCriterion(graph, node, var);
Slice slice = new Slice(criterion);
slice.print();
```

A convenient static method is also provided.

```
DependencyGraph graph;  // a dependency graph consisting of ClDGs, 
                        // which is used when extracting a slice
JavaFile jfile;         // a file including a class to be sliced
JavaClass jclass;       // a class to be sliced
JavaMethod jmethod;     // a method to be sliced
JavaField jfield;       // a field to be sliced
int lineNumber:         // the line number corresponding to a variable of interest
int columnNumber:       // column number corresponding to the variable on the line

SliceCriterion criterion;
criterion = SliceCriterion.find(graph, jfile, lineNumber, columnNumber);
criterion = SliceCriterion.find(graph, jclass, lineNumber, columnNumber);
criterion = SliceCriterion.find(graph, jmethod, lineNumber, columnNumber);
criterion = SliceCriterion.find(graph, jfield, lineNumber, columnNumber);
```

The following code snippet generates source code from a program slice.

```
JavaClass jclass;    // a class to be sliced
JavaMethod jmethod;  // a method to be sliced
JavaField jfield;    // a field to be sliced

String code;
code = slice.getCode(jclass);
code = slice.getCode(jmethod);
code = slice.getCode(jfield);
```

## History

* [JxPlatform](https://github.com/katsuhisamaruyama/jxplatform) Apr 8, 2017 
* [JxPlatfrom2](https://github.com/katsuhisamaruyama/jxplatform2) Nov 21, 2019 

## Author

[Katsuhisa Maruyama](https://www.fse.cs.ritsumei.ac.jp/~maru/index.html)
@Ritsumeikan Univ.
