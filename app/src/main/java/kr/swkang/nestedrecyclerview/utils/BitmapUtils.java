package kr.swkang.nestedrecyclerview.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kr.swkang.nestedrecyclerview.utils.common.ConstantParams;

/**
 * @author KangSung-Woo
 * @since 2016/09/01
 */
public class BitmapUtils {
  private static final String TAG = BitmapUtils.class.getSimpleName();

  /**
   * 어떤 View의 view cache bitmap 인스턴스를 어든ㄴ다.
   *
   * @param view View cache를 얻을 대상의 view.
   * @return null or Bitmap object.
   */
  public static Bitmap getViewCache(@NonNull View view) {
    view.setDrawingCacheEnabled(true);
    return view.getDrawingCache();
  }

  /**
   * 현재 액티비티의 View Cache를 Bitmap 인스턴스 로 얻는다. OOM에 유의 할 것.
   *
   * @param activity View Cache를 얻을 대상 Activity.
   * @return null or Bitmap object.
   */
  public static Bitmap getActivityViewCache(@NonNull Activity activity) {
    View view = activity.findViewById(android.R.id.content);
    if (view != null) {
      view.setDrawingCacheEnabled(true);
      return view.getDrawingCache();
    }
    return null;
  }

  /**
   * RGB565의 비트맵 이미지를 ARGB888으로 전환 한다.
   *
   * @param img ARGB8888으로 전환할 원본 이미지.
   * @return ARGB8888으로 전환된 비트맵 객체.
   */
  public static Bitmap convertRGB565toARGB8888(@NonNull Bitmap img) {
    int numPixels = img.getWidth() * img.getHeight();
    int[] pixels = new int[numPixels];

    //Get JPEG pixels.  Each int is the color values for one pixel.
    img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

    //Create a Bitmap of the appropriate format.
    Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

    //Set RGB pixels.
    result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
    return result;
  }

  /**
   * Bitmap이미지를 리사이즈 한다.
   *
   * @param originalImg   리사이즈 대상 원본 Bitmap 이미지.
   * @param maxResolution width, height 대상 중 최대 감안 크기.
   * @return 리사이징 된 Bitmap 이미지.
   */
  public static Bitmap getResizeImg(@NonNull Bitmap originalImg, int maxResolution) {
    final int width = originalImg.getWidth();
    final int height = originalImg.getHeight();
    int newWidth = width;
    int newHeight = height;
    float rate = 0.0f;

    if (width > height) {
      if (maxResolution < width) {
        rate = maxResolution / (float) width;
        newHeight = (int) (height * rate);
        newWidth = maxResolution;
      }
    }
    else {
      if (maxResolution < height) {
        rate = maxResolution / (float) height;
        newWidth = (int) (width * rate);
        newHeight = maxResolution;
      }
    }
    return Bitmap.createScaledBitmap(originalImg, newWidth, newHeight, true);
  }

  /**
   * get Aspaect ratio image resize point
   *
   * @param src      원본 비트맵 이미지
   * @param maxValue 원하는 값
   * @return 리사이징된 비트맵의 width, height가 설정된 Point객체 혹은 null
   */
  public static Point createScaledBitmapSize(@NonNull Bitmap src, int maxValue) {
    Point p = null;
    if (maxValue > 0) {
      int width = src.getWidth();
      int newWidth = width;
      int height = src.getHeight();
      int newHeight = height;
      float ratio = 0.0f;

      if (width > height) {
        if (maxValue < width) {
          ratio = maxValue / (float) width;
          newHeight = (int) (height * ratio);
          newWidth = maxValue;
        }
      }
      else {
        if (maxValue < height) {
          ratio = maxValue / (float) height;
          newWidth = (int) (width * ratio);
          newHeight = maxValue;
        }
      }
      p = new Point(newWidth, newHeight);
    }
    return p;
  }

  /**
   * 비트맵 이미지 원본 src를 maxWidth, maxHeight를 감안하여 ratio를 유지한체 리사이징 한다.
   *
   * @param src      원본 비트맵 이미지
   * @param maxValue 원하는 너비
   * @return 리사이징된 비트맵 src 혹은 원본 src.
   */
  public static Bitmap createScaledBitmap(@NonNull Bitmap src, int maxValue) {
    Point p = createScaledBitmapSize(src, maxValue);
    if (p != null) {
      src = Bitmap.createScaledBitmap(src, p.x, p.y, true);
    }
    return src;
  }

  /**
   * 저장된 비트맵 이미지 파일의 Exif정보 중 Orientation을 얻어온 뒤 비트맵 이미지를 회전하고 반환 한다.
   *
   * @param source        원본 이미지 비트맵 인스턴스
   * @param imageFilePath Exif정보를 읽어 올 이미지의 파일 경로
   * @return 회전한 이미지 혹은 원본 이미지
   */
  public static Bitmap getImageExifOrientation(@NonNull Bitmap source, @Nullable String imageFilePath) {
    if (!TextUtils.isEmpty(imageFilePath)) {
      // Read EXIF Data
      ExifInterface exif = null;
      try {
        exif = new ExifInterface(imageFilePath);
      } catch (IOException e) {
        e.printStackTrace();
      }

      if (exif != null) {
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle);

        // Return result
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
      }
    }
    return source;
  }

  /**
   * 비트맵 이미지를 저장하기 위한 파일을 만든다
   *
   * @param context Context instance.
   * @param dirName saved image file directory.
   * @return created File instance
   */
  public static File createBitmapFile(@NonNull Context context, @Nullable String dirName) {
    File dir = new File(context.getExternalFilesDir(null) + File.separator + (dirName != null ? dirName : ConstantParams.DEF_IMG_DIR), "");
    if (!dir.exists()) {
      boolean makedDir = dir.mkdir();
    }
    // System.currentTimeMillis()) 사용할 경우 이미지명이 겹치는 경우 발생하여 nanoTime으로 변경
    return new File(dir, "IMG_" + String.valueOf(System.nanoTime()) + ".jpg");
  }

  /**
   * Bitmap 을 파일로 저장 한다.
   *
   * @param context           Context instance.
   * @param originalFilePath  원본 파일 경로
   * @param saveTargetDirName 이미지 저장할 경로
   * @param bmp               저장할 비트맵 이미지
   * @param maxValue          이미지 리사이징을 하고 싶은 경우 원하는 너비 사이즈
   * @param isEnableResize    이미지 리사이징의 여부
   * @return 저장하고 난 File의 instance. or null.
   */
  public static File bitmapConvertToFile(@NonNull final Context context,
                                         @Nullable String originalFilePath,
                                         @Nullable String saveTargetDirName,
                                         @NonNull Bitmap bmp,
                                         int maxValue,
                                         boolean isEnableCompress,
                                         boolean isEnableResize) {
    FileOutputStream fos = null;
    File bitmapFile = null;
    try {
      if (isEnableResize) {
        // resize Bitmap
        if (maxValue > 0) {
          bmp = createScaledBitmap(bmp, maxValue);
        }
      }

      // 원본 이미지 파일의 exif 정보에 따라서 이미지를 회전하고 저장한다
      if (!TextUtils.isEmpty(originalFilePath)) {
        bmp = getImageExifOrientation(bmp, originalFilePath);
      }
      else {
        throw new IllegalArgumentException("original image file path is " + (originalFilePath == null ? "Null." : "Empty."));
      }

      bitmapFile = createBitmapFile(context, saveTargetDirName);

      if (isEnableCompress) {
        fos = new FileOutputStream(bitmapFile);
        bmp.compress(Bitmap.CompressFormat.PNG, ConstantParams.DEF_IMG_COMPRESS, fos);
      }
      else {
        fos = new FileOutputStream(bitmapFile);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
      }
      fos.close();

    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    }
    return bitmapFile;
  }

  /**
   * ImageView의 Bitmap을 recycle하고 GC가 처리 할 수 있게 null로 만든다.
   *
   * @param iv 더이상 사용하지 않아 Bitmap을 recycle할 대상 ImageView.
   * @return true일 경우 성공적으로 Recycle 성공.
   */
  public static boolean recycleBitmap(@NonNull ImageView iv) {
    Drawable d = iv.getDrawable();
    if (d != null) {
      if (d instanceof BitmapDrawable) {
        Bitmap bmp = ((BitmapDrawable) d).getBitmap();
        if (bmp != null) {
          bmp.recycle();
          bmp = null;
          Log.i(TAG, "recycled bmp...");
        }
      }
      d.setCallback(null);
      return true;
    }
    return false;
  }


  /**
   * Picasso 비동기 이미지 로딩 라이브러리에서 사용되는 Transformation으로서, 비트맵 이미지가 인스턴스화 된 뒤 이미지에 대한 후처리
   * 등을 설정 한다.
   * <p>
   * 이 Transformation에서는 이미지 리사이징을 한다.
   *
   * @param maxValue 원하는 이미지 리사이징 너비
   * @return Transformation instance
   */
  public static Transformation createPicassoResizeTransformation(@NonNull final String key, final int maxValue) {
    return new Transformation() {
      @Override
      public Bitmap transform(Bitmap source) {
        if (source != null) {
          Point p = createScaledBitmapSize(source, maxValue);
          if (p != null) {
            Bitmap result = Bitmap.createScaledBitmap(source, p.x, p.y, true);
            if (result != source) {
              source.recycle();
            }
            return result;
          }
        }
        return source;
      }

      @Override
      public String key() {
        return String.valueOf("transformation_" + key);
      }
    };
  }

  /**
   * 이미지Drawable 의 밝기를 조정하는 PorterDuffColorFilter를 생성한다.
   * 사용법 : drawable.setColorFilter(applyBrightness(-30));
   *
   * @param value -100(더 어둡게) 부터 100(더 밝게) 까지.
   * @return PorterDuffColorFilter instance.
   * @see PorterDuffColorFilter
   */
  public static PorterDuffColorFilter applyBrightness(@IntRange(from = -100, to = 100) int value) {
    if (value > 0) {
      int target = value * 255 / 100;
      return new PorterDuffColorFilter(Color.argb(target, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
    }
    else {
      int target = value * -1 * 255 / 100;
      return new PorterDuffColorFilter(Color.argb(target, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
    }
  }

  /**
   * 이미지의 밝기를 조정 할 수 있는 Color Matrix Filter를 생성한다.
   * 사용법 : imageView.setColorFilter(brightIt(100));
   *
   * @param value Dark -100 ~ 100 Light, brightness value
   * @return ColorMatrixColorFilter
   * @see ColorMatrixColorFilter
   */
  public static ColorMatrixColorFilter applyBrightnessByMatrixColorFilter(@IntRange(from = -100, to = 100) int value) {
    ColorMatrix cMat = new ColorMatrix();
    cMat.set(new float[]{
        1, 0, 0, 0, value,
        0, 1, 0, 0, value,
        0, 0, 1, 0, value,
        0, 0, 0, 0, 1
    });
    ColorMatrix cMatSet = new ColorMatrix();
    cMatSet.set(cMat);
    return new ColorMatrixColorFilter(cMatSet);
  }

  /**
   * Bitmap 이미지에 밝기 조정을 한다.
   *
   * @param src   밝기조정을 할 원본 Bitmap 이미지.
   * @param value 밝기 조정 값. (-255 ~ 255 Integer)
   * @return 밝기값이 적용된 Bitmap 이미지.
   */
  public static Bitmap setImageBrightness(@NonNull Bitmap src, @IntRange(from = 0, to = 255) int value) {
    // image size
    int width = src.getWidth();
    int height = src.getHeight();

    // create output bitmap
    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

    // color information
    int A, R, G, B;
    int pixel;

    // scan through all pixels
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        // get pixel color
        pixel = src.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);

        // increase/decrease each channel
        R += value;
        if (R > 255) {
          R = 255;
        }
        else if (R < 0) {
          R = 0;
        }

        G += value;
        if (G > 255) {
          G = 255;
        }
        else if (G < 0) {
          G = 0;
        }

        B += value;
        if (B > 255) {
          B = 255;
        }
        else if (B < 0) {
          B = 0;
        }

        // apply new pixel color to output bitmap
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    // return final image
    return bmOut;
  }


  /**
   * get image Uri from imageview drawable or exist cached file.
   *
   * @param iv ImageView instance
   * @return Uri instance
   */
  public static Uri getImageFromImageView(@NonNull Context context, @NonNull ImageView iv) {
    Uri imgUri = null;
    // extract bitmap from imageview drawable.

    Drawable drawable = iv.getDrawable();
    Bitmap bmp = null;
    if (drawable instanceof BitmapDrawable) {
      bmp = ((BitmapDrawable) drawable).getBitmap();
    }
    else {
      return null;
    }
    // store image to default external storage directory.
    try {
      File f = new File(context.getFilesDir(), "share_image_" + System.currentTimeMillis() + ".png");
      FileOutputStream fos = new FileOutputStream(f);
      bmp.compress(Bitmap.CompressFormat.PNG, ConstantParams.DEF_IMG_COMPRESS, fos);
      fos.close();
      imgUri = Uri.fromFile(f);
    } catch (IOException ioe) {
      Log.e(TAG, ioe.getMessage());
    }
    return imgUri;
  }

  /**
   * Drawable 을 Bitmap으로 전환 한다.
   *
   * @param drawable Bitmap으로 전환할 Drawable instance.
   * @return null or Bitmap instance. (OOM을 조심 할 것)
   */
  public static Bitmap drawableToBitmap(@NonNull Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }
    if (drawable.getIntrinsicWidth() <= 0 && drawable.getIntrinsicHeight() <= 0) {
      return null;
    }
    Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bmp);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bmp;
  }

  /**
   * Bitmap을 InputStream으로 전환 한다.
   *
   * @param bmp InputStream으로 전환할 Bitmap instance.
   * @return null or InputStream instance.
   */
  public static InputStream bitmapToInputStream(@NonNull Bitmap bmp) {
    InputStream is = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
    is = new ByteArrayInputStream(baos.toByteArray());
    return is;
  }

  /**
   * Bitmap을 Byte배열로 전환 한다.
   *
   * @param bmp 비트맵 이미지
   * @return byte array.
   */
  public static byte[] getBytesFromBitmap(@NonNull Bitmap bmp) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.PNG, ConstantParams.DEF_IMG_COMPRESS, os);
    return os.toByteArray();
  }

  /**
   * Bitmap이미지에 Blur를 radius만큼 적용한다.
   *
   * @param sentBitmap 원본 Bitmap 이미지
   * @param radius     blur radius value.
   * @return blur효과가 적용된 bitmap 객체.
   */
  public static Bitmap getBlurImage(Bitmap sentBitmap, @IntRange(from = 1) int radius) {
    // Stack Blur v1.0 from
    // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
    //
    // Java Author: Mario Klingemann <mario at quasimondo.com>
    // http://incubator.quasimondo.com
    // created Feburary 29, 2004
    // Android port : Yahel Bouaziz <yahel at kayenko.com>
    // http://www.kayenko.com
    // ported april 5th, 2012

    // This is a compromise between Gaussian Blur and Box blur
    // It creates much better looking blurs than Box Blur, but is
    // 7x faster than my Gaussian Blur implementation.
    //
    // I called it Stack Blur because this describes best how this
    // filter works internally: it creates a kind of moving stack
    // of colors whilst scanning through the image. Thereby it
    // just has to add one new block of color to the right side
    // of the stack and remove the leftmost color. The remaining
    // colors on the topmost layer of the stack are either added on
    // or reduced by one, depending on if they are on the right or
    // on the left side of the stack.
    //
    // If you are using this algorithm in your code please add
    // the following line:
    //
    // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

    Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

    if (radius < 1) {
      return (null);
    }

    int w = bitmap.getWidth();
    int h = bitmap.getHeight();

    int[] pix = new int[w * h];
    bitmap.getPixels(pix, 0, w, 0, 0, w, h);

    int wm = w - 1;
    int hm = h - 1;
    int wh = w * h;
    int div = radius + radius + 1;

    int r[] = new int[wh];
    int g[] = new int[wh];
    int b[] = new int[wh];
    int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
    int vmin[] = new int[Math.max(w, h)];

    int divsum = (div + 1) >> 1;
    divsum *= divsum;
    int dv[] = new int[256 * divsum];
    for (i = 0; i < 256 * divsum; i++) {
      dv[i] = (i / divsum);
    }

    yw = yi = 0;

    int[][] stack = new int[div][3];
    int stackpointer;
    int stackstart;
    int[] sir;
    int rbs;
    int r1 = radius + 1;
    int routsum, goutsum, boutsum;
    int rinsum, ginsum, binsum;

    /*
    if (startY >= 0 && startY < h) {
      y = startY;
    } */
    for (y = 0; y < h; y++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
      for (i = -radius; i <= radius; i++) {
        p = pix[yi + Math.min(wm, Math.max(i, 0))];
        sir = stack[i + radius];
        sir[0] = (p & 0xff0000) >> 16;
        sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);
        rbs = r1 - Math.abs(i);
        rsum += sir[0] * rbs;
        gsum += sir[1] * rbs;
        bsum += sir[2] * rbs;
        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        }
        else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }
      }
      stackpointer = radius;

      for (x = 0; x < w; x++) {

        r[yi] = dv[rsum];
        g[yi] = dv[gsum];
        b[yi] = dv[bsum];

        rsum -= routsum;
        gsum -= goutsum;
        bsum -= boutsum;

        stackstart = stackpointer - radius + div;
        sir = stack[stackstart % div];

        routsum -= sir[0];
        goutsum -= sir[1];
        boutsum -= sir[2];

        if (y == 0) {
          vmin[x] = Math.min(x + radius + 1, wm);
        }
        p = pix[yw + vmin[x]];

        sir[0] = (p & 0xff0000) >> 16;
        sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);

        rinsum += sir[0];
        ginsum += sir[1];
        binsum += sir[2];

        rsum += rinsum;
        gsum += ginsum;
        bsum += binsum;

        stackpointer = (stackpointer + 1) % div;
        sir = stack[(stackpointer) % div];

        routsum += sir[0];
        goutsum += sir[1];
        boutsum += sir[2];

        rinsum -= sir[0];
        ginsum -= sir[1];
        binsum -= sir[2];

        yi++;
      }
      yw += w;
    }

    for (x = 0; x < w; x++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
      yp = -radius * w;
      for (i = -radius; i <= radius; i++) {
        yi = Math.max(0, yp) + x;

        sir = stack[i + radius];

        sir[0] = r[yi];
        sir[1] = g[yi];
        sir[2] = b[yi];

        rbs = r1 - Math.abs(i);

        rsum += r[yi] * rbs;
        gsum += g[yi] * rbs;
        bsum += b[yi] * rbs;

        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        }
        else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }

        if (i < hm) {
          yp += w;
        }
      }
      yi = x;
      stackpointer = radius;
      for (y = 0; y < h; y++) {
        // Preserve alpha channel: ( 0xff000000 & pix[yi] )
        pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

        rsum -= routsum;
        gsum -= goutsum;
        bsum -= boutsum;

        stackstart = stackpointer - radius + div;
        sir = stack[stackstart % div];

        routsum -= sir[0];
        goutsum -= sir[1];
        boutsum -= sir[2];

        if (x == 0) {
          vmin[y] = Math.min(y + r1, hm) * w;
        }
        p = x + vmin[y];

        sir[0] = r[p];
        sir[1] = g[p];
        sir[2] = b[p];

        rinsum += sir[0];
        ginsum += sir[1];
        binsum += sir[2];

        rsum += rinsum;
        gsum += ginsum;
        bsum += binsum;

        stackpointer = (stackpointer + 1) % div;
        sir = stack[stackpointer];

        routsum += sir[0];
        goutsum += sir[1];
        boutsum += sir[2];

        rinsum -= sir[0];
        ginsum -= sir[1];
        binsum -= sir[2];

        yi += w;
      }
    }
    bitmap.setPixels(pix, 0, w, 0, 0, w, h);
    return (bitmap);
  }


}
