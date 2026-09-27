package ru.mtuci.drivenext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class OnboardingFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE = "arg_image"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESC  = "arg_desc"

        fun newInstance(imageRes: Int, title: String, desc: String) = OnboardingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_IMAGE, imageRes)
                putString(ARG_TITLE, title)
                putString(ARG_DESC, desc)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_onboarding, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = requireArguments()
        view.findViewById<ImageView>(R.id.slideImage).setImageResource(args.getInt(ARG_IMAGE))
        view.findViewById<TextView>(R.id.slideTitle).text = args.getString(ARG_TITLE)
        view.findViewById<TextView>(R.id.slideDesc).text  = args.getString(ARG_DESC)
    }
}