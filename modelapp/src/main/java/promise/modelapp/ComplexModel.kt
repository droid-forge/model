package promise.modelapp

import org.json.JSONObject
import promise.commons.data.log.LogUtil
import promise.commons.model.Identifiable
import promise.commons.model.List
import promise.commons.util.DoubleConverter
import promise.model.repo.AbstractAsyncIDataStore
import promise.model.repo.AbstractSyncIDataStore
import promise.model.repo.StoreRepository
import promise.model.store.PreferenceDatabase

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
      arrayOf(PreferenceDatabase("models",
          object : DoubleConverter<Int, String, String> {
            override fun deserialize(e: String): Int = e.toInt()
            override fun serialize(t: Int): String = t.toString()
          },
          object : DoubleConverter<ComplexModel, JSONObject, JSONObject> {
            override fun deserialize(e: JSONObject): ComplexModel = ComplexModel().apply {
              name = e.getString("name")
              isModel = e.getBoolean("isModel")
            }

            override fun serialize(t: ComplexModel): JSONObject = JSONObject().apply {
              put("name", t.name)
              put("isModel", t.isModel)
            }
          })))
}