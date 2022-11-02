package com.leveloper.paging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PagingViewModel by viewModels()

    private val adapter: PagingAdapter by lazy { PagingAdapter() }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefreshLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //생명주기
//        lifecycleScope.launchWhenResumed {
//            adapter.loadStateFlow.collectLatest { binding.loadingView.isVisible = it.isLoading() }
//        }

                                       //이쪽을 익스텐션으로 가능
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            PagingLoadStateAdapter { adapter.retry() },
            PagingLoadStateAdapter { adapter.retry() }
        )

        // 생명주기에따라 런치바꿔서
        lifecycleScope.launchWhenStarted {
            // flow는 정보를 뿌리고, collect는 정보를 챙겨옴 -> 새로운 데이터가 들어와도
            // 이전 데이터 처리를 끝낸 후 새로운 데이터를 처리함
            // 그러나 collectLatest는 새로운 데이터가 들어오면 이전 데이터의 처리를
            // 강제 종료시키고 새로운 데이터를 처리함.
            viewModel.pagingData.collectLatest {
                adapter.submitData(it)
                // 이쪽 submitData는 suspend 함수이기 때문에 코루틴을 사용하여 호출
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            // refresh
            adapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }


}