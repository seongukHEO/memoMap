package kr.co.lion.android01.mapmemoproject.Fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.Activity.LoginActivity
import kr.co.lion.android01.mapmemoproject.FragmentName
import kr.co.lion.android01.mapmemoproject.SQL.DAO.InfoDAO
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.FragmentSearchPWBinding
import kr.co.lion.android01.mapmemoproject.Util

class SearchPWFragment : Fragment() {

    lateinit var fragmentSearchPWBinding: FragmentSearchPWBinding
    lateinit var loginActivity: LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSearchPWBinding = FragmentSearchPWBinding.inflate(layoutInflater)
        loginActivity = activity as LoginActivity
        setEvent()
        setToolBar()
        setView()

        return fragmentSearchPWBinding.root
    }

    //툴바 설정
    private fun setToolBar(){
        fragmentSearchPWBinding.apply {
            materialToolbar4.apply {
                title = "비밀번호 찾기"

                setNavigationIcon(R.drawable.arrow_back_24px)
                //클릭
                setNavigationOnClickListener {
                    loginActivity.removeFragment(FragmentName.SEARCH_PW_FRAGMENT)
                }
            }
        }
    }

    //이벤트 설정
    private fun setEvent(){
        fragmentSearchPWBinding.apply {
            searchPwButton2.setOnClickListener {
                val chk = check123()
                if (chk){
                    checkOK()
                }
                Util.hideSoftInput(loginActivity)
            }
        }
    }

    //화면 세팅
    private fun setView(){
        fragmentSearchPWBinding.apply {
            Util.showSoftInput(textSearchPWId, loginActivity)

            textSearchPWId.addTextChangedListener {
                textLayoutSearchPWid.error = null
            }
            textSearchPWNumber.addTextChangedListener {
                textLayoutSeatchPWNum.error = null
            }
        }
    }

    //유효성 검사 2
    private fun check123():Boolean{
        fragmentSearchPWBinding.apply {
            var errorView:View? = null

            val id = textSearchPWId.text.toString()
            if (id.trim().isEmpty()){
                textLayoutSearchPWid.error = "아이디를 입력해주세요"
                errorView = textSearchPWId

            }else{
                textLayoutSearchPWid.error = null
            }


            val number = textSearchPWNumber.text.toString()
            if (number.trim().isEmpty()){
                textLayoutSeatchPWNum.error = "전화번호를 입력해주세요"
                if (errorView == null){
                    errorView = textSearchPWNumber
                }
            }else{
                textLayoutSeatchPWNum.error = null
            }

            if (errorView != null){
                loginActivity.showSoftInput2(errorView)
                return false
            }else{
                return true
            }
        }
    }

    //입력 유효성 검사
    private fun checkOK(){
        fragmentSearchPWBinding.apply {

            val id = textSearchPWId.text.toString()
            val str = InfoDAO.selectOneInfo2(loginActivity, id)
            val number = textSearchPWNumber.text.toString().toInt()
            if (id != str?.id || number != str.number){
                Util.showDiaLog(
                    loginActivity,
                    "정보 없음",
                    "일치하는 정보가 없습니다"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textSearchPWId, loginActivity)
                }
            }else{
                textviewsearchPW.apply {
                    text = "비밀번호 : ${str.pw}"
                }
            }
        }
    }
}