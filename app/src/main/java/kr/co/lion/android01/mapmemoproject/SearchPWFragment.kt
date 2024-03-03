package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.databinding.FragmentSearchPWBinding

class SearchPWFragment : Fragment() {

    lateinit var fragmentSearchPWBinding: FragmentSearchPWBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSearchPWBinding = FragmentSearchPWBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        setEvent()
        setToolBar()
        setView()

        return fragmentSearchPWBinding.root
    }

    //툴바 설정
    fun setToolBar(){
        fragmentSearchPWBinding.apply {
            materialToolbar4.apply {
                title = "비밀번호 찾기"

                setNavigationIcon(R.drawable.arrow_back_24px)
                //클릭
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.SEARCH_PW_FRAGMENT)
                }
            }
        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentSearchPWBinding.apply {
            searchPwButton2.setOnClickListener {
                var chk = check123()
                if (chk == true){
                    checkOK()
                }
                enum.hideSoftInput(mainActivity)
            }
        }
    }

    //화면 세팅
    fun setView(){
        fragmentSearchPWBinding.apply {
            enum.showSoftInput(textSearchPWId, mainActivity)

            textSearchPWId.addTextChangedListener {
                textLayoutSearchPWid.error = null
            }
            textSearchPWNumber.addTextChangedListener {
                textLayoutSeatchPWNum.error = null
            }
        }
    }

    //유효성 검사 2
    fun check123():Boolean{
        fragmentSearchPWBinding.apply {
            var errorView:View? = null

            var id = textSearchPWId.text.toString()
            if (id.trim().isEmpty()){
                textLayoutSearchPWid.error = "아이디를 입력해주세요"
                if (errorView == null){
                    errorView = textSearchPWId
                }
            }else{
                textLayoutSearchPWid.error = null
            }


            var number = textSearchPWNumber.text.toString()
            if (number.trim().isEmpty()){
                textLayoutSeatchPWNum.error = "전화번호를 입력해주세요"
                if (errorView == null){
                    errorView = textSearchPWNumber
                }
            }else{
                textLayoutSeatchPWNum.error = null
            }

            if (errorView != null){
                mainActivity.showSoftInput2(errorView)
                return false
            }else{
                return true
            }
        }
    }

    //입력 유효성 검사
    fun checkOK(){
        fragmentSearchPWBinding.apply {

            var id = textSearchPWId.text.toString()
            var str = InfoDAO.selectOneInfo2(mainActivity, id)
            var number = textSearchPWNumber.text.toString().toInt()
            if (id != str?.id || number != str?.number){
                enum.showDiaLog(mainActivity, "정보 없음", "일치하는 정보가 없습니다"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textSearchPWId, mainActivity)
                }
            }else{
                textviewsearchPW.apply {
                    text = "비밀번호 : ${str.pw}"
                }
            }
        }
    }
}