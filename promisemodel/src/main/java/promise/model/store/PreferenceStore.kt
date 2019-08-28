package promise.model.store

import android.os.Build
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import promise.commons.model.function.FilterFunction
import promise.commons.pref.Preferences
import promise.commons.util.DoubleConverter
import promise.commons.model.List
import promise.commons.model.Result
import promise.model.Extras
import promise.model.Store

/**
 * Created on 7/17/18 by yoctopus.
 */

abstract class PreferenceStore<T>(name: String, private val converter: DoubleConverter<T, JSONObject, JSONObject>) : Store<T, String, Throwable> {
  private val preferences: Preferences = Preferences(name)

  abstract fun findIndexFunction(t: T): FilterFunction<JSONObject>

  override fun get(s: String, callBack: Result<Extras<T>, Throwable>) {
    try {
      val k = preferences.getString(s)
      val array = JSONArray(k)
      if (array.length() == 0) callBack.error(Throwable("Not available"))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      object : Store.StoreExtra<T, Throwable>() {
        override fun <Y> filter(list: List<out T>, vararg y: Y): List<out T> {
          return list
        }
      }.getExtras(
          objects.map { jsonObject -> converter.deserialize(jsonObject) },
          callBack)
    } catch (e: JSONException) {
      callBack.error(e)
    }

  }

  override fun delete(s: String, t: T, callBack: Result<Boolean, Throwable>) =
      get(s, Result<Extras<T>, Throwable>()
          .responseCallBack { tExtras ->
            val list = tExtras.all()
            val index = list.findIndex { t == it }
            if (index != -1) {
              list.removeAt(index)
              clear(s, Result<Boolean, Throwable>()
                  .responseCallBack { _ ->
                    list.forEach {
                      save(s, it, Result())
                    }
                  })
            }
          }
          .errorCallBack { callBack.error(it) })

  override fun update(s: String, t: T, callBack: Result<Boolean, Throwable>) =
      get(s, Result<Extras<T>, Throwable>()
          .responseCallBack { tExtras ->
            val list = List(tExtras.all())
            val index = list.findIndex { t == it }
            if (index != -1) {
              list[index] = t
              clear(s, Result<Boolean, Throwable>()
                  .responseCallBack { _ ->
                    list.forEach {
                      save(s, it, Result())
                    }
                  })
            }
          }
          .errorCallBack { callBack.error(it) })

  override fun save(s: String, t: T, callBack: Result<Boolean, Throwable>) {
    try {
      var array = JSONArray(preferences.getString(s))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      val index = objects.findIndex { jsonObject -> findIndexFunction(t).filter(jsonObject) }
      if (index != -1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          array.remove(index)
          array.put(converter.serialize(t))
        } else {
          array = JSONArray()
          objects.add(converter.serialize(t))
          for (`object` in objects) array.put(`object`)
        }
      } else
        array.put(converter.serialize(t))
      preferences.save(s, array.toString())
      callBack.response(true)
    } catch (e: JSONException) {
      val jsonArray = JSONArray()
      jsonArray.put(converter.serialize(t))
      preferences.save(s, jsonArray.toString())
      callBack.response(true)
    }
  }

  fun save(s: String, list: List<T>, callBack: Result<Boolean, Throwable>) {
    try {
      val array = JSONArray(preferences.getString(s))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      list.forEach {
        array.put(converter.serialize(it))
      }
      preferences.save(s, array.toString())
      callBack.response(true)
    } catch (e: JSONException) {
      val jsonArray = JSONArray()
      list.forEach {
        jsonArray.put(converter.serialize(it))
      }
      preferences.save(s, jsonArray.toString())
      callBack.response(true)
    }
  }

  override fun clear(s: String, callBack: Result<Boolean, Throwable>) {
    preferences.clear(s)
    callBack.response(true)
  }

  override fun clear(callBack: Result<Boolean, Throwable>) {
    preferences.clearAll()
    callBack.response(true)
  }
}
