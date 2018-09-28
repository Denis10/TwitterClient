package com.vodolazskiy.twitterclient.presentation.base.adapter

import android.support.v7.util.SortedList
import android.support.v7.widget.util.SortedListAdapterCallback

abstract class BaseSortedRecyclerViewAdapter<T> : BaseRecyclerViewAdapter<T>() {
    abstract val itemsComparator: (item1: T, item2: T) -> Int

    fun add(item: T) = run { dataStorage.add(item); Unit }

    protected open class SortedDataStorage<T>(itemClass: Class<T>, private val adapter: BaseSortedRecyclerViewAdapter<T>) : DataStorage<T> {
        protected val wrappedList = SortedList<T>(itemClass, SortedDataStorageCallback(adapter))

        override fun clear() {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                wrappedList.clear()
                wrappedList.endBatchedUpdates()
            }
        }

        override fun add(element: T): Boolean {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                wrappedList.add(element)
                wrappedList.endBatchedUpdates()
            }

            return true
        }

        override fun addAll(elements: Collection<T>): Boolean {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                wrappedList.addAll(elements)
                wrappedList.endBatchedUpdates()
            }
            return true
        }

        override fun remove(element: T): Boolean {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                wrappedList.remove(element)
                wrappedList.endBatchedUpdates()
            }
            return true
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                elements.forEach {
                    wrappedList.remove(it)
                }
                wrappedList.endBatchedUpdates()
            }
            return true
        }

        override fun set(elements: List<T>) {
            adapter.execUpdate {
                wrappedList.beginBatchedUpdates()
                wrappedList.replaceAll(elements)
                wrappedList.endBatchedUpdates()
            }
        }

        override fun get(index: Int): T = wrappedList[index]

        override val size: Int get() = wrappedList.size()

        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                private var cursor = 0
                override fun hasNext() = cursor < size
                override fun next(): T = get(cursor++)
            }
        }
    }

    private class SortedDataStorageCallback<T>(private val adapter: BaseSortedRecyclerViewAdapter<T>) : SortedList.Callback<T>() {

        private val defCallback = object : SortedListAdapterCallback<T>(adapter) {
            override fun areItemsTheSame(item1: T, item2: T) = adapter.itemsTheSameComparator.invoke(item1, item2)
            override fun areContentsTheSame(oldItem: T, newItem: T) = adapter.contentTheSameComparator.invoke(oldItem, newItem)
            override fun compare(o1: T, o2: T) = adapter.itemsComparator.invoke(o1, o2)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) = defCallback.onMoved(convert(fromPosition), convert(toPosition))

        override fun onRemoved(position: Int, count: Int) = defCallback.onRemoved(convert(position), count)

        override fun onChanged(position: Int, count: Int) = defCallback.onChanged(convert(position), count)

        override fun onInserted(position: Int, count: Int) = defCallback.onInserted(convert(position), count)

        override fun compare(o1: T, o2: T): Int = defCallback.compare(o1, o2)

        override fun areContentsTheSame(oldItem: T, newItem: T) = defCallback.areContentsTheSame(oldItem, newItem)

        override fun areItemsTheSame(item1: T, item2: T) = defCallback.areItemsTheSame(item1, item2)

        private fun convert(dataPosition: Int) = adapter.convertDataIndexToAdapterPosition(dataPosition)
    }
}
