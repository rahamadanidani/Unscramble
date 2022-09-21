
package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    private var score = 0
    private var currentWordCount = 0
    private var currentScrambledWord = "test"


    // digunakan agar bisa mengakses ke tampilan di tata letak game_fragment.xml
    private lateinit var binding: GameFragmentBinding

    // Buat ViewModel saat pertama kali fragmen dibuat.
    //Jika fragmen dibuat ulang, ia menerima instance GameViewModel yang sama yang dibuat oleh
    //fragmen pertama

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // melakukan pengenmbangan file XML tata letak dan mengembalikan instance objek yang mengikat
        binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // digunakan untuk pada saat aplikasi digunakan maka akan ada tombol submit jika kita sudah
        // mengisi pertanyaan pada kolomnya jika tidak silahkan klik tombol skip untuk ke pertanyaan
        //selanjutnya.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // melakukan pengupdatean ulang pada hal yang kita kerjakan sebelumnya jika game dimulai kembali.
        updateNextWordOnScreen()
        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(
                R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    /*
    * berfungsi untuk menampilkan kata acak berikutnya dan menampilkan skor yang di peroleh.
    */
    private fun onSubmitWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        score += SCORE_INCREASE
        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
        binding.score.text = getString(R.string.score, score)
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /*
     *berfungsi untuk melewatkan kata yang tidak ingin dijawab untuk masuk ke pertanyaan berikutnya.
     */
    private fun onSkipWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
        setErrorTextField(false)
        updateNextWordOnScreen()
    }


    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }


    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }


    private fun exitGame() {
        activity?.finish()
    }


    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }


    private fun updateNextWordOnScreen() {
        binding.textViewUnscrambledWord.text = currentScrambledWord
    }
}
