package com.example.sqliteXMl;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import com.example.sqlitehelper.MyMusicData;

public class MyXmlTool implements XmlTool {

	@Override
	public MyMusicData parse(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		MyMusicData mmd=new MyMusicData();
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		MyHandler mh=new MyHandler();
		parser.parse(is, mh);
		
		
		
		return mh.getData();
	}

	@Override
	public String serialize(MyMusicData mmd) throws Exception {
		// TODO Auto-generated method stub
		//
        SAXTransformerFactory stff=(SAXTransformerFactory) TransformerFactory.newInstance();
        TransformerHandler handler=stff.newTransformerHandler();
        Transformer former=handler.getTransformer();
        former.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//设置输出的编码方式
		former.setOutputProperty(OutputKeys.INDENT, "yes");//是否自动添加额外的空白
		former.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");//是否忽略xml声明
		
		StringWriter writer=new StringWriter();
		Result result=new StreamResult(writer);
		handler.setResult(result);
		
		String uri="";
		String localName="";
		
		handler.startDocument();
		handler.startElement(uri, localName, "Music", null);
		char [] c=null;
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
		
		handler.startElement(uri, localName, "id", null);
		c=String.valueOf(mmd.getId()).toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "id");
		
		handler.startElement(uri, localName, "title", null);
		c=mmd.getTitle().toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "title");
		
		handler.startElement(uri, localName, "album", null);
		c=mmd.getAlbum().toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "album");
		
		handler.startElement(uri, localName, "artist", null);
		c=mmd.getArtist().toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "artist");
		
		
		
		
		handler.startElement(uri, localName, "data", null);
		c=mmd.getData().toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "data");
		
		
		
		handler.startElement(uri, localName, "duration", null);
		c=String.valueOf(mmd.getTime()).toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "duration");
		
		handler.startElement(uri, localName, "size", null);
		c=String.valueOf(mmd.getSize()).toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "size");
		
		handler.startElement(uri, localName, "album_id", null);
		c=String.valueOf(mmd.getAlbum_id()).toCharArray();
		handler.characters(c, 0, c.length);
		handler.endElement(uri, localName, "album_id");
		
		handler.endElement(uri, localName, "Music");
		handler.endDocument();
		
		String s=writer.toString();
		writer.close();
		return s;
	}

}
