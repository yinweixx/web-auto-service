package com.anyun.cloud.web.service.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JarCommon {
    private static JarCommon common;

    private JarCommon(){}

    public static JarCommon getCommon(){
        synchronized (JarCommon.class){
            if (common == null)
                common = new JarCommon();
            return common;
        }
    }

    public List<URL> resolveJarsByDirectory(String path) throws IOException {
        List<URL> allJarFileURLs = new ArrayList<>();
        resolveJarsByDirectory(path,allJarFileURLs);
        return allJarFileURLs;
    }

    private void resolveJarsByDirectory(String path,List<URL> allJarFileURLs) throws IOException {
        if(path == null || path.equals(""))
            throw new IOException("jar files path is null");
        File file = new File(path);
        if(!file.exists())
            return;
        if(file.isFile()){
            if(file.getName().endsWith(".jar"))
                allJarFileURLs.add(file.toURI().toURL());
        } else if(file.isDirectory()) {
            for(File afile : file.listFiles()){
                resolveJarsByDirectory(afile.getAbsolutePath(),allJarFileURLs);
            }
        }
    }
}
