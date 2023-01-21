package com.ssafy.smile.presentation.viewmodel.mypage

import com.ssafy.smile.Application
import com.ssafy.smile.presentation.base.BaseViewModel

class MyPageViewModel() : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

}