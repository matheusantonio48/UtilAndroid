package com.viana.androidutil.io;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by joao.viana on 21/02/2018.
 */

public class FileIo {
    public static String getFilePath(Context pContext) throws Exception {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + pContext.getPackageName()
                + "/Files/Compressed");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();

        return (mediaStorageDir.getAbsolutePath() + "/");
    }

    public static String getFilename(Context pContext) throws Exception {
        String mImageName = "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        return getFilePath(pContext) + mImageName;
    }
}