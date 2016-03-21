## Automated Image Processing System
###### Group repository for CSC3030 assignment :+1:

#### To use in intellij:
* Open -> Select the pom.xml file in the base directory and let the project index itself.
* Select the maven projects pane -> CSC3030 -> Lifecycle -> install
  * This will download the projects dependencies, compile, run unit tests, and package the application.
  * The main class can then be ran:
  ```
  ui/src/main/java/ui/App.java
  ```

#### Tutorial
* Press *"Choose training image(s)"* to select the directory of images to train the system
![Image1](tutorial/directory_selection.PNG "Training directory selection")

* Press *"Train System"* to train the system
![Image2](tutorial/training.PNG "Training")

* Move to the classification tab and press *"Choose test image(s)"* to select the directory of images to test the system
![Image3](tutorial/directory_selection2.PNG "Test directory selection")

* Press *"Classify"* to test the system
![Image4](tutorial/classification.PNG "Classification")

* Press *"Histogram Mode"* to activate histogram mode
![Image5](tutorial/histogram_mode.PNG "Histogram mode")

* To analyse the results, use the correct / incorrect buttons and press *"Analyse"* to get recognition rate
![Image6](tutorial/analysis.PNG "Recognition Rate")

* To save the result press *"Store Result"* and navigate to the results tab
![Image7](tutorial/results.PNG "Results")