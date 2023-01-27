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
import com.ssafy.smile.domain.model.CustomPostDomainDto

class CustomPost: ConstraintLayout {
    lateinit var ivImage: ImageView
    lateinit var tvCategory: TextView
    lateinit var tvName: TextView
    lateinit var tvLocation: TextView
    lateinit var ctvLike: CheckedTextView
    lateinit var tvLike: TextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_post, this, false)
        addView(view)
        ivImage = findViewById(R.id.iv_image)
        tvCategory = findViewById(R.id.tv_category)
        tvName = findViewById(R.id.tv_name)
        tvLocation = findViewById(R.id.tv_location)
        ctvLike = findViewById(R.id.ctv_like)
        tvLike = findViewById(R.id.tv_like)

        ctvLike.setOnClickListener {
            ctvLike.isChecked = !(ctvLike.isChecked)
        }
    }

    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomPost)
        setTypedArray(typedArray)
    }

    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        ivImage.setImageResource(
            typedArray.getResourceId(
                R.styleable.CustomPost_postImg,
                R.drawable.ic_launcher_foreground
            )
        )
        tvCategory.text = typedArray.getText(R.styleable.CustomPost_pCategory)
        tvName.text = typedArray.getText(R.styleable.CustomPost_pName)
        tvLocation.text = typedArray.getText(R.styleable.CustomPost_pLocation)
        ctvLike.isChecked = typedArray.getBoolean(R.styleable.CustomPost_pIsChecked, false)
        tvLike.text = typedArray.getText(R.styleable.CustomPost_pLike)
        typedArray.recycle()
    }

    fun setAttrs(data: CustomPostDomainDto) {
        Glide.with(context)
            .load(data.img)
            .into(ivImage)
        tvCategory.text = data.category
        tvName.text = data.name
        tvLocation.text = data.location
        ctvLike.isChecked = data.isLike
        tvLike.text = data.like.toString()

        invalidate()
        requestLayout()
    }
}