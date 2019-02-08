package io.blockv.sample.feature.inventory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import io.blockv.sample.BaseActivity
import io.blockv.sample.R
import android.view.Menu

class InventoryActivity : BaseActivity() {

  lateinit var presenter: InventoryPresenter

  companion object {
    fun getIntent(context: Context): Intent {
      return Intent(context, InventoryActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_inventory)

    val screen = InventoryScreenImpl(activity = this)
    presenter = InventoryPresenterImpl(inventoryScreen = screen)

    screen.registerEvents(presenter)
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
    presenter.onOptionsItemSelected(menuItem)
    return super.onOptionsItemSelected(menuItem)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_inventory, menu)
    return super.onCreateOptionsMenu(menu)
  }
}
