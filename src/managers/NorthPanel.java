package managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//This class is for the three buttons that appear on the top of the screen in the north border layout position
public class NorthPanel extends JPanel
{
    //Creating initial variables to be used
    private JButton BtnStart;
    private JButton BtnPause;
    private JButton BtnCheat;
    NorthListener Listener;
    GameManager ManagerRef;
    //Overloaded constructor setting basic information in the layout
    public NorthPanel(GameManager ref)
    {
        ManagerRef = ref;
        Listener = new NorthListener();
        setBackground(new Color(28, 87, 119));

        BtnStart = new JButton("Start");
        BtnPause = new JButton("Pause");
        BtnCheat = new JButton("Cheat");
        BtnStart.setFocusable(false);
        BtnPause.setFocusable(false);
        BtnCheat.setFocusable(false);
        BtnStart.addActionListener(Listener);
        BtnPause.addActionListener(Listener);
        BtnCheat.addActionListener(Listener);
        BtnStart.setBackground(new Color(188, 78, 76));
        BtnPause.setBackground(new Color(188, 78, 76));
        BtnCheat.setBackground(new Color(188, 78, 76));
        CheckButtonProperties();

        add(BtnStart);
        add(BtnPause);
        add(BtnCheat);

    }
    //Function that if the alive is set to false in the GameManager thread than the buttons will no longer be able to be clicked
    public void CheckButtonProperties()
    {
        if (ManagerRef.getAlive()) {
            BtnStart.setEnabled(false);
            BtnPause.setEnabled(true);
        }
        else {
            BtnStart.setEnabled(false);
            BtnPause.setEnabled(false);
        }
    }
    //Actual action listener used in the buttons that checks if they are clicked
    public class NorthListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Start"))
            {
                    ManagerRef.setAlive(true);
                    ManagerRef.RunGame();
                    BtnPause.setEnabled(true);
                    BtnStart.setEnabled(false);

            }
            if(e.getActionCommand().equals("Pause"))
            {
                ManagerRef.setAlive(false);
                ManagerRef.RunGame();
                BtnStart.setEnabled(true);
                BtnPause.setEnabled(false);
            }
            if(e.getActionCommand().equals("Cheat"))
            {
                if(ManagerRef.getCheat())
                {
                    ManagerRef.setCheat(false);
                }
                else
                    ManagerRef.setCheat(true);
            }
        }
    }

}
