package io.blockv.sample.feature.activated

import android.content.Intent
import android.view.MenuItem
import io.blockv.sample.BasePresenter
import io.blockv.sample.R
import android.text.TextUtils
import io.blockv.common.model.Vatom
import io.blockv.sample.constants.Extras
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ActivatedPresenterImpl(
  private val activatedScreen: ActivatedScreen
) : ActivatedPresenter, BasePresenter() {

  private var vatomId: String? = ""
  private var vatom: Vatom? = null
  private val disposable: CompositeDisposable = CompositeDisposable()

  override fun onCreate(intent: Intent) {

    vatomId = intent.extras?.getString(Extras.VATOM_ID)
    if (TextUtils.isEmpty(vatomId)) {
      activatedScreen.showToast(getString(R.string.feature_activated_no_vatom))
      activatedScreen.finish()
      return
    }
  }

  override fun startUpdate(): Flowable<Vatom> {

    return activatedScreen.setVatom(vatom!!)
      .toFlowable()
      .observeOn(Schedulers.io())
      .flatMap {
        eventManager
          .getVatomStateEvents()
      }
      .filter { event ->
        event.payload?.vatomId == vatom?.id
      }
      .observeOn(Schedulers.computation())
      .map { update ->
        synchronized(this)
        {
          val vatom = vatomManager.updateVatom(this.vatom!!, update.payload!!).blockingGet()
          this.vatom = vatom
          vatom
        }
      }
      .observeOn(AndroidSchedulers.mainThread())
      .flatMap { vatom ->
        activatedScreen
          .setVatom(vatom)
          .map { vatom }
          .toFlowable()
      }
  }

  override fun onResume() {

    //get vatom by id
    disposable.add(vatomManager
      .getVatoms(vatomId!!)
      .filter { vatoms -> vatoms.isNotEmpty() }
      .doFinally { activatedScreen.hideDialog() }
      .doOnSubscribe {
        activatedScreen.showDialog(getString(R.string.feature_activated_loading))
      }
      .toFlowable()
      .flatMap { vatoms ->
        this.vatom = vatoms[0]
        startUpdate()
      }
      .subscribe({

      }, { throwable ->
        activatedScreen.showToast(throwable.message!!)
        activatedScreen.finish()
      })
    )
  }

  override fun onPause() {
    disposable.dispose()
  }

  override fun onDestroy() {
    dispose()
  }

  override fun onOptionsItemSelected(menuItem: MenuItem) {
    if (menuItem.itemId == android.R.id.home) {
      activatedScreen.finish()
    } else if (menuItem.itemId == R.id.details) {
      activatedScreen.startVatomDetailsActivity(vatomId!!)
    }
  }
}
