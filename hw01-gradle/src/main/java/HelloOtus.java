import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.concurrent.ThreadLocalRandom;

public class HelloOtus {
    public static void main(String[] args) {
        var filter = BloomFilter.create(
                Funnels.integerFunnel(),
                400,
                0.01); //  false positive probability

        for(int i = 0; i < 100; i++) {
            filter.put(ThreadLocalRandom.current().nextInt(1, 50));
        }

        for(int i = 0; i < 3; i++) {
            var valueToCheck = ThreadLocalRandom.current().nextInt(1, 50);
            System.out.println(String.format("mightContain %d: %s", valueToCheck, filter.mightContain(valueToCheck)));
        }
    }
}
