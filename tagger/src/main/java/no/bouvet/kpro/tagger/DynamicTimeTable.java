package no.bouvet.kpro.tagger;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DynamicTimeTable {

    private List<Row> rows = new ArrayList<Row>();
    private JPanel panel = new JPanel();
    private PlayerIF player;

    public DynamicTimeTable(PlayerIF player) {
        this.player = player;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addRow(Float cue, Float end, String text) {
        rows.add(new Row(cue, end, text));
    }

    public void repaintRows() {
        System.out.println("Repaint");
        panel = new JPanel(new MigLayout("", "[right]"));

        //Titles
        panel.add(new JLabel("cue"), "");
        panel.add(new JLabel("end"), "");
        panel.add(new JLabel("text"), "");
        panel.add(new JLabel("Play/Pause"), "wrap");

        for (final Row row : rows) {
            final JTextField cueField = new JTextField(row.cue.toString(), 3);
            panel.add(cueField, "");

            JButton plussButton = new JButton("play/pause");
                        plussButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                row.cue = new Float(cueField.getText());
                                System.out.println("Row Cue after" + row.cue);
                                try {
                                    player.playPause(row.cue);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        });
            panel.add(plussButton, "wrap");
        }
    }
}

