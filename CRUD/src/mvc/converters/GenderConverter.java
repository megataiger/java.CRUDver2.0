package mvc.converters;
import objectForStrokeBase.Gender;
import org.springframework.core.convert.converter.Converter;


public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String genderValue) {
        return Gender.valueOf(genderValue);
    }
}