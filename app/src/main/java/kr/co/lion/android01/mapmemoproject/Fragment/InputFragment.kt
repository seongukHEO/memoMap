package kr.co.lion.android01.mapmemoproject.Fragment

import android.app.Activity.RESULT_OK
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
import kr.co.lion.android01.mapmemoproject.SQL.DAO.InfoDAO
import kr.co.lion.android01.mapmemoproject.SQL.DAO.MemoDAO
import kr.co.lion.android01.mapmemoproject.DataClassAll.MemoInfo
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.FragmentInputBinding
import kr.co.lion.android01.mapmemoproject.Util
import java.text.SimpleDateFormat
import java.util.Date

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var memoActivity: MemoActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)
        memoActivity = activity as MemoActivity
        setToolBar()
        setEvent()
        setView()

        return fragmentInputBinding.root
    }

    //툴바 설정
    private fun setToolBar(){
        fragmentInputBinding.apply {
            materialToolbar6.apply {
                title = "메모 추가"

            }
            bottomAppBar.apply {
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    val newIntent = Intent(memoActivity, NaverMapActivity::class.java)
                    startActivity(newIntent)
                }
            }

        }
    }

    //이벤트 설정
    private fun setEvent(){
        fragmentInputBinding.apply {
            floatingActionButton.setOnClickListener {
                val chk = checkOK()
                if (chk){
                    check123()
                }
            }
        }
    }
    //화면 구성
    private fun setView(){
        fragmentInputBinding.apply {

            val simple = SimpleDateFormat("yyyy-MM-dd")
            val date = simple.format(Date())

            //date는 화면에 보여지게 한다
            textInputDate.setText(date)
            //포커스 주기
            memoActivity.showSoftInput2(textInputNickName)

            //에러 해결
            textInputNickName.addTextChangedListener {
                textInputLayoutNickName.error = null
            }
            textInputTitle.addTextChangedListener {
                textLayoutInputTitle.error = null
            }
            textInputContents.addTextChangedListener {
                textLayoutInputContents.error = null
            }
        }
    }

    //유효성 검사1
    private fun checkOK():Boolean{
        fragmentInputBinding.apply {
            var errorView:View? = null

            val nickname = textInputNickName.text.toString()
            if (nickname.trim().isEmpty()){
                textInputLayoutNickName.error = "닉네임을 입력해주세요"
                errorView = textInputNickName

            }else{
                textInputLayoutNickName.error = null
            }

            val title = textInputTitle.text.toString()
            if (title.trim().isEmpty()){
                textLayoutInputTitle.error = "제목을 입력해주세요"
                if (errorView == null){
                    errorView = textInputTitle
                }
            }else{
                textLayoutInputTitle.error = null
            }

            val contents = textInputContents.text.toString()
            if (contents.trim().isEmpty()){
                textLayoutInputContents.error = "내용을 입력해주세요"
                if (errorView == null){
                    errorView = textInputContents
                }
            }else{
                textLayoutInputContents.error = null
            }

            if (errorView != null){
                memoActivity.showSoftInput2(errorView)
                return false
            }else{
                return true
            }
        }
    }
    //유효성 검사2
    private fun check123(){
        fragmentInputBinding.apply {
            val nickname = textInputNickName.text.toString()
            val str = InfoDAO.selectOneInfo(memoActivity, nickname)

            if (nickname != str?.nickName){
                Util.showDiaLog(
                    memoActivity,
                    "닉네임 오류",
                    "닉네임을 확인해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textInputNickName, memoActivity)
                }
                return
            }
            val latitude = arguments?.getDouble("latitude")!!
            val longitude = arguments?.getDouble("longitude")!!

            //Log.d("test1234", "${latitude}, ${longitude}")

            val simple = SimpleDateFormat("yyyy-MM-dd")
            val date = simple.format(Date())


            val title = textInputTitle.text.toString()

            val contents = textInputContents.text.toString()

            val memoList = MemoInfo(1, nickname, date, title, contents, latitude, longitude)


            MemoDAO.insertMemo1(memoActivity, memoList)

            val newIntent = Intent(memoActivity, NaverMapActivity::class.java)
            newIntent.putExtra("nickname", nickname)
            newIntent.putExtra("latitude", latitude)
            newIntent.putExtra("longitude", longitude)
            memoActivity.setResult(RESULT_OK, newIntent)
            memoActivity.finish()

        }
    }
}



















































