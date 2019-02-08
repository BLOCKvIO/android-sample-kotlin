package io.blockv.sample.feature.activated

import io.blockv.common.model.Vatom
import io.reactivex.Single

/**
 * ActivatedScreen handles screen layout for ActivatedActivity
 * @see ActivatedActivity
 */
interface ActivatedScreen {

  fun registerEvents(presenter: ActivatedPresenter)

  fun finish()

  fun showDialog(text: String)

  fun hideDialog()

  fun showToast(message: String)

  fun setVatom(vatom: Vatom): Single<Unit>

  fun startVatomDetailsActivity(vatomId: String)

}