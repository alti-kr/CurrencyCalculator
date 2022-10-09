package com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com_sergii_tymofieiev_currency_calculator.R
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import com_sergii_tymofieiev_currency_calculator.base.util.changeVisibility
import com_sergii_tymofieiev_currency_calculator.databinding.DialogBottomSheetCountryPickerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
class CountryPickerBottomSheet : BottomSheetDialogFragment() {

    private val supervisorJob = SupervisorJob()

    private val scope = CoroutineScope(supervisorJob + Dispatchers.Main)
    private lateinit var binding: DialogBottomSheetCountryPickerBinding

    var onCountrySelectedListener: ((CurrencyListItemModel) -> Unit)? = null

    private val viewState: MutableStateFlow<CountryPickerViewState> = MutableStateFlow(
        CountryPickerViewState(emptyList())
    )

    private val args: CountryPickerArguments by lazy {
        requireNotNull(requireArguments().getParcelable(BUNDLE_ARGS))
    }

    private val itemAdapter: CountryAdapter by lazy {
        CountryAdapter(args.itemLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomSheetCountryPickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectViewState()
    }

    private fun initView() = with(binding) {
        searchView.changeVisibility(args.isSearchEnabled)

        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchCountries(newText)
                return true
            }
        })

        itemAdapter.onItemClickListener = {
            onCountrySelectedListener?.invoke(it)
            dismiss()
        }
    }

    private fun collectViewState() = scope.launch {
        viewState.collect {
            itemAdapter.setup(it.countries)
        }
    }

    private fun searchCountries(query: String?) {
        scope.launch {
            query?.let {
                val countries = viewState.value.countries
                val filtered = countries.filter {
                    it.code.toString().startsWith(query) ||
                            it.name.lowercase(Locale.ROOT)
                                .contains(query.lowercase(Locale.ROOT))
                }
                binding.recyclerView.post {
                    itemAdapter.setup(filtered)
                }
            }
        }
    }

    fun setList(currencyList: List<CurrencyListItemModel>) {
        viewState.value = CountryPickerViewState(currencyList)
    }

    companion object {
        const val TAG = "countryPickerBottomSheet"
        private const val BUNDLE_ARGS = "bundleArgs"
        fun newInstance(
            args: CountryPickerArguments
        ) = CountryPickerBottomSheet().apply {
            arguments = bundleOf(BUNDLE_ARGS to args)
        }
    }
}


