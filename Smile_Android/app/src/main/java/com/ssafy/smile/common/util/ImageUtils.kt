package com.ssafy.smile.common.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageUtils {


    fun getFileFromUri(context: Context, uri:Uri, quality: Int=50) : File {
        val path = context.contentResolver.getPathFromURI(uri)
        val resizedBitmap = resizeImage(context, uri)
        return resizedBitmap?.run {
            convertBitmapToFile(path, context, quality)
        }!!
    }

    private fun ContentResolver.getPathFromURI(imageUri: Uri):String{
        val project = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = this.query(imageUri, project, null, null, null)
        var path = ""
        cursor?.let {
            if (it.moveToFirst()){
                val pathIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                path = cursor.getString(pathIndex)
                cursor.close()
            }
        }
        return path
    }

    fun resizeImage(context: Context, imageUri:Uri, resize:Pair<Int, Int> = Pair(1024, 1024)): Bitmap? {
        return BitmapFactory.Options().run{
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri), null, this)
            inSampleSize = this.resizeBitmapSize(resize.first, resize.second)
            inJustDecodeBounds = false
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri), null, this)
        }
    }

    private fun BitmapFactory.Options.resizeBitmapSize(reqWidth: Int = 1024, reqHeight: Int = 1024): Int {
        val (height: Int, width: Int) = this.run{outHeight to outWidth}
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    fun Bitmap.convertBitmapToFile(path:String?=null, context: Context, quality:Int=50): File? {
        return try {
            val newFile = createImageFile(context)
            val outputStream = FileOutputStream(newFile)
            compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.close()
            newFile
        } catch (e: Exception) { null }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmm ss", Locale.KOREA).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}

fun String?.convertToRequestBody() : RequestBody = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
fun File?.convertToRequestBody() : RequestBody = requireNotNull(this).asRequestBody("image/*".toMediaTypeOrNull())
