package com.example.sqlitehelper;

import android.net.Uri;
import android.provider.MediaStore;

public class MusicFinalVar {
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
    public static final Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final String ID=MediaStore.Audio.Media._ID;
    public static final String TITLE=MediaStore.Audio.Media.TITLE;
    public static final String ALBUM=MediaStore.Audio.Media.ALBUM;
    public static final String ARTIST=MediaStore.Audio.Media.ARTIST;
    public static final String DATA=MediaStore.Audio.Media.DATA;
    public static final String DURATION=MediaStore.Audio.Media.DURATION;
    public static final String SIZE=MediaStore.Audio.Media.SIZE;
    public static final String ALBUM_ID=MediaStore.Audio.Media.ALBUM_ID;
}
