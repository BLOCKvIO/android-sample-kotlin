package io.blockv.sample.feature.registration

import android.view.MenuItem
import io.blockv.sample.BasePresenter
import io.blockv.sample.R
import io.blockv.common.builder.RegistrationBuilder
import io.reactivex.android.schedulers.AndroidSchedulers

class RegistrationPresenterImpl(
  private val registrationScreen: RegistrationScreen
) : RegistrationPresenter, BasePresenter() {

  override fun onRegisterButtonClick(
    firstName: String,
    lastName: String,
    password: String,
    phoneNumber: String
  ) {

    if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
      registrationScreen.showToast(getString(R.string.feature_registration_all_fields_filled_in))
      return
    }

    collect(
      userManager.register(
        RegistrationBuilder()
          .setFirstName(firstName)
          .setLastName(lastName)
          .setPassword(password)
          .addPhoneNumber(phoneNumber.replace("[()\\-\\s]", ""))
          .build()
      )
        .doOnSubscribe { registrationScreen.showDialog(getString(R.string.feature_registration_dialog_registering)) }
        .doFinally { registrationScreen.hideDialog() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          registrationScreen.showToast(getString(R.string.feature_registration_success))
          registrationScreen.startInventoryActivity()
        }, { throwable ->
          registrationScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onDestroy() {
    dispose()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem) {
    if (menuItem.itemId == android.R.id.home) {
      registrationScreen.finish()
    }
  }
}