import io.github.jakobwilms.ultralong.LongNumber;
import io.github.jakobwilms.ultralong.UltraLong;

public class UltraLongTest {
    public static void main(String[] args) {
        LongNumber ultraLong = new UltraLong(5555L, 20);
        System.out.println(ultraLong);
        LongNumber other = new UltraLong(2226L, 20, false);
        System.out.println(other);
        ultraLong.add(other);
        System.out.println(ultraLong);
    }
}
