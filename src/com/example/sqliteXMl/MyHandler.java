package com.example.sqliteXMl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.sqlitehelper.MyMusicData;

public class MyHandler extends DefaultHandler {
	
	private MyMusicData mmd;
	private StringBuffer sb;
	
	public MyMusicData getData()
	{
		return mmd;
	}
//	
	
	
    @Override
    public void startDocument() throws SAXException {
    	// TODO Auto-generated method stub
    	super.startDocument();
    	sb=new StringBuffer();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
    		Attributes attributes) throws SAXException {
    	// TODO Auto-generated method stub
    	super.startElement(uri, localName, qName, attributes);
    	
    	if(localName.equals("Music"))
    	{
    		mmd=new MyMusicData();
    	}
    	sb.setLength(0);
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
    		throws SAXException {
    	// TODO Auto-generated method stub
    	super.characters(ch, start, length);
    	sb.append(ch, start, length);
    	
    	
    }
    
    /*
	 *   id
	 *   title 歌曲名称
	 *   album 专辑名
	 *   artist 歌手名
	 *   data 歌曲文件路径
	 *   duration 时长
	 *   size 文件大小
	 *   album_id 专辑ID
	 */
    
    @Override
    public void endElement(String uri, String localName, String qName)
    		throws SAXException {
    	// TODO Auto-generated method stub
    	super.endElement(uri, localName, qName);
    	
    	if(localName.equals("id"))
    	{
    		mmd.setId(Integer.valueOf(sb.toString()));
    	}else if(localName.equals("title"))
    	{
    		mmd.setTitle(sb.toString());
    	}
    	else if(localName.equals("album"))
    	{
    		mmd.setAlbum(sb.toString());
    	}
    	else if(localName.equals("artist"))
    	{
    		mmd.setArtist(sb.toString());
    	}
    	else if(localName.equals("data"))
    	{
    		mmd.setData(sb.toString());
    	}
    	else if(localName.equals("duration"))
    	{
    		mmd.setTime(Long.valueOf(sb.toString()));
    	}else if(localName.equals("size"))
    	{
    		mmd.setSize(Long.valueOf(sb.toString()));
    	}
    	else if(localName.equals("album_id"))
    	{
    		mmd.setAlbum_id(Integer.valueOf(sb.toString()));
    	}
    	else if(localName.equals("Music"))
    	{
    		
    	}
    	
    	
    }
    
    @Override
    public void endDocument() throws SAXException {
    	// TODO Auto-generated method stub
    	super.endDocument();
    }
}
