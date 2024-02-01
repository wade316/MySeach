package com.example.mysearch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mysearch.API.Document
import com.example.mysearch.MainActivity
import com.example.mysearch.RetrofitInstance
import com.example.mysearch.databinding.FragmentHomeBinding
import com.example.mysearch.recyclerview.HomeListAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter: HomeListAdapter by lazy {
        HomeListAdapter()
    }

    //클릭시 인터페이스로 넘어오는 데이
    //검색 버튼 눌렀을때 API리스트를 받아오는 변수를 밖으로 빼서 어댑터에서 클릭이벤트로 넘어오는 데이터도 같이 처리할수 있게 함
    private var dataList: List<Document> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        클릭이벤트
        adapter.itemClick = object : HomeListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                (activity as? MainActivity)?.addfavorites(dataList[position])
                Log.d("homefragment", "datalist ${dataList[position]}")

            }
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        클릭이벤트
        binding.tvButton.setOnClickListener {
            val seach = binding.editSeach.text    // EditText입력값 query에 넣기
            Log.d("chales", "$seach")
            lifecycleScope.launch {
                dataList =
                    RetrofitInstance.api.getSearchImage(query = "$seach").documents  //api리스트 받아오기
                Log.d("homefragment", "list = $dataList")
                adapter.submitList(dataList.toList()) //ListAdapter로 바꾸면서 submitlist로 가져온것
            }
        }
        binding.list.adapter = adapter //ListAdapter 만들때
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}