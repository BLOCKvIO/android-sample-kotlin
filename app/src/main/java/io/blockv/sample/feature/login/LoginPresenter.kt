package io.blockv.sample.feature.login

/**
 * Login presenter contains all business logic
 * for the LoginActivity.
 */
interface LoginPresenter {

  fun onCreate()

  fun onPhoneNumberChange(phoneNumber: String)

  fun onPasswordChange(password: String)

  fun onLoginClick()

  fun onRegistrationClick()

  fun onPause()

  fun onSendOtpClick()
}