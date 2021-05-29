import io.github.jakobwilms.ultralong.UltraLong;

public class UltraLongTest {
    public static void main(String[] args) {
        UltraLong ultraLong = new UltraLong(123433333L, 20);
        System.out.println(ultraLong);
        UltraLong other = new UltraLong(2222323455L, 19);
        System.out.println(other);
        ultraLong.add(other);
        System.out.println(ultraLong);
    }
}
