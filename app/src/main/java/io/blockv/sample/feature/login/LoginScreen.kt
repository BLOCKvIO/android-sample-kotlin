package io.blockv.sample.feature.login

/**
 * LoginScreen contains all the UI functionality
 * for the LoginActivity.
 */
interface LoginScreen {

  fun registerPresenter(loginPresenter: LoginPresenter)

  fun setPhoneNumber(phoneNumber: String)

  fun setPassword(password: String)

  fun showLoading(show: Boolean)

  fun showToast(message: String)

  fun showDialog(text: String)

  fun hideDialog()

  fun startInventoryActivity()

  fun startRegistrationActivity()

  fun enableFields(enable: Boolean)
}