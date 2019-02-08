package io.blockv.sample.feature.inventory

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import io.blockv.common.model.Vatom
import io.blockv.face.client.FaceManager
import io.blockv.face.client.VatomView
import io.blockv.sample.R
import io.reactivex.disposables.Disposable

class InventoryViewHolder(
  itemView: View,
  private val faceManager: FaceManager,
  private val listener: ((view: View, vatomId: String) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

  private val vatomView: VatomView = itemView.findViewById(R.id.vatom_item)
  lateinit var vatom: Vatom

  fun setVatomView(vatom: Vatom) {
    this.vatom = vatom
    this.vatomView.setOnClickListener { view -> listener?.invoke(view, vatom.id) }

    val disposable: Disposable = faceManager
      .load(vatom)
      .setLoaderDelay(200)//use loader delay to prevent loaders flicking when scrolling fast
      .into(vatomView)
      .subscribe({

      }, { throwable ->
        Log.e("setVatom", throwable.message)
      })
  }
}