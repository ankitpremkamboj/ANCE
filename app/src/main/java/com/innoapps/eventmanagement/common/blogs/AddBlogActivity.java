package com.innoapps.eventmanagement.common.blogs;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.postevents.presenter.PostEventsPresenter;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBlogActivity extends AppCompatActivity implements BlogsView {
    @InjectView(R.id.blog_image)
    ImageView blog_image;

    @InjectView(R.id.input_blog_title)
    EditText input_blog_title;

    @InjectView(R.id.input_description)
    EditText input_description;

    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;

    @InjectView(R.id.btn_post_blog)
    Button btn_post_blog;

    @InjectView(R.id.img_back)
    ImageView img_back;

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    private Dialog selectImageDialog;

    private static final String DIRECTORY_NAME = "EVENT";
    private static final String FILE_NAME = "cameraImage.jpg";
    private static final int CAMERA_PIC_REQUEST = 102;
    private static final int REQ_CODE_PICK_IMAGE = 101;
    private String cameraImageFilePath = "";
    private File file;

    private static final String LOG_TAG = "ExampleApp";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCSS6Tg6-cqNbCUGFAzNMzjc6rGyiaeOCY";
    String titleValue = "";
    String descriptionValue;
    BlogsPresenter blogsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);
        ButterKnife.inject(this);
        setfont();
        blogsPresenter = new BlogPresenterImpl();
        txt_header_title.setText("Post Your Blog");

    }

    private void setfont() {
        input_blog_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        input_description.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        btn_post_blog.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.blog_image)
    public void blogImageUpload() {
        selectImage();
    }

    private void selectImage() {
        selectImageDialog = new Dialog(this, R.style.DialogSlideAnim);
        if (!selectImageDialog.isShowing()) {
            selectImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(selectImageDialog.getWindow().getAttributes());
            lp.gravity = Gravity.BOTTOM;
            selectImageDialog.getWindow().setAttributes(lp);
            selectImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            selectImageDialog.setContentView(R.layout.dialog_select_image);
            selectImageDialog.findViewById(R.id.card_Photo).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }
                    openGallery();
                }
            });

            selectImageDialog.findViewById(R.id.card_Camera).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }
                    openCamera();
                }
            });

            selectImageDialog.findViewById(R.id.card_Cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectImageDialog != null) {
                        selectImageDialog.dismiss();
                    }

                }
            });


            selectImageDialog.show();

        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    public void openCamera() {
        try {
            File dir = Utils.getInstance().createDirectory(this, DIRECTORY_NAME);
            File cameraImageFile = Utils.getInstance().createFile(this, dir.getAbsolutePath(), FILE_NAME);
            cameraImageFilePath = cameraImageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(new File(cameraImageFile.getAbsolutePath()));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == this.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    file = new File(compressImage(data.getData(), false));

                    cameraImageFilePath = compressImage(data.getData(), false);
                    Picasso.with(this).load(file).placeholder(R.mipmap.ic_default_profile).error(R.mipmap.ic_default_profile).into(blog_image);
                }
            } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == this.RESULT_OK) {
                if (cameraImageFilePath != null) {
                    file = Utils.getInstance().processCameraImage(this, cameraImageFilePath, this, null);

                    Picasso.with(this).load(file).placeholder(R.mipmap.ic_default_profile).error(R.mipmap.ic_default_profile).into(blog_image);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String compressImage(Uri imageUri, boolean isFilePath) {

        String filePath = "";
        if (isFilePath)
            filePath = imageUri.toString();
        else
            filePath = getPath(this, imageUri);

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualWidth == 0 || actualHeight == 0) {
            actualHeight = 1280;
            actualWidth = 720;
            options.outHeight = 1280;
            options.outWidth = 720;
            bmp = BitmapFactory.decodeFile(filePath, options);
        }
//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        if (bmp != null) {
            int hei = bmp.getWidth();

        }
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), AppConstant.DIRECTORY_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + "local_file" + System.currentTimeMillis() + ".jpeg");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    @OnClick(R.id.btn_post_blog)
    public void postBlog() {

        String blog_title = input_blog_title.getText().toString().trim();
        String blog_description = input_description.getText().toString().trim();

        if (validateCredential(blog_title, blog_description, cameraImageFilePath)) {


            blogsPresenter.postBlogData(this, this, blog_title, blog_description, cameraImageFilePath);
            
        }
    }


    public boolean validateCredential(String blogTitle, String blogDescription, String blogImage) {

        if (TextUtils.isEmpty(blogTitle)) {
            Utils.showError(input_blog_title, "Please Enter Blog Title.", this);
            return false;
        } else if (TextUtils.isEmpty(blogDescription)) {
            Utils.showError(input_description, "Please Enter Blog Description.", this);
            return false;
        } else if (TextUtils.isEmpty(blogImage)) {
            // postEventsView.onEventImageError(activity.getString(R.string.empty_profile_image));
            SnackNotify.showMessage("Please upload image.", coordinateLayout);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void getBlogSuccessfull(ArrayList<BlogsModel.BlogList> blogLists) {

    }

    @Override
    public void getBlogDetailsSuccessfull(ArrayList<DetailsBlogsModel.BlogList> blogLists) {

    }

    @Override
    public void getBlogUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void postBlogSuccessful(String message) {

        Utils.showYesNoDialog(message, this);
        //  finish();

    }

    @Override
    public void blogInternetError() {

    }
}
