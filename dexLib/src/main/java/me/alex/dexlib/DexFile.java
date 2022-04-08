package me.alex.dexlib;

import com.kongzue.baseokhttp.util.JsonMap;

/**
 * ================================================
 * <p>
 * Created by Alex on 2022/4/8
 * <p>
 * Description:
 * <p>
 * ================================================
 */
public class DexFile {
    public String getData(String packageName) {
        JsonMap map = new JsonMap();
        map.put("packageName", packageName);
        return map.toString();
    }
}
