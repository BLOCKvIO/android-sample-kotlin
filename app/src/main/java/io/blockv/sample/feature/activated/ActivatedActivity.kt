package io.blockv.sample.feature.activated

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import io.blockv.sample.BaseActivity
import io.blockv.sample.R
import android.view.Menu
import android.content.Intent
import io.blockv.sample.constants.Extras

/**
 * The ActivatedActivity demonstrates fetching a vatom by id and loading a vatomview.
 *
 * @see ActivatedPresenterImpl
 */
class ActivatedActivity : BaseActivity() {

  lateinit var presenter: ActivatedPresenter

  companion object {

    fun getIntent(context: Context, vatomId: String): Intent {
      val intent = Intent(context, ActivatedActivity::class.java)
      intent.putExtra(Extras.VATOM_ID, vatomId)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_vatom_activated)

    val screen = ActivatedScreenImpl(activity = this)
    presenter = ActivatedPresenterImpl(activatedScreen = screen)
    screen.registerEvents(presenter)
    presenter.onCreate(intent)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
    presenter.onOptionsItemSelected(menuItem)
    return super.onOptionsItemSelected(menuItem)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_activated, menu)
    return super.onCreateOptionsMenu(menu)
  }
}