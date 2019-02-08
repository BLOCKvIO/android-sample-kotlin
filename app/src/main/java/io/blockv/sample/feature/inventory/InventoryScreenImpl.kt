package io.blockv.sample.feature.inventory

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import io.blockv.common.model.Vatom
import io.blockv.sample.BaseActivity
import io.blockv.sample.R
import io.blockv.sample.feature.login.LoginActivity
import android.support.v7.widget.Toolbar
import io.blockv.sample.BaseScreen
import io.blockv.sample.feature.profile.ProfileActivity
import io.blockv.sample.feature.activated.ActivatedActivity

class InventoryScreenImpl(
  activity: BaseActivity
) : BaseScreen(activity), InventoryScreen {

  private val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
  private val refresh: SwipeRefreshLayout = activity.findViewById(R.id.swipe_refresh)
  private val list: RecyclerView = activity.findViewById(R.id.vatom_list)
  private val adapter = InventoryAdapter(faceManager = faceManager)

  init {
    activity.setSupportActionBar(toolbar)
    list.adapter = adapter
    list.layoutManager = GridLayoutManager(activity, 2)
  }

  override fun registerEvents(presenter: InventoryPresenter) {
    refresh.setOnRefreshListener(presenter::onSwipeRefresh)
    adapter.setItemClickListener(presenter::onItemClicked)
  }

  override fun setVatoms(vatoms: List<Vatom>) {
    adapter.items = vatoms
  }

  override fun showRefreshing(show: Boolean) {
    refresh.isRefreshing = show
  }

  override fun startLoginActivity() {
    val loginIntent = LoginActivity.getIntent(activity)
    activity.startActivity(loginIntent)
  }

  override fun startActivatedActivity(vatomId: String) {
    activity.startActivity(ActivatedActivity.getIntent(activity, vatomId))
  }

  override fun startProfileActivity() {
    val profileIntent = ProfileActivity.getIntent(activity)
    activity.startActivity(profileIntent)
  }
}