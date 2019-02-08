package io.blockv.sample

import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, BlockvModule::class])
interface MainComponent {

  fun inject(activity: BaseActivity)

  fun inject(baseScreen: BaseScreen)

  fun inject(basePresenter: BasePresenter)
}