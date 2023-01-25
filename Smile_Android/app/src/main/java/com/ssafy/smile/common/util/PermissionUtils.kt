package com.ssafy.smile.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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

    fun actionGalleryProfilePermission(context : Context, imageLauncher : ActivityResultLauncher<Intent>){
        getPermission(Constants.PERMISSION_CAMERA, context.resources.getString(R.string.permission_deny_msg),
            object : PermissionListener {
                override fun onPermissionGranted() {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    imageLauncher.launch(intent)
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(context, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }

}