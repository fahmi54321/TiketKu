package com.example.tiketku.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.AlertDialog.OnClickListener;

public class ImageUtils {

    Context context;
    private Activity current_activity;
    private Fragment current_fragment;

    private ImageAttachmentListener imageAttachment_callback;
    private String selected_path = "";
    private Uri imageUri;
    private File path = null;

    private int from=0;
    private boolean isFragment=false;

    public ImageUtils(Activity activity)
    {
        this.context = activity;
        this.current_activity = activity;
        imageAttachment_callback = (ImageAttachmentListener)context;
    }

    public ImageUtils(Activity activity, Fragment fragment, boolean isFragment) {
        this.context = activity;
        this.current_activity = activity;
        imageAttachment_callback = (ImageAttachmentListener)fragment;
        if(isFragment)
        {
            this.isFragment = true;
            current_fragment=fragment;
        }
    }

    public String getfilename_from_path(String path)
    {
        return path.substring(path.lastIndexOf('/')+1,path.length());
    }

    public Uri getImageUri(Context context, Bitmap photo)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG,80,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),photo,"Title",null);
        return Uri.parse(path);
    }

    public String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.context.getContentResolver().query(uri,projection,null,null,null);
        int column_index = 0;
        if(cursor != null)
        {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        else
        {
            return uri.getPath();
        }
    }

    public Bitmap StringtoBitMap(String encodedString)
    {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
            return bitmap;
        }catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public boolean isDeviceSupportCamera()
    {
//        if(this.context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        if(this.context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            //this device has a camera
            return true;
        }
        else
        {
            // no camera on this device
            return false;
        }
    }

    public Bitmap compresImage(String imageUri, float height, float width)
    {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        //by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded
        //if you try use the bitmap here, you will get null

        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = height;
        float maxWidth = width;
        float imgRatio = actualWidth/actualHeight;
        float maxRatio = maxWidth/maxHeight;

        if(actualHeight > maxHeight || actualWidth > maxWidth)
        {
            if(imgRatio < maxRatio)
            {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio*actualWidth);
                actualHeight = (int) maxHeight;
            }
            else if(imgRatio > maxRatio)
            {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio*actualHeight);
                actualWidth = (int) maxWidth;
            }
            else
            {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        options.inSampleSize = calculateInSampleSize(options,actualWidth,actualHeight);

        options.inJustDecodeBounds = false;

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16*1024];

        try {
            bitmap = BitmapFactory.decodeFile(filePath,options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth,actualHeight, Bitmap.Config.ARGB_8888);
        }catch (OutOfMemoryError excpetion) {
            excpetion.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX,ratioY,middleX,middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,middleX - bitmap.getWidth()/2,middleY - bitmap.getHeight()/2,new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif;

        try {

            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,0);
            Log.d("EXIF","Exif: " + orientation);
            Matrix matrix = new Matrix();
            if(orientation == 6)
            {
                matrix.postRotate(90);
                Log.d("EXIF","Exif: " + orientation);
            }
            else if (orientation == 3)
            {
                matrix.postRotate(180);
                Log.d("EXIF","Exif: " + orientation);
            }
            else if (orientation == 8)
            {
                matrix.postRotate(270);
                Log.d("EXIF","Exif: " + orientation);
            }

            scaledBitmap = Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);
            return scaledBitmap;

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String getRealPathFromURI(String contentURI)
    {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri,null,null,null,null);
        if (cursor == null)
        {
            return contentUri.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height>reqHeight || width>reqWidth)
        {
            final int heightRatio = Math.round((float) height/(float) reqHeight);
            final int widthRatio = Math.round((float) width/(float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final  float totalPixels = width*height;
        final  float totalReqPixelsCap = reqWidth*reqHeight*2;
        while (totalPixels / (inSampleSize*inSampleSize) > totalReqPixelsCap)
        {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public void lauchCamera(int from)
    {
        this.from = from;

        if (Build.VERSION.SDK_INT >= 23)
        {
            if(isFragment)
            {
                permission_check_fragment(1);
            }
            else
            {
                permission_check(1);
            }
        }
        else
        {
            camera_call();
        }
    }

    public void lauchGallery(int from)
    {
        this.from = from;

        if(Build.VERSION.SDK_INT >=23)
        {
            if(isFragment)
            {
                permission_check_fragment(2);
            }
            else
            {
                permission_check(2);
            }
        }
        else
        {
            gallery_call();
        }
    }

    public void imagepicker(final int from)
    {
        this.from = from;

        final CharSequence[] items;

        if(isDeviceSupportCamera())
        {
            items = new CharSequence[2];
            items[0] = "Camera";
            items[1] = "Gallery";
        }
        else
        {
            items = new CharSequence[1];
            items[0] = "Gallery";
        }

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(current_activity);
        alertdialog.setTitle("Add Image");
        alertdialog.setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera"))
                {
                    lauchCamera(from);
                }
                else if (items[item].equals("Gallery"))
                {
                    lauchGallery(from);
                }
            }
        });
        alertdialog.show();
    }

    public void permission_check(final int code)
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                showMessageOKCancel("For adding images, Yoou need to provide permission to access your files", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(current_activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
                    }
                });
                return;
            }

            ActivityCompat.requestPermissions(current_activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
            return;
        }

        if (code == 1)
        {
            camera_call();
        }
        else if (code==2)
        {
            gallery_call();
        }
    }

    public void permission_check_fragment(final int code)
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                showMessageOKCancel("For adding images, Yoou need to provide permission to access your files", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        current_fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
                    }
                });
                return;
            }

            current_fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
            return;
        }

        if (code == 1)
        {
            camera_call();
        }
        else if (code==2)
        {
            gallery_call();
        }
    }

    private void showMessageOKCancel(String message, OnClickListener okListener)
    {
        new AlertDialog.Builder(current_activity)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }

    public void camera_call()
    {
        ContentValues values = new ContentValues();
        imageUri = current_activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        if (isFragment)
        {
            current_fragment.startActivityForResult(intent,0);
        }
        else
        {
            current_activity.startActivityForResult(intent,0);
        }
    }

    public void gallery_call()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        if(isFragment)
        {
            current_fragment.startActivityForResult(intent,1);
        }
        else
        {
            current_activity.startActivityForResult(intent,1);
        }
    }

    public void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 1 :
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    camera_call();
                }
                else
                {
                    Toast.makeText(current_activity,"Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
            break;

            case 2 :
            {
                // if request is cancelled, the result array are empty
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    gallery_call();
                }
                else
                {
                    Toast.makeText(current_activity,"Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String file_name;
        Bitmap bitmap;

        switch (requestCode)
        {
            case 0 :
            {
                if (resultCode == current_activity.RESULT_OK)
                {
                    Log.i("Camera Selected","Photo");
                    try {
                        selected_path = null;
                        selected_path = getPath(imageUri);
//                        Log.i("Selected","path"+selected_path);
                        file_name = selected_path.substring(selected_path.lastIndexOf("/")+1);
//                        Log.i("file","name"+file_name);
                        bitmap = compresImage(imageUri.toString(),816,612);

                        imageAttachment_callback.image_attachment(from,file_name,bitmap,imageUri);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            break;

            case 1 :
            {
                if (resultCode==current_activity.RESULT_OK)
                {
                    Log.i("Gallery","Photo");
                    Uri selectedImage = data.getData();

                    try {
                        selected_path = null;
                        selected_path = getPath(selectedImage);
                        file_name = selected_path.substring(selected_path.lastIndexOf("/")+1);
                        bitmap = compresImage(selectedImage.toString(),816,612);

                        imageAttachment_callback.image_attachment(from,file_name,bitmap,selectedImage);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    public Bitmap getImage_FromUri(Uri uri, float height, float width)
    {
        Bitmap bitmap = null;

        try {
            bitmap = compresImage(uri.toString(),height,width);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public String getFileName_from_Uri(Uri uri)
    {
        String path = null,file_name=null;

        try {
            path = getRealPathFromURI(uri.getPath());
            file_name = path.substring(path.lastIndexOf("/")+1);

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return file_name;
    }

    public boolean checkimage(String file_name, String file_path)
    {
        boolean flag;
        path = new File(file_path);

        File file = new File(path,file_name);
        if (file.exists())
        {
            Log.i("file","exists");
            flag = true;
        }
        else
        {
            Log.i("file","not exist");
            flag = false;
        }

        return flag;
    }

    public Bitmap getImage(String file_name, String file_path)
    {
        path = new File(file_path);
        File file = new File(path,file_name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16*1024];

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        if (bitmap!=null)
        {
            return bitmap;
        }
        else
        {
            return null;
        }
    }

    public void createImage(Bitmap bitmap, String file_name, String filepath, boolean file_replace)
    {
        path = new File(filepath);
        if (!path.exists())
        {
            path.mkdirs();
        }

        File file = new File(path,file_name);

        if (file.exists())
        {
            if (file_replace)
            {
                file.delete();
                file = new File(path,file_name);
                store_image(file,bitmap);
                Log.i("file","replaced");
            }
        }
        else
        {
            store_image(file,bitmap);
        }
    }

    private void store_image(File file, Bitmap bitmap) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,80,outputStream);
            outputStream.flush();
            outputStream.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
