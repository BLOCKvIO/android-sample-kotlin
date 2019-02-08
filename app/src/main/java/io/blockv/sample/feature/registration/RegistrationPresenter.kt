package io.blockv.sample.feature.registration

import android.view.MenuItem

/**
 * RegisterPresenter handles business logic for RegisterActivity
 * @see RegisterActivity
 */
interface RegistrationPresenter {

  /**
   * Attempt to register a new user.
   *
   * @param firstName is the user's first name
   * @param lastName is the user's last name
   * @param password is the user's desired password
   * @param phoneNumber is the user' phone number
   */
  fun onRegisterButtonClick(
    firstName: String,
    lastName: String,
    password: String,
    phoneNumber: String
  )

  fun onDestroy()

  fun onOptionsItemSelected(menuItem: MenuItem)
}