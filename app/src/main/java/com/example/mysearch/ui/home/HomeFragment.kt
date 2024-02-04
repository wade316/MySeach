package com.example.mysearch.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    //클릭시 인터페이스로 넘어오는 데이터
    //검색 버튼 눌렀을때 API리스트를 받아오는 변수를 밖으로 빼서 어댑터에서 클릭이벤트로 넘어오는 데이터도 같이 처리할수 있게 함
    private var dataList: List<Document> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("homefragment test", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("homefragment test", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("homefragment test", "onCreateView")
//        클릭이벤트 아이템 클릭했을때 아이콘 토글
        adapter.itemClick = object : HomeListAdapter.ItemClick {
            override fun onClick(item: Document) {
                if (item.favorite) {
                    (activity as? MainActivity)?.addfavorites(item)
                }else {
                    (activity as? MainActivity)?.removeFavorites(item)
                }
                Log.d("homefragment", "datalist ${item}")
            }
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//마지막 검색어 불러오기
// Fragment에서는 context가 없기때문에 Activity나 class를 따로 만들어서 불러와야한다
//Activity에서 불러오는 함수를 만들어서 가져왔다
        val lastSeach = (activity as? MainActivity)?.getPrefs(requireContext())
        binding.editSeach.setText(lastSeach)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("homefragment test", "onViewCreated")
//        클릭이벤트 검색버튼 눌렀을때 쿼리값에 넣음
        binding.tvButton.setOnClickListener {
            val seach = binding.editSeach.text.toString()    // EditText입력값 query에 넣기
            Log.d("chales", seach)
            //검색어가 비었을때 앱이 죽어버리는 현상이 생겨서 비어있는지 확인하고 검색어가 있을경우에 검색 시작
            if(seach.isNotEmpty()){
                lifecycleScope.launch {
                    dataList =
                        RetrofitInstance.api.getSearchImage(query = seach).documents  //api리스트 받아오기
                    //FavoritItemList List에 넣어보려다 실패
//                    RetrofitInstance.api.getSearchImage(query = seach).documents
//                    val image = RetrofitInstance.api.getSearchImage(query = seach).documents
//                    val title = RetrofitInstance.api.getSearchImage(query = seach).documents
//                    val date = RetrofitInstance.api.getSearchImage(query = seach).documents
//                    Log.d("homefragment", "API list = $dataList")
                    adapter.submitList(dataList.toList()) //ListAdapter로 바꾸면서 submitlist로 가져온것
                }
            }

            //마지막 검색어 저장
            if (seach.isNotEmpty()) {
                (activity as? MainActivity)?.savePrefs(requireContext(), seach)
            }
        }
        binding.list.adapter = adapter //ListAdapter 만들때
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("homefragment test", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("homefragment test", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("homefragment test", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("homefragment test", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("homefragment test", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("homefragment test", "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("homefragment test", "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("homefragment test", "onDestroy")
    }
}