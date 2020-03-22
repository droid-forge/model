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

import androidx.collection.ArrayMap
import org.json.JSONException
import org.json.JSONObject
import promise.commons.data.log.LogUtil
import promise.commons.model.Identifiable
import promise.commons.model.List
import promise.commons.pref.Preferences
import promise.commons.util.DoubleConverter

const val ID_ARG = "id_arg"

class PreferenceDatabase<ID : Any, T : Identifiable<ID>>(name: String,
                                                         private val idMapper: DoubleConverter<ID, String, String>,
                                                         private val converter: DoubleConverter<T, JSONObject, JSONObject>)
  : AbstractSyncIDataStore<T>() {
  /**
   *
   */
  private val preferences: Preferences = Preferences(name)

  /**
   *
   */
  private fun mapToPair(t: T): Pair<String?, JSONObject?> =
      Pair<String, JSONObject?>(idMapper.serialize(t.getId()), converter.serialize(t))

  /**
   *
   */
  override fun all(args: Map<String, Any?>?): Pair<List<out T>, Any?> {
    val list = List<T>()
    for ((key, value) in preferences.all) {
      val `val` = value as String
      try {
        list.add(converter.deserialize(JSONObject(`val`)).apply {
          setId(idMapper.deserialize(key))
        })
      } catch (e: JSONException) {
        LogUtil.e(TAG, "error de-serializing  ", `val`)
      }
    }
    return Pair(list, "all items")
  }

  override fun one(args: Map<String, Any?>?): Pair<T?, Any?> {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    val id = args[ID_ARG] as ID
    val prefKey = idMapper.serialize(id)
    val prefValue = preferences.getString(prefKey)
    if (prefValue.isEmpty()) return Pair(null, "not found")
    return Pair(converter.deserialize(JSONObject(prefValue)), prefKey)
  }
  /**
   *
   */
  @Throws(Exception::class)
  fun all(args: Map<String, *>?, mapFilter: MapFilter<in T>): List<out T> {
    val list = List<T>()
    for ((key, value) in preferences.all) {
      val `val` = value as String
      list.add(converter.deserialize(JSONObject(`val`))
          .apply {
            setId(idMapper.deserialize(key))
          })
    }
    return list.filter { t: T -> mapFilter.filter(t, args) }
  }

  /**
   *
   */
  interface MapFilter<T : Identifiable<*>> {
    /**
     *
     */
    fun filter(t: T, args: Map<String, *>?): Boolean
  }

  /**
   *
   */
  override fun save(t: T, args: Map<String, Any?>?): Pair<T, Any?> {
    val pair = mapToPair(t)
    preferences.save(pair.first!!, pair.second.toString())
    return Pair(t, pair.first)
  }

  /**
   *
   */
  override fun save(t: List<out T>, args: Map<String, Any?>?): Any? {
    val map = ArrayMap<String, Any>()
    t.forEach {
      val pair = mapToPair(it)
      map[pair.first] = pair.second.toString()
    }
    preferences.save(map)
    return "saved"
  }

  /**
   *
   */
  override fun delete(t: T, args: Map<String, Any?>?): Any? {
    preferences.clear(idMapper.serialize(t.getId()))
    return "deleted"
  }

  /**
   *
   */
  override fun delete(t: List<out T>, args: Map<String, Any?>?): Any? {
    t.forEach {
      delete(it, args)
    }
    return "deleted"
  }

  /**
   *
   */
  override fun update(t: T, args: Map<String, Any?>?): Pair<T, Any?> = save(t, args)

  /**
   *
   */
  override fun update(t: List<out T>, args: Map<String, Any?>?): Any? = save(t, args)

  /**
   *
   */
  fun clear() {
    preferences.clearAll()
  }

  companion object {
    /**
     *
     */
    private val TAG = LogUtil.makeTag(PreferenceDatabase::class.java)
  }
}