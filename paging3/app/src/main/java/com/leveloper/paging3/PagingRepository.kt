package com.leveloper.paging3

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// 레포지토리는 사용하라고 만들어 준 건가??
// 레포지토리는 내가 이런함수를 쓰겠다.
class PagingRepository @Inject constructor(
    private val service: SampleBackendService
    //서비스는 레트로핏에서 API 통신 규격과 비슷
) {

    // Pager는 PagingSource객체, PagingConfig 객체를 바탕으로 반응형
    // 스트림을 생성.
    // 반응형 스트림은 정보를 계속 뿌려준다는건가???
    fun getPagingData(): Flow<PagingData<String>> {
        // 컨피그 : 설정파일 1부터 몇개
        return Pager(PagingConfig(pageSize = 10)) {
            SamplePagingSource(service)
            // 리턴값을 방출
        }.flow
    }
}