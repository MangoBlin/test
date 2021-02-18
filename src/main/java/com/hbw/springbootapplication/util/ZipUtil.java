package com.hbw.springbootapplication.util;

import com.hbw.springbootapplication.entity.Receipt;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;


public class ZipUtil {

    public static void toZip(List<File> srcFiles, File zipFile) throws RuntimeException {
        long start = System.currentTimeMillis();
        if(zipFile == null){
            System.out.println("压缩包文件名为空！");
            return;
        }
        if(!zipFile.getName().endsWith(".zip")){
            System.out.println("压缩包文件名异常，zipFile={}");
            System.out.println(zipFile.getPath());
            return;
        }
        ZipOutputStream zos = null;
        try {
            FileOutputStream out = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[1024];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.setComment("我是注释");
                zos.closeEntry();
                in.close();
                out.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("zipFile error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {

        long start = System.currentTimeMillis();

        ZipOutputStream zos = null ;

        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void zipFiles(List<File> srcfile, String zipFileName, HttpServletResponse response) {

        byte[] buf = new byte[1024];
        // 获取输出流
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream in = null;
        ZipOutputStream out = null;
        try {
            response.reset(); // 重点突出
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + zipFileName + ".zip");

            // ZipOutputStream类：完成文件或文件夹的压缩
            out = new ZipOutputStream(bos);
            for (int i = 0; i < srcfile.size(); i++) {
                in = new FileInputStream(srcfile.get(i));
                // 给列表中的文件单独命名
                out.putNextEntry(new ZipEntry(srcfile.get(i).getName()));
                int len = -1;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }
            out.close();
            bos.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
        throws RuntimeException{
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

    //递归删除文件夹下的所有.jpg文件和.zip文件
    public static boolean deleteFolder(String url){
        File file=new File(url);
        if(!file.exists()){
            return false;
        }
        if(file.isFile()){
            if (file.getName().endsWith(".jpg")||file.getName().endsWith(".zip")){
                file.delete();
            }
            return true;
        }else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String root = files[i].getAbsolutePath();
                deleteFolder(root);
            }
//            file.delete();
            return true;
        }
    }

    public static boolean deleteAllDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteAllDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    //将/upload目录下的所有查询的发票转移到/zipupload目录下
    public static void transferFiles(List<Receipt> receiptList){

        String sourceDir= "F:/springboot_project/receipt_manage/upload";
        String targetDir = "F:/springboot_project/receipt_manage/uploadzip";
        for (Receipt receipt:receiptList){

            String filePath = "F:/springboot_project/receipt_manage/upload/"+receipt.getReceiptNum()+".jpg";
            String targetPath="";
            File file = new File(filePath);
            if (file.exists()){
                String firDir = receipt.getAssertClassFirdir();
                String secDir = receipt.getAssertClassSecdir();
                if ("0".equals(receipt.getFixedAssets())){
                    targetPath="F:/springboot_project/receipt_manage/uploadzip/非固定资产";
                }else if("1".equals(receipt.getFixedAssets())){
                    if (secDir!=null&&secDir!=""&&firDir!=null&&firDir!=""){
                        if(firDir.equals(secDir)){
                            targetPath="F:/springboot_project/receipt_manage/uploadzip/固定资产/"+firDir;
                        }else {
                            targetPath="F:/springboot_project/receipt_manage/uploadzip/固定资产/"+firDir+"/"+secDir;
                        }
                    }else if(firDir!=null&&firDir!=""){
                        targetPath="F:/springboot_project/receipt_manage/uploadzip/固定资产/"+firDir;
                    }
                }else if("2".equals(receipt.getFixedAssets())){
                    targetPath="F:/springboot_project/receipt_manage/uploadzip/无形资产/"+firDir;
                }
                System.out.println(file.getName()+"----------"+targetPath);
                File toFile=new File(targetPath+"/"+file.getName());
                if(toFile.exists()){
                }else {
//                    file.renameTo(toFile);
                    copyFile(file,toFile);
                }
            }
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
//        transferFiles();
//        FileOutputStream fos1 = new FileOutputStream(new File("F:/springboot_project/receipt_manage/zipDir/receipt.zip"));
//        ZipUtil.toZip("F:/springboot_project/receipt_manage/uploadzip", fos1,true);
//        deleteFolder("F:/springboot_project/receipt_manage/uploadzip");
//        deleteFolder("F:/springboot_project/receipt_manage/zipDir");

//        File src = new File("F:/springboot_project/receipt_manage/uploadzip");
//        File dest = new File("F:/springboot_project/receipt_manage/uploadTrueZip");
//        cDirectrory(src,dest);
//        delEmpthDir(dest);

        deleteAllDir(new File("F:/springboot_project/receipt_manage/uploadTrueZip/uploadzip"));
    }

    //递归拷贝文件夹并删除目标文件夹下的所有空文件夹
    public static void copytAndDelEmptyDir(){
        File src = new File("F:/springboot_project/receipt_manage/uploadzip");
        File dest = new File("F:/springboot_project/receipt_manage/uploadTrueZip");
        cDirectrory(src,dest);
        delEmpthDir(dest);

    }

    public static void delEmpthDir(File f){
        for (File f1:f.listFiles()){
            if(f1.isDirectory()){
                delEmpthDir(f1);
                //一直递归到最后的目录
                if(f1.listFiles().length==0){
                    //如果是文件夹里面没有文件证明是空文件，进行删除
                    f1.delete();
                }
            }
        }
    }


    public static void cDirectrory(File src,File dest) {
        if(src.isDirectory()) {//如果是文件夹
            File newDirectory=new File(dest.getAbsoluteFile()+"/"+src.getName());//新文件夹的名称
            newDirectory.mkdirs();//创建新文件夹
            for(File file:src.listFiles()) {//递归调用
                cDirectrory(file, newDirectory);
            }
        }else if(src.isFile()) {//如果是文件
            //选择流
            InputStream in=null;
            OutputStream out=null;
            try {
                //读入
                in=new FileInputStream(src);
                //写出
                out=new FileOutputStream(dest.getAbsoluteFile()+"/"+src.getName());
                byte[] write=new byte[1024];//每次读取1k，写入1k
                int len=-1;
                while((len=in.read(write))!=-1) {
                    out.write(write,0,len);
                }
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                if(out!=null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(in!=null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if(src==null||!src.exists()) {//如果文件不存在
            return;
        }
    }


    private static void copyFile(File source, File dest) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadFile(HttpServletResponse response, String fileName) {
        File path = null;
        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
//            path = new File(ResourceUtils.getURL("classpath:").getPath());

            path = new File("F:/springboot_project/receipt_manage/zipDir/receipt.zip");

            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(path));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            //e1.getMessage()+"系统找不到指定的文件";
            return ;
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}