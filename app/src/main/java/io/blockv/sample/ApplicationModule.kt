package io.blockv.sample

import android.app.Application
import android.content.res.Resources
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

  @Singleton
  @Provides
  fun provideApplication(): Application {
    return application
  }

  @Singleton
  @Provides
  fun provideResources(): Resources {
    return application.resources
  }

  @Singleton
  @Provides
  fun providePicasso(): Picasso {
    return Picasso.Builder(application).build()
  }
}