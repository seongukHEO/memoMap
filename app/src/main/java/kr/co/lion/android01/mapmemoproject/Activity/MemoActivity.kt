package kr.co.lion.android01.mapmemoproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.android01.mapmemoproject.FragmentName2
import kr.co.lion.android01.mapmemoproject.Fragment.InputFragment
import kr.co.lion.android01.mapmemoproject.Fragment.ModifyFragment
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.ActivityMemoBinding
import kotlin.concurrent.thread

class MemoActivity : AppCompatActivity() {

    lateinit var activityMemoBinding: ActivityMemoBinding

    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        activityMemoBinding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(activityMemoBinding.root)

        val latitude = intent.getDoubleExtra("latitude", 0.00)
        val longitude = intent.getDoubleExtra("longitude", 0.00)
        val idx = intent.getIntExtra("idx", 0)

//        Log.d("uk123", "$idx")
//        Log.d("uk123", "$latitude")


        if (idx != 0) {
            val bundleIdx = Bundle().apply {
                putInt("idx", idx)
            }
            replaceFragment(FragmentName2.MODIFY_FRAGMENT, false, false, bundleIdx)

        }else {
            val bundle = Bundle().apply {
                putDouble("latitude", latitude)
                putDouble("longitude", longitude)
            }
            replaceFragment(FragmentName2.INPUT_FRAGMENT, false, false, bundle)
        }
    }

    fun replaceFragment(name: FragmentName2, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        // 이름으로 분기한다.
        // Fragment의 객체를 생성하여 변수에 담아준다.
        when(name){
            FragmentName2.INPUT_FRAGMENT -> {
                newFragment = InputFragment()
            }
            FragmentName2.MODIFY_FRAGMENT -> {
                newFragment = ModifyFragment()
            }
        }

        // 새로운 Fragment에 전달할 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){

                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)

                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)

                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            fragmentTransaction.replace(R.id.thirdContainer, newFragment!!)


            if(addToBackStack == true){

                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: FragmentName2){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    //포커스를 주고 키보드를 올리는 메서드
    fun showSoftInput2(view: View){

        view.requestFocus()
        thread {
            //딜레이를 준다
            SystemClock.sleep(200)
            //키보드 관리 객체를 가져온다
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            //키보드를 올린다
            inputMethodManager.showSoftInput(view, 0)

        }
    }
}