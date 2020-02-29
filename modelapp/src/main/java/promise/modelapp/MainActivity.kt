package promise.modelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

  private var id = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    fab.setOnClickListener {
      val model = complexStore.one(ArrayMap<String, Any>().apply {
        put(ID_ARG, id)
      })
      select_textview.text = model.first.toString()
      id++
    }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    val models = complexStore.all(ArrayMap<String, Any>().apply {
      put(NUMBER_ARG, 10)
      put(TIMES_ARG, 2)
    })
    val result = "${models.first.toString()} \n meta \n ${models.second}"
    main_textview.text = result
  }
}
