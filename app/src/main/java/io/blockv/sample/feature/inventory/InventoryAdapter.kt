package io.blockv.sample.feature.inventory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.blockv.face.client.FaceManager
import io.blockv.sample.R
import io.blockv.common.model.Vatom

class InventoryAdapter(private val faceManager: FaceManager) : RecyclerView.Adapter<InventoryViewHolder>() {

  @set:Synchronized
  @get:Synchronized
  var items: List<Vatom> = listOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }
  private var listener: ((view: View, vatomId: String) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
    return InventoryViewHolder(
      itemView = LayoutInflater.from(parent.context).inflate(R.layout.vatom_list_item, parent, false),
      faceManager = faceManager,
      listener = listener
    )
  }

  override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
    holder.setVatomView(items[position])
  }

  override fun getItemCount() = items.size

  override fun getItemViewType(position: Int): Int {
    //recycle views based on vatom template variation id
    return items[position].property.templateVariationId.hashCode()
  }

  @Synchronized
  fun setItemClickListener(listener: (view: View, vatomId: String) -> Unit) {
    this.listener = listener
  }
}