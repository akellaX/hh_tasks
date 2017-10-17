import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TropicalIsland {
    public TropicalIsland() {};

    static class element {
        public int i;
        public int j;
        public int value;

        public element(int _i, int _j, int _value) {
            i = _i;
            j = _j;
            value = _value;
        }

        public String toString() {
            return i + " " + j + " " + value;
        }

        public boolean equals(Object o) {
            if (!(o instanceof element))
                return false;
            element other = (element) o;
            return ((this.i == other.i) && (this.j == other.j));
        }

    }

    public element findmin(Integer[][] island, Boolean[][]check) {
        int h = island.length;
        int w = island[0].length;
        element min = new element(1, 1, island[1][1]);
        for (int i = 1; i < h - 1; i++)
            for (int j = 1; j < w - 1; j++) {
                if ((min.value > island[i][j]) && (check[i][j]==false))
                    min = new element(i, j, island[i][j]);
            }
        return min;
    }

    public Integer findmaxvalue(Integer[][] island) {
        int h = island.length;
        int w = island[0].length;
        element max = new element(0, 0, island[0][0]);
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                if (max.value < island[i][j])
                    max = new element(i, j, island[i][j]);

            }
        return max.value;
    }

    public void findarrmin(Integer[][] island, List<element> ans, element minel) {
        ans.add(minel);

        if (minel.i < island.length - 1) {
            if (island[minel.i + 1][minel.j] == minel.value) {
                element temp = new element(minel.i + 1, minel.j, island[minel.i + 1][minel.j]);
                if (ans.indexOf(temp) == -1)
                    findarrmin(island, ans, temp);

            }
        }
        if (minel.i > 0) {
            if (island[minel.i - 1][minel.j] == minel.value) {
                element temp = new element(minel.i - 1, minel.j, island[minel.i - 1][minel.j]);
                if (ans.indexOf(temp) == -1)
                    findarrmin(island, ans, temp);
            }
        }
        if (minel.j < island[0].length - 1) {

            if (island[minel.i][minel.j + 1] == minel.value) {
                element temp = new element(minel.i, minel.j + 1, island[minel.i][minel.j + 1]);
                if (ans.indexOf(temp) == -1)
                    findarrmin(island, ans, temp);
            }
        }
        if (minel.j > 0) {
            if (island[minel.i][minel.j - 1] == minel.value) {
                element temp = new element(minel.i, minel.j - 1, island[minel.i][minel.j - 1]);
                if (ans.indexOf(temp) == -1)
                    findarrmin(island, ans, temp);
            }
        }

    }

    public static boolean isshore(int i, int j, List<element> list,Integer[][]island,Boolean[][] check) {
        boolean flag = false;
        for (element x : list) {
            if ((x.i == 0) || (x.i == i - 1) || (x.j == 0) || (x.j == j - 1) || (hihgerthancoast(x,island)) ||
                    (check[x.i+1][x.j]==true) || (check[x.i-1][x.j]==true) ||(check[x.i][x.j+1]==true) ||(check[x.i][x.j-1]==true) ) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public Integer nextlevel(Integer[][] island, List<element> ans, Integer max) {
        Integer min = max;
        for (int i = 0; i < ans.size(); i++) {
            if ((island[ans.get(i).i + 1][ans.get(i).j] < min) && (island[ans.get(i).i + 1][ans.get(i).j] != ans.get(i).value))
                min = island[ans.get(i).i + 1][ans.get(i).j];
            if ((island[ans.get(i).i - 1][ans.get(i).j] < min) && (island[ans.get(i).i - 1][ans.get(i).j] != ans.get(i).value))
                min = island[ans.get(i).i - 1][ans.get(i).j];
            if ((island[ans.get(i).i][ans.get(i).j + 1] < min) && (island[ans.get(i).i][ans.get(i).j + 1] != ans.get(i).value))
                min = island[ans.get(i).i][ans.get(i).j + 1];
            if ((island[ans.get(i).i][ans.get(i).j - 1] < min) && (island[ans.get(i).i][ans.get(i).j - 1] != ans.get(i).value))
                min = island[ans.get(i).i][ans.get(i).j - 1];
        }
        return min;
    }

    public static boolean hihgerthancoast(element a,Integer[][] island)
    {
        int h=island.length;
        int w=island[0].length;
        boolean flag=false;
        if((a.i+1==h-1)&& (a.value>=island[h-1][a.j]))
            flag=true;
        if((a.i-1==0)&& (a.value>=island[0][a.j]))
            flag=true;
        if((a.j+1==w-1)&& (a.value>=island[a.i][w-1]))
            flag=true;
        if((a.j-1==0)&& (a.value>=island[a.i][0]))
            flag=true;
        return flag;
    }






    public int getWaterVolume(Integer[][] island) {
        int h=island.length;
        int w=island[0].length;
        Boolean [][] check= new Boolean[h][w];
        for(int i=0;i<h;i++)
            for(int j=0;j<w;j++)
                check[i][j]=false;
        int counter=0;
        List<element> ans;
        element min =findmin(island,check);
        int max=findmaxvalue(island);
        while(check[min.i][min.j]==false)
        {
                ans = new ArrayList<element>();
                findarrmin(island, ans, min);
                if (isshore(h, w, ans,island,check)) {
                    for (int i = 0; i < ans.size(); i++) {
                        check[ans.get(i).i][ans.get(i).j]=true;
                    }
                } else {
                    int nextlvl = nextlevel(island, ans, max);
                    for (int i = 0; i < ans.size(); i++) {
                        island[ans.get(i).i][ans.get(i).j] = nextlvl;
                        counter += nextlvl - ans.get(i).value;
                    }
                }

            min=findmin(island,check);

        }



        return counter;
    }




}

