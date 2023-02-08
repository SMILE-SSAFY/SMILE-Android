package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
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

    private val _selectedAddressResponseLiveData : SingleLiveData<Long> = SingleLiveData<Long>(null)
    val selectedAddressResponseLiveData = _selectedAddressResponseLiveData

    val getAddressListResponseLiveData: LiveData<List<AddressDomainDto>>
        get() = addressRepository.getAddressList()

    val getAddressListWithSelectionResponseLiveData: LiveData<List<AddressDomainDto>>
        get() = addressRepository.getAddressListWithSelection()

    suspend fun insertAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        _insertAddressResponseLiveData.postValue(addressRepository.insertAddress(address))
    }

    suspend fun selectAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        _selectedAddressResponseLiveData.postValue(addressRepository.selectAddress(address))
    }

    suspend fun deleteAddress(address: AddressDomainDto) = viewModelScope.launch(Dispatchers.IO) {
        addressRepository.deleteAddress(address)
    }

    fun getAddressList() : LiveData<List<AddressDomainDto>> {
        return addressRepository.getAddressList()
    }

    fun getAddressListWithSelection() : LiveData<List<AddressDomainDto>>{
        return addressRepository.getAddressListWithSelection()
    }

}