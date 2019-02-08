package io.blockv.sample

import android.content.res.Resources
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.blockv.core.client.manager.UserManager
import io.blockv.core.client.manager.VatomManager
import io.blockv.core.client.manager.EventManager

import javax.inject.Inject

open class BasePresenter {

  private val disposables = CompositeDisposable()

  init {
    Injector.inject(this)
  }

  @Inject
  lateinit var resources: Resources

  @Inject
  lateinit var userManager: UserManager

  @Inject
  lateinit var vatomManager: VatomManager

  @Inject
  lateinit var eventManager: EventManager

  fun getString(id: Int): String {
    return resources.getString(id)
  }

  @Synchronized
  fun collect(disposable: Disposable) {
    disposables.add(disposable)
  }

  @Synchronized
  fun dispose() {
    disposables.clear()
  }
}