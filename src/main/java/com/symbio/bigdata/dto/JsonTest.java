package com.symbio.bigdata.dto;





//import com.alibaba.fastjson.JSONArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonTest {

    public static void main(String[] args) {
        String test = test("UBERTV");
        System.out.println(test);
    }

    public static String test(String str) {
        BufferedReader bufferedReader=null;
        String lobDisplayName=null;
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:json/lobConfiguration.json");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream inputStream = resource.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str1=null;
            while ((str1=bufferedReader.readLine())!=null){
                stringBuffer.append(str1);
            }
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject = jsonArray.getJSONObject(i);
                String lobName = jsonObject.getString("name");
                String displayName = jsonObject.getString("displayName");
                if(str.equalsIgnoreCase(lobName)){
                    lobDisplayName=displayName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lobDisplayName;
    }

}
