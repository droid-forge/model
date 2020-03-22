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

import promise.commons.createInstance
import promise.commons.model.List
import java.util.*
import kotlin.reflect.KClass

/**
 * The interface provides an instance through the {@link Injector#inject(}
 *
 * @param T the supertype of what is to be injected
 */
interface Injector<out T> {
  /**
   * returns any that extends from T
   *
   * @param E return type can be T
   * @return T or its child
   */
  fun inject(): T
}
/**
 *
 *
 */
interface OnSetupListener {
  /**
   *
   *
   * @param args
   */
  fun onPrepArgs(args: MutableMap<String, Any?>?)
}

interface StoreHelper<T> {
  /**
   *
   *
   * @return
   */
  fun syncStore(): SyncIDataStore<T>

  /**
   *
   *
   * @return
   */
  fun asyncStore(): AsyncIDataStore<T>
}

/**
 *
 *
 * @param T
 * @property store
 */
class StoreRepository<T>(private val store: StoreHelper<T>) {

  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun all(args: MutableMap<String, Any?>?, res: ((List<out T>, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<List<out T>?, Any?> {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().all(res, err, args)
    } else return store.syncStore().all(args)
    return Pair(null, null)
  }

  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun one(args: MutableMap<String, Any?>?, res: ((T, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<T?, Any?> {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().one(res, err, args)
    } else return store.syncStore().one(args)
    return Pair(null, null)
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun save(t: T, args: MutableMap<String, Any?>?, res: ((T, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<T, Any?>? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().save(t, res, err, args)
    } else return store.syncStore().save(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun save(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().save(t, res, err, args)
    } else return store.syncStore().save(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun update(t: T, args: MutableMap<String, Any?>?, res: ((T, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<T, Any?>? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().update(t, res, err, args)
    } else return store.syncStore().update(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun update(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().update(t, res, err, args)
    } else return store.syncStore().update(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun delete(t: T, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().delete(t, res, err, args)
    } else return store.syncStore().delete(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun delete(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().delete(t, res, err, args)
    } else return store.syncStore().delete(t, args)
    return null
  }

  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun clear(args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.asyncStore().clear(res, err, args)
    } else return store.syncStore().clear(args)
    return null
  }

  /**
   *
   *
   * @param args
   * @return
   */
  private fun checkCallbacksNotNull(vararg args: Any?): Boolean {
    for (arg in args) if (arg != null) return true
    return false
  }

  companion object {
    /**
     * used to prepopulate args passed to store calls if their needs to be default
     * variables present in every call
     */
    private var onSetupListener: OnSetupListener? = null

    /**
     * initializes the repo if its undefined
     * @param setup
     */
    @JvmStatic
    @JvmOverloads
    fun setup(setup: OnSetupListener? = null) {
      onSetupListener = setup
      repos = WeakHashMap()
    }

    /**
     * contains all the stores created
     */
    var repos: WeakHashMap<String, StoreRepository<*>>? = null

    /**
     * get the store in the repo with the specified key
     *
     * @param key store identifier
     * @return a store or null if not found
     */
    fun instance(key: String): StoreRepository<*>? = try {
      if (repos == null) setup()
      repos!![key] as StoreRepository<*>
    } catch (e: Exception) {
      null
    }

    /**
     * creates a store repository from injectors
     *
     * @param T type of the repository
     * @param syncInjector models an instance of Synchronous store
     * @param asyncInjector models an instance of Asynchronous store
     * @return a store repository instance
     */
    inline fun <reified T> create(syncInjector: Injector<SyncIDataStore<T>>, asyncInjector: Injector<AsyncIDataStore<T>>): StoreRepository<T> {
      var repo = instance(T::class.java.simpleName)
      if (repo != null) return repo as StoreRepository<T>
      repo = StoreRepository(object : StoreHelper<T> {
        override fun syncStore(): SyncIDataStore<T> = syncInjector.inject()
        override fun asyncStore(): AsyncIDataStore<T> = asyncInjector.inject()
      })
      repos?.put(T::class.java.simpleName, repo)
      return repo
    }

    /**
     * create a repository from classes to be initialized when the repository is not found
     *
     * @param T type of the repository {@code StoreRepository<SomeType>}
     * @param syncClass class of the synchronous store
     * @param asyncClass class of the asynchronous store
     * @param syncArgs constructor arguments for synchronous store class
     * @param asyncArgs constructor arguments for asynchronous store class
     * @return a storeRepository instance
     */
    inline fun <reified T> of(syncClass: KClass<out SyncIDataStore<T>>, asyncClass: KClass<out AsyncIDataStore<T>>, syncArgs: Array<out Any>? = null, asyncArgs: Array<out Any>? = null) =
        create(object : Injector<SyncIDataStore<T>> {
          override fun inject(): SyncIDataStore<T> =
              createInstance(syncClass, syncArgs) as SyncIDataStore<T>
        }, object : Injector<AsyncIDataStore<T>> {
          override fun inject(): AsyncIDataStore<T> =
              createInstance(asyncClass, asyncArgs) as AsyncIDataStore<T>
        })
    /**
     * clear all the cached stores from the repo
     *
     */
    fun clear() {
      repos?.clear()
    }
  }
}