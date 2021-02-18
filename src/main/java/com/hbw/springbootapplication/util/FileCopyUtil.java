package com.hbw.springbootapplication.util;

import java.io.*;

public class FileCopyUtil {

    public static void main(String[] args) {
//        copy("F:/springboot_project/receipt_manage/upload/32101735.jpg","F:/springboot_project/receipt_manage/upload/非固定资产/32101735.jpg");
        File file = new File("F:/springboot_project/receipt_manage/upload1/");
        deleteAll(file);
    }

    private static void copy(String src,String target) {
        //创建源文件，和目标文件
        File srcFile = new File(src);
        File targetFile = new File(target);
        //创建输入输出流
        InputStream in =  null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(targetFile);
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len = in.read(bytes))!=-1) {
                out.write(bytes,0,len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null) in.close();
                if(out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteAll(File file)
    {
        if(file.isFile() || file.list().length == 0)
        {
            file.delete();
        }
        else
        {
            File[] files = file.listFiles();
            for(File f : files)
            {
                deleteAll(f);//递归删除每一个文件
                f.delete();//删除该文件夹
            }
        }
    }

}
