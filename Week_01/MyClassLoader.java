package com.time.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

	public static void main(String[] args) throws Exception {
		MyClassLoader myLoader = new MyClassLoader();
		Class<?> cls = myLoader.findClass("Hello.xlass");
		Object obj = cls.newInstance();
		Method method = cls.getMethod("hello", null);
		method.invoke(obj, null);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("/resources/" + name); // ��/��ͷ��ʾclass��Ŀ¼��src���������/��ͷ���ʾ��ǰ�ļ�����Ŀ¼
			byte[] b = new byte[in.available()];
			in.read(b);
			for(int i = 0; i < b.length; i++) {
				b[i] = (byte) (255 - b[i]);
			}
			return defineClass("Hello", b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

}
