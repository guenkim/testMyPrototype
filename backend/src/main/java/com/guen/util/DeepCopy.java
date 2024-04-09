package com.guen.util;

import com.guen.program.shop.model.entity.Category;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCopy {

    public static Object copy(Object obj){
        Object retObj = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            byte[] byteArray = bos.toByteArray();

            // 바이트 배열을 다시 역직렬화하여 새로운 객체 생성
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            ObjectInputStream in = new ObjectInputStream(bis);
            retObj = in.readObject();
        }catch (Exception e){
            e.getStackTrace();
        }
        return retObj;
    }
}
