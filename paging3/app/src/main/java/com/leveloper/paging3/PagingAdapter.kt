package com.leveloper.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leveloper.paging3.databinding.ItemSampleBinding
import com.leveloper.paging3.databinding.ItemSampleHeaderBinding
import com.leveloper.paging3.databinding.ItemSampleSeparatorBinding
import java.lang.Exception

// PagingDataAdapter 페이지처리에관련된 모든것 도와줌
class PagingAdapter : PagingDataAdapter<SampleModel, RecyclerView.ViewHolder>(diffCallback) {

    //넘어온데이터의 타입을 알려주는 메소드
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return -1

        return when (item) {
            is SampleModel.Header -> HEADER
            is SampleModel.Data -> DATA
            is SampleModel.Separator -> SEPARATOR
        }
    }

    //데이터를 어떤형식으로 만들지 틀을 만듦
    // ViewType이 넘어오면 그걸 가지고 분기해서~~
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            HEADER -> PagingHeaderViewHolder(ItemSampleHeaderBinding.inflate(layoutInflater, parent, false))
            DATA -> PagingViewHolder(ItemSampleBinding.inflate(layoutInflater, parent, false))
            SEPARATOR -> PagingSeparatorViewHolder(ItemSampleSeparatorBinding.inflate(layoutInflater, parent, false))
            else -> throw Exception()
        }
    }

    //틀안에 내가 받은 데이터를 넣어줌.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder){
                is PagingHeaderViewHolder -> holder.bind(item as SampleModel.Header)
                is PagingViewHolder -> holder.bind(item as SampleModel.Data)
            }
        }
    }

    companion object {
        private const val HEADER = 0
        private const val DATA = 1
        private const val SEPARATOR = 2

        private val diffCallback = object : DiffUtil.ItemCallback<SampleModel>() {
            override fun areItemsTheSame(oldItem: SampleModel, newItem: SampleModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SampleModel, newItem: SampleModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PagingHeaderViewHolder(
    private val binding: ItemSampleHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SampleModel.Header) {
        binding.headerTitle.text = data.title
    }
}

class PagingViewHolder(
    private val binding: ItemSampleBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: SampleModel.Data) {
        binding.textView.text = data.value
    }
}

class PagingSeparatorViewHolder(binding: ItemSampleSeparatorBinding) : RecyclerView.ViewHolder(binding.root)