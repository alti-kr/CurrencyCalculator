package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.ui


import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com_sergii_tymofieiev_currency_calculator.R
import com_sergii_tymofieiev_currency_calculator.base.fragment.BaseVBindingFragment
import com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker.CountryPickerArguments
import com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker.CountryPickerBottomSheet
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.contract.*
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.view_model.MainFragmentViewModel
import com_sergii_tymofieiev_currency_calculator.base.util.getFlagResource
import com_sergii_tymofieiev_currency_calculator.base.util.headerList
import com_sergii_tymofieiev_currency_calculator.base.util.safeOnClickListener
import com_sergii_tymofieiev_currency_calculator.base.util.text_watcher.CurrencyInputWatcher
import com_sergii_tymofieiev_currency_calculator.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class MainFragment : BaseVBindingFragment<FragmentMainBinding>() {

    private lateinit var datePicker: MaterialDatePicker<Long>
    private val viewModel by viewModel<MainFragmentViewModel>()
    override fun getVBinding() = FragmentMainBinding.inflate(layoutInflater)
    private var selectedDate: Long = System.currentTimeMillis()
    private var selectedCurrencyFrom: CurrencyListItemModel? = null
    private var selectedCurrencyTo: CurrencyListItemModel? = null
    private lateinit var paymentAmountWatcher: CurrencyInputWatcher

    override fun setupUI() {
        makeDatePicker()
        setupCountryPickerBottomSheet()
        onDateChanged()
        with(binding) {
            btnCalculate.setOnClickListener {
                checkAndRequest()
            }
            rlCurrencyFromContainer.safeOnClickListener {
                showCountryCodePicker {
                    setFromCurrency(it)
                }
            }
            rlCurrencyToContainer.safeOnClickListener {
                showCountryCodePicker {
                    setToCurrency(it)
                }
            }
            rlDateContainer.safeOnClickListener {
                datePicker.show(parentFragmentManager, datePicker::class.simpleName)
            }
            paymentAmountWatcher = CurrencyInputWatcher(etAmount)
            etAmount.addTextChangedListener(paymentAmountWatcher)
        }
        viewModel.setEvent(MainFragmentEvent.FetchHistory)
    }

    private fun makeDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        val janThisYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(janThisYear)
                .setEnd(today)
                .setValidator(DateValidatorPointBackward.now())
        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            selectedDate = it
            onDateChanged()
        }
    }

    private fun onDateChanged() {
        binding.tvDate.text = SimpleDateFormat("dd MMM yyyy", Locale("us")).format(selectedDate)
    }

    private fun setFromCurrency(currencyItem: CurrencyListItemModel) {
        with(binding) {
            imageViewFlagFrom.setImageResource(
                getFlagResource(
                    imageViewFlagFrom,
                    currencyItem.code.substring(0, 2).lowercase()
                )
            )
            tvNameFrom.text = currencyItem.code
            selectedCurrencyFrom = currencyItem
        }
        fillAmount(0)
    }

    private fun setToCurrency(currencyItem: CurrencyListItemModel) {
        with(binding) {
            imageViewFlagTo.setImageResource(
                getFlagResource(
                    imageViewFlagTo,
                    currencyItem.code.substring(0, 2).lowercase()
                )
            )
            tvNameTo.text = currencyItem.code
            selectedCurrencyTo = currencyItem
        }
        fillAmount(0)
    }


    private fun toggleButtonCalculate(onOff: Boolean) {
        binding.btnCalculate.isEnabled = onOff
    }

    var cp: CountryPickerBottomSheet? = null
    private fun setupCountryPickerBottomSheet() {
        if (cp == null) {
            cp = CountryPickerBottomSheet.newInstance(
                CountryPickerArguments(
                    R.layout.item_country_picker,
                    true,
                    emptyList(),//excludedCountries,
                    emptyList(),//admittedCountries
                    headerList
                )
            )
        }
    }

    private fun showCountryCodePicker(clickListener: (CurrencyListItemModel) -> Unit) {
        cp?.apply {
            onCountrySelectedListener = {
                clickListener(it)
            }
        }
        cp?.show(parentFragmentManager, cp!!::class.simpleName)
    }

    override fun renderEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainFragmentEffect.ShowErrorEffect -> showError(it.message)
                        is MainFragmentEffect.ToggleProgress -> {// TODO}
                        }
                        is MainFragmentEffect.HistoryAsString -> fillHistory(it.history)
                    }
                }
            }
        }
    }

    private fun fillHistory(history: String) {
        binding.tvHistory.text = history
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
        when (it.type) {
            MainFragmentStateType.IDLE -> {}
            MainFragmentStateType.CURRENCY_LIST -> cp?.setList(it.currencyList)
            MainFragmentStateType.AMOUNT -> {
                fillAmount(it.currencyAmount)
            }
        }
    }

    private fun fillAmount(currencyAmount: Int) {
        binding.tvAmount.text = (currencyAmount.toFloat() / 100).toString()
    }

    private fun checkAndRequest() {
        if (selectedCurrencyFrom == null || selectedCurrencyTo == null || binding.etAmount.text.toString()
                .isEmpty()
        ) {
            showError(getString(R.string.error_empty_fields))
        } else {
            viewModel.setEvent(
                MainFragmentEvent.CalculateAmount(
                    selectedDate,
                    selectedCurrencyFrom!!,
                    selectedCurrencyTo!!,
                    paymentAmountWatcher.getNumericValue().toString()
                )
            )
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}