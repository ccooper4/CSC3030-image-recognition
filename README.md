## Automated Image Processing System
###### Group repository for CSC3030 assignment :+1:

### Table of Contents
[Instructions](#Instructions)
[Class breakdown](#hierarchy)
[Tutorial](#tutorial)

---
<a name="Instructions"/>

### To use in intellij:
* Open -> Select the pom.xml file in the base directory and let the project index itself.
* Select the maven projects pane -> CSC3030 -> Lifecycle -> install
  * This will download the projects dependencies, compile, run unit tests, and package the application.
  * The main class can then be ran:
  ```
  ui/src/main/java/ui/App.java
  ```
---

<a name="hierarchy"/>

### Class Hierarchy
#### Modules
![modules](resources/diagrams/Modules.PNG)
#### UI Module
![ui](resources/diagrams/UI.PNG)
#### Shared Module
![shared](resources/diagrams/Shared.PNG)
#### Pipeline Module
![pipeline](resources/diagrams/Pipeline.PNG)
#### Project
![project](resources/diagrams/Project.PNG)

---

<a name="tutorial"/>

### Tutorial
* Press *"Choose training image(s)"* to select the directory of images to train the system
![Image1](resources/tutorial/directory_selection.PNG "Training directory selection")

* Press *"Train System"* to train the system
![Image2](resources/tutorial/training.PNG "Training")

* Move to the classification tab and press *"Choose test image(s)"* to select the directory of images to test the system
![Image3](resources/tutorial/directory_selection2.PNG "Test directory selection")

* Press *"Classify"* to test the system
![Image4](resources/tutorial/classification.PNG "Classification")

* Press *"Histogram Mode"* to activate histogram mode
![Image5](resources/tutorial/histogram_mode.PNG "Histogram mode")

* To analyse the results, use the correct / incorrect buttons and press *"Analyse"* to get recognition rate
![Image6](resources/tutorial/analysis.PNG "Recognition Rate")

* To save the result press *"Store Result"* and navigate to the results tab
![Image7](resourcestutorial/results.PNG "Results")