import java.util.Random;

/**
 * Created by Anatoliy.Polyakov on 28.08.2019.
 */
public class MineField {
    Cell[][] field;
    int fieldX;
    int fieldY;
    int bombsLeft;
    int cellsLeft;
    boolean gameOver;
    boolean gameWon;
    boolean firstClick;

    public MineField(int x, int y, int mines){
        field = new Cell[x][y];
        fieldX = x;
        fieldY = y;
        bombsLeft = mines;
        cellsLeft = x*y;
        gameOver = false;
        gameWon = false;
        firstClick = true;

        for (int i = 0; i < x; i++) {
            field[i] = new Cell[y];
            for (int j = 0; j < y; j++) {
                field[i][j] = new Cell();
            }
        }

    }

    public void generateField(int xA, int yA, int mines) {
        Random random = new Random();

        for (int i = 0; i < mines; i++) {
            int x = Math.abs(random.nextInt() % fieldX);
            int y = Math.abs(random.nextInt() % fieldY);
            if(!field[x][y].bomb && x!=xA && y!=yA){
                field[x][y].bomb = true;
            }
            else{
                i--;
            }
        }

        for (int i = 0; i < fieldX; i++) {
            for (int j = 0; j < fieldY; j++) {
                field[i][j].number = getBombCount(i,j);
            }
        }
        firstClick = false;
    }

    private int getBombCount(int i, int j) {
        int count = 0;
        if(i-1 >= 0 && j-1 >= 0 && field[i-1][j-1].bomb)
            count++;
        if(j-1 >= 0 && field[i][j-1].bomb)
            count++;
        if(i-1 >= 0 && field[i-1][j].bomb)
            count++;
        if(i+1 < fieldX && j+1 < fieldY && field[i+1][j+1].bomb)
            count++;
        if(j+1 < fieldY && field[i][j+1].bomb)
            count++;
        if(i+1 < fieldX && field[i+1][j].bomb)
            count++;
        if(i-1 >= 0 && j+1 < fieldY && field[i-1][j+1].bomb)
            count++;
        if(i+1 < fieldX && j-1 >= 0 && field[i+1][j-1].bomb)
            count++;
        return count;
    }

    public void revealCell(int x, int y){
        field[x][y].hidden = false;
        cellsLeft--;
        if(field[x][y].bomb){
            if(!gameOver)
                field[x][y].fatal = true;
            gameOver = true;
            return;
        }
        if(field[x][y].number == 0){
            revealCellsAround(x,y);
        }
        if(cellsLeft == 0 && !gameOver)
            gameWon = true;
    }

    public void revealCellsAround(int x, int y) {
        if(x-1 >= 0 && y-1 >= 0 && field[x-1][y-1].hidden && !field[x-1][y-1].checked) {
            revealCell(x-1, y-1);
        }
        if(y-1 >= 0 && field[x][y-1].hidden && !field[x][y-1].checked) {
            revealCell(x, y-1);
        }
        if(x-1 >= 0 && field[x-1][y].hidden && !field[x-1][y].checked) {
            revealCell(x-1, y);
        }
        if(x+1 < fieldX && y+1 < fieldY && field[x+1][y+1].hidden && !field[x+1][y+1].checked) {
            revealCell(x+1, y+1);
        }
        if(y+1 < fieldY && field[x][y+1].hidden && !field[x][y+1].checked) {
            revealCell(x, y+1);
        }
        if(x+1 < fieldX && field[x+1][y].hidden && !field[x+1][y].checked) {
            revealCell(x+1, y);
        }
        if(x-1 >= 0 && y+1 < fieldY && field[x-1][y+1].hidden && !field[x-1][y+1].checked) {
            revealCell(x-1, y+1);
        }
        if(x+1 < fieldX && y-1 >= 0 && field[x+1][y-1].hidden && !field[x+1][y-1].checked) {
            revealCell(x+1, y-1);
        }
    }

    public int checkCell(int x, int y){
        if(!field[x][y].checked){
            field[x][y].checked = true;
            bombsLeft--;
        }
        else{
            field[x][y].checked = false;
            bombsLeft++;
        }
        return bombsLeft;
    }

}
