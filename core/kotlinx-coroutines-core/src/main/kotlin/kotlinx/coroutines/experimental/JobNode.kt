/*
 * Copyright 2016-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlinx.coroutines.experimental

import kotlinx.coroutines.experimental.internal.*

internal actual abstract class JobNode<out J : Job> actual constructor(
    @JvmField actual val job: J
) : LockFreeLinkedListNode(), DisposableHandle, CompletionHandler, Incomplete {
    actual inline val asHandler: CompletionHandler get() = this
    actual override val isActive: Boolean get() = true
    actual override val list: NodeList? get() = null
    actual override fun dispose() = (job as JobSupport).removeNode(this)
    actual abstract override fun invoke(reason: Throwable?)
}

internal actual inline val CompletionHandler.asJobNode: JobNode<*>?
    get() = this as? JobNode<*>
