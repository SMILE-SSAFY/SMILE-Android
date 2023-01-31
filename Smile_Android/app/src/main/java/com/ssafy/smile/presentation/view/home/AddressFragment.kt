package com.ssafy.smile.presentation.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.smile.R
import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.FragmentAddressBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.AddressRVAdapter
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.home.AddressGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class AddressFragment : BaseBottomSheetDialogFragment<FragmentAddressBinding>(FragmentAddressBinding::inflate) {

    private val navArgs : AddressFragmentArgs by navArgs()
    private val viewModel : AddressGraphViewModel by viewModels()
    private lateinit var rvAdapter : AddressRVAdapter
    private var isSelectionMode : Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                isDraggable = false
            }
            setupRatio(bottomSheet, 90)
        }
        return dialog
    }

    override fun initView() {
        isSelectionMode = navArgs.isSelectionMode
        binding.apply {
            lAddressSearch.btnAddressSearch.setOnSingleClickListener {
                moveToAddressSearchFragment()
            }
            btnAddressMapSearch.setOnClickListener {
                moveToAddressMapFragment()
            }
        }
        initRVAdapter()
    }

    override fun setEvent() {
        viewModel.apply {
            getAddressListWithSelectionResponseLiveData.observe(viewLifecycleOwner){
                if (it.isNullOrEmpty()){
                    setEmptyView(true)
                    setRVView(false)
                }else{
                    setEmptyView(false)
                    setRVView(true, it as ArrayList<AddressDomainDto>)
                }
            }
            getAddressListResponseLiveData.observe(viewLifecycleOwner){
                if (it.isNullOrEmpty()){
                    setEmptyView(true)
                    setRVView(false)
                }else{
                    setEmptyView(false)
                    setRVView(true, it as ArrayList<AddressDomainDto>)
                }
            }
            selectedAddressResponseLiveData.observe(viewLifecycleOwner){
                showToast(requireContext(), getString(R.string.msg_address_success), Types.ToastType.SUCCESS)
                moveToPopUpSelf()
            }
            if (isSelectionMode) getAddressListWithSelection()
            else getAddressList()
        }
    }

    private fun initRVAdapter(){
        rvAdapter = AddressRVAdapter(isSelectionMode).apply {
            setItemClickListener(object : AddressRVAdapter.ItemClickListener{
                override fun onClickItem(view: View, position: Int, addressDomainDto: AddressDomainDto) {
                    if (isSelectionMode){
                        if (addressDomainDto.isSelected) showToast(requireContext(), getString(R.string.msg_address_failure), Types.ToastType.WARNING)
                        else lifecycleScope.launch(Dispatchers.IO){ viewModel.selectAddress(addressDomainDto.apply { isSelected = true }) }
                    }else{
                        val bundle = Bundle().apply { putParcelable("addressDomainDto", addressDomainDto) }
                        requireActivity().supportFragmentManager.setFragmentResult("getAddress",bundle)
                        moveToPopUpSelf()
                    }
                }
                override fun onClickRemove(view: View, position: Int, addressDomainDto: AddressDomainDto) {
                    lifecycleScope.launch(Dispatchers.IO){ viewModel.deleteAddress(addressDomainDto) }
                }
            })
        }
        binding.rvAddress.apply {
            adapter = rvAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun setEmptyView(isSet : Boolean){
        if (isSet){
            binding.layoutAddressEmptyView.layoutEmptyView.visibility = View.VISIBLE
            binding.layoutAddressEmptyView.tvEmptyView.text = getString(R.string.tv_address_empty)
        }else binding.layoutAddressEmptyView.layoutEmptyView.visibility = View.GONE
    }

    private fun setRVView(isSet : Boolean, itemList : ArrayList<AddressDomainDto> = arrayListOf()){
        if (isSet) {
            binding.layoutRvAddress.visibility = View.VISIBLE
            rvAdapter.setListData(itemList)
        } else binding.layoutRvAddress.visibility = View.GONE
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_addressFragment_pop)
    private fun moveToAddressSearchFragment(){
        val action = AddressFragmentDirections.actionAddressFragmentToAddressSearchFragment(isSelectionMode)
        findNavController().navigate(action)
    }
    private fun moveToAddressMapFragment() {
        val action = AddressFragmentDirections.actionAddressFragmentToAddressMapFragment(isSelectionMode)
        findNavController().navigate(action)
    }

}