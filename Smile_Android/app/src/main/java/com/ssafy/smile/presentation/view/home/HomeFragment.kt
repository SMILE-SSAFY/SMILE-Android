package com.ssafy.smile.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.smile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewbinding : FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewbinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}