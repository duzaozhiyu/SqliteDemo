package com.example.sqlitehelper;
/**
 * 
 * @author duzaozhiyu
 * 查询接口
 *  Cursor query(Uri rui,String [] projection,String selection,String[] selectionArgs,String sortOrder)
 *  Uri 指顶要查询数据库表明
 *  Projection:指定查询数据库表中的那几列，返回的游标中将包括相应的信息。Null则返回所有信息
 *  selection 指定查询条件
 *  selectionArgs: selection 有？就用到
 *  SortOrder: 指定查询结果排列顺序
 */
public class MyMusicData {
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
     private String title;
     private int id;
    
	 private String album;
     private String data;
     private long size;
     private long time;
     private String artist;
     private int album_id;
     
     public String getArtist() {
 		return artist;
 	}
 	public void setArtist(String artist) {
 		this.artist = artist;
 	}
 	public int getAlbum_id() {
 		return album_id;
 	}
 	public void setAlbum_id(int album_id) {
 		this.album_id = album_id;
 	} 
     
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
     
}
