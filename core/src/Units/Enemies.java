package Units;

/**
 * Created by Caroline on 14/04/2016.
 */
public class Enemies extends Units{

    protected int fileDropCount;
    protected int spawnNum;
    protected double healthDropProb;
    protected int points;

    public Enemies(){    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFileDropCount() {
        return fileDropCount;
    }

    public void setFileDropCount(int fileDropCount) {
        this.fileDropCount = fileDropCount;
    }

    public int getSpawnNum() {
        return spawnNum;
    }

    public void setSpawnNum(int spawnNum) {
        this.spawnNum = spawnNum;
    }

    public double getHealthDropProb() {
        return healthDropProb;
    }

    public void setHealthDropProb(float healthDropProb) {
        this.healthDropProb = healthDropProb;
    }
}
