package com.leveloper.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class pureum(a : Int){

}

// Dagger가 객체를 주입해야하는 위치 선정???
// 페이지에 데이터 전송할때 미리 정하는 방식을 이쪽에서 정의
class SamplePagingSource @Inject constructor(
    private val service: SampleBackendService
) : PagingSource<Int, String>() {
    // 서버에 몇번째데이터를 보낼때 알려주는 데이터 타입
    // 내가 서버에다 요청했을때 리턴값의 데이터 타입


    // load는 params를 바탕으로 페이지의 데이터를 반환하게 됨
    // load의 파라미터로 params key 값이 페이지 정보임, loadSize도 가지고있음.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        //리턴을 통해 정상일 경우 LoadResult.Page.
        return try {
            delay(500)

            // 에러 발생 !
            if (Random.nextFloat() < 0.5) {
                throw Exception("error !!!")
            }

            // 스타트 인덱스는 유동적으로
            val next = params.key ?: 0

            val response = service.getPagingData(next)
            // 서비스 받는 쪽 (레드로핏)

            LoadResult.Page(
                data = response.data,
                prevKey = if (next == 0) null else next - 1,
                // 게시판에서 밑에 1 2 3 4 페이지쓸때
                // 앱에서 스코롤할땐 프리브 키가 없음
                nextKey = next + 1
                // 상황에 맞춰서 IF문으로 분기
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
            // 에러가 발생하면 어뎁터에서 스테이트 관리 처리해줌
        }
    }

    // [ PagingState를 인자로 받음.
    // 로드된 페이지 및 마지막 엑세스 위치 등의
    // 페이징 시스템 스냅샷 상태를 가지고있음 ]
//    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int = 0
    // 일자형 스크롤쓸때 사용 0 OR 1
}