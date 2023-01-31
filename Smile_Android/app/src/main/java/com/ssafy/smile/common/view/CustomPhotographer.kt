package com.ssafy.smile.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.ssafy.smile.R
import com.ssafy.smile.common.util.Constants
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

class CustomPhotographer: ConstraintLayout {
    lateinit var ivImage: ImageView
    lateinit var tvCategory: TextView
    lateinit var tvName: TextView
    lateinit var tvLocation: TextView
    lateinit var tvPrice: TextView
    lateinit var ctvLike: CheckedTextView
    lateinit var tvLike: TextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_photographer, this, false)
        addView(view)
        ivImage = findViewById(R.id.iv_image)
        tvCategory = findViewById(R.id.tv_category)
        tvName = findViewById(R.id.tv_name)
        tvLocation = findViewById(R.id.tv_location)
        tvPrice = findViewById(R.id.tv_price)
        ctvLike = findViewById(R.id.ctv_like)
        tvLike = findViewById(R.id.tv_like)
    }

    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomPhotographer)
        setTypedArray(typedArray)
    }

    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        ivImage.setImageResource(
            typedArray.getResourceId(
                R.styleable.CustomPhotographer_profileImg,
                R.drawable.ic_launcher_foreground
            )
        )
        tvCategory.text = typedArray.getText(R.styleable.CustomPhotographer_category)
        tvName.text = typedArray.getText(R.styleable.CustomPhotographer_name)
        tvLocation.text = typedArray.getText(R.styleable.CustomPhotographer_location)
        tvPrice.text = typedArray.getText(R.styleable.CustomPhotographer_price)
        ctvLike.isChecked = typedArray.getBoolean(R.styleable.CustomPhotographer_isChecked, false)
        tvLike.text = typedArray.getText(R.styleable.CustomPhotographer_like)
        typedArray.recycle()
    }

    fun setAttrs(data: CustomPhotographerDomainDto) {
        Glide.with(context)
            .load(Constants.IMAGE_BASE_URL+data.img)
            .into(ivImage)
        tvCategory.text = data.category
        tvName.text = data.name
        tvLocation.text = data.location
        tvPrice.text = data.price
        ctvLike.isChecked = data.isLike
        tvLike.text = data.like.toString()

        invalidate()
        requestLayout()
    }
}