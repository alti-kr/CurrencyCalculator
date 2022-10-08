package com_sergii_tymofieiev_mvi.base.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sergii_tymofieiev.base_mvi.R
import com_sergii_tymofieiev_mvi.base.mvi.state.RetainUiState

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
abstract class BaseFragment: Fragment() {
    private var retainState: RetainUiState? = null
    private var lastSafeClickTime: Long = 0
    private var defaultInterval: Int = 1500

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return setupBinding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingLayout()
        setupUI()
        setupData()
    }
    @SuppressLint("InflateParams")
    private fun setupLoadingLayout() {
        val content = activity?.findViewById<ViewGroup>(android.R.id.content)

        val inflater: LayoutInflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progress: View = inflater.inflate(R.layout.layout_loading, null)

        progress.visibility = View.GONE
        content?.addView(progress)
    }
    open fun setupData() {}
    open fun renderState() {
    }

    open fun renderEffect() {
    }

    override fun onStart() {
        super.onStart()
        renderState()
        renderEffect()
    }

    abstract fun setupBinding(): View
    abstract fun setupUI()

    override fun onDestroyView() {
        retainState?.retainedInstance = true
        super.onDestroyView()
    }
}