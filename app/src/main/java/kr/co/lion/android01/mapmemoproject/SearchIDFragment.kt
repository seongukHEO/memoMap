package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.databinding.FragmentSearchIDBinding

class SearchIDFragment : Fragment() {

    lateinit var fragmentSearchIDBinding: FragmentSearchIDBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSearchIDBinding = FragmentSearchIDBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        setEvent()
        setToolBar()
        setView()

        return fragmentSearchIDBinding.root
    }
    //툴바 설정
    fun setToolBar(){
        fragmentSearchIDBinding.apply {
            materialToolbar3.apply {
                title = "아이디 찾기"

                //아이콘
                setNavigationIcon(R.drawable.arrow_back_24px)
                //클릭했을 떄
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.SEARCH_ID_FRAGMENT)
                }
            }
        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentSearchIDBinding.apply {
            searchIdbutton2.setOnClickListener {
                var chk = check123()
                if (chk == true){
                    checkOK()
                }
                enum.hideSoftInput(mainActivity)
            }
        }
    }
    //화면 설정
    fun setView(){
        fragmentSearchIDBinding.apply {
            //포커스를 준다
            enum.showSoftInput(textSearchIdNumber, mainActivity)

            //입력할 경우 에러 메시지를 지워준다
            textSearchIdNumber.addTextChangedListener {
                textLayoutSearchID.error = null
            }
        }
    }

    //추가 유효성 검사
    fun check123():Boolean{
        fragmentSearchIDBinding.apply {
            var errorView:View? = null

            var number1 = textSearchIdNumber.text.toString()
            if (number1.trim().isEmpty()){
                textLayoutSearchID.error = "전화번호를 입력해주세요"
                if (errorView == null){
                    errorView = textSearchIdNumber
                }
            }else{
                textLayoutSearchID.error = null
            }
            if (errorView != null){
                mainActivity.showSoftInput2(errorView)
                return false
            }else{
                return true
            }
        }
    }

    //유효성 검사
    fun checkOK(){
        fragmentSearchIDBinding.apply {


            var number = textSearchIdNumber.text.toString().toInt()
            var str = InfoDAO.selectOneInfo1(mainActivity, number)


            if (number.toString().toInt() != str?.number){
                enum.showDiaLog(mainActivity, "전화번호 오류", "이 번호로 등록된 아이디가 없습니다"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textSearchIdNumber, mainActivity)
                }
                return
            }else{
                textviewSearchId.text = "아이디 : ${str?.id}"
            }

        }
    }

}































