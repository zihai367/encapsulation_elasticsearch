package com.sinux.tools;

/**
 * 枚举名转换为方法名工具类
 *
 * @author kevin
 * @date 2019-09-20 13:51
 */
public class EnumNameChangeToMethodNameTool {

    public static String changeName(String enumName){
        char[] chars = enumName.toCharArray();
        String methodName = "";

        for (int i = 0;i < chars.length;i++){
            if (chars[i] != 95){
                chars[i] = (char)(chars[i] + 32);
                methodName = methodName.concat(String.valueOf(chars[i]));
            }else {
                chars[i+1] = (char) (chars[i+1] - 32);
            }
        }
        return methodName;
    }




}
