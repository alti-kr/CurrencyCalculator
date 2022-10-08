package com_sergii_tymofieiev_mvi.ui.main.main_fragment.ui


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sergii_tymofieiev.base_mvi.R
import com.sergii_tymofieiev.base_mvi.databinding.FragmentMainBinding
import com_sergii_tymofieiev_mvi.base.fragment.BaseVBindingFragment
import com_sergii_tymofieiev_mvi.ui.common.country_picker.CountryPickerArguments
import com_sergii_tymofieiev_mvi.ui.common.country_picker.CountryPickerBottomSheet
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.contract.MainFragmentState
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.contract.MainFragmentStatus
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.view_model.MainFragmentViewModel
import com_sergii_tymofieiev_mvi.util.headerList
import com_sergii_tymofieiev_mvi.util.safeOnClickListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class MainFragment : BaseVBindingFragment<FragmentMainBinding>() {

    private val viewModel by viewModel<MainFragmentViewModel>()
    override fun getVBinding() = FragmentMainBinding.inflate(layoutInflater)

    override fun setupUI() {
        with(binding) {

            btnCalculate.setOnClickListener {

            }
            rlCurrencyFromContainer.safeOnClickListener {
                showCountryCodePicker()
            }
            rlCurrencyToContainer.safeOnClickListener {
                showCountryCodePicker()
            }
        }

    }





    private fun toggleButtonCalculate(onOff: Boolean) {
        binding.btnCalculate.isEnabled = onOff
    }
    var cp: CountryPickerBottomSheet? = null
    private fun showCountryCodePicker() {
        if (cp == null) {
            cp = CountryPickerBottomSheet.newInstance(
                CountryPickerArguments(
                    R.layout.item_country_picker,
                    true,
                    emptyList(),//excludedCountries,
                    emptyList(),//admittedCountries
                    headerList
                )
            ).apply {
                onCountrySelectedListener = { country ->
                   // setCountry(country)
                }
            }
        }
        cp!!.show(parentFragmentManager, cp!!::class.simpleName)
    }
    override fun renderState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when (it.status) {
                        MainFragmentStatus.IDLE -> {}
                        MainFragmentStatus.SUCCESS -> {
                            processSuccess(it)
                        }
                        MainFragmentStatus.FAILED -> {}
                    }
                }
            }
        }

    }

    private fun processSuccess(it: MainFragmentState) {

    }


}