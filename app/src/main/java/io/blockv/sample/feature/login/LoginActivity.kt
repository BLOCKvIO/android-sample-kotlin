package io.blockv.sample.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.blockv.sample.BaseActivity
import io.blockv.sample.R

class LoginActivity : BaseActivity() {

  lateinit var presenter: LoginPresenter

  companion object {
    fun getIntent(context: Context): Intent {
      return Intent(context, LoginActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val screen = LoginScreenImpl(activity = this)
    presenter = LoginPresenterImpl(loginScreen = screen)

    screen.registerPresenter(presenter)
    presenter.onCreate()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }
}
