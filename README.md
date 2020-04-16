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

In this visualization, I have attempted to create an interactive environment to analyze the relationship between these attributes, with a primary foucs on how all the attributes correlate to the valence of a song. In other words, trying to establish the level of dependence of the other attributes to the valence of a song. The visualization also serves other purposes. It is built on all the songs that Spotify has deemed popular in the last decade that is from 2010-2019. A guide to each view is provided within the application by clicking the tour button.


# System Requirements:
1. You should have java jdk version 1.8 and above. 
2. To check if java is enable in your system, open a terminal (Linux), command prompt (Windows) and run the following command:

```
java -version
```
3. If a definite java version is visible, you're good to go.
4. Else, [install java](https://java.com/en/download/manual.jsp) 

# How to run:

The project inlucdes a launcher application/jar  file which initiates the visualizer. The data has been preloaded. Clone the repository, or just download the 'target' folder.

1. Clone repository:
```
git clone https://github.com/scrntnstrnglr/SpotifyVisualization
```
2. Go to the target foloder.
2. Double click "SPOTIFY.jar" file to run.

Alternatively

3. Open a terminal/cmd and execute the following after navigating to the folder containing the executable jar.
```
java -jar SPOTIFY.JAR
```

A log file is generated to keep track of errors and debug lines.