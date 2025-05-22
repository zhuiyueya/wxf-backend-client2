package top.xcyyds.wxfbackendclient.util;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpDeserializer;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.JsonpUtils;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import jakarta.json.stream.JsonParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version:
 */

public class EsSearchAfterUtil {
    private static final JsonpMapper mapper=new JacksonJsonpMapper();

    /*
     * @Description: 将searchAfter的值转换为字符串
     */
    public static String serialize(List<FieldValue> searchAfter){
        List<Object>rawValues=new ArrayList<>();
        for (FieldValue value:searchAfter){
            if (value.isString()){
                rawValues.add(value.stringValue());
            } else if (value.isLong()) {
                rawValues.add(value.longValue());
            }else if (value.isDouble()){
                rawValues.add(value.doubleValue());
            }else if ((value.isBoolean())){
                rawValues.add(value.booleanValue());
            }else if (value.isAny()){
                rawValues.add(value.anyValue());
            }
        }
        return JsonpUtils.toJsonString(rawValues, mapper);
    }
    /*
     * @Description: 将字符串转换为searchAfter的值
     */
    public static List<FieldValue> deserialize(String searchAfter) {
        if (searchAfter==null||searchAfter.isEmpty()){
            return null;
        }
        try(JsonParser parser=mapper.jsonProvider().createParser(new StringReader(searchAfter))){
            return JsonpDeserializer.arrayDeserializer(FieldValue._DESERIALIZER).deserialize(parser, mapper);
        }catch (Exception e){
            throw new RuntimeException("EsSearchAfterUtil反序列化失败"+e);
        }
    }
}
