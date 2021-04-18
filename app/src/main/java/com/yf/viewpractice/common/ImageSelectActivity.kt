package com.yf.viewpractice.common

import android.content.ClipData
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yf.viewpractice.R

class ImageSelectActivity : AppCompatActivity() {

    var imageEncoded: String? = null
    var imagesEncodedList: MutableList<String> = mutableListOf()

    private val PICK_IMAGE_MULTIPLE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_select)

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            val a = ImageHelper.handleImageOnKitKat(this, data)
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        try {
//            // When an Image is picked
//            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
//                // Get the Image from data
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                imagesEncodedList = ArrayList()
//                if (data.data != null) {
//                    val mImageUri = data.data!!
//
//                    // Get the cursor
//                    val cursor = contentResolver.query(
//                        mImageUri,
//                        filePathColumn, null, null, null
//                    )
//                    cursor?.apply {
//                        // Move to first row
//                        moveToFirst()
//                        val columnIndex: Int = getColumnIndex(filePathColumn[0])
//                        imageEncoded = getString(columnIndex)
//                        close()
//                    }
//
//                } else {
//                    if (data.clipData != null) {
//                        val mClipData = data.clipData!!
//                        val mArrayUri = ArrayList<Uri>()
//                        for (i in 0 until mClipData.itemCount) {
//                            val item: ClipData.Item = mClipData.getItemAt(i)
//                            val uri: Uri = item.uri
//                            mArrayUri.add(uri)
//                            // Get the cursor
//                            val cursor: Cursor? =
//                                contentResolver.query(uri, filePathColumn, null, null, null)
//                            cursor?.apply {
//                                // Move to first row
//                                moveToFirst()
//                                val columnIndex: Int = getColumnIndex(filePathColumn[0])
//                                imageEncoded = getString(columnIndex)
//                                imagesEncodedList.add(imageEncoded!!)
//                                cursor.close()
//                            }
//
//                        }
//                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size)
//                    }
//                }
//            } else {
//                Toast.makeText(
//                    this, "You haven't picked Image",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                .show()
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}