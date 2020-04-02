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
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import promise.commons.model.List
import promise.commons.model.function.FilterFunction
import promise.commons.pref.Preferences
import promise.commons.tx.PromiseResult
import promise.commons.util.DoubleConverter

 abstract class PreferenceKeyStore<T: Any>
 @JvmOverloads constructor(name: String,
                           private val converter: DoubleConverter<T, JSONObject, JSONObject>? = null) :
    KeyStore<T, String, Throwable> {
  private val preferences: Preferences = Preferences(name)

  override fun <Y : Any?> filter(list: List<out T?>, vararg y: Y): List<out T> = list

  abstract fun findIndexFunction(t: T): FilterFunction<JSONObject>

  override fun get(k: String, callBack: PromiseResult<KeyStore.Extras<T>, Throwable>, type: Class<T>?) {
    try {
      callBack.response(get(k, type = type))
    } catch (e: JSONException) {
      callBack.error(e)
    }

  }

  override fun delete(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) =
      callBack.response(delete(s, t))

  override fun update(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) =
      callBack.response(update(s, t))

  override fun save(s: String, t: T, callBack: PromiseResult<Boolean, Throwable>) =
      callBack.response(save(s, t))

  override fun save(s: String, t: List<out T>, callBack: PromiseResult<Boolean, Throwable>) =
      callBack.response(save(s, t))

  override fun clear(s: String, callBack: PromiseResult<Boolean, Throwable>) {
    callBack.response(clear(s))
  }

  override fun clear(callBack: PromiseResult<Boolean, Throwable>) {
    callBack.response(clear())
  }

   override fun get(s: String,  type: Class<T>?): KeyStore.Extras<T> {
     val k = preferences.getString(s)
     val array = JSONArray(k)
     if (array.length() == 0) throw Throwable("Not available")
     val objects = List<JSONObject>()
     for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
     return getExtras(objects.map {
       converter?.deserialize(it) ?: gson.fromJson(it.toString(), type!!)
     })
   }

   override fun delete(k: String, t: T): Boolean {
    val tExtras = get(k, t.javaClass)
     val list = tExtras.all()
     val index = list.findIndex { t == it }
     if (index != -1) {
       list.removeAt(index)
       clear(k)
       list.forEach {
         save(k, it!!)
       }
     }
     return true
   }

   override fun update(k: String, t: T): Boolean {
     val tExtras = get(k, t.javaClass)
     val list = List(tExtras.all())
     val index = list.findIndex { t == it }
     if (index != -1) {
       list[index] = t
       clear(k)
       list.forEach {
         save(k, it!!)
       }
     }
     return true
   }

   override fun save(k: String, t: T): Boolean {
     try {
       var array = JSONArray(preferences.getString(k))
       val objects = List<JSONObject>()
       for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
       val index = objects.findIndex { jsonObject -> findIndexFunction(t).select(jsonObject) }
       if (index != -1) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           array.remove(index)
           array.put(converter?.serialize(t) ?: JSONObject(gson.toJson(t)))
         } else {
           array = JSONArray()
           objects.add(converter?.serialize(t) ?: JSONObject(gson.toJson(t)))
           for (`object` in objects) array.put(`object`)
         }
       } else
         array.put(converter?.serialize(t) ?: JSONObject(gson.toJson(t)))
       preferences.save(k, array.toString())
       return true
     } catch (e: JSONException) {
       val jsonArray = JSONArray()
       jsonArray.put(converter?.serialize(t) ?: JSONObject(gson.toJson(t)))
       preferences.save(k, jsonArray.toString())
       return false
     }
   }

   override fun save(k: String, t: List<out T>): Boolean {
     try {
       val array = JSONArray(preferences.getString(k))
       val objects = List<JSONObject>()
       for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
       t.forEach {
         array.put(converter?.serialize(it) ?: JSONObject(gson.toJson(it)))
       }
       preferences.save(k, array.toString())
       return true
     } catch (e: JSONException) {
       val jsonArray = JSONArray()
       t.forEach {
         jsonArray.put(converter?.serialize(it) ?: JSONObject(gson.toJson(it)))
       }
       preferences.save(k, jsonArray.toString())
       return true
     }
   }

   override fun clear(k: String): Boolean {
     preferences.clear(k)
     return true
   }

   override fun clear(): Boolean {
     preferences.clearAll()
     return true
   }

   companion object {
     val gson: Gson = Gson()
   }
 }
