package tht.webgames.lastcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tht.webgames.lastcode.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    companion object {
        private const val LOGIN_PATH = "login.php"
        private const val ALL_MEMBER_PATH = "getAllMember.php"
    }

    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            WebGameClient.sendMsg(Login(binding.etName.text.toString()), LOGIN_PATH) {
                WebGameClient.getMsg(ALL_MEMBER_PATH)
            }
        }
    }

}