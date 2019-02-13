package com.android.coinranking.core.extension

import android.animation.LayoutTransition
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.coinranking.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

@JvmOverloads
fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

fun ViewGroup.animateChangingTransitions() {
    layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGING) }
}

@JvmOverloads
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration)
        .apply {
            val margin = context.resources.getDimensionPixelSize(R.dimen.d16)
            view.layoutParams = (view.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(margin, margin, margin, margin)
            }
            view.background = ContextCompat.getDrawable(context, R.drawable.background_snackbar)
        }
        .show()
}

// region Components
fun TextInputLayout.showError(errorMessage: String) {
    error = errorMessage
    showKeyboard()
}

fun TextInputLayout.clearError() {
    error = null
}

fun EditText.textAsString(): String = text.toString()

fun EditText.clearText() {
    setText("")
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visibleIf(predicate: Boolean, otherwiseVisibility: Int = View.GONE) {
    visibility = if (predicate) View.VISIBLE else otherwiseVisibility
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun RecyclerView.withLinearLayoutManager(): RecyclerView = apply {
    layoutManager = LinearLayoutManager(context)
}
// endregion

// region Keyboard
fun isKeyboardSubmit(actionId: Int, event: KeyEvent?): Boolean =
    actionId == EditorInfo.IME_ACTION_GO ||
        actionId == EditorInfo.IME_ACTION_DONE ||
        (event != null && event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_ENTER)

fun View.showKeyboard() {
    requestFocus()
    context.getSystemService<InputMethodManager>()?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    context.getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(windowToken, 0)
}
// endregion
