package com.leveloper.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val repository: PagingRepository
) : ViewModel() {

    // viewmodel 의 사용 이유 : 뷰가 파괴될때 데이터 날라감. 상태관리에 용이하기에
    // viewModel을 사용. 뷰보다 조금 더 오래 살아있음.


    // ViewModel 레이어를 UI에 연결하는 구성요소
    // PagingData 객체는 페이징 된 데이터의 스냅샷을 보유하는 컨테이너.
    // PagingSource 객체를 쿼리 하여 결과를 저장.

    // 필터는 원하는 조건에 맞는 데이터만 가져옴 (20대 이상 이하)
    // 맵은 이 안에서 선언하는거에 따라 새로운 객체를 만듦 , 내가원하는대로 조합해서 새로운 형식으로 출력
    val pagingData = repository.getPagingData().map { pagingData ->
        pagingData.map<String, SampleModel> { SampleModel.Data(it) }
                    //페이징데이터에 실제로 들어간 데이터(string)
            .insertHeaderItem(item = SampleModel.Header("Header"))
            .insertFooterItem(item = SampleModel.Header("Footer"))
            .insertSeparators { before: SampleModel?, after: SampleModel? ->
                if (before is SampleModel.Header || after is SampleModel.Header)
                    SampleModel.Separator
                else
                    null
            }
    }.cachedIn(viewModelScope)
    // 리스트는 빠르게 바뀜
    // 불러올때마다 메모리에 적재시켜버림
    // flow의 기능
    // 비동기로 데이;터 적재

    // cachedIn 을 사용해서 데이터를 캐싱 함.
}