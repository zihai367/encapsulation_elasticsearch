package com.sinux.parse.base;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin
 * @date 2019-08-21 10:03
 */
public class CenterConfig {
    Map<String, Map<String,Object>> map = new HashMap<String, Map<String, Object>>();


    public Map parseFile(){
        try {
            //获取当前项目resource文件的绝对路径
            String s = CenterConfig.class.getResource("/").toString();
            //剪切掉头部名称，只剩路径
            String abPath = s.substring(s.indexOf("/")+1);
            //通过路径创建文件io
            File parentFile = new File(abPath);
            //获取当前文件路径下所有的子文件
            File[] files = parentFile.listFiles();
            //遍历所有子文件
            for (File file : files) {
                //判断是否为文件（不是文件夹）
                if(file.isFile()){
                    String fileName = file.getName();
                    //获取当前文件的后缀名
                    String suf = fileName.substring(fileName.indexOf(".")+1);


                    //转换把头字母小写转换为大写
                    char[] chars = suf.toCharArray();
                    chars[0] = (char) (chars[0] - 32);
                    String classSuf = String.valueOf(chars);
                    //生成类名
                    String className = "Parse".concat(classSuf);

                    //判断配置文件的在项目里是否存在对应的解析类
                    if (isExist(abPath,className)){
                        //生成项目的路径
                        String projectPath = "com.sinux.parse.".concat(className);
                        //通过类路径找到类
                        Class<?> cla = Class.forName(projectPath);
                        //实例化类
                        Object o = cla.newInstance();
                        //获取解析方法
                        Method parseMethod = cla.getMethod("parse",File.class);
                        //执行方法
                        Map<String,Object> parseMap = (Map<String, Object>) parseMethod.invoke(o,file);
                        //将解析出来的map装到总Map里
                        map.put(fileName,parseMap);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 判断送过来的类名是否在包里面存在，存在则返回true
     * @param abPath
     * @param className
     * @return
     */
    public boolean isExist(String abPath,String className){
        boolean isExist = false;
        File file = new File(abPath.concat("/com/sinux/parse"));
        File[] files = file.listFiles();
        for (File file1 : files) {

            //判断是否为文件
            if (file1.isFile()){
                //获取文件名
                String name = file1.getName();
                //将文件名剪切掉后缀
                String fileName = name.substring(0,name.indexOf("."));

                if(fileName.equals(className)){
                    isExist = true;
                }
            }

        }


        return isExist;
    }
}
