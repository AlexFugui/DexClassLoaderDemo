package me.alex.dexclassloaderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.FileUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private String dexName = "out1.dex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将assets下的文件放到data/data/包名/cache/目录下 模拟从服务器加载dex文件
        putAssetsToSDCard(dexName);
        //点击按钮显示dex文件中的内容
        findViewById(R.id.main_btn).setOnClickListener(v -> {
            loadDex("me.alex.dexlib.DexFile", "getData");
        });
    }

    /**
     * 加载dex
     */
    public void loadDex(String classPath, String methodName) {
        DexClassLoader loader = new DexClassLoader(
                getCacheDir().getAbsolutePath() + File.separator + dexName
                , getCacheDir().getAbsolutePath() + File.separator + dexName
                , null, getClassLoader()
        );
        try {
            Class<?> clz = loader.loadClass(classPath);
            Method dexRes = clz.getDeclaredMethod(methodName, String.class);
            Toast.makeText(this, (String) dexRes.invoke(clz.newInstance(), getPackageName()), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将assets下的文件放到data/data/包名/cache/目录下
     */
    public void putAssetsToSDCard(String strOutFileName) {
        try {
            InputStream myInput;
            OutputStream myOutput = null;
            myOutput = new FileOutputStream(getCacheDir() + File.separator + strOutFileName);

            myInput = this.getAssets().open(dexName);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }

            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}