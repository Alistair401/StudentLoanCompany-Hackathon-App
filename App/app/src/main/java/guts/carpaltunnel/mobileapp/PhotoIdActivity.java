package guts.carpaltunnel.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.ByteArrayOutputStream;

import guts.carpaltunnel.mobileapp.util.FormManager;

/**
 * Created by Kyle on 28/10/2017.
 */

public class PhotoIdActivity extends AppCompatActivity  {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    ImageButton photoButton;

    String encodedImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_id);
        this.imageView = this.findViewById(R.id.IdImage);

        photoButton = this.findViewById(R.id.btnSelectPhoto);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button submit_form = findViewById(R.id.submit_form);
        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SignatureActivity.class);
                startActivityForResult(myIntent, 0);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("image", encodedImage);
                //Log.d("BASE64", encodedImage);
            }
        });
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);


            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);
            photoButton.setVisibility(View.GONE);


            if(photo.getConfig() == null)
                return;
            
            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            Frame imageFrame = new Frame.Builder()

                    .setBitmap(photo)                 // your image bitmap
                    .build();

            String imageText = "";
            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText = imageText + textBlock.getValue();                   // return string
                System.out.println(textBlock.getValue());
            }
            TextView textView = this.findViewById(R.id.textView);
            textView.setText(imageText);
        }
    }
}