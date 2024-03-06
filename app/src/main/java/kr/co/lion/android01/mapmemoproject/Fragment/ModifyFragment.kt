package kr.co.lion.android01.mapmemoproject.Fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.Activity.NaverMapActivity
import kr.co.lion.android01.mapmemoproject.Activity.MemoActivity
import kr.co.lion.android01.mapmemoproject.FragmentName2
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.FragmentModifyBinding
import kr.co.lion.android01.mapmemoproject.Util

class ModifyFragment : Fragment() {

    lateinit var fragmentModifyBinding: FragmentModifyBinding
    lateinit var memoActivity: MemoActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentModifyBinding = FragmentModifyBinding.inflate(layoutInflater)
        memoActivity = activity as MemoActivity
        setToolBar()
        setEvent()
        showResult()
        setView()
        return fragmentModifyBinding.root
    }

    //툴바 설정
    private fun setToolBar() {
        fragmentModifyBinding.apply {
            materialToolbar9.apply {
                title = "메모 수정"
            }
            bottomAppBar2.apply {
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    memoActivity.removeFragment(FragmentName2.MODIFY_FRAGMENT)
                }
            }
        }
    }

    //이벤트 설정
    private fun setEvent() {
        fragmentModifyBinding.apply {
            floatingActionButton2.setOnClickListener {
                var chk = checkOK()
                if (chk) {
                    Util.showDiaLog(
                        memoActivity,
                        "메모 수정",
                        "메모를 수정하시겠습니까?"
                    ) { dialogInterface: DialogInterface, i: Int ->
                        var newIntent = Intent(memoActivity, NaverMapActivity::class.java)
                        startActivity(newIntent)

                    }
                }
            }
        }
    }

    //값을 보여준다
    private fun showResult() {
        fragmentModifyBinding.apply {
            textModifyNickName.setText("허성욱")
            textModifyDate.setText("2024-03-04")
            textModifyTitle.setText("후우")
            textModifyContents.setText("안녕 !!")
        }
    }

    //화면 구성
    private fun setView() {
        fragmentModifyBinding.apply {
            //포커스 주기
            memoActivity.showSoftInput2(textModifyNickName)

            //에러 해결
            textModifyNickName.addTextChangedListener {
                textModifyLayoutNickName.error = null
            }
            textModifyTitle.addTextChangedListener {
                textLayoutModifyTitle.error = null
            }
            textModifyContents.addTextChangedListener {
                textLayoutModifyContents.error = null
            }
        }
    }

    //유효성 검사
    private fun checkOK(): Boolean {
        fragmentModifyBinding.apply {
            var errorView: View? = null

            val nickname = textModifyNickName.text.toString()
            if (nickname.trim().isEmpty()) {
                textModifyLayoutNickName.error = "닉네임을 입력해주세요"
                errorView = textModifyNickName

            } else {
                textModifyLayoutNickName.error = null
            }

            val title = textModifyTitle.text.toString()
            if (title.trim().isEmpty()) {
                textLayoutModifyTitle.error = "제목을 입력해주세요"
                if (errorView == null) {
                    errorView = textModifyTitle
                }
            } else {
                textLayoutModifyTitle.error = null
            }

            val contents = textModifyContents.text.toString()
            if (contents.trim().isEmpty()) {
                textLayoutModifyContents.error = "내용을 입력해주세요"
                if (errorView == null) {
                    errorView = textModifyContents
                }
            } else {
                textLayoutModifyContents.error = null
            }

            if (errorView != null) {
                memoActivity.showSoftInput2(errorView)
                return false
            } else {
                return true
            }
        }
    }
}



















































