package com.aries.android.demo.test

import android.content.Context
import android.os.Environment
import android.util.Log

/**
 * Author wudaming
 * Created on 2018/6/25
 */
class FileTest(private val context: Context) {
    fun testCache() {

        val dirs = arrayOf(""
                , Environment.DIRECTORY_PICTURES
                , Environment.DIRECTORY_MUSIC
                , Environment.DIRECTORY_PODCASTS
                , Environment.DIRECTORY_RINGTONES
                , Environment.DIRECTORY_ALARMS
                , Environment.DIRECTORY_NOTIFICATIONS
                , Environment.DIRECTORY_MOVIES
                , Environment.DIRECTORY_DOWNLOADS
                , Environment.DIRECTORY_DCIM
                , Environment.DIRECTORY_DOCUMENTS)

        val des = arrayOf("根目录"
                , "图片目录"
                , "音乐目录"
                , "播客目录"
                , "铃声目录"
                , "提示音目录"
                , "通知目录"
                , "电影目录"
                , "下载目录"
                , "相机目录"
                , "文档目录"
        )
        val sb = StringBuilder()

//        sb.append("\n\n\n外部缓存目录")
//        for (i in 0 until dirs.size){
//            sb.append("\n${des[i]}:").append(FileUtil.getCacheDir(dirs[i]).absolutePath)
//        }
//
//        sb.append("\n\n\n内部缓存目录")
//
//        for (i in 0 until dirs.size){
//            sb.append("\n${des[i]}:").append(FileUtil.getInternalCacheDir(dirs[i]).absolutePath)
//        }

//        sb.append("\n\n\n外部文件目录")
//        for (i in 0 until dirs.size) {
//            sb.append("\n${des[i]}:").append(FileUtil.getFileDir(dirs[i])?.absolutePath)
//        }

//        sb.append("\n\n\n内部文件目录")
//        sb.append("\n内部文件目录:").append(FileUtil.getInternalFilesDir().absolutePath)


        Log.e("FileTest", sb.toString())


    }
}