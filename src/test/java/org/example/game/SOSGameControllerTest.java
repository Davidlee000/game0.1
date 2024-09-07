package org.example.game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Button;



class SOSGameControllerTest {
  @Test
  void checkSOS() {
    var sosGameController = new SOSGameController();
    String s = "S";
    String o = "O";
    // Test for a valid SOS
    assertTrue(sosGameController.checkLine(s, o, s));
    // Test for invalid combinations
    assertFalse(sosGameController.checkLine(s, s, s));
    assertFalse(sosGameController.checkLine(s, o, o));
    assertFalse(sosGameController.checkLine(o, s, s));
  }

  @Test
  void startGame(){
    var sosGameApp = new SOSGameApp();
    assertThrows(ExceptionInInitializerError.class, () -> sosGameApp.start(null));
  }
}