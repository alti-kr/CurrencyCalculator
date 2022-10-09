package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.ui


import com_sergii_tymofieiev_currency_calculator.base.activity.BaseActivity
import com_sergii_tymofieiev_currency_calculator.base.util.viewBinding
import com_sergii_tymofieiev_currency_calculator.databinding.ActivityMainBinding

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class MainActivity: BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun setupBinding() = binding.root

    override fun setupUI() {

    }
}