package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrim;
  private TorpedoStore mockSec;

  @BeforeEach
  public void init() {
    this.mockPrim = mock(TorpedoStore.class);
    this.mockSec = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrim, mockSec);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrim, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockSec.isEmpty()).thenReturn(false);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    when(mockSec.fire(anyInt())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrim, times(1)).fire(1);
    verify(mockSec, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty_SecondaryNotEmpty_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(true);
    when(mockSec.isEmpty()).thenReturn(false);
    when(mockSec.fire(anyInt())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSec, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_BothEmpty_Failure() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(true);
    when(mockSec.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrim, never()).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_SecondaryEmpty_PrimaryNotEmpty_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockSec.isEmpty()).thenReturn(true);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrim, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_BothNotEmpty_Alternating_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockSec.isEmpty()).thenReturn(false);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    when(mockSec.fire(anyInt())).thenReturn(true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrim, times(1)).fire(1);
    verify(mockSec, times(1)).fire(1);
    assertEquals(true, result1);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_SecondaryNotEmpty_Failure() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(true);
    when(mockSec.isEmpty()).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrim, never()).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_BothEmpty_Failure() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(true);
    when(mockSec.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrim, never()).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_SecondaryEmpty_PrimaryNotEmpty_Failure() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockSec.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrim, never()).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Double_Single_SecondaryEmpty_PrimaryNotEmpty_Success() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false);
    when(mockSec.isEmpty()).thenReturn(true);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockPrim, times(2)).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }
  @Test
  public void fireTorpedo_Double_Single_SecondaryEmpty_PrimaryNotEmpty_Failure() {
    // Arrange
    when(mockPrim.isEmpty()).thenReturn(false, true);
    when(mockSec.isEmpty()).thenReturn(true);
    when(mockPrim.fire(anyInt())).thenReturn(true);
    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    verify(mockPrim, times(1)).fire(anyInt());
    verify(mockSec, never()).fire(anyInt());
  }
}
