package com.viana.androidutil.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import static com.viana.androidutil.io.FileIo.getFilename;

/**
 * Created by joao.viana on 21/02/2018.
 */

public class ActivityUtil {
    public static void openNewActivity(Context pContext, Class<?> pClass, Bundle pParametros, boolean pFechar) throws Exception {
        Intent intent = new Intent(pContext, pClass);
        if (pParametros != null)
            intent.putExtras(pParametros);
        ((Activity) pContext).startActivity(intent);
        if (pFechar)
            ((Activity) pContext).finish();
    }

    public static void openNewActivityForResult(Context pContext, Class<?> pActivity, int pRequestCode, Bundle pParametros) throws Exception {
        Intent intent = new Intent(pContext, pActivity);
        if (pParametros != null)
            intent.putExtras(pParametros);
        ((Activity) pContext).startActivityForResult(intent, pRequestCode);
    }

    public static void openDialingScreen(Context pContext, String pNumberPhone) throws Exception {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pNumberPhone));
        pContext.startActivity(intent);
    }

    public static void shareText(Context pContext, String pTexto) throws Exception {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, pTexto);
        shareIntent.setType("text/plain");
        pContext.startActivity(Intent.createChooser(shareIntent, "Compartilhar com"));
    }

    public static void shareScreen(Context pContext) throws Exception {
        View view = ((Activity) pContext).getWindow().getDecorView();

        //Cria print
        DisplayMetrics dm = pContext.getResources().getDisplayMetrics();
        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        view.draw(canvas);
        File fotoFinal = new File(getFilename(pContext));
        FileOutputStream outStream = new FileOutputStream(fotoFinal);
        returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        //Compartilhar
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fotoFinal));
        shareIntent.setType("image/jpeg");
        pContext.startActivity(Intent.createChooser(shareIntent, "Compartilhar com"));
    }

    public static void openNewApp(Context pContext, String pPackageName) throws Exception {
        Intent intent = pContext.getPackageManager().getLaunchIntentForPackage(pPackageName);
        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pPackageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pContext.startActivity(intent);
    }
}