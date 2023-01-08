package timestampAdjuster;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
When the window pops up, copy this (for example):
0:00:58 - introduction
0:03:25 - problem formulation
0:05:15 - graphical interface implementation
0:10:05 - interface testing
0:10:44 - action manager implementation
0:25:03 - test run and patching
0:30:11 - debugging
0:33:25 - bug fixing
0:33:58 - testing
adjust Minute and/or second spinners then push Calculate button
 */

public class TimestampAdjuster extends JFrame{
    private JPanel panel1;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;
    private JButton calculateButton;
    private JTextArea timestampTextArea;


    public TimestampAdjuster() {

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Scanner scanner = new Scanner(timestampTextArea.getText())){
                    List<String> lines = new ArrayList<>();
                    while(scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        lines.add(line); //so far it's separated line by line, the list is ready
                    }
                    // take off the beginning which is the time stamp, e.g. "00:01:00
                    //stream:
                    String adjustedTimestamps = lines.stream().map(line -> {
                        String [] elements = line.split(" - ");
                        String oneTimstamp = elements[0]; //ez a 00:01:00
                        Integer minute = Integer.parseInt(minuteSpinner.getValue().toString());
                        Integer second = Integer.parseInt(secondSpinner.getValue().toString());
                        //LocalDateTime timestamp = LocalDateTime.parse(oneTimestamp, DateTimeFormatter.ofPattern("HH:mm:ss"));
                        LocalTime timestamp = LocalTime.parse(oneTimstamp, DateTimeFormatter.ofPattern("H:mm:ss"));
                        timestamp = timestamp.plusMinutes(minute); //you have to add a new variable because plus makes the new Object (Since everything date obj is immutable!)
                        timestamp = timestamp.plusSeconds(second); //LocalDateTime
                        //conversion back to String:
                        String newTimestamp = timestamp.format(DateTimeFormatter.ofPattern("H:mm:ss"));//String
                        return newTimestamp + " - " + elements[1];
                    }).collect(Collectors.joining("\n"));
                    timestampTextArea.setText(adjustedTimestamps);
                }
            }
        });
    }

    public static void main(String[] args) {
        TimestampAdjuster timestampAdjuster = new TimestampAdjuster();
        timestampAdjuster.setContentPane(timestampAdjuster.panel1);
        timestampAdjuster.setTitle("Hello");
        timestampAdjuster.setSize(300, 400);
        timestampAdjuster.setVisible(true);
        timestampAdjuster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Ismétlés");



    }



}

