package io.blockv.sample

import android.app.Application

class SampleApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    Injector.init(this)
  }

}