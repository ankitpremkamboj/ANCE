package com.innoapps.eventmanagement.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static Utils utilObject = null;

    public static Utils getInstance() {

        if (utilObject == null) {
            utilObject = new Utils();
        }
        return utilObject;

    }

    public static void showError(EditText editText, String msg, Activity activity) {
        editText.setError(msg);
        requestFocus(editText, activity);
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // email Validation
    public static boolean emailValidation(String email) {
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (emailValid) {
            return true;
        } else {
            return false;
        }
    }

    public File createDirectory(Context context, String dirName) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + dirName;
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        return fileDir;

    }

    public static void setStausBarColor(View statusBarBackground, Activity activity, int color) {
        try {
            /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(activity.getResources().getColor(color));
			}
*/

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window w = activity.getWindow();
                w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                w.setStatusBarColor(activity.getResources().getColor(color));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String distance(double lat1, double lon1, double lat2, double lon2) {
        String distValue = "";
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (dist < 1) {
            distValue = String.format("%.2f", dist * 1000) + " M";

        } else {
            distValue = String.format("%.2f", dist) + " KM";
        }

        return distValue;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static int convertDpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public static int getActionBarHeight(Activity activity) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public File createFile(Context context, String dirPath, String fileName) throws IOException {
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;

    }

    public static File createFile(String dirName, String fileName) {
        File file = null;
        try {
            String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + dirName;
            final File myDir = new File(folder);
            if (!myDir.exists())
                myDir.mkdirs();

            final String fname = fileName;
            file = new File(myDir, fname);
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }

    public String getPathFromUri(Activity context, Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, String filePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1; // Default subsampling size
        // See if image raw height and width is bigger than that of required
        // view
        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            // bigger
            final int halfHeight = options.outHeight / 2;
            final int halfWidth = options.outWidth / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public File saveBitmapToFile(final Bitmap img, final String imagePath) {
        try {
            final File f = new File(imagePath);
            if (f.isFile()) {
                f.delete();
            }
            img.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(imagePath)));
            final File f1 = new File(imagePath);
            Log.e("Bitmap Size", "Bitmap Size" + (f1.length() / 1024) + "KB");
            return f;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Bitmap getFixSizeScaledBitMap(String path, int IMAGE_MAX_SIZE, Activity activity) {

        Uri uri = getImageContentUri(activity, new File(path));
        InputStream in = null;
        try {
            in = activity.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap b = null;
            in = activity.getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;

                int orientation = 0;
                int rotate = 0;
                try {
                    final ExifInterface exif = new ExifInterface(path);
                    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);

                Bitmap scaledBitmap = Bitmap.createBitmap(b, 0, 0, (int) x, (int) y, matrix, true);

                // Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                // (int) y, matrix,true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            b.getHeight();

            return b;
        } catch (IOException e) {

            return null;
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public File processCameraImage(Context context, String cameraImagePath, Activity activity, View iv_RosterImage) {
        try {
            int xDim, yDim;
            Bitmap bm = null;
            int rotate = 0;
            if (iv_RosterImage != null) {
                xDim = iv_RosterImage.getWidth();
                yDim = iv_RosterImage.getHeight();
                bm = Utils.decodeSampledBitmapFromResource(activity.getResources(), cameraImagePath, xDim, yDim);
            }

            // bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(),
            // bitmap.getHeight(),

            File file = createFile(activity, createDirectory(activity, AppConstant.DIRECTORY_PATH).getAbsolutePath(), System.currentTimeMillis() + ".jpg");
            return saveFixedSizeFile(activity, cameraImagePath, file.getAbsolutePath(), 120000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File processGalleryImage(Context context, Uri selectedImageUri, Activity activity, View imageView) {
        try {
            int xDim, yDim;
            Bitmap bm = null;
            String galleryImagePath = "";
            galleryImagePath = getPathFromUri(activity, selectedImageUri);

            if (imageView != null) {
                xDim = imageView.getWidth();
                yDim = imageView.getHeight();
                bm = decodeSampledBitmapFromResource(activity.getResources(), galleryImagePath, xDim, yDim);
            }

            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(galleryImagePath);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            // vvbm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
            // bm.getHeight(), mat, true);
            File file = createFile(activity, createDirectory(activity, AppConstant.DIRECTORY_PATH).getAbsolutePath(), String.valueOf((int) System.currentTimeMillis()) + ".jpg");
            return saveFixedSizeFile(activity, galleryImagePath, file.getAbsolutePath(), 120000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File saveFixedSizeFile(Activity activity, String sourceFilePath, String destinationFilePath, int size) {
        try {
            if (new File(destinationFilePath).exists()) {
                new File(destinationFilePath).delete();
            }
            return saveBitmapToFile(getFixSizeScaledBitMap(sourceFilePath, size, activity), destinationFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getBitmap(Context context, Uri selectedImageUri, Activity activity, View view) {
        try {
            String galleryImagePath = "";
            galleryImagePath = getPathFromUri(activity, selectedImageUri);
            int xDim = view.getWidth();
            int yDim = view.getHeight();
            Bitmap bm = decodeSampledBitmapFromResource(activity.getResources(), galleryImagePath, xDim, yDim);

            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(galleryImagePath);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
            BitmapDrawable drawableBitmap = new BitmapDrawable(bm);
            view.setBackgroundDrawable(drawableBitmap);
            view.setBackgroundDrawable(new BitmapDrawable(bm));
            return bm;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File processCameraImageRoster(String cameraImagePath, ImageView iv_RosterImage, Activity activity) {
        try {

            int xDim = iv_RosterImage.getWidth();
            int yDim = iv_RosterImage.getHeight();
            Bitmap bm = Utils.getInstance().decodeSampledBitmapFromResource(activity.getResources(), cameraImagePath, xDim, yDim);
            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(cameraImagePath);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
            iv_RosterImage.setImageBitmap(bm);
            File file = createFile(activity, createDirectory(activity, AppConstant.DIRECTORY_PATH).getAbsolutePath(), String.valueOf((int) System.currentTimeMillis()) + ".jpg");
            return file;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File processGalleryImageRoster(Uri selectedImageUri, Activity activity, ImageView imageView) {
        try {
            int xDim, yDim;
            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(size);

            String galleryImagePath = "";
            galleryImagePath = getPathFromUri(activity, selectedImageUri);
            if (imageView != null) {
                xDim = imageView.getWidth();
                yDim = imageView.getHeight();
            } else {
                xDim = size.x;
                yDim = size.y;
            }
            Bitmap bm = decodeSampledBitmapFromResource(activity.getResources(), galleryImagePath, xDim, yDim);
            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(galleryImagePath);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
            if (imageView != null) {
                imageView.setImageBitmap(bm);

            }
            File file = createFile(activity, createDirectory(activity, AppConstant.DIRECTORY_PATH).getAbsolutePath(), String.valueOf((int) System.currentTimeMillis()) + ".jpg");
            return saveFixedSizeFile(activity, galleryImagePath, file.getAbsolutePath(), 120000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public File processCameraImage(String cameraImagePath, ImageView iv_RosterImage, Activity activity) {
        try {
            int xDim, yDim;
            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(size);

            if (iv_RosterImage != null) {
                xDim = iv_RosterImage.getWidth();
                yDim = iv_RosterImage.getHeight();
            } else {
                xDim = size.x;
                yDim = size.y;
            }

            Bitmap bm = Utils.decodeSampledBitmapFromResource(activity.getResources(), cameraImagePath, xDim, yDim);
            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(cameraImagePath);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
            if (iv_RosterImage != null)
                iv_RosterImage.setImageBitmap(bm);

            File file = createFile(activity, createDirectory(activity, AppConstant.DIRECTORY_PATH).getAbsolutePath(), String.valueOf((int) System.currentTimeMillis()) + ".jpg");
            return saveFixedSizeFile(activity, cameraImagePath, file.getAbsolutePath(), 120000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isGPSEnable(Context context) {

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Log.i("GPSStatus",String.valueOf(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)));

    }

    // for keyboard down
    public static void hideKeyboardIfOpen(Activity activity) {


        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static void showYesNoDialog(String message, final Activity activity) {
        try {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            activity.finish();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
