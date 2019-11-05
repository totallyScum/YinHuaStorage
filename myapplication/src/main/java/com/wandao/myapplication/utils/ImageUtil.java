package com.wandao.myapplication.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {
    private static ImageUtil s=null;
    private ImageUtil()
    {}
    public static  ImageUtil getInstance()
    {
        if(s==null)//和上面的相比只是多增加了一次判断
        {
            synchronized(ImageUtil.class)
            {
                if(s==null)
                    s=new ImageUtil();
                return s;
            }
        }
        return s;
    }
    public List<String> getPictures(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for (int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if (fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }


}
