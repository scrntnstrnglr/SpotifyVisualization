# Visualization of Last Decade Spotify Data

Almost everyone is familiar with Spotify, a widely using online music streaming application. Any song from any corner of the globe can be found in spotify. Spotify not only serves as an entertainment application, but also as an important data hub for all of its music. Appropos to this, Spotify quantifies all of its music with certain quintessential attributes that are relevant to any song. Some of them are:
1. Valence : The level of positivity/negativity of a song.
2. BPM : Beats per minute or the tempo of the song.
3. Danceability.
4. Energy.
5. Loudness : In terms of decibels.
6. Liveness.
7. Speech : Amount of words.
8. Duration.
9. Popularity.

![Main image](https://github.com/scrntnstrnglr/SpotifyVisualization/blob/master/readmeimages/mainImage.png)

In this visualization, I have attempted to create an interactive environment to analyze the relationship between these attributes, with a primary foucs on how all the attributes correlate to the valence of a song. In other words, trying to establish the level of dependence of the other attributes to the valence of a song. The visualization also serves other purposes. It is built on all the songs that Spotify has deemed popular in the last decade that is from 2010-2019. The visualization is divided into 3 separate interactive vies as following:
1. Main view: Is controlled with the mouse scroll. The top 3 songs and their attributes are displayed for the active year selected. The genres relevant for that year are also displayed.
2. Property to Song View: This view lets the user adjust attribute levels on the radial graph. The song with attributes closest to the combination of all attributes set in the radial graph is displayed. Along with the name of the song, the artist relevance throughout the decade is also plotted.
3. Song to Property View: This view lets the user adjust individual song attributes. The song attributes are placed on the Y-axis of the 2D-Slider. The X-Axis consists the years. The song with the closes attribute value is picked and displayed along with it's attributes.

A guide to each view is given within the application by clicking the guide button on the top right corner.


### System Requirements:
1. You should have java jdk version 1.8 and above. 
2. To check if java is enable in your system, open a terminal (Linux), command prompt (Windows) and run the following command:

```
java -version
```
3. If your Java version is below 1.8, please update using [install java](https://java.com/en/download/manual.jsp) or the latest version is also available in this repo [here](https://github.com/scrntnstrnglr/SpotifyVisualization/tree/master/install)
4. A stable internt connection as the application includes certain http requests to fetch image files.

### How to run:

Clone the repository, or download the repository 

1. Clone repository:
```
git clone https://github.com/scrntnstrnglr/SpotifyVisualization
```
2. Double click the runME.bat file on Windows, or the runME.sh file on Linux to launch the application.
4. It is imperative that all files and folders remain untouched for proper functioning of the application as they contain all related dependencies.

A log file is generated to keep track of errors and debug lines.

### Repository Contents and Structure:
The folders and files contained in the repository are explained as below:
1. Scripts - contains the python script for preprocessing.
2. bin - contains the java binary class files.
3. data - contains the original and processed data set csv files.
4. img - the image folder containing all images being used by the application.
5. install - contains jdk 8 installer.
6. lib - all dependency jar files.
7. natives - processing native dll files for all operating systems.
8. porperties - property files being used for logging and help-mode view.
9. readmeimages - contains HD thumbnail image of visualization.
10. report - contains updated technical paper for the visualization.
11. src - contains source code.
12. build.xml - for building executable jar file for the visualization
13. runME.bat - batch file for launching application in windows.
14. runME.sh - shell script file for launching application in Linux.