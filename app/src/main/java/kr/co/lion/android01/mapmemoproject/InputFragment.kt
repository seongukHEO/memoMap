package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.databinding.FragmentInputBinding
import java.text.SimpleDateFormat
import java.util.Date

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var thirdActivity: ThirdActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)
        thirdActivity = activity as ThirdActivity
        setToolBar()
        setEvent()
        setView()

        return fragmentInputBinding.root
    }

    //툴바 설정
    fun setToolBar(){
        fragmentInputBinding.apply {
            materialToolbar6.apply {
                title = "메모 추가"

            }
            bottomAppBar.apply {
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    var newIntent = Intent(thirdActivity, SecondActivity::class.java)
                    startActivity(newIntent)
                }
            }

        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentInputBinding.apply {
            floatingActionButton.setOnClickListener {
                var chk = checkOK()
                if (chk == true){
                    check123()
                }
            }
        }
    }
    //화면 구성
    fun setView(){
        fragmentInputBinding.apply {

            var simple = SimpleDateFormat("yyyy-MM-dd")
            var date = simple.format(Date())

            //date는 화면에 보여지게 한다
            textInputDate.setText("${date}")
            //포커스 주기
            thirdActivity.showSoftInput2(textInputNickName)

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
    fun checkOK():Boolean{
        fragmentInputBinding.apply {
            var errorView:View? = null

            var nickname = textInputNickName.text.toString()
            if (nickname.trim().isEmpty()){
                textInputLayoutNickName.error = "닉네임을 입력해주세요"
                if (errorView == null){
                    errorView = textInputNickName
                }
            }else{
                textInputLayoutNickName.error = null
            }

            var title = textInputTitle.text.toString()
            if (title.trim().isEmpty()){
                textLayoutInputTitle.error = "제목을 입력해주세요"
                if (errorView == null){
                    errorView = textInputTitle
                }
            }else{
                textLayoutInputTitle.error = null
            }

            var contents = textInputContents.text.toString()
            if (contents.trim().isEmpty()){
                textLayoutInputContents.error = "내용을 입력해주세요"
                if (errorView == null){
                    errorView = textInputContents
                }
            }else{
                textLayoutInputContents.error = null
            }

            if (errorView != null){
                thirdActivity.showSoftInput2(errorView)
                return false
            }else{
                return true
            }
        }
    }
    //유효성 검사2
    fun check123(){
        fragmentInputBinding.apply {
            var nickname = textInputNickName.text.toString()
            var str = InfoDAO.selectOneInfo(thirdActivity, nickname)

            if (nickname != str?.nickName){
                enum.showDiaLog(thirdActivity, "닉네임 오류", "닉네임을 확인해주세요"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textInputNickName, thirdActivity)
                }
                return
            }

            var simple = SimpleDateFormat("yyyy-MM-dd")
            var date = simple.format(Date())


            var title = textInputTitle.text.toString()

            var contents = textInputContents.text.toString()

            var newIntent = Intent(thirdActivity, SecondActivity::class.java)
            startActivity(newIntent)
        }
    }
}



















































