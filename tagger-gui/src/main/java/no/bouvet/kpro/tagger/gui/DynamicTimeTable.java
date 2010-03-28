package no.bouvet.kpro.tagger.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.lau.tagger.model.MediaFile;
import no.lau.tagger.model.Segment;
import no.lau.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.PlayerIF;
import org.apache.log4j.Logger;

public class DynamicTimeTable {

    private JPanel panel = new JPanel();
    private PlayerIF player;
    static Logger log = Logger.getLogger(DynamicTimeTable.class);

    public DynamicTimeTable(PlayerIF player, final SimpleSong simpleSong, final SimpleSongCallBack simpleSongCallBack) {
        this.player = player;

        panel = new JPanel(new MigLayout("", "[right]"));

        final JTextField fileNameField = new JTextField(simpleSong.mediaFile.fileName, 80);
        fileNameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSongCallBack.update(
                        new SimpleSong(simpleSong.reference,
                                new MediaFile(fileNameField.getText(), simpleSong.mediaFile.startingOffset),
                                simpleSong.segments,
                                simpleSong.bpm));
            }
        });
        panel.add(fileNameField, "span, wrap");

        final JTextField bpmField = new JTextField(simpleSong.bpm.toString(), 5);
        bpmField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSongCallBack.update(
                        new SimpleSong(simpleSong.reference,
                                simpleSong.mediaFile,
                                simpleSong.segments,
                                new Float(bpmField.getText())));
            }
        });
        panel.add(bpmField, "");
        panel.add(new JLabel("BPM"), "");

        final JTextField startingOffsetField = new JTextField(simpleSong.mediaFile.startingOffset.toString(), 5);
        startingOffsetField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleSongCallBack.update(
                        new SimpleSong(simpleSong.reference,
                                new MediaFile(simpleSong.mediaFile.fileName, new Float(startingOffsetField.getText())),
                                simpleSong.segments,
                                simpleSong.bpm));
            }
        });
        panel.add(startingOffsetField, "");
        panel.add(new JLabel("starting offset"), "wrap");

        //Titles
        panel.add(new JLabel("start"), "");
        panel.add(new JLabel("end"), "");
        panel.add(new JLabel("text"), "");
        panel.add(new JLabel("Play/Pause"), "wrap");
        for (Segment segment : simpleSong.segments) {
            createRowOnPanel(segment, simpleSong, simpleSongCallBack);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void createRowOnPanel(final Segment segment, final SimpleSong simpleSong, final SimpleSongCallBack simpleSongCallBack) {
        final JTextField startBeat = new JTextField(segment.start.toString(), 3);
        startBeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Segment newSegment = new Segment(segment.id, new Float(startBeat.getText()), segment.end, segment.text);
                simpleSongCallBack.update(updateSegmentInSimpleSong(newSegment, simpleSong));
            }
        });
        panel.add(startBeat, "");

        final JTextField endBeat = new JTextField(segment.end.toString(), 3);
        endBeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Segment newSegment = new Segment(segment.id, segment.start, new Float(endBeat.getText()), segment.text);
                simpleSongCallBack.update(updateSegmentInSimpleSong(newSegment, simpleSong));
            }
        });
        panel.add(endBeat, "");

        final JTextField textField = new JTextField(segment.text, 40);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Segment newSegment = new Segment(segment.id, segment.start, segment.end, textField.getText());
                simpleSongCallBack.update(updateSegmentInSimpleSong(newSegment, simpleSong));
            }
        });
        panel.add(textField, "");

        JButton playButton = new JButton("play/pause");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Float start = new Float(startBeat.getText());
                try {
                    player.playPause(start, segment.end);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        });
        panel.add(playButton, "wrap");
    }

    /*
     * Creates a new SimpleSong when a segment has changed
     */
    private SimpleSong updateSegmentInSimpleSong(Segment editedSegment, SimpleSong simpleSong) {
        List<Segment> segments = new ArrayList<Segment>();
        for (Segment thisSegment : simpleSong.segments) {
            if(thisSegment.id == null)
                thisSegment = cloneSegmentAndAddId(thisSegment);
            if (thisSegment.id.equals(editedSegment.id))
                segments.add(editedSegment);
            else
                segments.add(thisSegment);
        }
        return new SimpleSong(
                simpleSong.reference,
                simpleSong.mediaFile,
                segments,
                simpleSong.bpm);
    }

    private Segment cloneSegmentAndAddId(Segment thisSegment) {
        String id = String.valueOf(Math.abs(new Random().nextLong()));
        return new Segment(id, thisSegment.start, thisSegment.end, thisSegment.text);
    }
}