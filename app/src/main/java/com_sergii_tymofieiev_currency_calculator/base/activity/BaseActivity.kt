package com_sergii_tymofieiev_currency_calculator.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com_sergii_tymofieiev_currency_calculator.R
import org.koin.core.component.KoinComponent

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
abstract class BaseActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupBinding())
        setupLoadingLayout()
        setupUI()
        setupData()
        renderState()
        renderEffect()
    }

    @SuppressLint("InflateParams")
    private fun setupLoadingLayout() {
        val content = findViewById<ViewGroup>(android.R.id.content)
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progress: View = inflater.inflate(R.layout.layout_loading, null)
        progress.visibility = View.GONE
        content?.addView(progress)
    }

    open fun setupData() {

    }

    open fun renderState() {

    }

    open fun renderEffect() {

    }

    abstract fun setupBinding(): View
    abstract fun setupUI()
}