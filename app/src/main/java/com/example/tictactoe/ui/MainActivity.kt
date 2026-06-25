package com.example.tictactoe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private var currentPlayer = "X"
    private var gameActive = true
    private var xScore = 0
    private var oScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBoard()
    }

    private fun setupBoard() {
        val buttons = listOf(
            binding.btn00, binding.btn01, binding.btn02,
            binding.btn10, binding.btn11, binding.btn12,
            binding.btn20, binding.btn21, binding.btn22
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (gameActive) {
                    val row = index / 3
                    val col = index % 3
                    if (board[row][col] == null) {
                        board[row][col] = currentPlayer
                        button.text = currentPlayer
                        button.isEnabled = false

                        if (checkWinner()) {
                            gameActive = false
                            updateScore()
                            binding.tvStatus.text = "Player $currentPlayer Wins!"
                        } else if (isBoardFull()) {
                            gameActive = false
                            binding.tvStatus.text = "Draw!"
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                            binding.tvStatus.text = "Player $currentPlayer's turn"
                        }
                    }
                }
            }
        }

        binding.btnReset.setOnClickListener {
            resetBoard()
        }
    }

    private fun checkWinner(): Boolean {
        val winPatterns = listOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8), intArrayOf(2,4,6)
        )

        for (pattern in winPatterns) {
            val r1 = pattern[0] / 3; val c1 = pattern[0] % 3
            val r2 = pattern[1] / 3; val c2 = pattern[1] % 3
            val r3 = pattern[2] / 3; val c3 = pattern[2] % 3

            if (board[r1][c1] != null && board[r1][c1] == board[r2][c2] && board[r1][c1] == board[r3][c3]) {
                return true
            }
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it != null } }
    }

    private fun updateScore() {
        if (currentPlayer == "X") xScore++ else oScore++
        binding.tvXScore.text = "X: $xScore"
        binding.tvOScore.text = "O: $oScore"
    }

    private fun resetBoard() {
        board = Array(3) { arrayOfNulls(3) }
        currentPlayer = "X"
        gameActive = true
        binding.tvStatus.text = "Player X's turn"

        val buttons = listOf(
            binding.btn00, binding.btn01, binding.btn02,
            binding.btn10, binding.btn11, binding.btn12,
            binding.btn20, binding.btn21, binding.btn22
        )
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
    }
}
