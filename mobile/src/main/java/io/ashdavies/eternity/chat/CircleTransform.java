package io.ashdavies.eternity.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import com.squareup.picasso.Transformation;

class CircleTransform implements Transformation {

  private final int radius;
  private final int margin;

  CircleTransform(int radius) {
    this(radius, 0);
  }

  private CircleTransform(int radius, int margin) {
    this.radius = radius;
    this.margin = margin;
  }

  @Override
  public Bitmap transform(Bitmap source) {
    final Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

    Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

    if (source != output) {
      source.recycle();
    }

    return output;
  }

  @Override
  public String key() {
    return "rounded(radius=" + radius + ", margin=" + margin + ")";
  }
}
