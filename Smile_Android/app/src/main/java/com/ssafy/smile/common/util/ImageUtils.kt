package com.ssafy.smile.common.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.ssafy.smile.common.util.ImageUtils.rotate
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

    @Throws(IOException::class, NullPointerException::class)
    fun getFileFromUri(context: Context, uri:Uri, quality: Int=50): File {
        val path = context.contentResolver.getPathFromURI(uri)
        val file = File(context.cacheDir, context.contentResolver.getFileNameFromURI(uri))
        val resize = resizeImage(context, uri)
        val exif = ExifInterface(path)
        val bitmap = resize.getRotateInfo(exif)
        return bitmap?.run {
            convertBitmapToFile(file, context, quality)
        }!!

    }

    private fun ContentResolver.getFileNameFromURI(imageUri: Uri):String{
        val cursor = this.query(imageUri,null, null, null)
        var path = ""
        cursor?.let {
            if (it.moveToFirst()){
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                path = cursor.getString(nameIndex)
                cursor.close()
            }
        }
        return path
    }

    fun ContentResolver.getPathFromURI(imageUri: Uri):String{
        val cursor = this.query(imageUri,null, null, null)
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

    fun Bitmap?.getRotateInfo(exifInterface: ExifInterface) : Bitmap? {
        return when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> this?.rotate(90)
            ExifInterface.ORIENTATION_ROTATE_180 -> this?.rotate(180)
            ExifInterface.ORIENTATION_ROTATE_270 -> this?.rotate(270)
            else -> this
        }
    }

    private fun Bitmap.rotate(degrees: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        return createBitmap(this, 0, 0, width, height, matrix, true)
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


    fun Bitmap.convertBitmapToFile(file:File?=null, context: Context, quality:Int=50): File? {
        return try {
            val savedFile = createImageFile(context)
            val outputStream = FileOutputStream(savedFile)
            compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            savedFile
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
