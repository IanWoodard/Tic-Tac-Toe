package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import main.TicTacToe;

class TicTacToeTests {

  @Test
  void test() {
    TicTacToe ttt = TicTacToe.getInstance();
    for (JButton j : ttt.getGameButtons()) {
      assertEquals(j.getBackground(), Color.WHITE);
      assertEquals(j.getIcon(), null);
      assertEquals(j.isVisible(), true);
      assertEquals(j.isEnabled(), true);
      assertEquals(j.isValid(), true);
    }
  }

}
