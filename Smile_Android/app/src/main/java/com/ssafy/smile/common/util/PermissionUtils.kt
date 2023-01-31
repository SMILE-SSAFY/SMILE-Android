package com.ssafy.smile.common.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog

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

    fun getLocationServicePermission(listener: PermissionListener){
        TedPermission.create()
            .setPermissionListener(listener)
            .setDeniedMessage("[설정] 에서 위치 접근 권한을 부여해야만 사용이 가능합니다.")
            .setPermissions(     // 필요한 권한 설정
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

    fun showDialogForLocationServiceSetting(context: Context, action:()->Unit, cancelAction:()->Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context).apply {
            setTitle("위치 서비스 비활성화")
            setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n")
            setCancelable(true)
            setPositiveButton("설정") { _, _ -> action() }
            setNegativeButton("취소") { dialog, _ ->
                cancelAction()
                dialog.cancel() }
        }
        builder.create().show()
    }


}