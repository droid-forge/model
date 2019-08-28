package promise.modelapp

import promise.commons.data.log.LogUtil
import promise.commons.model.List
import promise.model.repo.AbstractAsyncIDataStore
import promise.model.repo.AbstractSyncIDataStore
import promise.model.repo.StoreRepository


class ComplexModel {
  var id = 0
  override fun toString(): String = "ComplexModel(id=$id)"
}

/**
 * sample store for complex models
 *
 */
class SyncComplexModelStore(private val number: Int, private val times: ComplexModel) : AbstractSyncIDataStore<ComplexModel>() {
  val TAG = LogUtil.makeTag(SyncComplexModelStore::class.java)
  /**
   * get all sample complex models
   *
   * @param args/*var currentArgIndex = 0
        val arguments = constructor.parameters
            .map { it.type.classifier as KClass<*> }
            .map {
              val arg = args[currentArgIndex]
              LogUtil.e("_StoreRepository", "arg ", args[currentArgIndex])
              currentArgIndex++
              return arg
            }
            .toTypedArray()*/
   * @return
   */
  override fun all(args: Map<String, Any?>?): Pair<List<ComplexModel>?, Any?> {
    LogUtil.e(TAG, "repo args ", number, times)
    val models = List<ComplexModel>()
    (0 until number * times.id).forEach {
      models.add(ComplexModel().apply { id = it + 1 })
    }
    return Pair(models, null)
  }
}

class AsyncComplexModelStore : AbstractAsyncIDataStore<ComplexModel>()

val complexStore: StoreRepository<ComplexModel> by lazy {
  StoreRepository.of(SyncComplexModelStore::class, AsyncComplexModelStore::class, arrayOf(10, ComplexModel().apply { id = 3 }))
}