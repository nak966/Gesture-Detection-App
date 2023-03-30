package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ExampleVidActivity extends AppCompatActivity {
    private VideoView practiceVideoView;
    private Button practiceButton;
    private int numTimesVideoPlayed;
    private ImageButton imagePlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2_exvid_activity);

        Bundle bundle = getIntent().getExtras();
        String gestureVideoName= bundle.getString("Gesture");
        Toast.makeText(getApplicationContext(), String.valueOf(gestureVideoName)+"gesture", Toast.LENGTH_SHORT).show();

        String videoName = getVideoName(gestureVideoName);
        practiceVideoView = (VideoView) findViewById(R.id.practiceVideoView);
        practiceButton = (Button) findViewById(R.id.practiceButton);
        imagePlayButton = (ImageButton) findViewById(R.id.imgPlayButton);
        imagePlayButton.setVisibility(View.INVISIBLE);
        String exampleVidsPath = "android.resource://" + this.getPackageName() + "/" + getResources().getIdentifier(videoName,"raw",getPackageName());
        Log.d("resource name", String.valueOf(getResources().getIdentifier("h0","raw",getPackageName())));
        practiceVideoView.setVideoURI(Uri.parse(exampleVidsPath));
        practiceVideoView.start();
        numTimesVideoPlayed = 0;

        imagePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePlayButton.setVisibility(View.INVISIBLE);
                practiceVideoView.start();
            }
        });

        practiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExampleVidActivity.this, RecordVidActivity.class);
                Bundle b = new Bundle();
                b.putString("Gesture", gestureVideoName);
                intent.putExtras(b);
                startActivity(intent);

            }
        });
        practiceVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                numTimesVideoPlayed++;
                imagePlayButton.setVisibility(View.VISIBLE);
                if(numTimesVideoPlayed >= 3)
                {
                    imagePlayButton.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    String getVideoName(String gestureName) {
        String videoName;
        switch (gestureName) {
            case "LightOn" : videoName = "hlighton"; break;
            case "LightOff" : videoName = "hlightoff"; break;
            case "FanOn" : videoName = "hfanon"; break;
            case "FanOff" : videoName = "hfanoff"; break;
            case "FanUp" : videoName = "hincreasefanspeed"; break;
            case "FanDown": videoName = "hdecreasefanspeed"; break;
            case "SetThermo" : videoName = "hsetthermo"; break;
            case "Num0" : videoName = "h0"; break;
            case "Num1" : videoName = "h1"; break;
            case "Num2" : videoName = "h2"; break;
            case "Num3" : videoName = "h3"; break;
            case "Num4" : videoName = "h4"; break;
            case "Num5" : videoName = "h5"; break;
            case "Num6" : videoName = "h6"; break;
            case "Num7" : videoName = "h7"; break;
            case "Num8" : videoName = "h8"; break;
            case "Num9" : videoName = "h9"; break;
            default: videoName = "h0";
        }
        return videoName;
    }
}
