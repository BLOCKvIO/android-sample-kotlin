package io.blockv.sample.feature.login

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import io.blockv.sample.BaseActivity
import io.blockv.sample.R
import io.blockv.sample.BaseScreen
import io.blockv.sample.feature.inventory.InventoryActivity
import io.blockv.sample.feature.registration.RegistrationActivity

class LoginScreenImpl(
  activity: BaseActivity
) : BaseScreen(activity), LoginScreen {

  private val phoneNumber: EditText = activity.findViewById(R.id.telephone_number)
  private val password: EditText = activity.findViewById(R.id.password)
  private val login: Button = activity.findViewById(R.id.submit)
  private val progressBar: ProgressBar = activity.findViewById(R.id.loader)
  private val register: Button = activity.findViewById(R.id.register)
  private val sendOneTimePin: Button = activity.findViewById(R.id.one_time_pin)


  override fun registerPresenter(loginPresenter: LoginPresenter) {

    phoneNumber.addTextChangedListener(object : TextWatcher() {

      override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        loginPresenter.onPhoneNumberChange(text.toString())
      }
    })
    password.addTextChangedListener(object : TextWatcher() {

      override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        loginPresenter.onPasswordChange(text.toString())
      }
    })
    login.setOnClickListener {
      loginPresenter.onLoginClick()
    }
    register.setOnClickListener {
      loginPresenter.onRegistrationClick()
    }
    sendOneTimePin.setOnClickListener {
      loginPresenter.onSendOtpClick()
    }
  }

  override fun setPhoneNumber(phoneNumber: String) {
    this.phoneNumber.setText(phoneNumber)
  }

  override fun setPassword(password: String) {
    this.password.setText(password)
  }

  override fun showLoading(show: Boolean) {
    if (show) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
  }

  override fun startInventoryActivity() {
    val inventoryIntent = InventoryActivity.getIntent(activity)
    inventoryIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    activity.startActivity(inventoryIntent)
  }

  override fun startRegistrationActivity() {
    val registrationIntent = RegistrationActivity.getIntent(activity)
    activity.startActivity(registrationIntent)
  }

  override fun enableFields(enable: Boolean) {
    phoneNumber.isEnabled = enable
    password.isEnabled = enable
  }
}