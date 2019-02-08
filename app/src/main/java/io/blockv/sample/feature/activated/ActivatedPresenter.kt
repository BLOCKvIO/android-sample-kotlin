package io.blockv.sample.feature.activated

import android.content.Intent
import android.view.MenuItem
import io.blockv.common.model.Vatom
import io.reactivex.Flowable

/**
 * ActivatedPresenter handles business logic for ActivatedActivity
 * @see ActivatedActivity
 */
interface ActivatedPresenter {

  /**
   * Load vatom details on activity create and update ActivatedScreen
   * @param intent
   */
  fun onCreate(intent: Intent)

  fun onResume()

  fun onPause()

  fun onDestroy()

  fun onOptionsItemSelected(menuItem: MenuItem)

  fun startUpdate(): Flowable<Vatom>

}