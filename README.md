# Gesture-Detection-App
Android App that uses Neural Networks to Classify Images of Gestures taken by Smartphone Camera - Nikhil Kumar

NOTE: This Project uses Flask to send images to a server. As is, the server is set to use the Local Host where the App is being run from. To set to a different URL please go to the 'RecordVidActivity.java' class and set the 'baseURL' attribute of the Retrofit object to your specific URL where you have set up a Flask server.

So instead of 'Retrofit retrofit = new Retrofit.Builder().baseUrl('http://localhost:5000\') -->

You'd have 'Retrofit retrofit = new Retrofit.Builder().baseUrl('[INPUT IP ADDRESS HERE]:5000')}
