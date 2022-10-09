@file:Suppress("NOTHING_TO_INLINE")

package com_sergii_tymofieiev_currency_calculator.base.util

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener

private const val DURATION_ALPHA_ANIMATION = 200L
private const val ALPHA_DISABLE = 0.3f
private const val ALPHA_ENABLE = 1f



fun View.dimView(show: Boolean, duration: Long = 300) {
  isEnabled = show
  ViewCompat.animate(this).alpha(if (show) 1f else 0f).setDuration(duration)
    .setInterpolator(DecelerateInterpolator())
    .setListener(object : ViewPropertyAnimatorListener {
      override fun onAnimationEnd(view: View) {
        view.visibility = if (show) View.VISIBLE else View.GONE
      }

      override fun onAnimationCancel(view: View) {
        view.hide()
      }

      override fun onAnimationStart(view: View) {
        view.show()
      }
    }).withLayer().start()
}

fun View.show() {
  when {
    visibility != View.VISIBLE -> visibility = View.VISIBLE
  }
}

fun View.hide() {
  when {
    visibility != View.GONE -> visibility = View.GONE
  }
}
fun View.invisible() {
  when {
    visibility != View.INVISIBLE -> visibility = View.INVISIBLE
  }
}

fun View.toggle(onOff: Boolean){
  visibility = if (onOff) View.VISIBLE else View.GONE
}

fun View?.changeVisibility(show: Boolean) {
  if (this == null) {
    return
  }

  if (show) show() else hide()
}
fun View?.toggleVisibility(show: Boolean) {
  if (this == null) {
    return
  }

  if (show) show() else invisible()
}



inline fun View.hideAnimate(hideControls: Boolean? = null, duration: Long = 100) {
  val isVisible = visibility == View.VISIBLE
  val needHide = hideControls ?: isVisible
  val alpha = if (needHide) 0f else 1f
  if (isVisible && !needHide || !isVisible && needHide) return
  animate().alpha(alpha).setDuration(duration).setInterpolator(AccelerateDecelerateInterpolator())
    .withStartAction { show() }.withEndAction {
      if (needHide) {
        hide()
      }
    }.start()
}



fun View?.enable(
    enable: Boolean,
    changeAlpha: Boolean,
    enabledAlpha: Float = ALPHA_ENABLE,
    disabledAlpha: Float = ALPHA_DISABLE,
) {
  if (this == null) {
    return
  }

  isEnabled = enable
  if (changeAlpha) {
    alpha = if (enable) enabledAlpha else disabledAlpha
  }
}

fun View?.scale(factor: Float) {
  if (this == null) {
    return
  }

  scaleX = factor
  scaleY = factor
}



