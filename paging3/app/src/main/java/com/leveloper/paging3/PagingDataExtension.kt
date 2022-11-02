package com.leveloper.paging3

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

/**
 * 2022-11-03
 * pureum
 */

// CombinedLoadStates 어뎁터에서 페이지 상태를 넘겨줌.
fun CombinedLoadStates.isEmpty(itemCount: Int): Boolean =
    this.refresh is LoadState.NotLoading && itemCount == 0

fun CombinedLoadStates.isLoading(): Boolean = this.source.refresh is LoadState.Loading

fun CombinedLoadStates.isRetry(): Boolean = this.source.refresh is LoadState.Error

fun CombinedLoadStates.errorMessage(): String {
    val errorState = this.source.append as? LoadState.Error
        ?: this.source.prepend as? LoadState.Error
        ?: this.append as? LoadState.Error
        ?: this.prepend as? LoadState.Error
        ?: this.refresh as? LoadState.Error

    return errorState?.error?.localizedMessage ?: ""
}

fun CombinedLoadStates.isError(): Boolean{
    val errorState = this.source.append as? LoadState.Error
        ?: this.source.prepend as? LoadState.Error
        ?: this.append as? LoadState.Error
        ?: this.prepend as? LoadState.Error
        ?: this.refresh as? LoadState.Error

    return errorState != null
}