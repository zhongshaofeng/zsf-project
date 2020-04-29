package com.zsf.test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author zhongshao
 * @Description
 * @Date 2020/4/29 23:02
 */
public class TestDemo {

    private Integer id;

    private String name;

    private Date dateTime;

    public TestDemo() {
    }

    public TestDemo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public static void main(String[] args) {

        TestDemo testDemo = new TestDemo();

        Class c = testDemo.getClass();

        Field [] field = c.getDeclaredFields();

        Field [] fields = c.getFields();

        System.out.println(field[2].getType().toString());

        String[] fieldNames=new String[field.length];
        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<field.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", field[i].getType().toString());
            infoMap.put("name", field[i].getName());
            //infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }

        System.out.println(1111);

    }

}
