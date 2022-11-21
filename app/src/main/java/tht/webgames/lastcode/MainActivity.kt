package tht.webgames.lastcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import tht.webgames.lastcode.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOGIN_PATH = "login.php"
        private const val ALL_MEMBER_PATH = "getAllMember.php"
    }

    private var _binding: MainActivityBinding? = null
    private val binding: MainActivityBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WebGameClient.init(lifecycleScope)
        initView()
        initLogger()
    }

    private fun initView() {
        replaceFragment(
            supportFragmentManager,
            R.id.fragment_frame,
            LoginFragment(),
            LoginFragment::class.simpleName
        )
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, backStackTag: String?) {
        val transaction = fragmentManager.beginTransaction()
        if (!backStackTag.isNullOrEmpty()) {
            transaction.addToBackStack(backStackTag)
        }
        transaction.replace(containerId, fragment).commit()
    }
}