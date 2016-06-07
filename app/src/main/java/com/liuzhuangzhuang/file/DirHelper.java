package com.liuzhuangzhuang.file;

import android.os.Environment;
import android.util.SparseArray;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * liuzhuangzhuang
 * 2016-06-07
 */
public class DirHelper {

    private SparseArray<List<String>> sparseArray = new SparseArray<>();
    private static int order = 0; // 几级目录 参考 http://blog.csdn.net/gangwazi0525/article/details/7569701

    // 获得目录结构
    public SparseArray<List<String>> getDirectoryStructure() {
        try {
            printDirectoryStructure(Environment.getRootDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sparseArray;
    }

    // 打印目录结构
    private void printDirectoryStructure(File file) throws IOException {
        saveFileName(file);

        if (file.isDirectory()) {
            String[] fileNames = file.list(new DirectoryFilter());
            if (fileNames != null && fileNames.length > 0) {
                for (int i = 0; i < fileNames.length; i++) {
                    File subFile = new File(file.getCanonicalPath() + File.separator + fileNames[i]);
                    order++; // 进入下层级目录加1
                    printDirectoryStructure(subFile);
                    order--; // 退出层级目录减1
                }
            }
        }
    }

    // 添加文件名或目录名
    private void saveFileName(File file) {
        List<String> list;
        if ((list = sparseArray.get(order)) != null) {
            list.add(file.getName());
        } else {
            list = new ArrayList<>();
            list.add(file.getName());
            sparseArray.put(order, list);
        }
    }

    // 文件过滤
    private class DirectoryFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            // 不显示隐藏目录和隐藏文件
            // 在 linux unix 下.开头是隐藏文件, windows 下不是
            return !dir.isHidden();
        }
    }


}
