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

package promise.modelapp

import org.json.JSONObject
import promise.commons.data.log.LogUtil
import promise.commons.model.Identifiable
import promise.commons.model.List
import promise.commons.util.DoubleConverter
import promise.model.AbstractAsyncIDataStore
import promise.model.AbstractSyncIDataStore
import promise.model.StoreRepository
import promise.model.PreferenceDatabase

class ComplexModel : Identifiable<Int> {
  var uId = 0

  var name: String = ""
  var isModel = false

  override fun toString(): String = "ComplexModel(id=$uId)"
  override fun getId(): Int = uId
  override fun setId(t: Int) {
    this.uId = t
  }
}

const val NUMBER_ARG = "number_arg"
const val TIMES_ARG = "times_arg"
const val ID_ARG = "id_arg"

/**
 * sample store for complex models
 *
 */

class SyncComplexModelStore(private val preferenceDatabase: PreferenceDatabase<Int, ComplexModel>) : AbstractSyncIDataStore<ComplexModel>() {
  val TAG = LogUtil.makeTag(SyncComplexModelStore::class.java)

  override fun all(args: Map<String, Any?>?): Pair<List<ComplexModel>?, Any?> {
    if (args == null) throw IllegalArgumentException("number and times args must be passed")
    val number = args[NUMBER_ARG] as Int
    val times = args[TIMES_ARG] as Int
    LogUtil.e(TAG, "repo args ", number, times)
    val savedModels = preferenceDatabase.all().first
    return if (savedModels.isEmpty()) {
      val models = List<ComplexModel>()
      (0 until number * times).forEach {
        models.add(ComplexModel().apply {
          uId = it + 1
          name = getId().toString() + "n"
          isModel = getId().rem(2) == 0
        })
      }
      Pair(models, preferenceDatabase.save(models))
    } else Pair(List(savedModels), "from cache")
  }

  override fun one(args: Map<String, Any?>?): Pair<ComplexModel?, Any?> {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    return preferenceDatabase.one(args)
  }
}

class AsyncComplexModelStore : AbstractAsyncIDataStore<ComplexModel>()

val complexStore: StoreRepository<ComplexModel> by lazy {
  StoreRepository.of(
      SyncComplexModelStore::class,
      AsyncComplexModelStore::class,
      arrayOf(preferenceDatabase))
}