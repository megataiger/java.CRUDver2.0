package mvc.converters;
import objectForStrokeBase.Gender;
import org.springframework.core.convert.converter.Converter;


public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String genderValue) {

        Gender gender = null;
        for (Gender g : Gender.values()) {
            if (genderValue.equals(g.toString())) {
                gender = g;
            }
        }

        return gender;
    }
}