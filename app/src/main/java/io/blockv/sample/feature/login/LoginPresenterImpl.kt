package io.blockv.sample.feature.login

import io.blockv.sample.BasePresenter
import io.blockv.sample.R
import io.blockv.core.client.manager.UserManager

class LoginPresenterImpl(
  private val loginScreen: LoginScreen
) : BasePresenter(), LoginPresenter {

  private var phoneNumber: String = ""
  private var password: String = ""

  override fun onCreate() {
    //if previous login then re-login automatically
    if (userManager.isLoggedIn()) {
      loginScreen.startInventoryActivity()
    }
  }

  override fun onPause() {
    dispose()
  }

  override fun onPhoneNumberChange(phoneNumber: String) {
    this.phoneNumber = phoneNumber
  }

  override fun onPasswordChange(password: String) {
    this.password = password
  }

  override fun onLoginClick() {

    //Check that login details have been filled in correctly
    if (phoneNumber.isEmpty()) {
      loginScreen.showToast(getString(R.string.feature_login_fill_in_telephone_number))
      return
    } else if (password.isEmpty()) {
      loginScreen.showToast(getString(R.string.feature_login_fill_in_password))
      return
    }
    if (phoneNumber.first().toString() != "+") {
      loginScreen.showToast(getString(R.string.feature_login_number_starts_with))
      return
    }

    collect(
      userManager
        .login(
          token = phoneNumber,
          tokenType = UserManager.TokenType.PHONE_NUMBER,
          password = password
        )
        .doOnSubscribe {
          loginScreen.showLoading(show = true)
          loginScreen.enableFields(enable = false)
        }
        .doFinally {
          loginScreen.showLoading(show = false)
          loginScreen.enableFields(enable = true)
        }
        .subscribe({
          loginScreen.startInventoryActivity()
        }, { throwable ->
          loginScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onSendOtpClick() {

    if (phoneNumber.isEmpty()) {
      loginScreen.showToast(getString(R.string.feature_login_fill_in_telephone_number))
      return
    }

    collect(
      userManager
        .resetToken(
          token = phoneNumber,
          tokenType = UserManager.TokenType.PHONE_NUMBER
        )
        .doOnSubscribe {
          loginScreen.showDialog(getString(R.string.feature_login_sending_pin))
          loginScreen.enableFields(enable = false)
        }
        .doFinally {
          loginScreen.hideDialog()
          loginScreen.enableFields(enable = true)
        }
        .subscribe({
          loginScreen.showToast(getString(R.string.feature_login_pin_sent))
        }, { throwable ->
          loginScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onRegistrationClick() {
    loginScreen.startRegistrationActivity()
  }
}
