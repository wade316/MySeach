package com.example.mysearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mysearch.API.Document
import com.example.mysearch.databinding.ActivityMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {// 클래스 옆에있으면 상속 변수옆에 있으면 자료형

    private lateinit var binding: ActivityMainBinding

    val favoritesList: MutableList<Document> = mutableListOf() //

    override fun onCreate(savedInstanceState: Bundle?) {//부모가 가지고있는 함수를 쓰려면 override로 불러와야함
        super.onCreate(savedInstanceState)//엄마가 할일을 다끝내고 나서 실행할꺼야 라는 의미
        Log.d("MainActivity test", "$favoritesList")


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
//클릭한 아이템 보관함 리스트에 저장
    fun addfavorites(item: Document) { //
        favoritesList.add(item)
        Log.d("MainActivity", "add $favoritesList")
    }
//보관함에서 클릭한 아이템 리스트에서 삭제
    fun removeFavorites(item: Document) { //
        favoritesList.remove(item)
        Log.d("MainActivity", "remove $favoritesList")
        //홈프레그먼트한테 지웠다고 알려주기
    }
//마지막 검색어 저장
    fun savePrefs(context: Context, query: String){
        val prefs = context.getSharedPreferences("lastSeachDataFile", Context.MODE_PRIVATE)
        prefs.edit().putString("lastSeach", query).apply()
    }
//마지막 검색어 호출
    fun getPrefs(context: Context): String? {
        val prefs = context.getSharedPreferences("lastSeachDataFile", Context.MODE_PRIVATE)
        return prefs.getString("lastSeach", null)
    }

    object Util {
        fun getDateTimeFormat(
            timestamp: String?,
            fromFormatformat: String?,
            toFormatformat: String?
        ): String {
            var date: Date? = null
            var res = ""
            try {
                val format = SimpleDateFormat(fromFormatformat)
                date = format.parse(timestamp)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            Log.d("jbdate", "getDateFromTimestampWithFormat date >> $date")

            val df = SimpleDateFormat(toFormatformat)
            res = df.format(date)
            return res
        }
    }

}