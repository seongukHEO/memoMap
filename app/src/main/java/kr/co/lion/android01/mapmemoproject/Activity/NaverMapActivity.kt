package kr.co.lion.android01.mapmemoproject.Activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kr.co.lion.android01.mapmemoproject.DataClassAll.MemoInfo
import kr.co.lion.android01.mapmemoproject.Fragment.MemoInfoFragment
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.SQL.DAO.InfoDAO
import kr.co.lion.android01.mapmemoproject.SQL.DAO.MemoDAO
import kr.co.lion.android01.mapmemoproject.Util
import kr.co.lion.android01.mapmemoproject.databinding.ActivityNaverMapBinding


class NaverMapActivity : AppCompatActivity() {

    lateinit var activityNaverMapBinding: ActivityNaverMapBinding

    //위치 정보를 관리하는 객체
    lateinit var locationManager: LocationManager

    //위치 측정이 성공하면 동작할 리스너
    var gpsLocationListener: MyLocationListener? = null

    //네이버 지도 객체를 담을 프로퍼티
    lateinit var naverMap: NaverMap

    lateinit var locationSource: FusedLocationSource


    //런쳐
    lateinit var thirdActivityLauncher: ActivityResultLauncher<Intent>

    //확인받은 권한 목록
    var permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var allMemo: List<MemoInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        activityNaverMapBinding = ActivityNaverMapBinding.inflate(layoutInflater)
        setContentView(activityNaverMapBinding.root)
        requestPermissions(permissionList, 0)
        initialization()
        setToolBar()
        settingNaverMap()
        initView()
        //markerFromDataBase()


        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("89e2h0bkk5")
    }

    override fun onResume() {
        super.onResume()
        initialization()
        //markerFromDataBase()
    }

    //초기화 작업
    private fun initialization() {
        allMemo = MemoDAO.selectAllMemo(this)

        //Log.d("seong7182", "${allMemo}")
    }

    //위 경도를 추출하여 마커를 표시하는 함수
    private fun markerFromDataBase(){
        val memoList = allMemo
        for (memo in memoList){
            screenJob(memo.latitude, memo.longitude)
        }
    }

    //뷰 설정
    private fun initView() {
        val contract = ActivityResultContracts.StartActivityForResult()
        thirdActivityLauncher = registerForActivityResult(contract) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    val latitude = it?.data!!.getDoubleExtra("latitude", 0.00)
                    val longitude = it?.data!!.getDoubleExtra("longitude", 0.00)
                    //Log.d("test1234", "${latitude}, ${longitude}")
                    screenJob(latitude, longitude)
                }
            }
        }
    }

    private fun getUserMemo(latitude: Double, longitude: Double): MemoInfo? {
        allMemo.forEach {
            if (it.latitude == latitude && it.longitude == longitude) {
                return it
            }
        }
        return null
    }

    //툴바 설정
    private fun setToolBar() {
        activityNaverMapBinding.apply {
            materialToolbar5.apply {
                val nickname = intent.getStringExtra("nickname")
                if (nickname != null){
                    val showNickName = InfoDAO.selectOneInfo(this@NaverMapActivity, nickname)
                    title = "${showNickName?.nickName}의 메모 지도"
                }


                //연습
                inflateMenu(R.menu.main_menu)
                //클릭
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.practice_menu -> {
                            val newIntent = Intent(this@NaverMapActivity, MemoActivity::class.java)
                            startActivity(newIntent)
                        }

                        R.id.my_location_here -> {
                            getMyLocation()
                        }
                    }

                    true
                }
            }
        }
    }

    //위치 측정이 성공하면 동작하는 리스너
    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {

            when (location.provider) {
                //Gps 프로바이더 라면?
                LocationManager.GPS_PROVIDER -> {
                    locationManager.removeUpdates(gpsLocationListener!!)
                    gpsLocationListener == null
                }
            }
            setMyLocation(location)
        }

    }

    //화면 작업
    private fun screenJob(latitude1: Double, longitude1: Double) {
        //마커 옵션을 만들어준다
        val marker = Marker()
        marker.position = LatLng(latitude1, longitude1)
        marker.map = naverMap

        marker.setOnClickListener {
            val getData = getUserMemo(latitude1, longitude1)
            if (getData != null) {
                val bottomShowFragment = MemoInfoFragment()

                val bundle = Bundle()
                bundle.putInt("idx", getData.idx)
                bottomShowFragment.arguments = bundle

                bottomShowFragment.show(this.supportFragmentManager, "bottomSheet")
            } else {
                Util.showDiaLog(
                    this,
                    "네트워크 오류",
                    "GPS가 불안정합니다"
                ) { dialogInterface: DialogInterface, i: Int ->

                }
            }

            true
        }



    }


    //네이버 지도 세팅
    private fun settingNaverMap() {
        var fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync {
            //위치 정보를 관리하는 객체를 가져온다
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

            //네이버 지도 객체를 담아준다
            naverMap = it

            markerFromDataBase()

            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

            naverMap.locationSource = locationSource
            naverMap.uiSettings.isLocationButtonEnabled = true
            naverMap.locationTrackingMode = LocationTrackingMode.Face

            //네이버 맵을 클릭?
            naverMap.setOnMapClickListener { pointF, latLng ->
                Util.showDiaLog(
                    this,
                    "메모 추가",
                    "이 지점에 메모를 추가하시겠습니까?"
                ) { dialogInterface: DialogInterface, i: Int ->
                    val marker = Marker()
                    marker.position = latLng
                    marker.map = naverMap
                    //Log.d("test1234", "${latLng.latitude}")



                    val latitudelocation = latLng.latitude
                    val longitudeLocation = latLng.longitude

                    val newIntent = Intent(this@NaverMapActivity, MemoActivity::class.java)
                    newIntent.putExtra("latitude", latitudelocation)
                    newIntent.putExtra("longitude", longitudeLocation)
                    thirdActivityLauncher.launch(newIntent)
                }
            }


            //단말기에 저장되어있는 위치 값을 가져온다
            val str = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (str == true) {
                val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                //현재 위치를 지도에 표시한다
                if (location1 != null) {
                    setMyLocation(location1)
                }
                //현재 위치를 측정한다
                getMyLocation()
            }
        }

    }
    // (화면에 진입하면) 나의 디비에 있는 모든 메모 리스트를 가져와 변수에 리스트를 저장해둔다. List<MemoInfo>
    // 마커를 클릭했을 때, 마커에 있는 위경도 값으로 현재 메모리스트에 있는 메모들 중에 같은 위경도에 있는 메모가 있는지 체크한다.
    // 그 메모를 리턴하거나 메모의 index값을 리턴한다.


    //현재 나의 위치를 가져오는 메서드
    private fun getMyLocation() {
        //위치 정보 사용 권한 허용 여부 확인
        val a1 = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_DENIED

        if (a1 == true) {
            return
        }

        //만약 gps프로바이더가 사용 가능할 경우
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsLocationListener = MyLocationListener()
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0.0f,
                gpsLocationListener!!
            )
        }
    }

    //지도의 위치를 설정하는 메서드
    private fun setMyLocation(location: Location) {
        //Snackbar.make(activitySecondBinding.root, "위도 : ${location.latitude}, 경도 : ${location.longitude}", Snackbar.LENGTH_SHORT).show()

        //위도와 경도를 관리하는 객체를 생성한다
        val userLocation = LatLng(location.latitude, location.longitude)

        naverMap.cameraPosition =
            CameraPosition(LatLng(location.latitude, location.longitude), 16.0)

        //지도를 이동시키기 위한 객체를 생성한다
        val cameraUpdate = CameraUpdate.scrollTo(userLocation).animate(CameraAnimation.Easing)

        //카메라를 이동시킨다
        naverMap.moveCamera(cameraUpdate)

    }

    companion object {
        val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}