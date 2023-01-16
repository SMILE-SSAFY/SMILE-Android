package com.ssafy.smile.presentation.view.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.smile.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private lateinit var viewbinding : FragmentMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewbinding = FragmentMapBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}