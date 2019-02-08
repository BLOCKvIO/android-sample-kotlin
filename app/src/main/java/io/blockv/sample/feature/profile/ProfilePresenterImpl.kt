package io.blockv.sample.feature.profile

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.MenuItem
import android.view.View
import io.blockv.common.builder.UpdateUserBuilder
import io.blockv.sample.BasePresenter
import io.blockv.sample.R
import io.reactivex.Single
import android.graphics.Matrix
import android.support.media.ExifInterface
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenterImpl(
  private val profileScreen: ProfileScreen,
  private val contentResolver: ContentResolver
) : BasePresenter(), ProfilePresenter {

  private val PHOTO_REQUEST_CODE = 1000

  override fun onCreate() {
    reload()
  }

  private fun reload() {

    collect(
      userManager.getCurrentUser()
        .doOnSubscribe { profileScreen.showDialog(getString(R.string.feature_profile_loading)) }
        .doFinally { profileScreen.hideDialog() }
        .observeOn(AndroidSchedulers.mainThread())
        .map { user ->
          profileScreen.setUserId(user.id)
          profileScreen.setFirstName(user.firstName)
          profileScreen.setLastName(user.lastName)
          profileScreen.setBirthday(user.birthday)
          profileScreen.setAvatar(user.avatarUri)
        }
        .observeOn(Schedulers.io())
        .flatMap {
          userManager
            .getCurrentUserTokens()
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ tokens ->
          profileScreen.setTokens(tokens)
        }, { throwable ->
          profileScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onOptionsItemSelected(menuItem: MenuItem) {
    if (menuItem.itemId == android.R.id.home) {
      profileScreen.finish()
    }

  }

  override fun onSaveDetailsClick(firstName: String, lastName: String, birthday: String) {

    collect(
      userManager.updateCurrentUser(
        UpdateUserBuilder()
          .setFirstName(firstName)
          .setLastName(lastName)
          .setBirthday(birthday)
          .build()
      )
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { profileScreen.showDialog(getString(R.string.feature_profile_saving)) }
        .doFinally { profileScreen.hideDialog() }
        .observeOn(AndroidSchedulers.mainThread())
        .map { user ->
          profileScreen.setUserId(user.id)
          profileScreen.setFirstName(user.firstName)
          profileScreen.setLastName(user.lastName)
          profileScreen.setBirthday(user.birthday)
        }
        .subscribe({

        }, { throwable ->
          profileScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onSavePasswordClick(password: String) {

    if (password.isEmpty()) {
      profileScreen.showToast(getString(R.string.feature_profile_enter_new_password))
      return
    }

    collect(
      userManager.updateCurrentUser(
        UpdateUserBuilder()
          .setPassword(password)
          .build()
      )
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { profileScreen.showDialog(getString(R.string.feature_profile_saving)) }
        .doFinally { profileScreen.hideDialog() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({

        }, { throwable ->
          profileScreen.showToast(throwable.message!!)
        })
    )
  }

  override fun onLogOutClick(view: View) {

    onDestroy()

    val logout = userManager
      .logout()
      .doOnSubscribe { profileScreen.showDialog(getString(R.string.feature_profile_logging_out)) }
      .doFinally {
        profileScreen.hideDialog()
        profileScreen.restartApp()
      }
      .subscribe({

      }, { throwable ->
        profileScreen.showToast(throwable.message!!)
      })
  }

  override fun onAvatarClick(view: View) {
    profileScreen.startSelectPhotoActivity(PHOTO_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == PHOTO_REQUEST_CODE) {
        val selectedImage: Uri = data.data!!
        val loadAvatar = loadAvatar(selectedImage)
          .flatMapCompletable { userManager.uploadAvatar(it) }
          .doFinally {
            profileScreen.hideDialog()
            reload()
          }
          .doOnSubscribe { profileScreen.showDialog(getString(R.string.feature_profile_uploading)) }
          .subscribe({

          }, { throwable ->
            profileScreen.showToast(throwable.message!!)
          })
      }
    }
  }

  private fun loadAvatar(uri: Uri): Single<Bitmap> {

    return Single.fromCallable {

      val matrix = Matrix()
      lateinit var out: Bitmap

      try {
        val inputStream = contentResolver.openInputStream(uri)
        val exif = ExifInterface(inputStream!!)
        val rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val rotationInDegrees = when (rotation) {
          ExifInterface.ORIENTATION_ROTATE_90 -> 90
          ExifInterface.ORIENTATION_ROTATE_180 -> 180
          ExifInterface.ORIENTATION_ROTATE_270 -> 270
          else -> 0
        }
        if (rotation.toFloat() != 0f) matrix.preRotate(rotationInDegrees.toFloat())
      } catch (e: Exception) {
        Log.e("loadAvatar", e.message)
      }

      var input = contentResolver.openInputStream(uri)

      val options = BitmapFactory.Options()
      options.inJustDecodeBounds = true
      BitmapFactory.decodeStream(input, null, options)
      input?.close()

      options.inJustDecodeBounds = false
      options.inSampleSize = calculateInSampleSize(options, 500, 500)

      input = contentResolver.openInputStream(uri)
      val orignal = BitmapFactory.decodeStream(input, null, options)
      input?.close()

      if (orignal != null) {
        out = if (orignal.width >= orignal.height) {
          Bitmap.createBitmap(
            orignal,
            orignal.width / 2 - orignal.height / 2,
            0,
            orignal.height,
            orignal.height,
            matrix,
            true
          )
        } else {
          Bitmap.createBitmap(
            orignal,
            0,
            orignal.height / 2 - orignal.width / 2,
            orignal.width,
            orignal.width,
            matrix,
            true
          )
        }
      }
      out
    }
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
  }

  private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
      val halfHeight = height / 2
      val halfWidth = width / 2

      while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
        inSampleSize *= 2
      }
    }
    return inSampleSize
  }

  override fun onDestroy() {
    profileScreen.hideDialog()
    dispose()
  }
}
