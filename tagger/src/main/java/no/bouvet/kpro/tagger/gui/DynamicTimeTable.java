package no.bouvet.kpro.tagger.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import no.bouvet.kpro.tagger.model.Row;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.PlayerIF;

public class DynamicTimeTable {

    private JPanel panel = new JPanel();
    private PlayerIF player;

    public DynamicTimeTable(PlayerIF player, final SimpleSong simpleSong) {
        this.player = player;

        panel = new JPanel(new MigLayout("", "[right]"));

        final JTextField fileNameField = new JTextField(simpleSong.fileName, 80);
        fileNameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSong.fileName = fileNameField.getText();
            }
        });
        panel.add(fileNameField, "span, wrap");

        final JTextField bpmField = new JTextField(simpleSong.bpm.toString(), 5);
        bpmField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSong.bpm = new Float(bpmField.getText());
            }
        });
        panel.add(bpmField, "");
        panel.add(new JLabel("BPM"), "");

        final JTextField startingOffsetField = new JTextField(simpleSong.startingOffset.toString(), 5);
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

        final JTextField endBeat = new JTextField(row.end.toString(), 3);
        endBeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                row.end = new Float(endBeat.getText());
            }
        });
        panel.add(endBeat, "");

        final JTextField textField = new JTextField(row.text, 40);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                row.text = textField.getText();
            }
        });
        panel.add(textField, "");

        JButton playButton = new JButton("play/pause");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                row.cue = new Float(startBeat.getText());
                try {
                    player.playPause(row.cue);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        panel.add(playButton, "wrap");
    }
}

