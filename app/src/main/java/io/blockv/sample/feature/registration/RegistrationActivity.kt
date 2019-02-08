package io.blockv.sample.feature.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import io.blockv.sample.R
import io.blockv.sample.BaseActivity

class RegistrationActivity : BaseActivity() {

  companion object {
    fun getIntent(context: Context): Intent {
      return Intent(context, RegistrationActivity::class.java)
    }
  }

  lateinit var presenter: RegistrationPresenter

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registration)

    val screen = RegistrationScreenImpl(activity = this)
    presenter = RegistrationPresenterImpl(registrationScreen = screen)

    screen.registerEvents(presenter)
  }

  public override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
    presenter.onOptionsItemSelected(menuItem)
    return super.onOptionsItemSelected(menuItem)
  }
}