package com.example.sqliteXMl;

import java.io.InputStream;

import com.example.sqlitehelper.MyMusicData;

public interface XmlTool {
  /*
   *  解析xml文件  用的是SAXpares 方法
   *  
   */
	
	public  MyMusicData parse(InputStream is) throws Exception;
	
	/**
	 * 
	 * @param mmd  
	 * @throws Exception
	 */
	public String serialize(MyMusicData mmd) throws Exception;
}
