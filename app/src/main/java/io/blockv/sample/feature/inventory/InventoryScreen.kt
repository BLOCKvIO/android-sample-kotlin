package io.blockv.sample.feature.inventory

import io.blockv.common.model.Vatom

interface InventoryScreen {

  fun startActivatedActivity(vatomId: String)

  fun startProfileActivity()

  fun registerEvents(presenter: InventoryPresenter)

  fun startLoginActivity()

  fun setVatoms(vatoms: List<Vatom>)

  fun showRefreshing(show: Boolean)
}