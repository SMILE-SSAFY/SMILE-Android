package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressGraphViewModel : BaseViewModel() {
    private val addressRepository = Application.repositoryInstances.getAddressRepository()

    private val _insertAddressResponseLiveData : SingleLiveData<Long> = SingleLiveData<Long>(null)
    val insertAddressResponseLiveData = _insertAddressResponseLiveData

    private val _selectAddressResponseLiveData : SingleLiveData<Long> = SingleLiveData<Long>(null)
    val selectAddressResponseLiveData = _selectAddressResponseLiveData

    private val _deleteAddressResponseLiveData : SingleLiveData<Int> = SingleLiveData<Int>(null)
    val deleteAddressResponseLiveData = _deleteAddressResponseLiveData

    private val _getAddressListResponseLiveData: SingleLiveData<List<AddressDomainDto>> = SingleLiveData<List<AddressDomainDto>>(null)
    val getAddressListResponseLiveData = _getAddressListResponseLiveData

    private val _getAddressListWithSelectionResponseLiveData : SingleLiveData<List<AddressDomainDto>> = SingleLiveData<List<AddressDomainDto>>(null)
    val getAddressListWithSelectionResponseLiveData = _getAddressListWithSelectionResponseLiveData

    fun insertAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        _insertAddressResponseLiveData.postValue(addressRepository.insertAddress(address))
    }

    fun selectAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        _selectAddressResponseLiveData.postValue(addressRepository.selectAddress(address))
    }

    fun deleteAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        _deleteAddressResponseLiveData.postValue(addressRepository.deleteAddress(address))
    }

    fun getAddressList() = viewModelScope.launch(Dispatchers.IO) {
        _getAddressListResponseLiveData.postValue(addressRepository.getAddressList())
    }

    fun getAddressListWithSelection() = viewModelScope.launch(Dispatchers.IO){
        _getAddressListWithSelectionResponseLiveData.postValue(addressRepository.getAddressListWithSelection())
    }

}