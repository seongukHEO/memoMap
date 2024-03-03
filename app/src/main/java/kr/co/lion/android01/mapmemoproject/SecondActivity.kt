package kr.co.lion.android01.mapmemoproject

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import kr.co.lion.android01.mapmemoproject.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var activitySecondBinding: ActivitySecondBinding

    //확인받은 권한 목록
    var permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("89e2h0bkk5")

        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)
        requestPermissions(permissionList, 0)
        setToolBar()
        settingNaverMap()
    }

    //툴바 설정
    fun setToolBar(){
        activitySecondBinding.apply {
            materialToolbar5.apply {
                title = "나의 MapMemo"
            }
        }
    }

    //네이버 지도 세팅
    fun settingNaverMap(){
        var fm = supportFragmentManager
        var mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync {

        }

    }



}