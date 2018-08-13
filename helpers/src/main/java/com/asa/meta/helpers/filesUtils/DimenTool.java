package com.asa.meta.helpers.filesUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 快速生成适配工具类
 * 根据最小宽度"BASE_SMALL_WIDTH "dp 适配
 * <
 * 以160ppi为基准，160ppi对应的density是1.0，240ppi对应的density是1.5，320ppi对应的density是2.0…
 * 最关键的一点是，真机屏幕的ppi会取和以上标准最相近的标准ppi！然后density按照最相近的ppi来定
 * 例如，小米2s和Nexus 3都是720P的屏幕，一个4.3英寸一个4.7英寸，一个341ppi一个312ppi，都取最相近的320ppi为标准，他们的density是2.0。
 */
public class DimenTool {
    //根据最小宽度"BASE_SMALL_WIDTH "dp 适配
    public static int BASE_SMALL_WIDTH = 360;


    public static ArrayList<FileInfoController> fileInfoControllers;
    public static DecimalFormat mFormat = new DecimalFormat("0.00");

    public static void main(String[] args) {
        generate();
//        start();
//        startText();
    }

    public static void initFileInfoControllers() {
        fileInfoControllers = new ArrayList<>();
        fileInfoControllers.clear();
        fileInfoControllers.add(new FileInfoController(320));
        fileInfoControllers.add(new FileInfoController(360));
        fileInfoControllers.add(new FileInfoController(400));
        fileInfoControllers.add(new FileInfoController(411));
        fileInfoControllers.add(new FileInfoController(420));
        fileInfoControllers.add(new FileInfoController(432));
        fileInfoControllers.add(new FileInfoController(480));
        fileInfoControllers.add(new FileInfoController(600));
        fileInfoControllers.add(new FileInfoController(720));
        fileInfoControllers.add(new FileInfoController(800));
        fileInfoControllers.add(new FileInfoController(820));
    }


    public static String appendFilePath(int smallWidthDp) {
        return new StringBuilder().append("./helpers/src/main/res/values-sw").append(smallWidthDp).append("dp/dimens.xml").toString();
    }

    private static void startText() {
        for (int i = 0; i < 50; i++) {
            //  <dimen name="dp_0">0dp</dimen>

            System.out.println("<dimen name=\"sp_" + i + "\">" + i + "sp</dimen>");

        }
    }

    private static void start() {
        for (int i = -300; i < 301; i++) {
            //  <dimen name="dp_0">0dp</dimen>
            if (i < 0) {
                System.out.println("<dimen name=\"dp_neg_" + Math.abs(i) + "\">" + i + "dp</dimen>");
            } else {
                System.out.println("<dimen name=\"dp_" + i + "\">" + i + "dp</dimen>");
            }
        }
    }

    public static void generate() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./helpers/src/main/res/values/dimens.xml");

        BufferedReader reader = null;
        initFileInfoControllers();

        try {

            System.out.println("生成不同分辨率：");

            reader = new BufferedReader(new FileReader(file));

            String tempString;

            int line = 1;

            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {


                if (tempString.contains("</dimen>")) {

                    //tempString = tempString.replaceAll(" ", "");

                    String start = tempString.substring(0, tempString.indexOf(">") + 1);

                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));

                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。

                    for (FileInfoController fileInfo : fileInfoControllers) {
                        fileInfo.setFileContentStart(start, num, end);
                    }

                } else {
                    for (FileInfoController fileInfo : fileInfoControllers) {
                        fileInfo.setFileContentEnd(tempString);
                    }

                }

                line++;

            }

            reader.close();


            //将新的内容，写入到指定的文件中去

            for (FileInfoController fileInfo : fileInfoControllers) {
                writeFile(fileInfo.getFilePath(), fileInfo.fileContent.toString());
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                try {
                    System.out.println("已完成");
                    reader.close();

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

            }

        }

    }

    /**
     * 写入方法
     */

    public static void writeFile(String file, String text) {

        PrintWriter out = null;

        try {
            File temp = new File(file);
            if (!temp.exists()) {
                new File(file.substring(0, file.lastIndexOf("/"))).mkdirs();//创建目录
                new File(file).createNewFile();//创建文件
            }
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            out.println(text);

        } catch (IOException e) {

            e.printStackTrace();

        }


        out.close();

    }

    public static class FileInfoController {

        private String filePath;
        private StringBuilder fileContent = new StringBuilder();
        private int smallWidth;

        public FileInfoController(int smallWidth) {
            this.smallWidth = smallWidth;
            this.filePath = appendFilePath(smallWidth);
        }

        public String getFilePath() {
            return filePath;
        }

        public StringBuilder getFileContent() {
            return fileContent;
        }

        public void setFileContentStart(String start, double num, String end) {
            fileContent.append(start).append(mFormat.format(num * smallWidth / BASE_SMALL_WIDTH)).append(end).append("\r\n");
        }

        public void setFileContentEnd(String tempString) {
            fileContent.append(tempString).append("");
        }

        public int getSmallWidth() {
            return smallWidth;
        }


    }


}
