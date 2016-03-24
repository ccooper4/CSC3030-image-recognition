## Automated Image Processing System
###### Group repository for CSC3030 assignment :+1:

### Table of Contents
[Instructions](#Instructions)

[Class breakdown](#hierarchy)

[Tutorial](#tutorial)

---
<a name="Instructions"/>

### Intellij:
* Open -> Select the pom.xml file in the base directory and let the project index itself.
* Select the maven projects pane -> CSC3030 -> Lifecycle -> install
  * This will download the projects dependencies, compile, run unit tests, and package the application.
  * The main class can then be ran:
```
ui/src/main/java/ui/App.java
```

### Eclipse
* Import -> Maven -> Existing maven project -> Select the project folder and let the project index itself.
* Dependencies should download automatically.
* If they do not, right click the root module -> Maven -> Update maven
* The main class can then be ran:
```
ui/src/main/java/ui/App.java
```

### Executable jar:
* There is a jar file in the root of the project; "CSC3030.jar". This is the final build of the project.
* It can be ran via the ui by double clicking or from command line using:
```
java -jar CSC3030.jar
```
---

<a name="hierarchy"/>

### Class Hierarchy
#### Modules
![modules](resources/diagrams/Modules.PNG?raw=true "Modules")
#### UI Module
![ui](resources/diagrams/UI.PNG?raw=true "UI")
#### Shared Module
![shared](resources/diagrams/Shared.PNG?raw=true "Shared")
#### Pipeline Module
![pipeline](resources/diagrams/Pipeline.PNG?raw=true "Pipeline")
#### Project
![project](resources/diagrams/Project.png?raw=true "Project")

---

<a name="tutorial"/>

### Tutorial
* Press *"Choose training image(s)"* to select the directory of images to train the system
* The original and extended sets of training images can be found in CSC3030\shared\src\main\resources\assignment\training
![Image1](resources/tutorial/directory_selection.PNG?raw=true "Training directory selection")

* Press *"Train System"* to train the system
![Image2](resources/tutorial/training.PNG?raw=true "Training")

* Move to the classification tab and press *"Choose test image(s)"* to select the directory of images to test the system
* The original and extended sets of test images can be found in CSC3030\shared\src\main\resources\assignment\test
![Image3](resources/tutorial/directory_selection2.PNG?raw=true "Test directory selection")

* Press *"Classify"* to test the system
![Image4](resources/tutorial/classification.PNG?raw=true "Classification")

* Press *"Histogram Mode"* to activate histogram mode
![Image5](resources/tutorial/histogram_mode.PNG?raw=true "Histogram mode")

* To analyse the results, use the correct / incorrect buttons and press *"Analyse"* to get recognition rate
![Image6](resources/tutorial/analysis.PNG "Recognition Rate")

* To save the result press *"Store Result"* and navigate to the results tab
![Image7](resources/tutorial/results.PNG?raw=true "Results")