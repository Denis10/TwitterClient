package com.vodolazskiy.twitterclient.presentation.base.adapter

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.core.StateExecutor

const val TYPE_UNDEF = -1
const val TYPE_HEADER = 7778881
const val TYPE_LOADING_ENABLED = 777888
const val TYPE_LOADING_DISABLED = -888777

abstract class BaseRecyclerViewAdapter<T>(@LayoutRes private val loadingLayout: Int = R.layout.loader_item_layout) :
        RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseVH<T>>() {

    protected var recyclerViewExecutor = StateExecutor<RecyclerView?, RecyclerView>(null) { it != null }
    var itemsTheSameComparator: ((item1: T, item2: T) -> Boolean) = { item1, item2 -> item1 == item2 }
    var contentTheSameComparator: ((oldItem: T, newItem: T) -> Boolean) = { _, _ -> true }
    var dataStorage: DataStorage<T> = SimpleDataProviderImpl()
        set(value) {
            field = value
            execUpdate { notifyDataSetChanged() }
        }
    var isLoadingEnabled = false
        set(value) {
            if (field != value) {
                field = value
                execUpdate { notifyItemChanged(itemCount - 1) }
            }
        }

    inline fun execUpdate(crossinline update: (() -> Unit)) {
        `access$recyclerViewExecutor`.invoke {
            it.post { update.invoke() }
        }
    }

    /////////////////////////////////// BASIC DATASET OPERATIONS ///////////////////////////////////

    fun add(list: List<T>) = run { dataStorage.addAll(list); Unit }

    fun set(list: List<T>) = dataStorage.set(list)

    fun clear() = dataStorage.clear()

    fun isEmpty() = dataStorage.isEmpty()

    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerViewExecutor.value = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerViewExecutor.value = null
        recyclerViewExecutor.clear()
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<T> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_LOADING_ENABLED -> {
                val view = inflater.inflate(loadingLayout, parent, false)
                LoadingViewHolder(view)
            }
            TYPE_LOADING_DISABLED, TYPE_HEADER -> LoadingViewHolder(View(parent.context))
            else -> createView(parent, viewType, inflater)
        }
    }

    //////////////////////////////////// VIEW HOLDER CREATION //////////////////////////////////////

    /**
     * Create view here instead of onCreateViewHolder
     */
    protected abstract fun createView(parent: ViewGroup, viewType: Int, inflater: LayoutInflater): BaseVH<T>

    override fun onBindViewHolder(holder: BaseVH<T>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_LOADING_ENABLED, TYPE_LOADING_DISABLED, TYPE_HEADER -> {
            }
            else -> holder.bind(getDataItemByAdapterPosition(position))
        }
    }

    //////////////////////////////////////// VIEW TYPES ////////////////////////////////////////////

    open fun getViewType(position: Int): Int = TYPE_UNDEF

    final override fun getItemViewType(position: Int) = when (position) {
        0 -> TYPE_HEADER
        itemCount - 1 -> if (isLoadingEnabled) TYPE_LOADING_ENABLED else TYPE_LOADING_DISABLED
        else -> getViewType(position - emptyHeaderItemsCount)
    }

    /////////////////////////////////// ITEMS COUNT & POSITIONS ////////////////////////////////////

    open val emptyItemCount get() = emptyHeaderItemsCount + emptyBottomItemsCount
    private val emptyHeaderItemsCount = 1
    private val emptyBottomItemsCount = 1

    override fun getItemCount() = dataStorage.size + emptyItemCount

    private fun getDataItemByAdapterPosition(position: Int): T = dataStorage.get(position - emptyHeaderItemsCount)

    fun convertDataIndexToAdapterPosition(index: Int) = index + emptyHeaderItemsCount

    fun convertAdapterPositionToDataIndex(index: Int): Int {
        if (index < 0) return index
        if (index == 0) return -1
        if (index == (itemCount - 1)) return -1
        return index - emptyHeaderItemsCount
    }

    /////////////////////////////////// ABSTRACT VIEW HOLDERS //////////////////////////////////////

    abstract class BaseVH<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    private class LoadingViewHolder<in T>(itemView: View) : BaseVH<T>(itemView) {
        override fun bind(item: T) {}
    }

    /**
     * Base holder for binding holder
     */
    protected abstract class BindingVH<in T>(itemView: View) : BaseVH<T>(itemView)

    /**
     * Base holder for non-binding holder
     */
    protected abstract class KotlinVH<in T>(itemView: View) : BaseVH<T>(itemView)

    ////////////////////////////////////// ABSTRACT DATA STORAGE ///////////////////////////////////

    interface DataStorage<T> : Iterable<T> {

        fun clear()

        fun add(element: T): Boolean

        fun addAll(elements: Collection<T>): Boolean

        fun remove(element: T): Boolean

        fun removeAll(elements: Collection<T>): Boolean

        fun set(elements: List<T>)

        fun get(index: Int): T

        val size: Int

        fun isEmpty() = size == 0
    }

    ////////////////////////////////////////// DATA STORAGE ////////////////////////////////////////

    private inner class SimpleDataProviderImpl : ArrayList<T>(), DataStorage<T> {

        override fun clear() {
            execUpdate {
                val previousSize = size
                super.clear()
                if (previousSize != 0) notifyItemRangeRemoved(emptyHeaderItemsCount, previousSize)
            }
        }

        override fun add(element: T): Boolean {
            execUpdate {
                super.add(element)
                notifyItemInserted(itemCount - emptyBottomItemsCount - 1)
            }
            return true
        }

        override fun addAll(elements: Collection<T>): Boolean {
            execUpdate {
                val newDataList = ArrayList(this)
                newDataList.addAll(elements)

                val diffResult = DiffUtil.calculateDiff(DiffUtilsCallback(this, ReadOnlyDataStorageWrapper(newDataList)), true)
                super.clear()
                super.addAll(newDataList)
                diffResult.dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
            }
            return true
        }

        override fun remove(element: T): Boolean {
            execUpdate {
                val newDataList = ArrayList(this)
                newDataList.remove(element)

                val diffResult = DiffUtil.calculateDiff(DiffUtilsCallback(this, ReadOnlyDataStorageWrapper(newDataList)), true)
                super.clear()
                super.addAll(newDataList)
                diffResult.dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
            }
            return true
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            execUpdate {
                val newDataList = ArrayList(this)
                newDataList.removeAll(elements)

                val diffResult = DiffUtil.calculateDiff(DiffUtilsCallback(this, ReadOnlyDataStorageWrapper(newDataList)), true)
                super.clear()
                super.addAll(newDataList)
                diffResult.dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
            }
            return true
        }

        override fun set(elements: List<T>) {
            execUpdate {
                val diffResult = DiffUtil.calculateDiff(DiffUtilsCallback(this, ReadOnlyDataStorageWrapper(elements)), true)
                super.clear()
                super.addAll(elements)
                diffResult.dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
            }
        }

        override fun isEmpty(): Boolean = size == 0
    }

    /////////////////////////////////////// DIFF UTILS HELPERS /////////////////////////////////////

    inner class DiffUtilsCallback(private val oldData: DataStorage<T>, private val newData: DataStorage<T>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItemType: Int = when (oldItemPosition) {
                0 -> TYPE_HEADER
                oldListSize - 1 -> TYPE_LOADING_ENABLED
                else -> TYPE_UNDEF
            }
            val newItemType: Int = when (newItemPosition) {
                0 -> TYPE_HEADER
                newListSize - 1 -> TYPE_LOADING_ENABLED
                else -> TYPE_UNDEF
            }

            return if (oldItemType == TYPE_UNDEF && newItemType == TYPE_UNDEF) {
                val oldClientPos = oldItemPosition - emptyHeaderItemsCount
                val newClientPos = newItemPosition - emptyHeaderItemsCount
                val oldClientItem = oldData.get(oldClientPos)
                val newClientItem = newData.get(newClientPos)

                return itemsTheSameComparator.invoke(oldClientItem, newClientItem)
            } else {
                oldItemType == newItemType
            }
        }

        override fun getOldListSize(): Int = oldData.size + emptyItemCount

        override fun getNewListSize(): Int = newData.size + emptyItemCount

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItemType: Int = when (oldItemPosition) {
                0 -> TYPE_HEADER
                oldListSize - 1 -> TYPE_LOADING_ENABLED
                else -> TYPE_UNDEF
            }
            val newItemType: Int = when (newItemPosition) {
                0 -> TYPE_HEADER
                newListSize - 1 -> TYPE_LOADING_ENABLED
                else -> TYPE_UNDEF
            }

            return if (oldItemType == TYPE_UNDEF && newItemType == TYPE_UNDEF) {

                val oldClientPos = oldItemPosition - emptyHeaderItemsCount
                val newClientPos = newItemPosition - emptyHeaderItemsCount
                val oldClientItem = oldData.get(oldClientPos)
                val newClientItem = newData.get(newClientPos)

                return contentTheSameComparator.invoke(oldClientItem, newClientItem)
            } else oldItemType == newItemType
        }
    }

    class MutableDataStorageWrapper<T>(private val wrappedMutableVal: MutableList<T>) : ReadOnlyDataStorageWrapper<T>(wrappedMutableVal) {

        override fun clear(): Unit = wrappedMutableVal.clear()

        override fun add(element: T): Boolean = wrappedMutableVal.add(element)

        override fun addAll(elements: Collection<T>): Boolean = wrappedMutableVal.addAll(elements)

        override fun remove(element: T): Boolean = wrappedMutableVal.remove(element)

        override fun removeAll(elements: Collection<T>): Boolean = wrappedMutableVal.removeAll(elements)

        override fun set(elements: List<T>) {
            wrappedMutableVal.clear()
            wrappedMutableVal.addAll(elements)
        }
    }

    open class ReadOnlyDataStorageWrapper<T>(private val wrappedVal: List<T>) : DataStorage<T> {

        override val size: Int get() = wrappedVal.size

        override fun clear(): Unit = throw IllegalStateException("clear() called in the ReadOnlyDataStorageWrapper")

        override fun add(element: T): Boolean = throw IllegalStateException("add() called in the ReadOnlyDataStorageWrapper")

        override fun addAll(elements: Collection<T>): Boolean = throw IllegalStateException("addAll() called in the ReadOnlyDataStorageWrapper")

        override fun remove(element: T): Boolean = throw IllegalStateException("remove() called in the ReadOnlyDataStorageWrapper")

        override fun removeAll(elements: Collection<T>): Boolean = throw IllegalStateException("removeAll() called in the ReadOnlyDataStorageWrapper")

        override fun set(elements: List<T>): Unit = throw IllegalStateException("set() called in the ReadOnlyDataStorageWrapper")

        override fun get(index: Int): T = wrappedVal[index]

        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                private var cursor = 0
                override fun hasNext() = cursor < size

                override fun next(): T = get(cursor++)
            }
        }
    }

    @PublishedApi
    internal var `access$recyclerViewExecutor`: StateExecutor<RecyclerView?, RecyclerView>
        get() = recyclerViewExecutor
        set(value) {
            recyclerViewExecutor = value
        }
}
