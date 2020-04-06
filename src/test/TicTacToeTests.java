package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import org.junit.jupiter.api.Test;

import main.TicTacToe;

class TicTacToeTests {

  @Test
  void test() {
    TicTacToe ttt = TicTacToe.getInstance();
    assertEquals(ttt, ttt);
  }

}
