package com.ssafy.smile.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.smile.R
import gun0912.tedimagepicker.builder.TedImagePicker


object PermissionUtils {
    private fun getPermission(permission: String, denyMsg: String, listener: PermissionListener){
        TedPermission.create()
            .setPermissionListener(listener)
            .setDeniedMessage(denyMsg)
            .setPermissions(permission)
            .check()
    }


    fun actionGalleryPermission(context : Context, maxNum: Int, maxMsg : String, action:(List<Uri>)->Unit) {
        TedImagePicker.with(context)
            .title("사진 선택")
            .max(maxNum, maxMsg)
            .startMultiImage { uriList -> action(uriList) }
    }


}