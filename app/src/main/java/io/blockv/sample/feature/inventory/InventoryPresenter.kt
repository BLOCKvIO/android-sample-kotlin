package io.blockv.sample.feature.inventory

import android.view.MenuItem
import android.view.View

interface InventoryPresenter {

  fun onPause()

  fun onResume()

  fun onOptionsItemSelected(menuItem: MenuItem)

  fun onItemClicked(view: View, vatomId: String)

  fun onSwipeRefresh()

  fun refresh()
}