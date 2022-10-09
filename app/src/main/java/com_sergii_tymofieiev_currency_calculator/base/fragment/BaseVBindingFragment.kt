package com_sergii_tymofieiev_currency_calculator.base.fragment

import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
abstract class BaseVBindingFragment<VBinding : ViewBinding> : BaseFragment() {
    private var _binding: VBinding? = null
    protected val binding get() = _binding!!

    override fun setupBinding(): View {
        initVBinding()
        return binding.root
    }

    private fun initVBinding() {
        _binding = getVBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun getVBinding(): VBinding
}