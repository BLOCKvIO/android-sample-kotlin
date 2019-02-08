package io.blockv.sample

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * BaseActivity contains shared functionality use by all activities.
 */
abstract class BaseActivity : AppCompatActivity() {

  init {
    Injector.inject(this)
  }
}