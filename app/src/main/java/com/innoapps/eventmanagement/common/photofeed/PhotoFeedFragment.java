package com.innoapps.eventmanagement.common.photofeed;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innoapps.eventmanagement.MainApplication;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.helper.TouchImageView;
import com.innoapps.eventmanagement.common.photofeed.adapter.PhotoAdapter;
import com.innoapps.eventmanagement.common.photofeed.adapter.PhotoFeedAdapter;
import com.innoapps.eventmanagement.common.photofeed.model.PhotoFeedModel;
import com.innoapps.eventmanagement.common.photofeed.presenter.PhotoFeedPresenterImpl;
import com.innoapps.eventmanagement.common.photofeed.view.PhotoFeedView;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class PhotoFeedFragment extends Fragment implements PhotoFeedView {
    private View rootView;
    private GridView gridView;
    private ImageView camera;
    private PhotoFeedAdapter gridAdapter;
    Bitmap bitmap;
    ArrayList<PhotoFeedModel.GalleryList> listOfGallery = new ArrayList<PhotoFeedModel.GalleryList>();
    private String img_id;
    private ProgressDialog simpleWaitDialog;
    private Dialog selectImageDialog;
    private static final int REQ_CODE_PICK_IMAGE = 101;
    private static final String DIRECTORY_NAME = "ANCE";
    private static final String FILE_NAME = "cameraImage.jpg";
    ;
    private static final int CAMERA_PIC_REQUEST = 102;
    private String cameraImageFilePath;
    private File file;
    private static Dialog dialog;
    TouchImageView imageView;
    public static DisplayImageOptions options;
    PhotoFeedPresenterImpl photoFeedPresenter;
    PhotoAdapter photoAdapter;
    RelativeLayout coordinateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        rootView = (View) inflater.inflate(R.layout.photofeed, container, false);
        initViews();
        photoFeedPresenter = new PhotoFeedPresenterImpl();
        photoFeedPresenter.callGetPhotoFeedList(this, getActivity());
        initConfiguration();
        return rootView;
    }


    public void initConfiguration() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.event_default)
                .showImageForEmptyUri(R.mipmap.event_default)
                .showImageOnFail(R.mipmap.event_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

    }


    public static class MyDialogFragment1 extends DialogFragment {
        String img_id, total_Like, LikeStatus;
        TextView tv_Count;
        ImageView likeImage;
        String imageURl;
        private boolean likeAlready;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.image_view_dialog, container, false);

            Bundle bundle = this.getArguments();

            imageURl = bundle.getString("image");

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }


            //Bitmap bitmap = bundle.getParcelable("image");

            TouchImageView imageView = (TouchImageView) root.findViewById(R.id.image);
            ImageView back = (ImageView) root.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            if (imageURl != null & !imageURl.equalsIgnoreCase("")) {

                ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance

                imageLoader.displayImage(imageURl, imageView, options);

                imageLoader.loadImage(imageURl, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                    }
                });
            }


            return root;
        }


    }

    public void initViews() {
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        camera = (ImageView) rootView.findViewById(R.id.camera);
        coordinateLayout = (RelativeLayout) rootView.findViewById(R.id.coordinateLayout);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle bun = new Bundle();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MyDialogFragment1 frag = new MyDialogFragment1();
                bun.putString("image", listOfGallery.get(position).getGalleryImage());
                frag.setArguments(bun);
                frag.show(ft, "txn_tag");

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();


            }
        });
    }

    private void selectImage() {
        selectImageDialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
        if (!selectImageDialog.isShowing()) {
            selectImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(selectImageDialog.getWindow().getAttributes());
            lp.gravity = Gravity.BOTTOM;
            selectImageDialog.getWindow().setAttributes(lp);
            selectImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

    private void openCamera() {
        try {
            File dir = Utils.getInstance().createDirectory(getActivity(), DIRECTORY_NAME);
            File cameraImageFile = Utils.getInstance().createFile(getActivity(), dir.getAbsolutePath(), FILE_NAME);
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == getActivity().RESULT_OK) {

                if (data != null && data.getData() != null) {

                    file = new File(compressImage(data.getData(), false));
                    // uploadImage();
                    photoFeedPresenter.callUploadPhotoFeedList(this, getActivity(), compressImage(data.getData(), false));
                }


            } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == getActivity().RESULT_OK) {
                if (cameraImageFilePath != null) {
                    file = Utils.getInstance().processCameraImage(getActivity(), cameraImageFilePath, getActivity(), null);
                    // uploadImage();
                    photoFeedPresenter.callUploadPhotoFeedList(this, getActivity(), cameraImageFilePath);
                }// else
                //  Toast.makeText(getBaseContext(), "Soory, this image file can not be access", 5000).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String compressImage(Uri imageUri, boolean isFilePath) {

        String filePath = "";
        if (isFilePath)
            filePath = imageUri.toString();
        else
            filePath = getPath(getActivity(), imageUri);

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

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), AppConstant.DIRECTORY_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + "local_file" + System.currentTimeMillis() + ".jpeg");
        return uriSting;

    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
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

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
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

    public static void showProgressDialog(String pBarMessage, Context context) {
        try {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.progress_dialog);
            ((TextView) dialog.findViewById(R.id.text)).setText(pBarMessage);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //// Photo Feed Response
    @Override
    public void getPhotoFeedSuccessfull(ArrayList<PhotoFeedModel.GalleryList> galleryLists) {
        this.listOfGallery = galleryLists;
        photoAdapter = new PhotoAdapter(getActivity(), listOfGallery);
        gridView.setAdapter(photoAdapter);
    }

    @Override
    public void getPhotoFeedUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void getPhotoFeedUploadSuccessful(String message) {
        photoFeedPresenter.callGetPhotoFeedList(this, getActivity());

    }

    @Override
    public void PhotoFeedInternetError() {

    }

}
