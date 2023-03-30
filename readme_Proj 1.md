{\rtf1\ansi\ansicpg1252\cocoartf2639
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 SmartHome Gesture Detection \
Nikhil Kumar\
\
NOTE: This Project uses Flask to send images to a server. As is, the server is set to use the Local Host where the App is being run from. To set to a different URL please go to the \'93RecordVidActivity.java\'94 class and set the \'93baseURL\'94 attribute of the Retrofit object to your specific URL where you have set up a Flask server.\
\
So instead of \'93Retrofit retrofit = new Retrofit.Builder().baseUrl(\'93http://localhost:5000\'94)\'85\'94\
You\'92d have    \'93Retrofit retrofit = new Retrofit.Builder().baseUrl(\'93[INPUT IP ADDRESS HERE]:5000\'94)\'85\'94}