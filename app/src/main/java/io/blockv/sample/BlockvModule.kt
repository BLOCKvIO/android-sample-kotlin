package io.blockv.sample

import android.content.Context

import dagger.Module
import dagger.Provides
import io.blockv.core.client.Blockv
import io.blockv.core.client.manager.*
import io.blockv.face.client.FaceManager

import javax.inject.Singleton

/**
 * Dagger Module wrapping the Blockv SDK
 */
@Module
class BlockvModule(private val context: Context) {

  @Singleton
  @Provides
  fun provideBlockv(): Blockv {
    val blockv = Blockv(context, "replace-with-your-app-id")//creates the blockv singleton
    return blockv
  }

  @Singleton
  @Provides
  fun provideUserManager(blockv: Blockv): UserManager {
    return blockv.userManager
  }

  @Singleton
  @Provides
  fun provideVatomManager(blockv: Blockv): VatomManager {
    return blockv.vatomManager
  }

  @Singleton
  @Provides
  fun provideEventManager(blockv: Blockv): EventManager {
    return blockv.eventManager
  }

  @Singleton
  @Provides
  fun provideResourceManager(blockv: Blockv): ResourceManager {
    return blockv.resourceManager
  }

  @Singleton
  @Provides
  fun provideActivityManager(blockv: Blockv): ActivityManager {
    return blockv.activityManager
  }

  @Singleton
  @Provides
  fun provideFaceManager(blockv: Blockv): FaceManager {
    return blockv.faceManager
  }
}