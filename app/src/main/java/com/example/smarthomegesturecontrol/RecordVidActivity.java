package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecordVidActivity extends AppCompatActivity {
    private Button recordButton;
    private Button videoPreviewButton;
    private VideoView myVideoView;
    private Button uploadButton;
    private Button nextGestureButton;

    private TextView textCurrGest;
    private TextView uploadText;
    private int idxPractice = 1;
    private String gestureVideoName;
    //public videoFile;
    static int videoNumber = 1;
    private File vFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3_recpload_activity);
        askPermissions();
        Bundle bundle = getIntent().getExtras();
        gestureVideoName= bundle.getString("Gesture");


        recordButton = (Button) findViewById(R.id.recordButton);
        videoPreviewButton = (Button) findViewById(R.id.videoPreviewButton);

        myVideoView = (VideoView) findViewById(R.id.myVideoView);

        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadText = (TextView) findViewById(R.id.uploadtextView);

        textCurrGest = (TextView) findViewById(R.id.textViewCurrGest);

        textCurrGest.setText(gestureVideoName);

        nextGestureButton = (Button) findViewById(R.id.nextGestureButton);

        videoPreviewButton.setVisibility(View.INVISIBLE);
        uploadButton.setVisibility(View.INVISIBLE);
        uploadText.setText("Press RECORD (above) to record video");



        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //creating a new VideoFile each time RecButton is pressed
               // File videoFile = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "video.mp4");
                Intent takeVideoIntent = new Intent("android.media.action.VIDEO_CAPTURE");   
                vFile = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "video.mp4");
                Uri videoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.smarthomegesturecontrol.fileprovider", vFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                startActivity(takeVideoIntent);
                uploadText.setText("Upload Recorded Video?");
                uploadButton.setVisibility(View.VISIBLE);
                videoPreviewButton.setVisibility(View.VISIBLE);
            }
        });
        // @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            if (requestCode == 1 && resultCode == RESULT_OK) {
//                // Handle the video capture result
//            }
//        }


        videoPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VideoView videoPreview = (VideoView) findViewById(R.id.video_preview);
                myVideoView.setVideoURI(Uri.fromFile(vFile));
                myVideoView.start();
                videoPreviewButton.setVisibility(View.INVISIBLE);


            }
        });
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoPreviewButton.setVisibility(View.VISIBLE);

            }
        });

        uploadButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo DETERMINE if vFile exists.
                String videoPath = vFile.getAbsolutePath();
                uploadVideo(videoPath);
            }
        }));

        nextGestureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordVidActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    public interface Service {
        @Multipart
        @POST("/upload_video")
        Call<ResponseBody> uploadVideo(@Part MultipartBody.Part video);
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:5000")             //TODO using LocalHost here
           // .baseUrl("[INPUT IP ADDRESS HERE]:5000")   // OR can Input IP address of Computer running Flask server
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Service service = retrofit.create(Service.class);


    private void uploadVideo(String videoPath) {
        uploadText.setText("Uploading...");
        File videoFile = new File(videoPath);
        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        String videoName = gestureVideoName+"_PRACTICE_"+idxPractice+".mp4";// ((String) idxPractice);
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoName, videoBody);
        //MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), videoBody);
        Call<ResponseBody> call = service.uploadVideo(videoPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle successful response
                System.out.println("SUCCESS!!");
                uploadText.setText(videoName+ " Succesfully Uploaded!");
                idxPractice++;
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                System.out.println("Exception: " + t.getMessage());
                uploadText.setText("Failed to Upload!");


            }
        });
    }
}
