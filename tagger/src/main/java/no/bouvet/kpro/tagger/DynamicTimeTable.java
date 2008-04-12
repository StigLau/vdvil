package no.bouvet.kpro.tagger;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DynamicTimeTable {

    private JPanel panel = new JPanel();
    private PlayerIF player;

    public DynamicTimeTable(PlayerIF player, final SimpleSong simpleSong) {
        this.player = player;

        panel = new JPanel(new MigLayout("", "[right]"));

        final JTextField bpmField = new JTextField(simpleSong.bpm.toString(), 3);
        bpmField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSong.bpm = new Float(bpmField.getText());
            }
        });
        panel.add(bpmField, "");
        panel.add(new JLabel("BPM"), "");

        final JTextField startingOffsetField = new JTextField(simpleSong.startingOffset.toString(), 3);
        startingOffsetField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSong.startingOffset = new Float(startingOffsetField.getText());
            }
        });
        panel.add(startingOffsetField, "");
        panel.add(new JLabel("starting offset"), "wrap");

        //Titles
        panel.add(new JLabel("cue"), "");
        panel.add(new JLabel("end"), "");
        panel.add(new JLabel("text"), "");
        panel.add(new JLabel("Play/Pause"), "wrap");
        for (Row row : simpleSong.rows) {
            createRowOnPanel(row);
        }

        JButton saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new SimpleSongParser().save(simpleSong, "/corona.dvl");
            }
        });
        panel.add(saveButton, "wrap");
    }

    public JPanel getPanel() {
        return panel;
    }

    public void createRowOnPanel(final Row row) {
        final JTextField startBeat = new JTextField(row.cue.toString(), 3);
        startBeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                row.cue = new Float(startBeat.getText());
            }
        });
        panel.add(startBeat, "");

        JButton plussButton = new JButton("play/pause");
        plussButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                row.cue = new Float(startBeat.getText());
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

