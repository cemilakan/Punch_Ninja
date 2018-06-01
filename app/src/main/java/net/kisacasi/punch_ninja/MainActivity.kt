package net.kisacasi.punch_ninja

/**
 * Created by Cemil from kisacasi.net
 */
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val PREFS_FILENAME = "net.kisacasi.hit_black_ninja"
    val FINISH_SCORE = "sonSkor"
    var prefs: SharedPreferences? = null

    var score: Int = 0
    var scoreFinish: Int = 0
    var enYuksek: Int = 0
    var hiz: Long = 1000
    var resimIndex: Int = 0
    var imageArray = ArrayList<ImageView>()
    var mp = MediaPlayer()
    var mp2 = MediaPlayer()
    var sure: Long = 40

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        scoreFinish = prefs!!.getInt(FINISH_SCORE, scoreFinish)

        CustomLoad()

        mp = MediaPlayer.create(this, R.raw.hit)
        mp2 = MediaPlayer.create(this, R.raw.game_over2)
        imageArray = arrayListOf(imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9)


    }

    fun CountTime() {
        object : CountDownTimer(sure * 1000, hiz) {
            override fun onFinish() {
                scoreFinish = score
                if (score > scoreFinish) {
                    enYuksek = score
                } else {
                    enYuksek = scoreFinish
                }
                score = 0
                mp2.start()
                olusturPref()
                CustomLoad()
                scoreText.text = "Score: 0"
                timeText.text = "Time: 0"
            }

            override fun onTick(p0: Long) {
                timeText.text = "Time: " + p0 / 1000


            }

        }.start()
    }

    fun hideImages() {
        resimReset()

        for (image in imageArray) {
            image.visibility = View.INVISIBLE
        }

    }


    fun increaseScore(view: View) {

        mp.start()
        score++
        imageArray[resimIndex].setImageResource(R.drawable.ninca_touch)
        imageArray[resimIndex].visibility = View.INVISIBLE
        scoreText.text = "Score: " + score
        hiz = hiz - 20
        resimGoster()
        resimReset()
    }

    fun resimGoster() {
        val random = Random()
        val index = random.nextInt(8 - 0)
        resimIndex = index
        imageArray[index].visibility = View.VISIBLE
    }

    fun resimReset() {

        imageArray[0].setImageResource(R.drawable.ninja)
        imageArray[1].setImageResource(R.drawable.ninja)
        imageArray[2].setImageResource(R.drawable.ninja)
        imageArray[3].setImageResource(R.drawable.ninja)
        imageArray[4].setImageResource(R.drawable.ninja)
        imageArray[5].setImageResource(R.drawable.ninja)
        imageArray[6].setImageResource(R.drawable.ninja)
        imageArray[7].setImageResource(R.drawable.ninja)
        imageArray[8].setImageResource(R.drawable.ninja)


    }

    fun CustomLoad() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val radioButton = dialogView.findViewById<RadioButton>(R.id.radioButton)
        val radioButton2 = dialogView.findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3 = dialogView.findViewById<RadioButton>(R.id.radioButton3)
        val txt = dialogView.findViewById<TextView>(R.id.textView)
        txt.text = "En Son Skor: " + prefs!!.getInt(FINISH_SCORE, scoreFinish)


        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setNegativeButton("Oyuna Başla", { dialogInterface: DialogInterface, i: Int ->
            if (radioButton.isChecked) {
                sure = 10
            } else if (radioButton2.isChecked) {
                sure = 20
            } else if (radioButton3.isChecked) {
                sure = 30
            } else {
                sure = 40
            }
            scoreText.text = "Score: 0"
            CountTime()
            hideImages()
            resimGoster()

        })



        dialog.show()

    }

    fun olusturPref() {
        prefs!!.edit().putInt(FINISH_SCORE, scoreFinish).apply()
    }
}
