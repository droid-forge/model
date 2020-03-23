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
import java.util.*
import kotlin.reflect.KClass

object StoreRepository {
  /**
   * used to prepopulate args passed to store calls if their needs to be default
   * variables present in every call
   */
  internal var onSetupListener: OnSetupListener? = null

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
  var repos: WeakHashMap<String, Repository<*>>? = null

  /**
   * get the store in the repo with the specified key
   *
   * @param key store identifier
   * @return a store or null if not found
   */
  fun instance(key: String): Repository<*>? = try {
    if (repos == null) setup()
    repos!![key] as Repository<*>
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
  inline fun <reified T> create(syncInjector: Injector<SyncIDataStore<T>>,
                                asyncInjector: Injector<AsyncIDataStore<T>>): Repository<T> {
    var repo = instance(T::class.java.simpleName)
    if (repo != null) return repo as Repository<T>
    repo = RepositoryImpl(object : StoreHelper<T> {
      override fun syncStore(): SyncIDataStore<T>? = syncInjector.inject()
      override fun asyncStore(): AsyncIDataStore<T>? = asyncInjector.inject()
    })
    repos?.put(T::class.java.simpleName, repo!!)
    return repo
  }

  inline fun <reified T : Any> create(injector: Injector<EitherIDataStore<T>>): Repository<T> {
    var repo = instance(T::class.java.simpleName)
    if (repo != null) return repo as Repository<T>
    repo = EitherRepositoryImpl(object : Injector<EitherIDataStore<T>> {
      override fun inject(): EitherIDataStore<T> = injector.inject()!!
    })

    repos?.put(T::class.java.simpleName, repo!!)
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
  inline fun <reified T> of(syncClass: KClass<out SyncIDataStore<T>>,
                            asyncClass: KClass<out AsyncIDataStore<T>>,
                            syncArgs: Array<out Any?>? = null,
                            asyncArgs: Array<out Any?>? = null) =
      create(object : Injector<SyncIDataStore<T>> {
        override fun inject(): SyncIDataStore<T> =
            createInstance(syncClass, syncArgs) as SyncIDataStore<T>
      }, object : Injector<AsyncIDataStore<T>> {
        override fun inject(): AsyncIDataStore<T> =
            createInstance(asyncClass, asyncArgs) as AsyncIDataStore<T>
      })

  /**
   *
   */
  inline fun <reified T> sync(syncClass: KClass<out SyncIDataStore<T>>,
                              syncArgs: Array<out Any?>? = null) =
      create(object : Injector<SyncIDataStore<T>> {
        override fun inject(): SyncIDataStore<T> =
            createInstance(syncClass, syncArgs) as SyncIDataStore<T>
      }, object : Injector<AsyncIDataStore<T>> {
        override fun inject(): AsyncIDataStore<T>? = null
      })

  /**
   *
   */
  inline fun <reified T> async(asyncClass: KClass<out AsyncIDataStore<T>>,
                               asyncArgs: Array<out Any?>? = null) =
      create(object : Injector<SyncIDataStore<T>> {
        override fun inject(): SyncIDataStore<T>? =
            null
      }, object : Injector<AsyncIDataStore<T>> {
        override fun inject(): AsyncIDataStore<T> =
            createInstance(asyncClass, asyncArgs) as AsyncIDataStore<T>
      })

  /**
   *
   */
  inline fun <reified T : Any> of(eitherClass: KClass<out EitherIDataStore<T>>,
                                  eitherArgs: Array<out Any?>? = null) =
      create(object : Injector<EitherIDataStore<T>> {
        override fun inject(): EitherIDataStore<T>? =
            createInstance(eitherClass, eitherArgs)
      })

  /**
   * clear all the cached stores from the repo
   *
   */
  fun clear() {
    repos?.clear()
  }
}