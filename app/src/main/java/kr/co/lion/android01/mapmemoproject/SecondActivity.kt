package kr.co.lion.android01.mapmemoproject

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
import kr.co.lion.android01.mapmemoproject.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {

    lateinit var activitySecondBinding: ActivitySecondBinding

    //위치 정보를 관리하는 객체
    lateinit var locationManager:LocationManager

    //위치 측정이 성공하면 동작할 리스너
    var gpsLocationListener:MyLocationListener? = null

    //네이버 지도 객체를 담을 프로퍼티
    lateinit var naverMap:NaverMap

    lateinit var locationSource: FusedLocationSource


    //런쳐
    lateinit var thirdActivityLauncher:ActivityResultLauncher<Intent>

    //확인받은 권한 목록
    var permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)
        requestPermissions(permissionList, 0)
        setToolBar()
        settingNaverMap()
        initView()
       // showMarkerFromSQlite()

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("89e2h0bkk5")
    }

//    override fun onMapReady(p0: NaverMap) {
//        this.naverMap = naverMap
//
//        showMarkerFromSQlite()
//    }




    //뷰 설정
    fun initView(){
        var contract = ActivityResultContracts.StartActivityForResult()
        thirdActivityLauncher = registerForActivityResult(contract){
            if (it.resultCode == RESULT_OK){
                if (it.data != null){
                    var latitude = it?.data!!.getDoubleExtra("latitude", 0.00)
                    var longitude = it?.data!!.getDoubleExtra("longitude", 0.00)

                    Log.d("test1234", "${latitude}, ${longitude}")

                    screenJob(latitude, longitude)
                }
            }
        }
    }

    //툴바 설정
    fun setToolBar(){
        activitySecondBinding.apply {
            materialToolbar5.apply {
                var nickname = intent.getStringExtra("nickname")

                title = "${nickname}의 메모 지도"

                //연습
                inflateMenu(R.menu.main_menu)
                //클릭
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.practice_menu -> {
                            var newIntent = Intent(this@SecondActivity, ThirdActivity::class.java)
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

    //SQLite에서 데이터를 가져오는 코드 작성
    fun showMarkerFromSQlite(){
        //sqlite에서 데이터를 가져온다
        var nickname = intent?.getStringExtra("nickname")
        if (nickname != null){
            var latitude = MemoDAO.selectOneMemo(this, nickname)
            var longitude = MemoDAO.selectOneMemo(this, nickname)

            Log.d("show1234", "${latitude}, ${longitude}")


            var markerList1 = latitude?.latitude
            var markerList2 = longitude?.longitude

            if (markerList1 != null && markerList2 != null) {

                val marker = Marker()
                marker.position = LatLng(markerList1, markerList2)
                marker.map = naverMap
            }
        }
    }

    //위치 측정이 성공하면 동작하는 리스너
    inner class MyLocationListener : LocationListener{
        override fun onLocationChanged(location: Location) {

            when(location.provider){
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
    fun screenJob(latitude:Double, longitude:Double){
        //마커 옵션을 만들어준다
        var marker = Marker()
        marker.position = LatLng(latitude, longitude)
        marker.map = naverMap

    }

    //네이버 지도 세팅
    fun settingNaverMap(){
        var fm = supportFragmentManager
        var mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync {
            //위치 정보를 관리하는 객체를 가져온다
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

            //네이버 지도 객체를 담아준다
            naverMap = it

            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

            naverMap.locationSource = locationSource
            naverMap.uiSettings.isLocationButtonEnabled = true
            naverMap.locationTrackingMode = LocationTrackingMode.Face

            //네이버 맵을 클릭?
            naverMap.setOnMapClickListener { pointF, latLng ->
                enum.showDiaLog(this, "메모 추가", "이 지점에 메모를 추가하시겠습니까?"){ dialogInterface: DialogInterface, i: Int ->
                    var marker = Marker()
                    marker.position = latLng
                    marker.map = naverMap
                    //Log.d("test1234", "${latLng.latitude}")

                    marker.setOnClickListener {
                        var nickname = intent.getStringExtra("nickname")
                        var bottomShowFragment = BottomFragment()

                        var bundle = Bundle()
                        bundle.putString("nickname", nickname)
                        bundle.putDouble("latitude", latLng.latitude)
                        bundle.putDouble("longitude", latLng.longitude)
                        bottomShowFragment.arguments = bundle

                        bottomShowFragment.show(this.supportFragmentManager, "bottomSheet")

                        true
                    }


                    var latitudelocation = latLng.latitude
                    var longitudeLocation = latLng.longitude


                    var newIntent = Intent(this@SecondActivity, ThirdActivity::class.java)
                    newIntent.putExtra("latitude",latitudelocation)
                    newIntent.putExtra("longitude", longitudeLocation)
                    thirdActivityLauncher.launch(newIntent)
                }
            }


            //단말기에 저장되어있는 위치 값을 가져온다
            var str = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if (str == true){
                var location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                //현재 위치를 지도에 표시한다
                if (location1 != null){
                    setMyLocation(location1)
                }
                //현재 위치를 측정한다
                getMyLocation()
            }
        }

    }

    //현재 나의 위치를 가져오는 메서드
    fun getMyLocation(){
        //위치 정보 사용 권한 허용 여부 확인
        var a1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED

        if (a1 == true){
            return
        }

        //만약 gps프로바이더가 사용 가능할 경우
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true){
            gpsLocationListener = MyLocationListener()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, gpsLocationListener!!)
        }
    }

    //지도의 위치를 설정하는 메서드
    fun setMyLocation(location: Location){
        //Snackbar.make(activitySecondBinding.root, "위도 : ${location.latitude}, 경도 : ${location.longitude}", Snackbar.LENGTH_SHORT).show()

        //위도와 경도를 관리하는 객체를 생성한다
        var userLocation = LatLng(location.latitude, location.longitude)

        naverMap.cameraPosition = CameraPosition(LatLng(location.latitude, location.longitude), 16.0)

        //지도를 이동시키기 위한 객체를 생성한다
        var cameraUpdate = CameraUpdate.scrollTo(userLocation).animate(CameraAnimation.Easing)

        //카메라를 이동시킨다
        naverMap.moveCamera(cameraUpdate)

    }

    companion object{
        val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }



}