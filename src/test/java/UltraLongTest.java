import io.github.jakobwilms.ultralong.LongNumber;
import io.github.jakobwilms.ultralong.UltraLong;

public class UltraLongTest {
    public static void main(String[] args) {
        LongNumber ultraLong = new UltraLong(5545345365555455L, 100);
        System.out.println(ultraLong);
        LongNumber other = new UltraLong(244845738945345226L, 100);
        System.out.println(other);
        for (int i = 0; i < 50; i++) {
            ultraLong.add(other);
        }
        System.out.println(ultraLong);
    }
}
