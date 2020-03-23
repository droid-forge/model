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
import promise.commons.model.List



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
  fun syncStore(): SyncIDataStore<T>?

  /**
   *
   *
   * @return
   */
  fun asyncStore(): AsyncIDataStore<T>?
}

/**
 *
 *
 * @param T
 * @property store
 */
class RepositoryImpl<T>(private val store: StoreHelper<T>): Repository<T> {

   @Throws(Exception::class)
  override fun findAll(args: MutableMap<String, Any?>?, res: ((List<out T>?) -> Unit)?, err: ((Exception) -> Unit)?): List<out T>? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.findAll(res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.findAll(args)
    }
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
  @Throws(Exception::class)
  override fun findOne(args: MutableMap<String, Any?>?, res: ((T?) -> Unit)?, err: ((Exception) -> Unit)?): T? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()?.findOne(res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.findOne(args)
    }
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
  @Throws(Exception::class)
  override fun save(t: T, args: MutableMap<String, Any?>?, res: ((T) -> Unit)?, err: ((Exception) -> Unit)?): T {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.save(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.save(t, args)
    }
    return t
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
  @Throws(Exception::class)
  override fun save(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.save(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.save(t, args)
    }
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
  @Throws(Exception::class)
  override fun update(t: T, args: MutableMap<String, Any?>?, res: ((T) -> Unit)?, err: ((Exception) -> Unit)?): T {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.update(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.update(t, args)
    }
    return t
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
  @Throws(Exception::class)
  override fun update(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.update(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.update(t, args)
    }
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
  @Throws(Exception::class)
  override fun delete(t: T, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.delete(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.delete(t, args)
    }
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
  @Throws(Exception::class)
  override fun delete(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.delete(t, res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.delete(t, args)
    }
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
  @Throws(Exception::class)
  override fun clear(args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      requireNotNull(store.asyncStore()) { "Repo does not have async store" }
      store.asyncStore()!!.clear(res, err, args)
    } else {
      if (store.syncStore() == null) throw IllegalStateException("Repo does not have Sync store")
      return store.syncStore()!!.clear(args)
    }
    return null
  }


}
