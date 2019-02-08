package io.blockv.sample.feature.registration

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.support.v7.widget.Toolbar
import io.blockv.sample.BaseActivity
import io.blockv.sample.BaseScreen
import io.blockv.sample.R
import io.blockv.sample.feature.inventory.InventoryActivity

class RegistrationScreenImpl(
  activity: BaseActivity
) : BaseScreen(activity), RegistrationScreen {

  private val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
  private val phone: EditText = activity.findViewById(R.id.phone_number)
  private val firstName: EditText = activity.findViewById(R.id.first_name)
  private val lastName: EditText = activity.findViewById(R.id.last_name)
  private val password: EditText = activity.findViewById(R.id.password)
  private val register: Button = activity.findViewById(R.id.register)

  init {
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun registerEvents(presenter: RegistrationPresenter) {
    register.setOnClickListener{
      presenter.onRegisterButtonClick(
        firstName = firstName.text.toString(),
        lastName = lastName.text.toString(),
        password = password.text.toString(),
        phoneNumber = phone.text.toString()
      )
    }
  }

  override fun startInventoryActivity() {
    val inventoryIntent = InventoryActivity.getIntent(activity)
    inventoryIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    activity.startActivity(inventoryIntent)
  }

  override fun finish() {
    activity.finish()
  }
}