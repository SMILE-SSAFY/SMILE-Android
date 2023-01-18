package com.ssafy.smile.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException

object ImageUtils {


    private fun temp(){
        // 이미지 리사이징 (resize : 원하는 이미지 리사이즈 크기)
        private fun resizeImage(context: Context, uri: Uri, resize:Int): Bitmap? {
            var resizeBitmap: Bitmap? = null
            val options = BitmapFactory.Options()

            try{
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)

                var width = options.outWidth
                var height = options.outHeight
                // 한 픽셀당 표현하는 픽셀 수 -> 수가 높아질수록 이미지 크기가 작아지고 고화질이 됨
                var sampleSize = 1

                while(true){
                    if(width / 2 < resize || height / 2 < resize){
                        break
                    }
                    width /= 2
                    height /= 2
                    sampleSize *= 2
                }

                options.inSampleSize = sampleSize
                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)
                resizeBitmap = bitmap

            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
            return resizeBitmap
        }
    }
}