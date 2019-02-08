package io.blockv.sample.feature.login

import android.text.Editable

abstract class TextWatcher : android.text.TextWatcher {

  override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
  }

  override fun afterTextChanged(s: Editable) {
  }
}