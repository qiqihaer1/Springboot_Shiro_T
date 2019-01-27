package com.symbio.bigdata.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class sortUtil {

    public static void main(String[] args){
//        Map map1 = new LinkedHashMap<>();
//        map1.put("rate","0.4");
//        map1.put("hc",20);
//        map1.put("hcC",12);
//
//        Map map2 = new LinkedHashMap<>();
//        map2.put("rate","0.25");
//        map2.put("hc",54);
//        map2.put("hcC",7);
//
//       Map map3 = new LinkedHashMap<>();
//        map3.put("rate","0.77");
//        map3.put("hc",25);
//        map3.put("hcC",22);
//        List<Map<String,Object>> list = Arrays.asList(map1, map2, map3);
//        System.out.println(list.toString());
//        sortMap(list, "rate");
//        System.out.println(list.toString());
        String str="13/01/2019";
        LocalDate date = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(date);

    }

    /**
     * desc
     * @param list
     * @param field
     * @return
     */
    public static void sortMap(List<Map<String,Object>> list,String field){
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(o1.get(field) instanceof String ){
                    Double v1 = Double.valueOf(o1.get(field).toString());
                    Double v2 = Double.valueOf(o2.get(field).toString());
                    return v2>v1?1:-1;
                }else if(o1.get(field) instanceof Integer){
                    int v1 = (Integer) o1.get(field);
                    int v2 = (Integer) o2.get(field);
                    return v2 - v1;
                }
                return 0;
            }
        });
    }




}
