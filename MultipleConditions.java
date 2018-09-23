import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static javafx.scene.input.KeyCode.T;

public class MultipleConditions {

    public static void main(String[] args) {
        MultipleConditions multipleConditions = new MultipleConditions();
        String result = multipleConditions.processTable("a", "b", null, null);
        System.out.println(result);
    }

    String processTable(String cid, String postcode, String[] ratingvalue, String ratingType) {
        final int hasCID = 1, hasPostcode = 2, hasRatingValue = 4;
//        Map<Integer, Supplier<String>> map = new HashMap<>();
        Map<Integer, Supplier<String>> map = new HashMap<>();
        map.put(hasCID, getDescription("table 5"));
        map.put(hasPostcode, getDescription("table 2"));
//        map.put(hasCID|hasPostcode|hasRatingValue, getDescription("table 6"));
//        map.put(hasCID, getDescription("table 3"));
//        map.put(hasCID|hasRatingValue, getDescription("table 4"));
//        map.put(hasPostcode, getDescription("table 2"));
//        map.put(hasPostcode|hasRatingValue, getDescription("table 1"));

//        return map.getOrDefault(
//                (cid!=null? hasCID: 0) | (postcode!=null? hasPostcode: 0)
//                        | (ratingvalue!=null? hasRatingValue: 0),
//                () -> "").get();

        return map.get(hasCID).get();
    }

    String processTable2(String cid, String postcode, String[] ratingvalue, String ratingType) throws Exception {
        final int hasCID = 1, hasPostcode = 2, hasRatingValue = 4;
        Map<String, CheckedFunction<String, String>> map = new HashMap<>();
        map.put("Jet Li", getResult("table 5"));

        return map.get(ratingType).apply("any file");
    }

    private Supplier<String> getDescription(String descryption) {
        return () -> descryption;
    }

    private CheckedFunction<String, String> getResult(String file) {
        return  f -> checkState(file).orElse(false) == true ? "OK"
                : (String) Optional.empty().orElseThrow(() -> new Exception(""));
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    private Optional<Boolean> checkState(String file) {
        try {
            File.createTempFile("", "");
        } catch (IOException e) {
            return Optional.of(true);
        }

        return Optional.of(true);
    }
}

