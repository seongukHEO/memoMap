package kr.co.lion.android01.mapmemoproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.android01.mapmemoproject.Activity.UserInfoActivity
import kr.co.lion.android01.mapmemoproject.FragmentNameUserInfo
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.FragmentUserInfoModifyBinding

class UserInfoModifyFragment : Fragment() {

    lateinit var fragmentUserInfoModifyBinding: FragmentUserInfoModifyBinding
    lateinit var userInfoActivity: UserInfoActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentUserInfoModifyBinding = FragmentUserInfoModifyBinding.inflate(layoutInflater)
        userInfoActivity = activity as UserInfoActivity
        settingToolBar()

        return fragmentUserInfoModifyBinding.root
    }

    //툴바 세팅
    fun settingToolBar(){
        fragmentUserInfoModifyBinding.apply {
            toolBarUserInfoModify.apply {
                title = "나의 정보 수정"
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    userInfoActivity.removeFragment(FragmentNameUserInfo.USER_INFO_MODIFY_FRAGMENT)
                }
            }
        }
    }



}














































