package io.blockv.sample

import android.support.v7.app.AlertDialog
import android.widget.Toast
import io.blockv.face.client.FaceManager
import io.blockv.core.client.manager.ResourceManager
import com.squareup.picasso.Picasso

import javax.inject.Inject

open class BaseScreen(val activity: BaseActivity) {

  private var dialog: AlertDialog? = null

  init {
    Injector.inject(this)
  }

  @Inject
  lateinit var picasso: Picasso

  @Inject
  lateinit var resourceManager: ResourceManager

  @Inject
  lateinit var faceManager: FaceManager

  fun showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
  }

  @Synchronized
  fun showDialog(text: String) {
    val builder = AlertDialog.Builder(activity)
    builder.setMessage(text)
    if (dialog != null) dialog?.dismiss()
    dialog = builder.create()
    dialog?.show()
  }

  @Synchronized
  fun hideDialog() {
    dialog?.dismiss()
    dialog = null
  }
}