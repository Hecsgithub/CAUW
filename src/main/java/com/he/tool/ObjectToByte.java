package com.he.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectToByte {

	public static byte[] ser(Object obj) throws IOException{
		//接收被写入的字节数组
		ByteArrayOutputStream bos= new ByteArrayOutputStream();
		//把对象序列化成字节数组
		ObjectOutputStream oos= new ObjectOutputStream(bos);
		//写入
		oos.writeObject(obj);
		return bos.toByteArray();
	}
	//反序列化
	public static Object dser(byte[] src) throws Exception{
		//从字节数组读取数据
		ByteArrayInputStream bis = new ByteArrayInputStream(src);
		//把字节数组反序列化成对象
		ObjectInputStream ois= new ObjectInputStream(bis);
		return ois.readObject();	
	}
	
}
