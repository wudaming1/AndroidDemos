package com.aries.android.demo.compress

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.aries.android.demo.R
import com.aries.android.demo.common.MyActivity
import com.aries.android.demo.databinding.ActivityCompressBinding

class CompressActivity : MyActivity() {

    companion object {
        private const val REQUEST_CODE = 1
    }

    val compressViewModel = CompressViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compress)
        val binding = ActivityCompressBinding.inflate(layoutInflater)!!
        binding.config = compressViewModel

        initTitle(R.string.compress_title)
    }


    private fun openAlbumForResult() {
        val intent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            data?.apply { parserData(this) }
        }
    }


    private fun parserData(data: Intent) {
        val uri = data.data
        val filePathColumns = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumns, null, null, null)
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(filePathColumns[0])
            val filePath = cursor.getColumnName(columnIndex)
        }
        cursor.close()
    }
}
