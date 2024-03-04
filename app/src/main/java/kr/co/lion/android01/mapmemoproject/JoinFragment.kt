package kr.co.lion.android01.mapmemoproject

import android.animation.Animator
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.android01.mapmemoproject.databinding.FragmentJoinBinding
import java.util.regex.Pattern

class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentJoinBinding = FragmentJoinBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        setEvent()
        setToolBar()
        initView()

        return fragmentJoinBinding.root
    }

    //툴바 설정
    fun setToolBar(){
        fragmentJoinBinding.apply {
            materialToolbar2.apply {
                title = "회원가입"
            }
        }
    }

    //입력요소 설정
    fun initView(){
        fragmentJoinBinding.apply {
            //우선 화면이 시작되면 포커스를 준다
            mainActivity.showSoftInput2(textJoinName)

            //에러 메시지가 보여지는 상황일 경우를 대비하여 무언가를 입력하면 에러 메시지를 없앤다

            //이름
            textJoinName.addTextChangedListener {
                textJoinLayoutName.error = null
            }
            //전화번호
            textJoinNumber.addTextChangedListener {
                textJoinLayoutNumber.error = null
            }
            //닉네임
            textJoinNickName.addTextChangedListener {
                textJoinLayoutNickName.error = null
            }
            //아이디
            textJoinID.addTextChangedListener {
                textJoinLayoutID.error = null
            }
            //비밀번호
            textJoinPW.addTextChangedListener {
                textJoinLayoutPW.error = null
            }
            //비번확인
            textJoinCheckPW.addTextChangedListener {
                textJoinLayoutCheckPW.error = null
            }
        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentJoinBinding.apply {
            joinButton.setOnClickListener {
                var chk = checkOK()
                if (chk == true){
                    check123()
                    enum.hideSoftInput(mainActivity)
                }
            }
        }
    }

    //입력 유효성 검사
    fun checkOK():Boolean{
        fragmentJoinBinding.apply {
            //입력하지 않은 입력 요소 중 가장 위에 있는 View를 담을 변수
            var errorView:View? = null

            var name = textJoinName.text.toString()
            if (name.trim().isEmpty()){
                textJoinLayoutName.error = "이름을 입력해주세요"
                if (errorView == null){
                    errorView = textJoinName
                }
            }else{
                textJoinLayoutName.error = null
            }
            var number1 = textJoinNumber.text.toString()
            if (number1.trim().isEmpty()){
                textJoinLayoutNumber.error = "전화 번호를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinNumber
                }
            }else{
                textJoinLayoutNumber.error = null
            }

            var nickname = textJoinNickName.text.toString()
            if (nickname.trim().isEmpty()){
                textJoinLayoutNickName.error = "닉네임을 입력해주세요"
                if (errorView == null){
                    errorView = textJoinNickName
                }
            }else{
                textJoinLayoutNickName.error = null
            }

            var id = textJoinID.text.toString()
            if (id.trim().isEmpty()){
                textJoinLayoutID.error = "아이디를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinID
                }
            }else{
                textJoinLayoutID.error = null
            }

            var pw = textJoinPW.text.toString()
            if (pw.trim().isEmpty()){
                textJoinLayoutPW.error = "비밀번호를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinPW
                }
            }else{
                textJoinLayoutPW.error = null
            }
            var checkPw = textJoinCheckPW.text.toString()
            if (checkPw.trim().isEmpty()){
                textJoinLayoutCheckPW.error = "비밀번호를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinCheckPW
                }
            }else{
                textJoinLayoutCheckPW.error = null
            }


            //비어있는 입력 요소가 있다면 비어있는 입력 요소에 포커스를 준다
            if (errorView != null){
                mainActivity.showSoftInput2(errorView)
                return  false
            }else{
                return true
            }
        }
    }

    //추가로 유효성 검사
    fun check123(){
        fragmentJoinBinding.apply {
            var name = textJoinName.text.toString()
            var number = textJoinNumber.text.toString().toInt()

            var nickName = textJoinNickName.text.toString()
            var str = InfoDAO.selectOneInfo(mainActivity, nickName)
            if (nickName == str?.nickName){
                enum.showDiaLog(mainActivity, "닉네임 중복 오류", "현재 사용중인 닉네임입니다"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textJoinNickName, mainActivity)
                }
                return
            }

            var id = textJoinID.text.toString()
            var checkId = InfoDAO.selectOneInfo2(mainActivity, id)
            if (id == checkId?.id){
                enum.showDiaLog(mainActivity, "아이디 중복 오류", "현재 사용중인 아이디입니다"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textJoinID, mainActivity)

                }
                return
            }


            var pw = textJoinPW.text.toString()
            var special123 = Pattern.compile("[!@#$%^&*+?><~`=)(}{]")
            var matchers = special123.matcher(pw)
            if (!matchers.find()){
                enum.showDiaLog(mainActivity, "특수문자 입력 오류", "비밀번호에는 특수문자를 포함해주세요"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textJoinPW, mainActivity)
                }
                return
            }

            var checkPw = textJoinCheckPW.text.toString()
            if (checkPw != pw){
                enum.showDiaLog(mainActivity, "비밀번호 확인 오류", "비밀번호가 일치하지 않습니다"){ dialogInterface: DialogInterface, i: Int ->
                    enum.showSoftInput(textJoinCheckPW, mainActivity)
                }
                return
            }

            var infoModel = UserInfo(name, number, nickName, id, pw)
            InfoDAO.insertInfo(mainActivity, infoModel)

            lottieLayout.visibility = View.VISIBLE

            fragmentJoinBinding.apply {


                //로티 애니메이션 재생

                lottieJoin.repeatCount = 1
                lottieJoin.playAnimation()

                //로티 종료 이벤트 설정 리스너
                lottieJoin.addAnimatorListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        mainActivity.removeFragment(FragmentName.JOIN_FRAGMENT)

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
            }

        }
    }
}



























