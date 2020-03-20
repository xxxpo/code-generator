package com.xxxlin.core.utils;

import com.xxxlin.core.config.ResultStatus;

import java.util.HashMap;
import java.util.Map;

public class TextUtils {

    private static final char[] PASSWORD = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+[]{},:;"
            .toCharArray();
    private static final String[] SUFFIX = {
            ".9.png"
    };

    /**
     * 验证密码
     *
     * @param psw 密码
     */
    public static boolean pswValidate(String psw) {
        for (char ch : psw.toCharArray()) {
            if (!contain(PASSWORD, ch)) {
                return false;
            }
        }
        return true;
    }

    private static boolean contain(char[] ary, char ch) {
        for (char item : ary) {
            if (item == ch) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the parameter is null or of zero length
     */
    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    /**
     * Returns true if the parameter is null or contains only whitespace
     */
    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @since 4.4
     */
    public static boolean containsBlanks(final CharSequence s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件后缀
     *
     * @param name
     * @return
     */
    public static String getFileSuffix(String name) {
        if (isEmpty(name) || !name.contains(".")) {
            return "";
        }
        for (String suffix : SUFFIX) {
            if (name.endsWith(suffix)) {
                return suffix;
            }
        }
        int index = name.lastIndexOf('.');
        return name.substring(index);
    }

    public static String fileNamePlus(String name) {
        int pos = name.indexOf('.');
        if (pos > -1) {
            return name.substring(0, pos) + "_" + UUID19Util.uuid() + name.substring(pos);
        } else {
            return name + "_" + UUID19Util.uuid();
        }
    }

    /**
     * 取出URL中的哈希值
     *
     * @param url
     * @return e.g. #login/callback
     */
    public static String getUrlHash(String url) {
        int index = url.indexOf('#');
        if (index > -1) {
            return url.substring(index);
        }
        return "";
    }

    /**
     * 获取URL中所有的参数
     *
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParams(String url) {
        int endIndex = url.length();
        if (url.contains("#")) {
            endIndex = url.indexOf('#');
        }
        url = url.substring(url.indexOf('?') + 1, endIndex);
        Map<String, String> map = new HashMap<>();
        String[] ary = url.split("&");
        for (String row : ary) {
            if (TextUtils.isEmpty(row) || !row.contains("=")) {
                continue;
            }
            String[] ps = row.split("=");
            if (ps.length == 1) {
                map.put(ps[0], "");
            } else if (ps.length > 1) {
                map.put(ps[0], ps[1]);
            }
        }
        return map;
    }

    /**
     * 给URL添加参数,如果已存在该参数则覆盖
     *
     * @param url
     * @param key
     * @param val
     * @return
     */
    public static String appendUrlParam(String url, String key, String val) {
        String hash = getUrlHash(url);
        Map<String, String> params = getUrlParams(url);
        params.put(key, val);// 添加参数

        int hostEnd = url.length();
        if (url.contains("?")) {
            hostEnd = url.indexOf('?');
        } else if (url.contains("#")) {
            hostEnd = url.indexOf('#');
        }
        String host = url.substring(0, hostEnd);

        StringBuilder sb = new StringBuilder(host);
        sb.append('?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        if(sb.length() > 0 && sb.charAt(sb.length()-1) == '&'){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(hash);
        return sb.toString();
    }

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 转换为小驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            underscoreName = underscoreName.toLowerCase();// 转小写
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ('_' == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * 第一个字母转大写
     *
     * @param str
     * @return
     */
    public static String toFirstUp(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 表名转实体名
     *
     * @param tableName
     * @return
     */
    public static String toEntityName(String tableName) {
        StringBuilder sb = new StringBuilder();
        String[] ary = tableName.split("_");
        for (String row : ary) {
            sb.append(toFirstUp(row));
        }
        return sb.toString();
    }

}
