package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import generator.*;
import solver.BiDFSSolve;
import solver.CASolve;
import util.Cell;

public class MazeGridPanel extends JPanel {

    private static final long serialVersionUID = 7237062514425122227L;
    private final List<Cell> grid = new ArrayList<Cell>();
    private List<Cell> currentCells = new ArrayList<Cell>();
    public boolean running = true;


    public MazeGridPanel(int rows, int cols) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                grid.add(new Cell(x, y));
            }
        }
    }


    @Override
    public Dimension getPreferredSize() {
        // +1 pixel on width and height so bottom and right borders can be drawn.
        return new Dimension(main.Maze.WIDTH + 1, main.Maze.HEIGHT + 1);
    }

    public void generate(int index) {
        // switch statement for gen method read from combobox in Maze.java
        switch (index) {

            case 0:
                new DFSGen(grid, this);
                break;
            case 1:
                new BinaryTreeGen(grid, this);
                break;
            case 2:
                new QuadDFSGen(grid, this);
                break;
            case 3:
                new CellularAutomata(grid, this);
                break;

            case 4:
                new PrimGen(grid, this);
                break;

            case 5:
                new KruskalGen(grid, this);
                break;
        }
    }

    public void solve(int index) {
        switch (index) {
            case 0:
                new BiDFSSolve(grid, this);
                break;
            case 1:
                new CASolve(grid, this);
                break;

        }
    }

    public void resetSolution() {
        for (Cell c : grid) {
            c.setDeadEnd(false);
            c.setPath(false);
            c.setDistance(-1);
            c.setParent(null);
        }
        repaint();
    }


    public void step() {

////        for (Cell c : grid) {
////            c.draw(getGraphics());
////        }
//        int i = 0;
//        for (Cell c: grid) {
//            while (i < 2) {
//                c.draw(getGraphics());
//                i += 1;
//            }
////            c.run(false);
//        }
    }



    public void setCurrent(Cell current) {
        if(currentCells.size() == 0) {
            currentCells.add(current);
        } else {
            currentCells.set(0, current);
        }
    }

    public void setCurrentCells(List<Cell> currentCells) {
        this.currentCells = currentCells;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Cell c : grid) {
            c.draw(g);
        }
        for (Cell c : currentCells) {
            if(c != null) c.displayAsColor(g, Color.YELLOW);
        }
        grid.get(0).displayAsColor(g, Color.GREEN); // start cell
        grid.get(grid.size() - 1).displayAsColor(g, Color.YELLOW); // end or goal cell
//        grid.get(getX()).displayAsColor(g, Color.GREEN);
//        grid.get(grid.size() - getX()).displayAsColor(g, Color.YELLOW);
    }
}
