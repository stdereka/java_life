public class LifeEngine {
    public char[][] field;


    LifeEngine(){
        field = new char[25][25];
        for (int i = 1; i < 24; i++)
            for (int j = 1; j < 24; j++)
                field[i][j] = '.';
    }


    public char get_new(int x, int y){
        int count = 0;
        if (field[x][y] == '*') {
            if (field[x][y - 1] == '*')
                count += 1;
            if (field[x][y + 1] == '*')
                count += 1;
            if (field[x - 1][y] == '*')
                count += 1;
            if (field[x + 1][y] == '*')
                count += 1;
            if (field[x + 1][y + 1] == '*')
                count += 1;
            if (field[x + 1][y - 1] == '*')
                count += 1;
            if (field[x - 1][y + 1] == '*')
                count += 1;
            if (field[x - 1][y - 1] == '*')
                count += 1;
            if (count == 2 || count == 3)
                return '*';
            else
                return '.';
        }


        if (field[x][y] == '.'){
            if (field[x][y - 1] == '*')
                count += 1;
            if (field[x][y + 1] == '*')
                count += 1;
            if (field[x - 1][y] == '*')
                count += 1;
            if (field[x + 1][y] == '*')
                count += 1;
            if (field[x + 1][y + 1] == '*')
                count += 1;
            if (field[x + 1][y - 1] == '*')
                count += 1;
            if (field[x - 1][y + 1] == '*')
                count += 1;
            if (field[x - 1][y - 1] == '*')
                count += 1;
            if (count == 3)
                return '*';
            else
                return '.';
        }
        return '.';
    }


    public void update(){
        char[][] nf = new char[25][25];
        for (int i = 1; i < 24; i++)
            for (int j = 1; j < 24; j++)
                nf[i][j] = get_new(i, j);
        this.field = nf;
    }
}
