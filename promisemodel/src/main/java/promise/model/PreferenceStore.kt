/*
 *  Copyright 2017, Peter Vincent
 *  Licensed under the Apache License, Version 2.0, Android Promise.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.model

import android.os.Build
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import promise.commons.model.List
import promise.commons.model.function.FilterFunction
import promise.commons.pref.Preferences
import promise.commons.tx.PromiseResult
import promise.commons.util.DoubleConverter

abstract class PreferenceStore<T>(name: String,
                                  private val converter: DoubleConverter<T, JSONObject, JSONObject>) :
    Store<T, String, Throwable> {
  private val preferences: Preferences = Preferences(name)

  override fun <Y : Any?> filter(list: List<out T>, vararg y: Y): List<out T> = list

  abstract fun findIndexFunction(t: T): FilterFunction<JSONObject>

  override fun get(s: String, callBack: PromiseResult<Store.Extras<T>, Throwable>) {
    try {
      val k = preferences.getString(s)
      val array = JSONArray(k)
      if (array.length() == 0) callBack.error(Throwable("Not available"))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      getExtras(
          objects.map { jsonObject -> converter.deserialize(jsonObject) },
          callBack)
    } catch (e: JSONException) {
      callBack.error(e)
    }

  }

  override fun delete(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) =
      get(s, PromiseResult<Store.Extras<T>, Throwable>()
          .withCallback { tExtras ->
            val list = tExtras.all()
            val index = list.findIndex { t == it }
            if (index != -1) {
              list.removeAt(index)
              clear(s, PromiseResult<Boolean, Throwable>()
                  .withCallback { _ ->
                    list.forEach {
                      save(s, it, PromiseResult())
                    }
                  })
            }
          }
          .withErrorCallback { callBack.error(it) })

  override fun update(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) =
      get(s, PromiseResult<Store.Extras<T>, Throwable>()
          .withCallback { tExtras ->
            val list = List(tExtras.all())
            val index = list.findIndex { t == it }
            if (index != -1) {
              list[index] = t
              clear(s, PromiseResult<Boolean, Throwable>()
                  .withCallback { _ ->
                    list.forEach {
                      save(s, it, PromiseResult())
                    }
                  })
            }
          }
          .withErrorCallback { callBack.error(it) })

  override fun save(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) {
    try {
      var array = JSONArray(preferences.getString(s))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      val index = objects.findIndex { jsonObject -> findIndexFunction(t).select(jsonObject) }
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

  fun save(s: String, list: List<T>, callBack: PromiseResult<Boolean, Throwable>) {
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

  override fun clear(s: String, callBack: PromiseResult<Boolean, Throwable>) {
    preferences.clear(s)
    callBack.response(true)
  }

  override fun clear(callBack: PromiseResult<Boolean, Throwable>) {
    preferences.clearAll()
    callBack.response(true)
  }
}
