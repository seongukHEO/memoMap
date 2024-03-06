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
import kr.co.lion.android01.mapmemoproject.databinding.FragmentSearchIDBinding
import kr.co.lion.android01.mapmemoproject.Util

class SearchIDFragment : Fragment() {

    lateinit var fragmentSearchIDBinding: FragmentSearchIDBinding
    lateinit var loginActivity: LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSearchIDBinding = FragmentSearchIDBinding.inflate(layoutInflater)
        loginActivity = activity as LoginActivity
        setEvent()
        setToolBar()
        setView()

        return fragmentSearchIDBinding.root
    }

    //툴바 설정
    private fun setToolBar() {
        fragmentSearchIDBinding.apply {
            materialToolbar3.apply {
                title = "아이디 찾기"

                //아이콘
                setNavigationIcon(R.drawable.arrow_back_24px)
                //클릭했을 떄
                setNavigationOnClickListener {
                    loginActivity.removeFragment(FragmentName.SEARCH_ID_FRAGMENT)
                }
            }
        }
    }

    //이벤트 설정
    private fun setEvent() {
        fragmentSearchIDBinding.apply {
            searchIdbutton2.setOnClickListener {
                val chk = check123()
                if (chk) {
                    checkOK()
                }
                Util.hideSoftInput(loginActivity)
            }
        }
    }

    //화면 설정
    private fun setView() {
        fragmentSearchIDBinding.apply {
            //포커스를 준다
            Util.showSoftInput(textSearchIdNumber, loginActivity)

            //입력할 경우 에러 메시지를 지워준다
            textSearchIdNumber.addTextChangedListener {
                textLayoutSearchID.error = null
            }
        }
    }

    //추가 유효성 검사
    private fun check123(): Boolean {
        fragmentSearchIDBinding.apply {
            var errorView: View? = null

            val number1 = textSearchIdNumber.text.toString()
            if (number1.trim().isEmpty()) {
                textLayoutSearchID.error = "전화번호를 입력해주세요"
                errorView = textSearchIdNumber

            } else {
                textLayoutSearchID.error = null
            }
            if (errorView != null) {
                loginActivity.showSoftInput2(errorView)
                return false
            } else {
                return true
            }
        }
    }

    //유효성 검사
    private fun checkOK() {
        fragmentSearchIDBinding.apply {
            val number = textSearchIdNumber.text.toString().toInt()
            val str = InfoDAO.selectOneInfo1(loginActivity, number)


            if (number.toString().toInt() != str?.number) {
                Util.showDiaLog(
                    loginActivity,
                    "전화번호 오류",
                    "이 번호로 등록된 아이디가 없습니다"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textSearchIdNumber, loginActivity)
                }
                return
            } else {
                textviewSearchId.text = "아이디 : ${str?.id}"
            }

        }
    }

}































