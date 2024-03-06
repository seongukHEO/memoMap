package kr.co.lion.android01.mapmemoproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.android01.mapmemoproject.FragmentName
import kr.co.lion.android01.mapmemoproject.Fragment.JoinFragment
import kr.co.lion.android01.mapmemoproject.Fragment.LoginFragment
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.Fragment.SearchIDFragment
import kr.co.lion.android01.mapmemoproject.Fragment.SearchPWFragment
import kr.co.lion.android01.mapmemoproject.databinding.ActivityLoginBinding
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    lateinit var activityLoginBinding: ActivityLoginBinding

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        replaceFragment(FragmentName.LOGIN_FRAGMENT, false, false, null)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
    }

    fun replaceFragment(
        name: FragmentName,
        addToBackStack: Boolean,
        isAnimate: Boolean,
        data: Bundle?
    ) {

        SystemClock.sleep(200)

        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if (newFragment != null) {
            oldFragment = newFragment
        }


        // Fragment의 객체를 생성하여 변수에 담아준다.
        when (name) {
            FragmentName.LOGIN_FRAGMENT -> {
                newFragment = LoginFragment()
            }

            FragmentName.JOIN_FRAGMENT -> {
                newFragment = JoinFragment()
            }

            FragmentName.SEARCH_ID_FRAGMENT -> {
                newFragment = SearchIDFragment()
            }

            FragmentName.SEARCH_PW_FRAGMENT -> {
                newFragment = SearchPWFragment()
            }
        }

        // 새로운 Fragment에 전달할 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if (data != null) {
            newFragment?.arguments = data
        }

        if (newFragment != null) {

            // 애니메이션 설정
            if (isAnimate == true) {

                if (oldFragment != null) {

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

            fragmentTransaction.replace(R.id.mainContainer, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if (addToBackStack == true) {
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: FragmentName) {
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    //포커스를 주고 키보드를 올리는 메서드
    fun showSoftInput2(view: View) {
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



























