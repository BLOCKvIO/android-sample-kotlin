package io.blockv.sample

import android.app.Application

object Injector : MainComponent {

  private var component: MainComponent? = null

  fun init(application: Application) {

    if (component != null) {
      throw RuntimeException("Can't init twice!")
    }

    component = DaggerMainComponent.builder()
      .applicationModule(ApplicationModule(application))
      .blockvModule(BlockvModule(application))
      .build()
  }

  override fun inject(activity: BaseActivity) {
    component?.inject(activity)
  }

  override fun inject(baseScreen: BaseScreen) {
    component?.inject(baseScreen)
  }

  override fun inject(basePresenter: BasePresenter) {
    component?.inject(basePresenter)
  }
}