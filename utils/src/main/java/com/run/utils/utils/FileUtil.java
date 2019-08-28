package com.run.utils.utils;

import android.os.Environment;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * 作者：Silence
 * 时间：2019/5/20 18:02
 * 邮箱：2463423656@qq.com
 */
public class FileUtil {

    // 生成文件夹
    public static void makeDir(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.e("error:", e+"");
        }
    }

    // 生成文件
    public static File makeFile(String filePath, String fileName) {
        File file = null;
        makeDir(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 将字符串写入到文件中
    public static void writeFile(String content, String filePath, String fileName) {
        //makeFile(filePath, fileName);
        String strFilePath = filePath+fileName;
        try {
            File file = new File(strFilePath);
            file.getParentFile().mkdirs();
            //覆盖原文件
            file.createNewFile();

            //连续写入
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(content.getBytes());
            raf.close();

//            FileOutputStream fs = new FileOutputStream(file);
//            byte[] b = content.getBytes();
//            fs.write(b);
//            fs.flush();
//            fs.close();

//            FileWriter fileWriter =new FileWriter(file);
//            fileWriter.write("");
//            fileWriter.flush();
//            fileWriter.close();

        } catch (Exception e) {
            Log.e("TAG", "Error on write MFile:" + e);
        }
    }

    //读文件数据
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            return "File not found!";
        }
        FileInputStream fs;
        String result = null;
        try {
            fs = new FileInputStream(f);
            byte[] b = new byte[fs.available()];
            fs.read(b);
            fs.close();
            result = new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    //文件后面增加
    public static boolean append(String fileName, String fileContent) {
        boolean result = false;
        File f = new File(fileName);
        try {
            if (f.exists()) {
                FileInputStream fsIn = new FileInputStream(f);
                byte[] bIn = new byte[fsIn.available()];
                fsIn.read(bIn);
                String oldFileContent = new String(bIn);
                //System.out.println("旧内容:" + oldFileContent);
                fsIn.close();
                if (!oldFileContent.equalsIgnoreCase("")) {
                    fileContent = oldFileContent + "\r\n" + fileContent;
                    //System.out.println("新内容:" + fileContent);
                }
            }

            FileOutputStream fs = new FileOutputStream(f);
            byte[] b = fileContent.getBytes();
            fs.write(b);
            fs.flush();
            fs.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //清除文件内容
    public static void clearFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean delete(String fileName){
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()){
            try{
                result = f.delete();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            result = true;
        }
        return result;
    }

    /**
     * 复制单个文件
     * @param oldPath
     * String 原文件路径 如：c:/fqf.txt
     * @param newPath
     * String 复制后路径 如：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            //如果文件夹不存在 则建立新文件夹
            (new File(newPath)).mkdirs();
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
    }


    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 从指定文件夹获取文件
     *
     * @return 如果文件不存在则创建, 如果如果无法创建文件或文件名为空则返回null
     */
    public static File getFile(String folderPath, String fileNmae) {
        File file = new File(getPath(folderPath) + File.separator
                + fileNmae);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取SD卡下指定文件夹的绝对路径
     *
     * @return 返回SD卡下的指定文件夹的绝对路径
     */
    public static String getPath(String folderName) {
        return getFolder(folderName).getAbsolutePath();
    }

    /**
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getFolder(String folderName) {
        File file = new File(getExternalStorageDirectory()
                .getAbsoluteFile()
                + File.separator
                + folderName
                + File.separator);
        file.mkdirs();
        return file;
    }

    /**
     * 关闭流
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取单个文件的MD5值！
     * @param file
     * 文件
     * @return MD5值
     */
    public static String getFileMD5(File file){
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获取文件夹中文件的MD5值
     * @param file
     * 文件目录
     * @param listChild
     * 是否递归子目录中的文件
     * @return Map
     */
    public static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getDirMD5(f, listChild));
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

}
