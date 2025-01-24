package stage.maps;

import java.util.List;

//GameMap Interface
public interface GameMap {
    public final int LEN = 17;  //map 크기
    public int[][] getMap();  //stage의 map(component 구성)
    public int getLeftTime();  //stage의 시간
    public List<Integer> getBallColors();  //공 개수, 각각의 색깔 지정
    public int getSpeed();  //공 속도
}
