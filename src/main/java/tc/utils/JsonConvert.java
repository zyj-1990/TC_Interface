package tc.utils;

import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyanji on 2016/7/6.
 */
public class JsonConvert {
    public static List<Parameter> mapToKV(Map m){
        String[] array = JSONObject.fromObject(m).toString().split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < array.length; i++) {
            String[] keyValue = array[i].split(":");
            String[] temp = keyValue[0].split("\"");
            String key = temp[1];
            String[] temp1 = keyValue[1].split("\"");
            if(temp1.length > 0){
                String value = temp1[0].length() == 0 ? temp1[1] : null;
                if (value != null) {
                    map.put(key, value);
                }
            }
        }
        List<Parameter> para = new ArrayList<Parameter>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            para.add(new Parameter(entry.getKey(), entry.getValue()));
        }
        return para;
    }
}
