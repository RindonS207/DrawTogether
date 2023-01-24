package rindong.project.shape;

import rindong.project.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoiceShape extends JDialog
{
    private Main.formWindows parentWindows;

    private JToggleButton shape_rect=new JToggleButton("□");
    private JToggleButton shape_oval = new JToggleButton("○");

    private JRadioButton fill_trueButton=new JRadioButton("填充");
    private JRadioButton fill_falseButton=new JRadioButton("空心");

    private JTextField width_text=new JTextField("宽");
    private JTextField height_text=new JTextField("高");

    private JButton close_true=new JButton("确定");
    private JButton close_false=new JButton("取消");

    private boolean normalClose=false;

    public ChoiceShape(Main.formWindows parent)
    {
        super(parent,"选择图形数据",true);
        this.parentWindows = parent;

        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(300,400);

        this.setLayout(null);

        this.setBounds(500,100,300,400);

        addButtonLayout();
        addButtonListener();

        setVisible(true);
    }

    private void addButtonLayout()
    {
        this.getContentPane().add(shape_rect);
        shape_rect.setBounds(50,50,70,50);

        this.getContentPane().add(shape_oval);
        shape_oval.setBounds(175,50,70,50);
        shape_rect.setSelected(true);

        ButtonGroup bg_1=new ButtonGroup();
        bg_1.add(shape_rect);
        bg_1.add(shape_oval);

        this.getContentPane().add(fill_trueButton);
        fill_trueButton.setBounds(50,100,70,50);

        this.getContentPane().add(fill_falseButton);
        fill_falseButton.setBounds(175,100,70,50);
        fill_falseButton.setSelected(true);

        ButtonGroup bg_2=new ButtonGroup();
        bg_2.add(fill_trueButton);
        bg_2.add(fill_falseButton);

        this.getContentPane().add(width_text);
        width_text.setBounds(50,170,70,25);

        this.getContentPane().add(height_text);
        height_text.setBounds(175,170,70,25);

        this.getContentPane().add(close_true);
        close_true.setBounds(50,230,70,50);


        this.getContentPane().add(close_false);
        close_false.setBounds(175,230,70,50);


    }

    private void sureClosing()
    {
        int width;
        int height;
        try
        {
            width=Integer.parseInt(width_text.getText());
            height=Integer.parseInt(height_text.getText());
        }
        catch (NumberFormatException ex)
        {
            width = 0;
            height = 0;
        }
        if (width <= 0 || height <= 0)
        {
            normalClose = false;
            dispose();
        }
        else
        {
            normalClose = true;
            parentWindows.ShapeHeight = height;
            parentWindows.ShapeWidth = width;
            dispose();
        }
    }

    private void addButtonListener()
    {
        close_true.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sureClosing();
            }
        });

        close_false.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
    }

    @Override
    public void dispose()
    {
        if (normalClose)
        {
            if (shape_rect.isSelected())
            {
                if (fill_trueButton.isSelected())
                {
                    parentWindows.setShapeType(ChoiceType.FILL_RECT);
                }
                else
                {
                    parentWindows.setShapeType(ChoiceType.NORMAL_RECT);
                }
            }
            else
            {
                if (fill_trueButton.isSelected())
                {
                    parentWindows.setShapeType(ChoiceType.FILL_OVAL);
                }
                else
                {
                    parentWindows.setShapeType(ChoiceType.NORMAL_OVAL);
                }
            }
        }
        else
        {
            parentWindows.setShapeType(ChoiceType.CHOICE_NOTHING);
        }
        parentWindows.setShapeMode(normalClose);
        super.dispose();
    }
}
