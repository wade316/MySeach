package com.example.mysearch.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysearch.API.Document
import com.example.mysearch.MainActivity
import com.example.mysearch.databinding.FragmentFavoriteBinding
import com.example.mysearch.recyclerview.FavoriteListAdapter
import com.example.mysearch.recyclerview.HomeListAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val mainActivity by lazy { activity as MainActivity }

    private val adapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter()
    }

    //마이페이지 리싸이클러뷰에 메인액티비티에 리스트 출력
    //마이페이지에서 클릭했을때 페이보릿리스트에서 삭제하기
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




//        mainActivity.favoritesList

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.repositoryList.adapter = FavoriteListAdapter()
        adapter.submitList(mainActivity.favoritesList)
        binding.repositoryList.adapter = adapter

        adapter.itemClick = object : FavoriteListAdapter.ItemClick {
            override fun onClick(item: Document) {

                (activity as? MainActivity)?.removeFavorites(item)
                adapter.notifyDataSetChanged()


                Log.d("favoritefragment", "remove  ${item}")

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//클래스, 함수, 인터페이스, 변수사용?