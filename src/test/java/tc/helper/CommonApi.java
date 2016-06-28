package tc.helper;

import net.sf.json.JSONObject;

import java.security.MessageDigest;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class CommonApi {
    public static String get_ErrorMsg(JSONObject json){
        String err_msg = json.getJSONObject("status").getString("error_msg");
        return err_msg;
    }

    public static int get_ErrorCode(JSONObject json){
        int error_code = json.getJSONObject("status").getInt("error_code");
        return error_code;
    }

    public static Long get_UnixTime(){
        return System.currentTimeMillis();
    }

    public static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32位: " + buf.toString());// 32位的加密
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
