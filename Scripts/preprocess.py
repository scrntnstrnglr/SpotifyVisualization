# To add a new cell, type '# %%'
# To add a new markdown cell, type '# %% [markdown]'
# %%
import pandas as pd
import numpy as np
import json

# %%
df = pd.read_csv("C:\\Users\\SIDDHARTHA\\workspace_sid\\SpotifyVisualization\\data\\2010-2019.csv")
df['dB'] = abs(df['dB'])
f=open("C:\\Users\\SIDDHARTHA\\workspace_sid\\SpotifyVisualization\\properties\\maxminvalue.properties","a")
f.write("bpm_max="+str(max(df['bpm'])))
f.write("\nbpm_min="+str(min(df['bpm'])))
f.write("\nenergy_max="+str(max(df['nrgy'])))
f.write("\nenergy_min="+str(min(df['nrgy'])))
f.write("\ndance_max="+str(max(df['dnce'])))
f.write("\ndance_min="+str(min(df['dnce'])))
f.write("\nloudness_max="+str(max(df['dB'])))
f.write("\nloudness_min="+str(min(df['dB'])))
f.write("\nliveness_max="+str(max(df['live'])))
f.write("\nliveness_min="+str(min(df['live'])))
f.write("\nduration_max="+str(max(df['dur'])))
f.write("\nduration_min="+str(min(df['dur'])))
f.write("\nacoustic_max="+str(max(df['acous'])))
f.write("\nacoustic_min="+str(min(df['acous'])))
f.write("\nspeechness_max="+str(max(df['spch'])))
f.write("\nspeechness_min="+str(min(df['spch'])))

f.close()
df['bpm'] = (df['bpm']-min(df['bpm']))/(max(df['bpm'])-min(df['bpm']))
df['nrgy'] = (df['nrgy']-min(df['nrgy']))/(max(df['nrgy'])-min(df['nrgy']))
df['dnce'] = (df['dnce']-min(df['dnce']))/(max(df['dnce'])-min(df['dnce']))
df['dB'] = (df['dB']-min(df['dB']))/(max(df['dB'])-min(df['dB']))
df['live'] = (df['live']-min(df['live']))/(max(df['live'])-min(df['live']))
df['dur'] = (df['dur']-min(df['dur']))/(max(df['dur'])-min(df['dur']))
df['acous'] = (df['acous']-min(df['acous']))/(max(df['acous'])-min(df['acous']))
df['spch'] = (df['spch']-min(df['spch']))/(max(df['spch'])-min(df['spch']))
df['bpm'].round(decimals=3)
df
#df.to_csv('C:\\Users\\SIDDHARTHA\\workspace_sid\\SpotifyVisualization\\data\\2010-2019-normalised.csv',index=False)

