package kr.co.lion.android01.mapmemoproject.Fragment

import android.animation.Animator
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
import kr.co.lion.android01.mapmemoproject.DataClassAll.UserInfo
import kr.co.lion.android01.mapmemoproject.databinding.FragmentJoinBinding
import kr.co.lion.android01.mapmemoproject.Util
import java.util.regex.Pattern

class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var loginActivity: LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentJoinBinding = FragmentJoinBinding.inflate(layoutInflater)
        loginActivity = activity as LoginActivity
        setEvent()
        setToolBar()
        initView()

        return fragmentJoinBinding.root
    }

    //툴바 설정
    private fun setToolBar(){
        fragmentJoinBinding.apply {
            materialToolbar2.apply {
                title = "회원가입"
            }
        }
    }

    //입력요소 설정
    private fun initView(){
        fragmentJoinBinding.apply {
            //우선 화면이 시작되면 포커스를 준다
            loginActivity.showSoftInput2(textJoinName)

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
    private fun setEvent(){
        fragmentJoinBinding.apply {
            joinButton.setOnClickListener {
                var chk = checkOK()
                if (chk){
                    check123()
                    Util.hideSoftInput(loginActivity)
                }
            }
        }
    }

    //입력 유효성 검사
    private fun checkOK():Boolean{
        fragmentJoinBinding.apply {
            //입력하지 않은 입력 요소 중 가장 위에 있는 View를 담을 변수
            var errorView:View? = null

            val name = textJoinName.text.toString()
            if (name.trim().isEmpty()){
                textJoinLayoutName.error = "이름을 입력해주세요"
                errorView = textJoinName

            }else{
                textJoinLayoutName.error = null
            }
            val number1 = textJoinNumber.text.toString()
            if (number1.trim().isEmpty()){
                textJoinLayoutNumber.error = "전화 번호를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinNumber
                }
            }else{
                textJoinLayoutNumber.error = null
            }

            val nickname = textJoinNickName.text.toString()
            if (nickname.trim().isEmpty()){
                textJoinLayoutNickName.error = "닉네임을 입력해주세요"
                if (errorView == null){
                    errorView = textJoinNickName
                }
            }else{
                textJoinLayoutNickName.error = null
            }

            val id = textJoinID.text.toString()
            if (id.trim().isEmpty()){
                textJoinLayoutID.error = "아이디를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinID
                }
            }else{
                textJoinLayoutID.error = null
            }

            val pw = textJoinPW.text.toString()
            if (pw.trim().isEmpty()){
                textJoinLayoutPW.error = "비밀번호를 입력해주세요"
                if (errorView == null){
                    errorView = textJoinPW
                }
            }else{
                textJoinLayoutPW.error = null
            }
            val checkPw = textJoinCheckPW.text.toString()
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
                loginActivity.showSoftInput2(errorView)
                return  false
            }else{
                return true
            }
        }
    }

    //추가로 유효성 검사
    private fun check123(){
        fragmentJoinBinding.apply {
            val name = textJoinName.text.toString()
            val number = textJoinNumber.text.toString().toInt()

            val nickName = textJoinNickName.text.toString()
            val str = InfoDAO.selectOneInfo(loginActivity, nickName)
            if (nickName == str?.nickName){
                Util.showDiaLog(
                    loginActivity,
                    "닉네임 중복 오류",
                    "현재 사용중인 닉네임입니다"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textJoinNickName, loginActivity)
                }
                return
            }

            val id = textJoinID.text.toString()
            val checkId = InfoDAO.selectOneInfo2(loginActivity, id)
            if (id == checkId?.id){
                Util.showDiaLog(
                    loginActivity,
                    "아이디 중복 오류",
                    "현재 사용중인 아이디입니다"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textJoinID, loginActivity)

                }
                return
            }


            val pw = textJoinPW.text.toString()
            val special123 = Pattern.compile("[!@#$%^&*+?><~`=)(}{]")
            val matchers = special123.matcher(pw)
            if (!matchers.find()){
                Util.showDiaLog(
                    loginActivity,
                    "특수문자 입력 오류",
                    "비밀번호에는 특수문자를 포함해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textJoinPW, loginActivity)
                }
                return
            }

            val checkPw = textJoinCheckPW.text.toString()
            if (checkPw != pw){
                Util.showDiaLog(
                    loginActivity,
                    "비밀번호 확인 오류",
                    "비밀번호가 일치하지 않습니다"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textJoinCheckPW, loginActivity)
                }
                return
            }

            val infoModel = UserInfo(name, number, nickName, id, pw)
            InfoDAO.insertInfo(loginActivity, infoModel)

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
                        loginActivity.removeFragment(FragmentName.JOIN_FRAGMENT)

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



























