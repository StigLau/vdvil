package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class MigLayoutManagerTest {
    @Test
    public void testBuildingSimpleLayout() throws InterruptedException {
        JPanel p = new JPanel(new MigLayout("", "[right]"));

        p.add(new JLabel("General"), "split, span, gaptop 10");
        p.add(new JSeparator(), "growx, wrap, gaptop 10");

        p.add(new JLabel("Company"), "gap 10");
        p.add(new JTextField(""), "span, growx");
        p.add(new JLabel("Contact"), "gap 10");
        p.add(new JTextField(""), "span, growx, wrap");

        p.add(new JLabel("Propeller"), "split, span, gaptop 10");
        p.add(new JSeparator(), "growx, wrap, gaptop 10");

        p.add(new JLabel("PTI/kW"), "gap 10");
        p.add(new JTextField(10), "");
        p.add(new JLabel("Power/kW"), "gap 10");
        p.add(new JTextField(10), "wrap");
        p.add(new JLabel("R/mm"), "gap 10");
        p.add(new JTextField(10), "wrap");
        p.add(new JLabel("D/mm"), "gap 10");
        p.add(new JTextField(10));

        JFrame frame = new JFrame();
        frame.add(p);
        frame.pack();
        frame.show();

        Thread.sleep(2000);
    }
}
