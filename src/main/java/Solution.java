import com.google.gson.Gson;
import javafx.util.Builder;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        Map<String, Banana> map = new HashMap<>();
        map.put("1111", new Banana(1, 1));
        map.put("2222", new Banana(2, 2));
        map.put("3333", new Banana(3, 3));
        //String mapStr = map.toString();

        String mapStr = "{general_booking.unified_book.supply.default-ProductPPDiff=DiffResponse(diffStatusEnum=SUCCEED, errorMsg=null, url=http://unity.nibscp.test.sankuai.com/diff-result-detail?taskId=1377869225934200886, diffId=0), general_booking.unified_book.supply.default-ProductScpDiff=DiffResponse(diffStatusEnum=EXCEPTION, errorMsg=执行比对逻辑异常!com.meituan.nibscp.unity.validation.service.exception.JsonSourceException: thrift json source error! config = null params = SourceParam(param={$subjectId$=403138014}) ipOrDomain = OCTO, url=null, diffId=0)}";
        System.out.println(strToMap(mapStr));

    }

    public static String strToMap(String mapStr) {
        mapStr = mapStr.substring(1, mapStr.length() - 1);
        StringBuilder builder = new StringBuilder();
        int leftCnt = 0;
        String pairLeft = null;
        String pairRight = null;
        Map<String, String> anotherMap = new HashMap<>();
        for(int i = 0; i < mapStr.length(); i++) {
            char c = mapStr.charAt(i);
            if(leftCnt == 0 && mapStr.charAt(i) == ',') {
                pairRight = builder.toString();
                anotherMap.put(pairLeft, pairRight);
                builder = new StringBuilder();
            } else if(leftCnt == 0 && mapStr.charAt(i) == '=') {
                pairLeft = builder.toString().trim();
                builder = new StringBuilder();
            } else {
                if(c == '(') leftCnt++;
                else if(c == ')') leftCnt--;
                builder.append(c);
            }
        }
        if(!builder.toString().isEmpty()) {
            pairRight = builder.toString().trim();
            anotherMap.put(pairLeft, pairRight);
        }

        System.out.println(anotherMap);

        Iterator<String> iterator = anotherMap.keySet().iterator();
        while(iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
            System.out.println(anotherMap.get(next));
        }

        Gson gson = new Gson();
        String json = gson.toJson(anotherMap);
        System.out.println(json);
        return json;
    }
}

class Banana {
    int weight;
    int price;

    public Banana(int weight, int price) {
        this.weight = weight;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Banana(" +
                "weight=" + weight +
                ", price=" + price +
                ')';
    }
}
