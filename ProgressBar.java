/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progressbar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Navaneeth Sen <navaneeth.sen@multichoice.co.za>
 */


public class ProgressBar extends JPanel
{

    // Sample program that dispalys the progress in a frame and runs it.
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("ProgressBar");
        ProgressBar progressBar = new ProgressBar();
        frame.add(progressBar);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        progressBar.incrementOverTime();
    }

    private final static int MAX_DRAW_PROGRESS = 1000;
    private final static int FADE_TIME = 5;
    private static final int FRACTION_OF_OUTER_RING = 5;

    private int progress = 0;

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        if (progress <= FADE_TIME + MAX_DRAW_PROGRESS)
        {
            // Here you paint your circle progress bar. Use the progress
            // variable to paint
            // as much progress as it indicates.

            int progressToDraw = Math.min(progress, MAX_DRAW_PROGRESS);
            // Clear the background
            g2.setColor(getBackground());
            g2.fill(g.getClip());
            
            Color drawColor = getForeground();

            // Fade the progress out when it is fully complete
            if (progress > MAX_DRAW_PROGRESS)
            {
                int fadeAmount = FADE_TIME - (progress - MAX_DRAW_PROGRESS);
                drawColor = new Color(drawColor.getRed(), drawColor.getGreen(),
                        drawColor.getBlue(),
                        (int) ((((float) fadeAmount) / FADE_TIME) * 255));
            }

            // Fill in the arc
            g2.setColor(drawColor);
            int angle = -(int) (((float) progressToDraw / MAX_DRAW_PROGRESS) * 360);
            g.fillArc(0, 0, getWidth(), getHeight(), 90, angle);

            // Remove the inner circle
            g2.setColor(getBackground());
            g.fillArc(getWidth() / FRACTION_OF_OUTER_RING / 2, getHeight()
                    / FRACTION_OF_OUTER_RING / 2, getWidth()
                    * (FRACTION_OF_OUTER_RING - 1) / FRACTION_OF_OUTER_RING,
                    getHeight() * (FRACTION_OF_OUTER_RING - 1)
                    / FRACTION_OF_OUTER_RING, 90, angle);

            // Draw the outer circle
            g2.setColor(drawColor);
            g.drawOval(0, 0, getWidth(), getHeight());

            // Draw the inner circle
            g.drawOval(getWidth() / FRACTION_OF_OUTER_RING / 2, getHeight()
                    / FRACTION_OF_OUTER_RING / 2, getWidth()
                    * (FRACTION_OF_OUTER_RING - 1) / FRACTION_OF_OUTER_RING,
                    getHeight() * (FRACTION_OF_OUTER_RING - 1)
                    / FRACTION_OF_OUTER_RING);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
     // Change this to how big you want your component.
        // If you aren't using a LayoutManager, then this is not needed and you
        // can set the size
        // directly
        return new Dimension(100, 100);
    }

    @Override
    public Dimension getMinimumSize()
    {
     // If this component is put in a smaller aera, how small can it get
        // before you just let the
        // the aera clip your component off
        return new Dimension(50, 50);
    }

  // Setter for progress. If you want to increment externally as is usual with
    // most progress bars, use this.
    public void setProgress(int progress)
    {
        this.progress = progress;
        repaint();
    }

    public int getProgress()
    {
        return progress;
    }

    // If you simply want the progress to build up over a set time, use this
    public void incrementOverTime()
    {
     // create a timer that fires every 50 milliseconds or every 1/20th of a
        // second.
        final Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setProgress(progress + 1);
                if (progress >= MAX_DRAW_PROGRESS + FADE_TIME)
                {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
