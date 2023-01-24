package rindong.project;

import rindong.project.file.SaveImage;
import rindong.project.shape.ChoiceShape;
import rindong.project.shape.ChoiceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args)
    {
        new formWindows();
    }

    public static class formWindows extends JFrame
    {
        BufferedImage image=new BufferedImage(570,390,BufferedImage.TYPE_INT_BGR);
        Graphics2D gs2d;
        Color backGroundColor=Color.WHITE;
        Color drawColor=Color.BLACK;

        myCanvas canvas=new myCanvas();

        int mouseX = -1;
        int mouseY = -1;

        boolean clearmode=false;

        private JToolBar toolBar = new JToolBar();
        private JToggleButton JButtonLess = new JToggleButton("细线");
        private JToggleButton JButtonNormal = new JToggleButton("粗线");
        private JToggleButton JButtonLarge = new JToggleButton("较粗");
        private JButton JButtonClearMode = new JButton("橡皮");
        private JButton JButtonColor_selectBackGround = new JButton("背景色");
        private JButton JButtonColor_selectDrawColor = new JButton("前景色");
        private JButton JButtonClearAll = new JButton("清除");
        private JButton JButtonSaveFile = new JButton("保存");
        private JButton JButtonChoiceShape = new JButton("图形");

        private boolean ShapeMode = false;
        private ChoiceType shapeType=ChoiceType.CHOICE_NOTHING;
        public int ShapeWidth = -1;
        public int ShapeHeight = -1;

        public void setShapeMode(boolean mode)
        {
            ShapeMode = mode;
        }

        public void setShapeType(ChoiceType type)
        {
            shapeType = type;
        }

        public formWindows()
        {
            setResizable(false);
            setTitle("好玩的一批");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setBounds(500,100,574,460);

            Graphics gs=image.getGraphics();
            gs2d=(Graphics2D)gs;

            gs2d.setColor(backGroundColor);
            gs2d.fillRect(0,0,570,390);
            gs2d.setColor(drawColor);

            canvas.setImg(image);
            this.getContentPane().add(canvas);

            addMouseListenerEvent();
            addButtons();
            addButtonListenerEvent();

            setVisible(true);
        }

        private void addMouseListenerEvent()
        {
            canvas.addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseDragged(MouseEvent e)
                {
                    if (mouseX > 0 && mouseY > 0)
                    {
                        if (clearmode)
                        {
                            gs2d.setColor(backGroundColor);
                            gs2d.fillRect(mouseX,mouseY,10,10);
                        }
                        else
                        {
                            gs2d.setColor(drawColor);
                            gs2d.drawLine(mouseX,mouseY,e.getX(),e.getY());
                        }
                    }
                    mouseX = e.getX();
                    mouseY = e.getY();
                    canvas.repaint();
                }
            });

            canvas.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    mouseX = -1;
                    mouseY = -1;
                }
            });

            canvas.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (!clearmode)
                    {
                        if (ShapeMode)
                        {
                            switch (shapeType)
                            {
                                case NORMAL_RECT:
                                {
                                    gs2d.setColor(drawColor);
                                    gs2d.drawRect(e.getX() - ShapeWidth / 2,e.getY() - ShapeHeight / 2,ShapeWidth,ShapeHeight);
                                    break;
                                }
                                case FILL_RECT:
                                {
                                    gs2d.setColor(drawColor);
                                    gs2d.fillRect(e.getX() - ShapeWidth / 2,e.getY() - ShapeHeight / 2,ShapeWidth,ShapeHeight);
                                    break;
                                }
                                case NORMAL_OVAL:
                                {
                                    gs2d.setColor(drawColor);
                                    gs2d.drawOval(e.getX() - ShapeWidth / 2,e.getY() - ShapeHeight / 2,ShapeWidth,ShapeHeight);
                                    break;
                                }
                                case FILL_OVAL:
                                {
                                    gs2d.setColor(drawColor);
                                    gs2d.fillOval(e.getX() - ShapeWidth / 2,e.getY() - ShapeHeight / 2,ShapeWidth,ShapeHeight);
                                    break;
                                }
                                default:
                                {
                                    break;
                                }
                            }
                            canvas.repaint();
                        }
                    }
                }
            });
        }

        private void addButtons()
        {
            this.getContentPane().add(toolBar,BorderLayout.NORTH);

            toolBar.add(JButtonSaveFile);
            toolBar.addSeparator();

            JButtonLess.setSelected(true);
            ButtonGroup bg=new ButtonGroup();
            bg.add(JButtonLess);
            bg.add(JButtonNormal);
            bg.add(JButtonLarge);

            toolBar.add(JButtonLess);
            toolBar.add(JButtonNormal);
            toolBar.add(JButtonLarge);

            toolBar.addSeparator();

            toolBar.add(JButtonColor_selectBackGround);
            toolBar.add(JButtonColor_selectDrawColor);

            toolBar.addSeparator();

            toolBar.add(JButtonClearAll);
            toolBar.add(JButtonClearMode);

            toolBar.addSeparator();

            toolBar.add(JButtonChoiceShape);
        }

        private void addButtonListenerEvent()
        {
            JButtonLess.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    gs2d.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
                }
            });

            JButtonNormal.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    gs2d.setStroke(new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
                }
            });

            JButtonLarge.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    gs2d.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
                }
            });

            JButtonColor_selectBackGround.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Color choiceColor=JColorChooser.showDialog(formWindows.this,"选择你的英雄",backGroundColor);

                    if (choiceColor != null)
                    {
                        backGroundColor = choiceColor;
                        JButtonColor_selectBackGround.setBackground(backGroundColor);
                        gs2d.setColor(backGroundColor);
                        gs2d.fillRect(0,0,570,390);
                        gs2d.setColor(drawColor);
                        canvas.repaint();
                    }
                }
            });

            JButtonColor_selectDrawColor.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Color selectColor=JColorChooser.showDialog(formWindows.this,"选择你的英雄",drawColor);

                    if (selectColor != null)
                    {
                        drawColor = selectColor;
                        JButtonColor_selectDrawColor.setBackground(drawColor);
                    }
                }
            });

            JButtonClearAll.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    gs2d.setColor(backGroundColor);
                    gs2d.fillRect(0,0,570,390);
                    gs2d.setColor(drawColor);
                    canvas.repaint();
                }
            });

            JButtonClearMode.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (JButtonClearMode.getText().equals("橡皮"))
                    {
                        clearmode = true;
                        JButtonClearMode.setText("画图");
                    }
                    else
                    {
                        clearmode = false;
                        JButtonClearMode.setText("橡皮");
                    }
                }
            });

            JButtonChoiceShape.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    new ChoiceShape(formWindows.this);
                }
            });

            JButtonSaveFile.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    SaveImage.savefile(formWindows.this,image);
                }
            });
        }


    }

    private static class myCanvas extends Canvas
    {
        private Image img=null;

        public void setImg(Image img)
        {
            this.img = img;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D draw=(Graphics2D)g;

            g.drawImage(this.img,0,0,null);

            Font font=new Font("宋体",Font.BOLD,16);

            draw.setColor(Color.blue);
            draw.setFont(font);

        }

        @Override
        public void update(Graphics g)
        {
            paint(g);
        }
    }
}
