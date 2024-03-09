package kr.co.lion.android01.mapmemoproject.Fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.Activity.NaverMapActivity
import kr.co.lion.android01.mapmemoproject.Activity.MemoActivity
import kr.co.lion.android01.mapmemoproject.DataClassAll.MemoInfo
import kr.co.lion.android01.mapmemoproject.FragmentName2
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.SQL.DAO.MemoDAO
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
                        getData()
                        startActivity(newIntent)

                    }
                }
            }
        }
    }

    //값을 보여준다
    private fun showResult() {
        fragmentModifyBinding.apply {
            var idx = arguments?.getInt("idx")
            //Log.d("gye123", "${idx}")
            if (idx != null){
                val memoInfo = MemoDAO.selectOneMemo(memoActivity, idx)
                textModifyNickName.setText("${memoInfo?.nickName}")
                textModifyDate.setText("${memoInfo?.date}")
                textModifyTitle.setText("${memoInfo?.title}")
                textModifyContents.setText("${memoInfo?.contents}")
            }
        }
    }

    //화면 구성
    private fun setView() {
        fragmentModifyBinding.apply {
            //포커스 주기
            memoActivity.showSoftInput2(textModifyTitle)

            //에러 해결
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

    //값을 구해 insert 해준다
    private fun getData(){
        fragmentModifyBinding.apply {
            var idx = arguments?.getInt("idx")

            if (idx != null){
                val info = MemoDAO.selectOneMemo(memoActivity, idx)

                var latitude = info?.latitude
                var longitude = info?.longitude

                var nickname = textModifyNickName.text.toString()
                var date = textModifyDate.text.toString()
                var title = textModifyTitle.text.toString()
                var contents = textModifyContents.text.toString()

                val memoList = MemoInfo(info!!.idx, nickname, date, title, contents, latitude!!, longitude!!)

                MemoDAO.updateMemo(memoActivity, memoList)

            }
        }
    }
}



















































