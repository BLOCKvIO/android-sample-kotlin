package io.blockv.sample.feature.registration

/**
 * LoginScreen handles screen layout and navigation for RegisterActivity
 * @see RegisterActivity
 */

interface RegistrationScreen {

  fun registerEvents(presenter: RegistrationPresenter)

  fun showDialog(text: String)

  fun hideDialog()

  fun showToast(message: String)

  fun startInventoryActivity()

  fun finish()
}