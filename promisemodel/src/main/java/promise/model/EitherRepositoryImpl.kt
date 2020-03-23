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
 */
class EitherRepositoryImpl <T: Any>(
    /**
     *
     */
    private val store: Injector<EitherIDataStore<T>>): Repository<T> {
  /**
   *
   */
  override fun findAll(args: MutableMap<String, Any?>?, res: ((List<out T>?) -> Unit)?, err: ((Exception) -> Unit)?): List<out T>? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.findAll(args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })

    } else return store.inject()!!.findAll(args).foldSync()
    return null
  }

  override fun findOne(args: MutableMap<String, Any?>?, res: ((T?) -> Unit)?, err: ((Exception) -> Unit)?): T? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.findOne(args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.findOne(args).foldSync()
    return null
  }

  override fun save(t: T, args: MutableMap<String, Any?>?, res: ((T) -> Unit)?, err: ((Exception) -> Unit)?): T {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.save(t, args).fold({
        res(it)
      },{
        err?.invoke(it)
      })

    } else return store.inject()!!.save(t, args).foldSync()!!
    return t
  }

  override fun save(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.save(t, args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.save(t, args).foldSync()
    return null
  }

  override fun update(t: T, args: MutableMap<String, Any?>?, res: ((T) -> Unit)?, err: ((Exception) -> Unit)?): T {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.update(t, args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.update(t, args).foldSync()!!
    return t
  }

  override fun update(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.update(t, args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.update(t, args).foldSync()
    return null
  }

  override fun delete(t: T, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.delete(t, args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.delete(t, args).foldSync()
    return null
  }

  override fun delete(t: List<out T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.delete(t, args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.delete(t, args).foldSync()
    return null
  }

  override fun clear(args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)?, err: ((Exception) -> Unit)?): Any? {
    StoreRepository.onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      requireNotNull(res) { "response must be provided to together with err" }
      store.inject()!!.clear(args).fold({
        res(it)
      }, {
        err?.invoke(it)
      })
    } else return store.inject()!!.clear(args).foldSync()
    return null
  }
}