package io.blockv.sample.feature.profile

import de.hdodenhof.circleimageview.CircleImageView
import io.blockv.sample.BaseActivity
import io.blockv.sample.R
import android.os.Handler
import android.os.Looper
import android.widget.*
import android.support.v7.widget.Toolbar
import android.util.Log
import io.blockv.common.model.Token
import io.blockv.common.internal.net.rest.auth.ResourceEncoder
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import io.blockv.sample.BaseScreen
import io.blockv.sample.feature.login.LoginActivity

class ProfileScreenImpl(
  activity: BaseActivity
) : BaseScreen(activity), ProfileScreen {

  private val handler = Handler(Looper.getMainLooper())

  private val avatar: CircleImageView = activity.findViewById(R.id.avatar)
  private var firstName: EditText = activity.findViewById(R.id.first_name)
  private var lastName: EditText = activity.findViewById(R.id.last_name)
  private var birthday: EditText = activity.findViewById(R.id.birthday)
  private var password: EditText = activity.findViewById(R.id.password)
  private val saveDetails: Button = activity.findViewById(R.id.save)
  private val savePassword: Button = activity.findViewById(R.id.save_password)
  private val logout: Button = activity.findViewById(R.id.logout)
  private val userId: TextView = activity.findViewById(R.id.user_id)
  private val tokenList: LinearLayout = activity.findViewById(R.id.token_list)
  private val toolbar: Toolbar = activity.findViewById(R.id.toolbar)

  init {
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun registerEvents(presenter: ProfilePresenter) {
    saveDetails.setOnClickListener {
      presenter.onSaveDetailsClick(
        firstName = firstName.text.toString(),
        lastName = lastName.text.toString(),
        birthday = birthday.text.toString()
      )
    }
    savePassword.setOnClickListener {
      presenter.onSavePasswordClick(
        password = password.text.toString()
      )
    }

    logout.setOnClickListener(presenter::onLogOutClick)
    avatar.setOnClickListener(presenter::onAvatarClick)
  }

  override fun finish() {
    activity.finish()
  }

  override fun setAvatar(url: String) {

    var urlEncoded: String? = null
    try {
      //add asset provider credentials
      urlEncoded = resourceManager.encodeUrl(url)
    } catch (e: ResourceEncoder.MissingAssetProviderException) {
      Log.e("SetAvatar", e.message)
    }
    picasso
      .load(urlEncoded)
      .placeholder(R.drawable.progress_bar)
      .error(R.drawable.profile_placeholder)
      .into(avatar)
  }

  override fun setFirstName(firstName: String) {
    this.firstName.setText(firstName)
  }

  override fun setLastName(lastName: String) {
    this.lastName.setText(lastName)
  }

  override fun setBirthday(birthday: String) {
    this.birthday.setText(birthday)
  }

  override fun setUserId(id: String) {
    this.userId.text = id
  }

  override fun enableSaveDetails(enable: Boolean) {
    saveDetails.isEnabled = enable
  }

  override fun enableSavePassword(enable: Boolean) {
    savePassword.isEnabled = enable
  }

  override fun startSelectPhotoActivity(code: Int) {
    val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    activity.startActivityForResult(intent, code)
  }

  override fun restartApp() {
    showToast(activity.resources.getString(R.string.feature_profile_restarting))
    handler.postDelayed({
      val loginIntent = LoginActivity.getIntent(activity)
      loginIntent.flags = FLAG_ACTIVITY_CLEAR_TASK
      activity.startActivity(loginIntent)
    }, 1500)
  }

  override fun setTokens(tokens: List<Token>) {

    tokenList.removeAllViews()
    for (token in tokens) {
      val view = activity.layoutInflater.inflate(R.layout.view_token_list_item, null)
      val tokenText: TextView = view.findViewById(R.id.token)
      tokenText.text = token.token
      tokenList.addView(view)
    }
  }
}