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

import promise.commons.data.log.LogUtil
import promise.commons.model.Identifiable
import promise.commons.model.List
import promise.commons.tx.Either
import promise.commons.tx.Left
import promise.commons.tx.Right
import promise.model.AbstractEitherIDataStore
import promise.model.PreferenceDatabase
import promise.model.Repository
import promise.model.StoreRepository

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

class SyncComplexModelStore(
    private val preferenceDatabase: PreferenceDatabase<Int, ComplexModel>) :
    AbstractEitherIDataStore<ComplexModel>() {

  val TAG = LogUtil.makeTag(SyncComplexModelStore::class.java)

  override fun findAll(args: Map<String, Any?>?): Either<List<out ComplexModel>> {
    if (args == null) throw IllegalArgumentException("number and times args must be passed")
    val number = args[NUMBER_ARG] as Int
    val times = args[TIMES_ARG] as Int
    LogUtil.e(TAG, "repo args ", number, times)
    val savedModels = preferenceDatabase.findAll()
    return Right(if (savedModels.isEmpty()) {
      val models = List<ComplexModel>()
      (0 until number * times).forEach {
        models.add(ComplexModel().apply {
          uId = it + 1
          name = getId().toString() + "n"
          isModel = getId().rem(2) == 0
        })
      }
      preferenceDatabase.save(models)
      models
    } else List(savedModels))
  }

  override fun findOne(args: Map<String, Any?>?): Either<ComplexModel> {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    val model = preferenceDatabase.findOne(args)
    return if (model != null) Right(model) else
      Left(Exception("model not found"))
  }
}

val complexStore: Repository<ComplexModel> by lazy {
  StoreRepository.of(
      SyncComplexModelStore::class,
      arrayOf(preferenceDatabase))
}