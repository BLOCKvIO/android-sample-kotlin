package io.blockv.sample.feature.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import io.blockv.sample.BaseActivity
import io.blockv.sample.R

class ProfileActivity : BaseActivity() {

  lateinit var presenter: ProfilePresenter

  companion object {
    fun getIntent(context: Context): Intent {
      return Intent(context, ProfileActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    val screen = ProfileScreenImpl(activity = this)
    presenter = ProfilePresenterImpl(
      profileScreen = screen,
      contentResolver = contentResolver
    )

    screen.registerEvents(presenter)
    presenter.onCreate()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
    presenter.onOptionsItemSelected(menuItem)
    return super.onOptionsItemSelected(menuItem)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) presenter.onActivityResult(requestCode, resultCode, data)
  }
}