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
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import promise.commons.data.log.LogUtil
import promise.commons.model.Identifiable
import promise.commons.model.List
import promise.commons.pref.Preferences
import promise.commons.util.DoubleConverter
import java.lang.IllegalStateException

const val ID_ARG = "id_arg"
const val CLASS_ARG = "class_arg"

class PreferenceDatabase<T : Identifiable<*>>
@JvmOverloads constructor (name: String,
                           private val converter: DoubleConverter<T, JSONObject, JSONObject>? = null)

  : AbstractSyncIDataStore<T>() {
  /**
   *
   */
  private val preferences: Preferences = Preferences(name)

  /**
   *
   */
  private fun mapToPair(t: T): Pair<String?, JSONObject?> {
    val id = t.getId()
    if (isWrapperType(id!!.javaClass))
      return Pair<String, JSONObject?>(id.toString(), converter?.serialize(t) ?: JSONObject(gson.toJson(t)))
    else throw IllegalStateException("id must be primitive type")
  }

  /**
   *
   */
  override fun findAll(args: Map<String, Any?>?): List<out T> {
    val list = List<T>()
    for ((_, value) in preferences.all) {
      val `val` = value as String
      try {
        list.add(converter?.deserialize(JSONObject(`val`)) ?: gson.fromJson(`val`, args!![CLASS_ARG] as Class<T>))
      } catch (e: JSONException) {
        LogUtil.e(TAG, "error de-serializing  ", `val`)
      }
    }
    return list
  }

  override fun findOne(args: Map<String, Any?>?): T? {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    val id = args[ID_ARG]
    val prefKey = id.toString()
    val prefValue = preferences.getString(prefKey)
    if (prefValue.isEmpty()) return null
    return converter?.deserialize(JSONObject(prefValue)) ?: gson.fromJson(prefValue, args!![CLASS_ARG] as Class<T>)
  }
  /**
   *
   */
  @Throws(Exception::class)
  fun findAll(args: Map<String, *>?, mapFilter: MapFilter<in T>): List<out T> {
    val list = List<T>()
    for ((key, value) in preferences.all) {
      val `val` = value as String
      list.add(converter?.deserialize(JSONObject(`val`)) ?: gson.fromJson(`val`, args!![CLASS_ARG] as Class<T>))
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
  override fun save(t: T, args: Map<String, Any?>?): T {
    val pair = mapToPair(t)
    preferences.save(pair.first!!, pair.second.toString())
    return t
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
    preferences.clear(t.getId().toString())
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
  override fun update(t: T, args: Map<String, Any?>?): T = save(t, args)

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

    val gson: Gson = Gson()
    /**
     *
     */
    private val TAG = LogUtil.makeTag(PreferenceDatabase::class.java)


    fun isWrapperType(clazz: Class<*>?): Boolean = getWrapperTypes().contains(clazz)

    private fun getWrapperTypes(): Set<Class<*>> {
      val ret: MutableSet<Class<*>> = HashSet()
      ret.add(Boolean::class.java)
      ret.add(Char::class.java)
      ret.add(Byte::class.java)
      ret.add(Short::class.java)
      ret.add(Int::class.java)
      ret.add(Long::class.java)
      ret.add(Float::class.java)
      ret.add(Double::class.java)
      return ret
    }
  }
}