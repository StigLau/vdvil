package org.jdesktop.swing.animation.rendering;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import org.jdesktop.core.animation.i18n.I18N;
import org.jdesktop.core.animation.rendering.JRenderer;
import org.jdesktop.core.animation.rendering.JRendererTarget;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.TimingSource.PostTickListener;

/**
 * Manages passive rendering on a Swing {@link JRendererPanel}.
 * <p>
 * To use this renderer a client constructs a {@link JRendererPanel} and passes
 * it to the constructor with a {@link JRendererTarget} implementation and a
 * timing source. A typical sequence would be
 * 
 * <pre>
 * JFrame frame = new JFrame(&quot;Renderer Demonstration&quot;);
 * final JRendererPanel on = new JRendererPanel();
 * frame.setContentPane(on);
 * final JRendererTarget&lt;GraphicsConfiguration, Graphics2D&gt; target = this;
 * final TimingSource timingSource = new SwingTimerTimingSource();
 * JRenderer renderer = new JPassiveRenderer(on, target, timingSource);
 * timingSource.init();
 * </pre>
 * 
 * In the above snippet <tt>on</tt> will be rendered to. The enclosing instance,
 * <tt>this</tt>, implements {@link JRendererTarget} and will be called to
 * customize what is displayed on-screen.
 * 
 * @author Tim Halloran
 */
public class JPassiveRenderer implements JRenderer<JRendererPanel> {

  /*
   * Thread-confined to the EDT thread
   */
  private final JRendererPanel f_on;
  private final JRendererTarget<GraphicsConfiguration, Graphics2D> f_target;
  private final TimingSource f_ts;
  private final PostTickListener f_postTick = new PostTickListener() {
    public void timingSourcePostTick(TimingSource source, long nanoTime) {
      long now = System.nanoTime();
      if (f_renderCount != 0) {
        f_totalRenderTime += now - f_lastRenderTimeNanos;
      }
      f_lastRenderTimeNanos = now;
      f_renderCount++;
      f_target.renderUpdate();
      f_on.repaint();
    }
  };

  /*
   * Statistics counters
   */
  private long f_lastRenderTimeNanos;
  private long f_totalRenderTime = 0;
  private long f_renderCount = 0;

  public JPassiveRenderer(JRendererPanel on, JRendererTarget<GraphicsConfiguration, Graphics2D> target, TimingSource timingSource) {
    if (!SwingUtilities.isEventDispatchThread())
      throw new IllegalStateException(I18N.err(100));

    if (on == null)
      throw new IllegalArgumentException(I18N.err(1, "on"));
    f_on = on;

    if (target == null)
      throw new IllegalArgumentException(I18N.err(1, "life"));
    f_target = target;

    if (timingSource == null)
      throw new IllegalArgumentException(I18N.err(1, "timingSource"));
    f_ts = timingSource;

    f_on.setDoubleBuffered(true);
    f_on.setOpaque(true);
    f_on.setTarget(f_target, f_ts, f_postTick);
  }

  @Override
  public JRendererPanel getOn() {
    return f_on;
  }

  @Override
  public void invokeLater(Runnable task) {
    SwingUtilities.invokeLater(task);
  }

  @Override
  public TimingSource getTimingSource() {
    return f_ts;
  }

  @Override
  public long getFPS() {
    final long avgCycleTime = getAverageCycleTimeNanos();
    if (avgCycleTime != 0) {
      return TimeUnit.SECONDS.toNanos(1) / avgCycleTime;
    } else
      return 0;
  }

  @Override
  public long getAverageCycleTimeNanos() {
    if (f_renderCount != 0) {
      return (f_totalRenderTime) / f_renderCount;
    } else
      return 0;
  }

  @Override
  public void shutdown() {
    f_on.clearTarget();
    f_target.renderShutdown();
  }
}
