package io.blockv.sample.feature.profile

import io.blockv.common.model.Token

interface ProfileScreen {
  fun registerEvents(presenter: ProfilePresenter)

  fun finish()

  fun setAvatar(url: String)

  fun setFirstName(firstName: String)

  fun setLastName(lastName: String)

  fun setBirthday(birthday: String)

  fun setUserId(id: String)

  fun enableSaveDetails(enable: Boolean)

  fun enableSavePassword(enable: Boolean)

  fun showDialog(text: String)

  fun hideDialog()

  fun showToast(message: String)

  fun startSelectPhotoActivity(code: Int)

  fun restartApp()

  fun setTokens(tokens: List<Token>)
}