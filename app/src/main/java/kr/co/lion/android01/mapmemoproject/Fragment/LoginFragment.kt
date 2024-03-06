package kr.co.lion.android01.mapmemoproject.Fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.lion.android01.mapmemoproject.Activity.LoginActivity
import kr.co.lion.android01.mapmemoproject.Activity.NaverMapActivity
import kr.co.lion.android01.mapmemoproject.FragmentName
import kr.co.lion.android01.mapmemoproject.SQL.DAO.InfoDAO
import kr.co.lion.android01.mapmemoproject.Util
import kr.co.lion.android01.mapmemoproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var loginActivity: LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
        loginActivity = activity as LoginActivity
        setToolBar()
        setEvent()

        return fragmentLoginBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentLoginBinding.apply {
            textMainId.setText("")
            textMainPW.setText("")
            lottieLayout2.visibility = View.GONE
        }
    }

    //툴바 설정
    private fun setToolBar(){
        fragmentLoginBinding.apply {
            materialToolbar.apply {
                title = "메모히어"
            }
        }
    }

    //이벤트 설정
    private fun setEvent(){
        fragmentLoginBinding.apply {
            loginButton.setOnClickListener {
                inputData()
                Util.hideSoftInput(loginActivity)

            }
            gojoinButton.setOnClickListener {
                loginActivity.replaceFragment(FragmentName.JOIN_FRAGMENT, true, true, null)
            }
            searchIDbutton.setOnClickListener {
                loginActivity.replaceFragment(FragmentName.SEARCH_ID_FRAGMENT, true, true, null)
            }
            searchPWbutton.setOnClickListener {
                loginActivity.replaceFragment(FragmentName.SEARCH_PW_FRAGMENT, true, true, null)

            }
        }
    }

    //입력(유효성) 설정
    private fun inputData(){
        fragmentLoginBinding.apply {

            val showId = textMainId.text.toString()
            val userInfo = InfoDAO.selectOneInfo2(loginActivity, showId)
            if (showId != userInfo?.id){
                Util.showDiaLog(
                    loginActivity,
                    "아이디 오류",
                    "아이디를 확인해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textMainId, loginActivity)
                }
                return
            }

            val showPw = textMainPW.text.toString()
            if (showPw != userInfo.pw){
                Util.showDiaLog(
                    loginActivity,
                    "비밀번호 입력 오류",
                    "비밀번호를 확인해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textMainPW, loginActivity)
                }
                return
            }
            lottieLayout2.visibility = View.VISIBLE

            lottieMain.repeatCount = 1
            lottieMain.playAnimation()

            lifecycleScope.launch {
                delay(1500)
                val str = InfoDAO.selectOneInfo2(loginActivity, showId)
                val newId = str?.nickName

                val newIntent = Intent(loginActivity, NaverMapActivity::class.java)
                newIntent.putExtra("nickname", newId)
                startActivity(newIntent)
                Util.hideSoftInput(loginActivity)
            }

        }
    }
}