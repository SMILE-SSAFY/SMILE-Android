package com.ssafy.smile.presentation.viewmodel

import com.ssafy.smile.Application.Companion.repositoryInstances
import com.ssafy.smile.presentation.base.BaseViewModel


class ExampleViewModel : BaseViewModel() {
    private val exampleRepository = repositoryInstances.getExampleRemoteRepository()

    // ViewModel 로직
}
