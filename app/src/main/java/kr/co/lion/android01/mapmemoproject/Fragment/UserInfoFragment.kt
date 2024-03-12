package kr.co.lion.android01.mapmemoproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.android01.mapmemoproject.Activity.UserInfoActivity
import kr.co.lion.android01.mapmemoproject.R
import kr.co.lion.android01.mapmemoproject.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {

    lateinit var fragmentUserInfoBinding: FragmentUserInfoBinding
    lateinit var userInfoActivity: UserInfoActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentUserInfoBinding = FragmentUserInfoBinding.inflate(layoutInflater)
        userInfoActivity = activity as UserInfoActivity
        setToolBar()
        return fragmentUserInfoBinding.root
    }

    //툴바 설정
    private fun setToolBar(){
        fragmentUserInfoBinding.apply {
            toolbarUserInfo.apply {
                title = "나의 정보"

                setNavigationIcon(R.drawable.menu_24px)
                setNavigationOnClickListener {
                    userInfoActivity.activityUserInfoBinding.drawerLayoutUserInfo.open()
                }
            }
        }
    }
}