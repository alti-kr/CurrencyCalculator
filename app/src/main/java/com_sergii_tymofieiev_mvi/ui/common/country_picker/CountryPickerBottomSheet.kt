package com_sergii_tymofieiev_mvi.ui.common.country_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergii_tymofieiev.base_mvi.R
import com.sergii_tymofieiev.base_mvi.databinding.DialogBottomSheetCountryPickerBinding
import com_sergii_tymofieiev_mvi.ui.common.country_picker.FileReader.readAssetFile
import com_sergii_tymofieiev_mvi.util.ASSET_FILE_NAME
import com_sergii_tymofieiev_mvi.util.changeVisibility
import com_sergii_tymofieiev_mvi.util.default
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

    var onCountrySelectedListener: ((Country) -> Unit)? = null

    private val viewState: MutableStateFlow<CountryPickerViewState> = MutableStateFlow(
        CountryPickerViewState(emptyList())
    )

    private val args: CountryPickerArguments by lazy {
        requireNotNull(requireArguments().getParcelable(BUNDLE_ARGS))
    }

   /* override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }*/
    private val itemAdapter: CountryAdapter by lazy {
        CountryAdapter(args.itemLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
       // dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomSheetCountryPickerBinding.inflate(layoutInflater)
      /*  dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheet.parent.parent.requestLayout()
            }
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectViewState()
        fetchData()
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

    private fun fetchData() = scope.launch {
        val countries = default {
            readAssetFile(requireContext(), ASSET_FILE_NAME)
                .toCountryList()
                .filter {
                    args.admittedCountries.isEmpty() || args.admittedCountries.contains(it.iso2)
                }.filterNot {
                    args.excludedCountries.contains(it.iso2)
                }.addHeaderSection(args.admittedToHeader)
        }
        viewState.value = CountryPickerViewState(countries)
    }

    private fun searchCountries(query: String?) {
        scope.launch {
            query?.let {
                val countries = viewState.value.countries
                val filtered = countries.filter {
                    it.code.toString().startsWith(query) ||
                            it.name.toLowerCase(Locale.ROOT)
                                .contains(query.toLowerCase(Locale.ROOT))
                }
                binding.recyclerView.post {
                    itemAdapter.setup(filtered)
                }
            }
        }
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


