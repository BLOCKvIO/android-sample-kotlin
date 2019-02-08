package io.blockv.sample.feature.activated

import android.support.v7.widget.Toolbar
import android.widget.TextView
import io.blockv.common.model.Vatom
import io.blockv.face.client.VatomView
import io.blockv.sample.R
import io.blockv.sample.BaseActivity
import io.reactivex.Single
import io.blockv.sample.BaseScreen

class ActivatedScreenImpl(
  activity: BaseActivity
) : BaseScreen(activity), ActivatedScreen {

  private val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
  private val name: TextView = activity.findViewById(R.id.name)
  private val description: TextView = activity.findViewById(R.id.description)
  private val icon: VatomView = activity.findViewById(R.id.icon)
  private val engaged: VatomView = activity.findViewById(R.id.engaged)
  private val card: VatomView = activity.findViewById(R.id.card)

  init {
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun registerEvents(presenter: ActivatedPresenter) {
  }

  override fun finish() {
    activity.finish()
  }

  override fun setVatom(vatom: Vatom): Single<Unit> {

    name.text = vatom.property.title
    description.text = vatom.property.description

    val icon = faceManager
      .load(vatom)
      .setFaceSelectionProcedure(io.blockv.face.client.FaceManager.EmbeddedProcedure.ICON.procedure)
      .into(icon)
      .map { }
      .onErrorReturn { }

    val engaged = faceManager
      .load(vatom)
      .setFaceSelectionProcedure(io.blockv.face.client.FaceManager.EmbeddedProcedure.ENGAGED.procedure)
      .into(engaged)
      .map { }
      .onErrorReturn { }

    val card = faceManager
      .load(vatom)
      .setFaceSelectionProcedure(io.blockv.face.client.FaceManager.EmbeddedProcedure.CARD.procedure)
      .into(card)
      .map { }
      .onErrorReturn { }

    return Single.zip(
      listOf(
        icon,
        engaged,
        card
      )
    ) { }
  }

  override fun startVatomDetailsActivity(vatomId: String) {
  }
}