package tc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaoyanji on 2016/7/7.
 */
public class StringUtils {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
