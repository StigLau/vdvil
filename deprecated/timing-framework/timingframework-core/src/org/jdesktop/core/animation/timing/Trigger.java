package org.jdesktop.core.animation.timing;

import com.surelogic.ThreadSafe;

/**
 * This interface provides methods supported by all triggers, an event-driven
 * approach to starting an animation. Methods are provided to disarm a trigger
 * and check if it is armed.
 * 
 * @author Chet Haase
 * @author Tim Halloran
 */
@ThreadSafe
public interface Trigger {

  /**
   * Disables this trigger. If the event that fires this trigger occurs
   * subsequently it will be ignored.
   */
  void disarm();

  /**
   * Gets if this trigger is armed.
   * 
   * @return {@code true} indicates that this trigger is armed, {@code false}
   *         indicates that it has been disarmed with a call to
   *         {@link #disarm()}.
   */
  boolean isArmed();
}