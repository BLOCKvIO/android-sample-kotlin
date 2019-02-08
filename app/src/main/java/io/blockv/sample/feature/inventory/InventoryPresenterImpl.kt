package io.blockv.sample.feature.inventory

import android.util.Log
import android.view.MenuItem
import android.view.View
import io.blockv.sample.BasePresenter
import android.os.Bundle
import io.blockv.sample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InventoryPresenterImpl(
  private val inventoryScreen: InventoryScreen
) : BasePresenter(), InventoryPresenter {

  private var inventoryUpdate: Disposable? = null

  override fun onOptionsItemSelected(menuItem: MenuItem) {
    if (menuItem.itemId == R.id.profile) {
      inventoryScreen.startProfileActivity()
    }
  }

  override fun onItemClicked(view: View, vatomId: String) {
    inventoryScreen.startActivatedActivity(vatomId)
  }

  override fun onResume() {
    inventoryScreen.showRefreshing(show = true)
    refresh()
  }

  override fun onPause() {
    dispose()
  }

  override fun onSwipeRefresh() {
    inventoryUpdate?.dispose()
    refresh()
  }

  override fun refresh() {

    //if user is not logged in push to login page
    if (!userManager.isLoggedIn()) inventoryScreen.startLoginActivity()

    //load the user's vAtoms from root inventory
    inventoryUpdate = vatomManager
      .getInventory(id = ".", page = 1, limit = 100)//inventory id "." is root
      .doFinally { inventoryScreen.showRefreshing(show = false) }
      .observeOn(Schedulers.computation())
      .map { vatoms ->
        vatoms.filter { vatom ->
          !vatom.property.isDropped//Filter out dropped vAtoms
            && !vatom.property.templateId.endsWith("::vAtom::Avatar")//filter out avatar vAtoms
            && !vatom.property.templateId.endsWith("::vAtom::CoinWallet")//filter out wallet vAtoms
        }
      }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ vatoms ->
        inventoryScreen.setVatoms(vatoms)
      }, { throwable ->
        Log.e("getInventory", throwable.message)
      })

    if (inventoryUpdate != null) {
      collect(inventoryUpdate!!)
    }
  }
}