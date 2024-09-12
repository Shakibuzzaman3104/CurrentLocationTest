package com.diatomicsoft.tntodo.base

import androidx.viewbinding.ViewBinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.diatomicsoft.tntodo.utils.autoCleared


abstract class BaseFragment<T : ViewDataBinding> constructor(@LayoutRes private val mContentLayoutId: Int) :
    Fragment() {

    //private var navigationHost: NavigationHost? = null
    var mBinding by autoCleared<T>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            mContentLayoutId,
            container,
            false
        )
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.root.filterTouchesWhenObscured = true

        return mBinding.root
    }


}