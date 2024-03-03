package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.lion.android01.mapmemoproject.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        setToolBar()
        setEvent()

        return fragmentMainBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentMainBinding.apply {
            textMainId.setText("")
            textMainPW.setText("")
            lottieLayout2.visibility = View.GONE
        }
    }

    //툴바 설정
    fun setToolBar(){
        fragmentMainBinding.apply {
            materialToolbar.apply {
                title = "메모히어"
            }
        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentMainBinding.apply {
            loginButton.setOnClickListener {
                inputData()
                enum.hideSoftInput(mainActivity)

            }
            gojoinButton.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.JOIN_FRAGMENT, true, true, null)
            }
            searchIDbutton.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.SEARCH_ID_FRAGMENT, true, true, null)
            }
            searchPWbutton.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.SEARCH_PW_FRAGMENT, true, true, null)

            }
        }
    }

    //입력(유효성) 설정
    fun inputData(){
        fragmentMainBinding.apply {

            var showId = textMainId.text.toString()
            var userInfo = InfoDAO.selectOneInfo2(mainActivity, showId)
            if (showId != userInfo?.id){
                enum.showDiaLog(mainActivity, "아이디 오류", "아이디를 확인해주세요"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textMainId, mainActivity)
                }
                return
            }

            var showPw = textMainPW.text.toString()
            if (showPw != userInfo?.pw){
                enum.showDiaLog(mainActivity, "비밀번호 입력 오류", "비밀번호를 확인해주세요"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textMainPW, mainActivity)
                }
                return
            }

            lottieLayout2.visibility = View.VISIBLE



            lottieMain.repeatCount = 1
            lottieMain.playAnimation()

            lifecycleScope.launch {
                delay(1500)
                var newIntent = Intent(mainActivity, SecondActivity::class.java)
                startActivity(newIntent)
                enum.hideSoftInput(mainActivity)
            }

        }
    }
}