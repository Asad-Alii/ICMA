package com.asad.icma;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TeacherDetailsActivity extends AppCompatActivity {

    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 10;
    static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    String value = null;
    double dLat;
    double dLong;
    private GoogleMap mMap;

    String username;
    TextView user;
    TextView dateTime;

    SimpleDateFormat dff = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat df = new SimpleDateFormat("hh:mm a");

    Uri imageUri = null;
    public static ImageView showImg = null;
    public static TextView loc = null;
    TeacherDetailsActivity CameraActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherdetails);

        CameraActivity = this;

        loc = findViewById(R.id.location_textview);

        showImg = findViewById(R.id.captured_image);

        final Button markAttendance = findViewById(R.id.mark_btn);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        showImg = findViewById(R.id.captured_image);
        //capturedImageButton = findViewById(R.id.captureImage_btn);
        //loc = findViewById(R.id.location_textview);

        //namelayout = findViewById(R.id.name_layout);

        //namelayout.addView(photoCaptureIntent);

        /*setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        camera = Camera.open(currentCameraId);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();*/

        showImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onTakePhotoClicked(view);

            }
        });

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Your Attendance has been marked!", Toast.LENGTH_SHORT).show();

                Intent in = new Intent(TeacherDetailsActivity.this, TeacherPanelActivity.class);
                startActivity(in);
                finish();
            }
        });

        /*selectImage = findViewById(R.id.selectImage_btn);
        profileImage = findViewById(R.id.teacher_image);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "Select Picture"), REQUEST_CODE );
            }
        });*/

        Calendar c = Calendar.getInstance();
        String formatteddate = dff.format(c.getTime());
        String formattedTime = df.format(c.getTime());


        username = getIntent().getStringExtra("username");
        user = findViewById(R.id.teacher_name_textview);
        //user.setText(username);

        dateTime = findViewById(R.id.dateTime_textview);
        dateTime.setText(formattedTime + ", " + formatteddate);

    }

    /*public void onTakePhotoClicked(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                invokeCameraForApiGreaterThan23();
            } else {
                // let's request permission.
                String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            invokeCameraForApiLessThan23();
        }
    }

    private void invokeCameraForApiGreaterThan23()
    {

        *//*************************** Camera Intent Start ************************//*

        // Define the file-name to save photo taken by Camera activity

        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        *//**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****//*


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        *//*************************** Camera Intent End ************************//*

    }

    private void invokeCameraForApiLessThan23() {
        *//*************************** Camera Intent Start ************************//*

        // Define the file-name to save photo taken by Camera activity

        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        *//**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****//*


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        *//*************************** Camera Intent End ************************//*
    }

    *//************ Convert Image Uri path to physical path **************//*

    public static String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            *//*********** Which columns values want to get *******//*
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            *//*******  If size is 0, there are no images on the SD Card. *****//*

            if (size == 0) {


                //imageDetails.setText("No Image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    *//**************** Captured image details ************//*

     *//*****  Used to show image on view in LoadImagesFromSDCard class ******//*
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + Path + "\n";

                    // Show Captured Image detail on activity
                    //imageDetails.setText(CapturedImageDetails);

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return "" + imageID;
    }

    *//**
     * Async task for loading the images from the SD card.
     *
     * @author Android Example
     *//*

    // Class with extends AsyncTask class

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(TeacherDetailsActivity.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            *//****** NOTE: You can call UI Element here. *****//*

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {

                *//**  Uri.withAppendedPath Method Description
     * Parameters
     *    baseUri  Uri to append path segment to
     *    pathSegment  encoded path segment to append
     * Returns
     *    a new Uri based on baseUri with the given segment appended to the path
     *//*

                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);

                *//**************  Decode an input stream into a bitmap. *********//*
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    *//********* Creates a new bitmap, scaled from an existing bitmap. ***********//*

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                *//********* Cancel execution of this task. **********//*
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (mBitmap != null) {
                // Set Image to ImageView

                imageHolder.setImageBitmap(mBitmap);
            }

        }
    }

    private void invokeCamera()
    {
        Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", createImageFile());

        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        in.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        in.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(in, CAMERA_REQUEST_CODE);
    }

    private File createImageFile() {
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp =  sdf.format(new Date());

        File imageFile = new File(picturesDirectory, "ICMA" + timestamp + ".jpg");

        return imageFile;
    }

    public void btnTakePhotoClicked(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //ContextWrapper context = new ContextWrapper(getApplicationContext());

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        pictureUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private String getPictureName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp =  sdf.format(new Date());
        return "ICMA" + timestamp + ".jpg";
    }

    void getLocation() {

        *//*SharedPreferences preferences=getSharedPreferences("latLong",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();*//*

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                *//*e1 = (EditText) findViewById(R.id.atLocationLat);
                e1.setText(latti + " , " + longi);*//*
                //value = e1.getText().toString();
                //value = latti + " ," + longi;
                //editor.putString("value", value);
                //editor.commit();
                //e2.setText(value);
                //e2.setText("Latitude: " + longi);

                //Geocoder geocoder;
                //List<Address> addresses;
                //geocoder = new Geocoder(this, Locale.getDefault());
                loc = findViewById(R.id.location_textview);

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latti, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    String[] seperated = address.split(",");
                    String address1 = seperated[0].trim();
                    String address2 = seperated[1].trim();
                    String address3 = seperated[2].trim();
                    String address4 = seperated[5].trim();
                    //dLat = Double.parseDouble(latiPos);
                    //dLong = Double.parseDouble(longiPos);
                    loc.setText(address1 + ", " + address2 + ", " + address3 + ", " + address4);
                    //loc.setText(address);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


                //((EditText)findViewById(R.id.atLocationLat)).setText("Latitude: " + latti);
                //((EditText)findViewById(R.id.atLocationLong)).setText("Longitude: " + longi);

            } else {
                *//*((EditText) findViewById(R.id.atLocationLat)).setText("Unable to find correct location.");
                ((EditText) findViewById(R.id.atLocationLong)).setText("Unable to find correct location. ");*//*
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                //break;

            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    invokeCameraForApiGreaterThan23();
                }
                else
                {
                    Toast.makeText(this, "Cannot use camera without permissions!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                *//*********** Load Captured Image And Data Start ****************//*

                String imageId = convertImageUriToFile(imageUri, CameraActivity);


                //  Create and excecute AsyncTask to load capture image

                new LoadImagesFromSDCard().execute("" + imageId);

                */

    /*********** Load Captured Image And Data End ****************//*


            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    public void onTakePhotoClicked(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                invokeCameraForApiGreaterThan23();
            } else {
                // let's request permission.
                String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
            }
        } else {
            invokeCameraForApiLessThan23();
        }
    }

    private void invokeCameraForApiGreaterThan23() {

        /*************************** Camera Intent Start ************************/

        // Define the file-name to save photo taken by Camera activity

        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        /*************************** Camera Intent End ************************/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_LOCATION:
                getLocation();

            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    invokeCameraForApiGreaterThan23();
                } else {
                    Toast.makeText(this, "Cannot use camera without permissions!", Toast.LENGTH_SHORT).show();
                }


        }
    }

    private void invokeCameraForApiLessThan23() {
        /*************************** Camera Intent Start ************************/

        // Define the file-name to save photo taken by Camera activity

        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        /*************************** Camera Intent End ************************/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                /*********** Load Captured Image And Data Start ****************/

                String imageId = convertImageUriToFile(imageUri, CameraActivity);


                //  Create and excecute AsyncTask to load capture image

                new LoadImagesFromSDCard().execute("" + imageId);

                /*********** Load Captured Image And Data End ****************/


            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /************ Convert Image Uri path to physical path **************/

    public static String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {


                //loc.setText("No Image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + Path + "\n";

                    // Show Captured Image detail on activity
                    //loc.setText(CapturedImageDetails);

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return "" + imageID;
    }


    /**
     * Async task for loading the images from the SD card.
     *
     * @author Android Example
     */

    // Class with extends AsyncTask class

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(TeacherDetailsActivity.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading Image...");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {

                /**  Uri.withAppendedPath Method Description
                 * Parameters
                 *    baseUri  Uri to append path segment to
                 *    pathSegment  encoded path segment to append
                 * Returns
                 *    a new Uri based on baseUri with the given segment appended to the path
                 */

                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);

                /**************  Decode an input stream into a bitmap. *********/
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    /********* Creates a new bitmap, scaled from an existing bitmap. ***********/

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (mBitmap != null) {
                // Set Image to ImageView

                showImg.setImageBitmap(mBitmap);
            }

        }
    }

    void getLocation() {

        SharedPreferences preferences=getSharedPreferences("latLong",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                //e1 = (EditText) findViewById(R.id.atLocationLat);
                //e1.setText(latti + " , " + longi);
                //value = e1.getText().toString();
                //value = latti + " ," + longi;
                //editor.putString("value", value);
                //editor.commit();
                //e2.setText(value);
                //e2.setText("Latitude: " + longi);

                //Geocoder geocoder;
                //List<Address> addresses;
                //geocoder = new Geocoder(this, Locale.getDefault());
                loc = findViewById(R.id.location_textview);

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latti, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    String[] seperated = address.split(",");
                    String address1 = seperated[0].trim();
                    String address2 = seperated[1].trim();
                    String address3 = seperated[2].trim();
                    String address4 = seperated[5].trim();
                    //dLat = Double.parseDouble(latiPos);
                    //dLong = Double.parseDouble(longiPos);
                    loc.setText(address1 + ", " + address2 + ", " + address3 + ", " + address4);
                    //loc.setText(address);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


                //((EditText)findViewById(R.id.atLocationLat)).setText("Latitude: " + latti);
                //((EditText)findViewById(R.id.atLocationLong)).setText("Longitude: " + longi);

            } else {
                /*((EditText) findViewById(R.id.atLocationLat)).setText("Unable to find correct location.");
                ((EditText) findViewById(R.id.atLocationLong)).setText("Unable to find correct location. ");*/
            }
        }

    }


}
