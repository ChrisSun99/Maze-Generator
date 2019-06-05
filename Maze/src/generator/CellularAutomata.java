package generator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.swing.Timer;

import main.*;
import util.Cell;

public class CellularAutomata {
    private final List<Cell> stacks = new ArrayList<>(); //Cells that are currently on.
    private final List<Cell> grid;

    private List<Cell> currentCells = new ArrayList<Cell>(4);  //Neighbors
    private final Random r = new Random();
    private Cell current;

    public CellularAutomata(List<Cell> grid, MazeGridPanel panel) {
        this.grid = grid;

        for (int i = 0; i < grid.size(); i++) {
            Cell temp = grid.get(r.nextInt(grid.size()));
            temp.turnOn();
            temp.setVisited(true);
            stacks.add(temp);

        }

        final Timer timer = new Timer(Maze.speed, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
                carve();
            } else {
                current = null;
                Maze.generated = true;
                timer.stop();
            }
            panel.setCurrent(current);
            panel.repaint();
            timer.setDelay(Maze.speed);
        }
    });
        timer.start();
    }

    private void carve() {
        for (int i = 0; i < grid.size(); i++) {
            current = grid.get(i);
            currentCells = current.getUnvisitedNeighboursForCA(grid);

            if (current != null) {
                current.setCA(true);
                Cell next = current.getUnvisitedNeighbourforCA(grid);

                //Birth

                if (currentCells.size() == 3 && !current.isOn()) {
                    current.setVisited(true);
                    current.turnOn();
                    stacks.add(current);
                    current.removeWalls(next);
                    current = next;

                } else if (currentCells.size() >= 1 && currentCells.size() <= 5 && current.isOn()) {
                    //Survival
                    current.setVisited(true);
                    current.removeWalls(next);
                    current = next;

                }

                //Dead
                else {
                    current.setVisited(false);
                    current.turnOff();
                    stacks.remove(current);
                    current = next;
                }
            }
        }

    }

}


