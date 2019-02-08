package io.blockv.sample.feature.profile

import android.content.Intent
import android.view.MenuItem
import android.view.View

interface ProfilePresenter {

  fun onCreate()

  fun onDestroy()

  fun onOptionsItemSelected(menuItem: MenuItem)

  fun onSaveDetailsClick(firstName: String, lastName: String, birthday: String)

  fun onSavePasswordClick(password: String)

  fun onLogOutClick(view: View)

  fun onAvatarClick(view: View)

  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
}